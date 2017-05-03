package mesh;

import geometry.Geometry;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class MeshDefinition {

    public final Geometry geometry;
    public final int numXiNodes;
    public final int numEtaNodes;

    public MeshDefinition(Geometry geometry, int numXiNodes, int numEtaNodes) {
        this.geometry = geometry;
        this.numXiNodes = numXiNodes;
        this.numEtaNodes = numEtaNodes;
    }
}
