package mesh;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class StructuredMesh {

    public final Node[][] nodes;
    public final Map<String, List<Face>> boundaries;

    public StructuredMesh(Node[][] nodes, Map<String, List<Face>> boundaries) {
        this.nodes = nodes;
        this.boundaries = boundaries;
    }
}
