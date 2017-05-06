package mesh;

import geometry.Geometry;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class MeshDefinition {

    public static final String DEFAULT_MARKER = "Unnamed";

    public final Geometry geometry;
    public final int numXiNodes;
    public final int numEtaNodes;

    public final String xi_0_marker;
    public final String xi_1_marker;
    public final String eta_0_marker;
    public final String eta_1_marker;

    public MeshDefinition(Geometry geometry, int numXiNodes, int numEtaNodes,
            String xi_0_marker, String xi_1_marker,
            String eta_0_marker, String eta_1_marker) {
        this.geometry = geometry;
        this.numXiNodes = numXiNodes;
        this.numEtaNodes = numEtaNodes;

        this.xi_0_marker = markerName(xi_0_marker);
        this.xi_1_marker = markerName(xi_1_marker);
        this.eta_0_marker = markerName(eta_0_marker);
        this.eta_1_marker = markerName(eta_1_marker);
    }

    private static String markerName(String name) {
        String modifiedName = (name == null || name.isEmpty()) ? DEFAULT_MARKER : name;
        modifiedName = modifiedName.replaceAll(" ", "");

        return modifiedName;
    }
}
