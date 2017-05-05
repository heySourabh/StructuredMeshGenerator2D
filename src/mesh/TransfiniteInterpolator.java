package mesh;

import geometry.Geometry;
import geometry.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class TransfiniteInterpolator {

    private final MeshDefinition meshDef;

    public TransfiniteInterpolator(MeshDefinition meshDef) {
        this.meshDef = meshDef;
    }

    public StructuredMesh interpolate() {
        int numXiNodes = meshDef.numXiNodes;
        int numEtaNodes = meshDef.numEtaNodes;
        Geometry geom = meshDef.geometry;

        double dXi = 1.0 / (numXiNodes - 1);
        double dEta = 1.0 / (numEtaNodes - 1);

        Node[][] nodes = new Node[numXiNodes][numEtaNodes];
        for (int i = 0; i < numXiNodes; i++) {
            Parameter xi = new Parameter(i * dXi);
            for (int j = 0; j < numEtaNodes; j++) {
                Parameter eta = new Parameter(j * dEta);
                Point point = TransfiniteInterpolator.interpolate(geom, xi, eta);
                nodes[i][j] = new Node(point);
            }
        }

        List<Face> xi_0_faces = new ArrayList<>(numEtaNodes - 1);
        List<Face> xi_1_faces = new ArrayList<>(numEtaNodes - 1);
        List<Face> eta_0_faces = new ArrayList<>(numXiNodes - 1);
        List<Face> eta_1_faces = new ArrayList<>(numXiNodes - 1);

        for (int i = 0; i < numXiNodes - 1; i++) {
            int j = 0;
            Node node1 = nodes[i][j];
            Node node2 = nodes[i + 1][j];
            Face eta_0_face = new Face(node1, node2);
            eta_0_faces.add(eta_0_face);

            j = numEtaNodes - 1;
            node1 = nodes[i][j];
            node2 = nodes[i + 1][j];
            Face eta_1_face = new Face(node1, node2);
            eta_1_faces.add(eta_1_face);
        }

        for (int j = 0; j < numEtaNodes - 1; j++) {
            int i = 0;
            Node node1 = nodes[i][j];
            Node node2 = nodes[i][j + 1];
            Face xi_0_face = new Face(node1, node2);
            xi_0_faces.add(xi_0_face);

            i = numXiNodes - 1;
            node1 = nodes[i][j];
            node2 = nodes[i][j + 1];
            Face xi_1_face = new Face(node1, node2);
            xi_1_faces.add(xi_1_face);
        }

        Map<String, List<Face>> boundaries = new HashMap<>();
        addBoundaryfaces(boundaries, meshDef.xi_0_marker, xi_0_faces);
        addBoundaryfaces(boundaries, meshDef.xi_1_marker, xi_1_faces);
        addBoundaryfaces(boundaries, meshDef.eta_0_marker, eta_0_faces);
        addBoundaryfaces(boundaries, meshDef.eta_1_marker, eta_1_faces);

        return new StructuredMesh(nodes, boundaries);
    }

    private void addBoundaryfaces(Map<String, List<Face>> boundaries, String marker, List<Face> faces) {
        if (boundaries.containsKey(marker)) {
            boundaries.get(marker).addAll(faces);
        } else {
            boundaries.put(marker, faces);
        }
    }

    private static Point interpolate(Geometry geom, Parameter xi, Parameter eta) {
        return geom.xi_0(eta).mult((1 - xi.value))
                .add(geom.xi_1(eta).mult(xi.value))
                .add(geom.eta_0(xi).mult(1 - eta.value))
                .add(geom.eta_1(xi).mult(eta.value))
                .sub(geom.eta_0(new Parameter(0)).mult((1 - xi.value) * (1 - eta.value)))
                .sub(geom.eta_1(new Parameter(0)).mult(eta.value * (1 - xi.value)))
                .sub(geom.eta_0(new Parameter(1)).mult((1 - eta.value) * xi.value))
                .sub(geom.eta_1(new Parameter(1)).mult(xi.value * eta.value));
    }
}
