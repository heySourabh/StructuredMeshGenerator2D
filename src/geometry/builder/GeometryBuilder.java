package geometry.builder;

import geometry.Point;

/**
 * Convenience class for building a list of <code>ParametricCurve</code>s for
 * creating <code>Geometry</code> object.
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
