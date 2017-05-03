package mesh;

import geometry.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Node {

    private static final double TOLERANCE = 1e-8;

    public final Point point;
    public final List<Element> belongsTo;
    public int index = -1;

    public Node(Point point) {
        this.point = point;
        this.belongsTo = new ArrayList<>(4);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!(obj instanceof Node)) {
            return false;
        } else {
            Node other = (Node) obj;
            return overlapping(this.point, other.point);
        }
    }

    private boolean overlapping(Point p1, Point p2) {
        return p1.dist(p2) < TOLERANCE;
    }
}
