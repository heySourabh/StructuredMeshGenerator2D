package mesh;

import geometry.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class UnstructuredMesh {

    public final List<Element> elemList;
    public final List<Node> nodeList;

    public UnstructuredMesh(Point[][] structuredPoints) {
        final int NUM_XI_NODES = structuredPoints.length;
        final int NUM_ETA_NODES = structuredPoints[0].length;

        Node[][] nodes = new Node[NUM_XI_NODES][NUM_ETA_NODES];
        nodeList = new ArrayList<>(NUM_XI_NODES * NUM_ETA_NODES);

        for (int i = 0; i < NUM_XI_NODES; i++) {
            for (int j = 0; j < NUM_ETA_NODES; j++) {
                Node newNode = new Node(structuredPoints[i][j]);
                nodes[i][j] = newNode;
                nodeList.add(newNode);
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

    }

    public UnstructuredMesh stitch(UnstructuredMesh other) {
        if (this == other) {
            return this;
        }

        // Check if any node in other mesh is at same position as a node in this mesh.
        // if yes then replaceEquivalentNodeBy the node from this mesh by other mesh node.
        // Find boundary elements of this cell
        Set<Element> bndryElems = new HashSet<>();
        for (Node node : this.nodeList) {
            if (node.belongsTo.size() < 4) {
                bndryElems.addAll(node.belongsTo);
            }
        }
        Set<Node> otherBndryNodes = new HashSet<>();
        for (Node node : other.nodeList) {
            if (node.belongsTo.size() < 4) {
                otherBndryNodes.add(node);
            }
        }

        for (Node node : otherBndryNodes) {
            for (Element elem : bndryElems) {
                elem.replaceEquivalentNodeBy(node);
            }
        }

        for (Element ele : bndryElems) {
            for (Node node : ele.nodes) {
                List<Element> belongsTo = node.belongsTo.stream()
                        .distinct().collect(Collectors.toList());
                node.belongsTo.clear();
                node.belongsTo.addAll(belongsTo);
            }
        }

        elemList.addAll(other.elemList);
        nodeList.clear();
        Set<Node> nodeSet = new HashSet<>();
        for (Element ele : elemList) {
            nodeSet.addAll(Arrays.asList(ele.nodes));
        }
        nodeList.addAll(nodeSet);

        return this;
    }
}
