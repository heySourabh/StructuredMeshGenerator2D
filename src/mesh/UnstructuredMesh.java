package mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class UnstructuredMesh {

    public final List<Element> elemList;
    public final List<Node> nodeList;
    public final Map<String, List<Face>> boundaries;

    public UnstructuredMesh(StructuredMesh structuredMesh) {
        final int NUM_XI_NODES = structuredMesh.nodes.length;
        final int NUM_ETA_NODES = structuredMesh.nodes[0].length;

        Node[][] nodes = new Node[NUM_XI_NODES][NUM_ETA_NODES];
        nodeList = new ArrayList<>(NUM_XI_NODES * NUM_ETA_NODES);

        for (int i = 0; i < NUM_XI_NODES; i++) {
            for (int j = 0; j < NUM_ETA_NODES; j++) {
                nodes[i][j] = structuredMesh.nodes[i][j];
                nodeList.add(nodes[i][j]);
            }
        }

        elemList = new ArrayList<>((NUM_XI_NODES - 1) * (NUM_ETA_NODES - 1));
        for (int i = 0; i < NUM_XI_NODES - 1; i++) {
            for (int j = 0; j < NUM_ETA_NODES - 1; j++) {
                Node node0 = nodes[i][j];
                Node node1 = nodes[i + 1][j];
                Node node2 = nodes[i + 1][j + 1];
                Node node3 = nodes[i][j + 1];

                // Added in the order as per VTK_QUAD
                Element elem = new Element(new Node[]{
                    node0, node1, node2, node3
                });

                elemList.add(elem);

                node0.belongsTo.add(elem);
                node1.belongsTo.add(elem);
                node2.belongsTo.add(elem);
                node3.belongsTo.add(elem);
            }
        }

        // Set up the boundary markers
        boundaries = structuredMesh.boundaries;
    }

    public UnstructuredMesh stitch(UnstructuredMesh other) {
        if (this == other) {
            return this;
        }

        // Check if any node in other mesh is at same position as a node in this mesh.
        // if yes then replaceNode the node from this mesh by other mesh node.
        Set<Node> bndryNodes = new HashSet<>();
        for (Node node : this.nodeList) {
            if (node.isOnBoundary()) {
                bndryNodes.add(node);
            }
        }
        Set<Node> otherBndryNodes = new HashSet<>();
        for (Node node : other.nodeList) {
            if (node.isOnBoundary()) {
                otherBndryNodes.add(node);
            }
        }

        for (Node nodeOther : otherBndryNodes) {
            for (Node node : bndryNodes) {
                if (node.equals(nodeOther)) {
                    for (Element elem : node.belongsTo) {
                        elem.replaceNode(node, nodeOther);
                    }
                }
            }
        }

        elemList.addAll(other.elemList); // Adding the two mesh cells together

        // Clear the old nodelist so that the combined list can be populated
        nodeList.clear();
        Set<Node> nodeSet = new HashSet<>();
        for (Element ele : elemList) {
            nodeSet.addAll(Arrays.asList(ele.nodes));
        }
        nodeList.addAll(nodeSet);

        // Clear the belongsTo and new belongsTo cells are populated
        for (Node node : nodeList) {
            node.belongsTo.clear();
        }
        for (Element elem : elemList) {
            for (Node node : elem.nodes) {
                node.belongsTo.add(elem);
            }
        }

        // remove internal boundary faces
        List<List<Face>> bndryMarkerFaces = boundaries.keySet().stream()
                .map(key -> boundaries.get(key))
                .collect(Collectors.toList());

        List<Node> otherBndryMarkerNodes = other.boundaries.keySet().stream()
                .flatMap(key -> other.boundaries.get(key).stream())
                .flatMap(face -> Stream.of(face.node1, face.node2))
                .collect(Collectors.toList());

        for (Node node : otherBndryMarkerNodes) { // Replace overlapping nodes from this boundary faces with other boundary nodes
            for (List<Face> bndryFaceList : bndryMarkerFaces) {
                for (Face face : bndryFaceList) {
                    if (face.node1.equals(node)) {
                        face.node1 = node;
                    }
                    if (face.node2.equals(node)) {
                        face.node2 = node;
                    }
                }
            }
        }

        Map<String, List<Face>> allBoundaries = combine(boundaries, other.boundaries);
        allBoundaries.keySet().stream()
                .forEach(marker -> {
                    allBoundaries.get(marker)
                            .removeIf(face -> !face.isOnBoundary());
                });
        List<String> emptyMarkers = allBoundaries.keySet().stream()
                .filter(key -> allBoundaries.get(key).isEmpty())
                .collect(Collectors.toList());

        emptyMarkers.stream()
                .forEach(emptyMarker -> allBoundaries.remove(emptyMarker));

        boundaries.clear();

        boundaries.putAll(allBoundaries);

        return this;
    }

    private Map<String, List<Face>> combine(Map<String, List<Face>> boundaries1, Map<String, List<Face>> boundaries2) {
        Map<String, List<Face>> newMap = new HashMap<>();
        boundaries1.keySet().stream().forEach((marker) -> {
            // Add all the lists from boundaries1
            newMap.put(marker, boundaries1.get(marker));
        });

        // While adding boundaries2, check if the marker exists,
        // if it does then append to the existing list, 
        // else add the new list to map
        boundaries2.keySet().stream().forEach((marker) -> {
            if (newMap.containsKey(marker)) {
                newMap.get(marker).addAll(boundaries2.get(marker));
            } else {
                newMap.put(marker, boundaries2.get(marker));
            }
        });

        return newMap;
    }
}
