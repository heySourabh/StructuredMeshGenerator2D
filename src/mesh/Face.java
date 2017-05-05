package mesh;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Face {

    public Node node1, node2;

    public Face(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public boolean isOnBoundary() {
        return node1.isOnBoundary() && node2.isOnBoundary();
    }
}
