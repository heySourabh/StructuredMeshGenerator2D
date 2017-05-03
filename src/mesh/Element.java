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

    public void replaceEquivalentNodeBy(Node newNode) {
        if (newNode == null) {
            throw new IllegalArgumentException("The newNode provided must not be mull.");
        }

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null && nodes[i].equals(newNode)) {
                newNode.belongsTo.addAll(nodes[i].belongsTo);
                nodes[i] = newNode;
            }
        }
    }
}
