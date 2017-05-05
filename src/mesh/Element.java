package mesh;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Element {

    public final Node[] nodes;

    public Element(Node[] nodes) {
        this.nodes = nodes;
    }

    public void replaceNode(Node oldNode, Node newNode) {
        if (newNode == null || oldNode == null) {
            throw new IllegalArgumentException("The newNode/oldNode provided must not be null.");
        }

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == oldNode) {
                nodes[i] = newNode;
            }
        }
    }
}
