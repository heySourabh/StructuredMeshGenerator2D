package main;

import util.Range;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class SimpleQuadGeometry implements Geometry {

    private final Point p1, p2, p3, p4;
    private final Range parameterRange;

    /**
     * Points p1, p2, p3 and p4 must be in a clockwise or anti-clockwise order.
     *
     * @param p1 Corner
     * @param p2 Corner
     * @param p3 Corner
     * @param p4 Corner
     */
    public SimpleQuadGeometry(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;

        parameterRange = new Range(0, 1);
    }

    @Override
    public Point xi_0(Parameter eta) {
        return new Point(
                Range.map(eta.parameter, parameterRange, new Range(p1.x, p4.x)),
                Range.map(eta.parameter, parameterRange, new Range(p1.y, p4.y))
        );
    }

    @Override
    public Point xi_1(Parameter eta) {
        return new Point(
                Range.map(eta.parameter, parameterRange, new Range(p2.x, p3.x)),
                Range.map(eta.parameter, parameterRange, new Range(p2.y, p3.y))
        );
    }

    @Override
    public Point eta_0(Parameter xi) {
        return new Point(
                Range.map(xi.parameter, parameterRange, new Range(p1.x, p2.x)),
                Range.map(xi.parameter, parameterRange, new Range(p1.y, p2.y))
        );
    }

    @Override
    public Point eta_1(Parameter xi) {
        return new Point(
                Range.map(xi.parameter, parameterRange, new Range(p4.x, p3.x)),
                Range.map(xi.parameter, parameterRange, new Range(p4.y, p3.y))
        );
    }
}
