package geometry.builder;

import main.Point;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class GeometryBuilder {

    private GeometryBuilder() {
    }

    public static Corner1 begin(Point point1) {
        Corner1 corner1 = new Corner1(point1);
        return corner1;
    }
}
