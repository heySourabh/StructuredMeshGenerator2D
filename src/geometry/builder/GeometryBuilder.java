package geometry.builder;

import geometry.Point;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class GeometryBuilder {

    private GeometryBuilder() {
    }

    public static Corner1 beginFrom(Point point1) {
        Corner1 corner1 = new Corner1(point1);
        return corner1;
    }
}
