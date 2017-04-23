package geometry.builder;

import java.util.Optional;
import main.Parameter;
import main.Point;
import util.Range;
import geometry.ParametricCurve;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class CubicCurve implements ParametricCurve {

    private final Point p1, p2;
    private final Optional<Angle> angleStart, angleEnd;

    public CubicCurve(Point p1, Point p2, Optional<Angle> angleStart, Optional<Angle> angleEnd) {
        this.p1 = p1;
        this.p2 = p2;
        this.angleStart = angleStart;
        this.angleEnd = angleEnd;
    }

    @Override
    public Point getPoint(Parameter parameter) {
        return new Point(
                Range.map(parameter.value, new Range(0, 1), new Range(p1.x, p2.x)),
                Range.map(parameter.value, new Range(0, 1), new Range(p1.y, p2.y))
        );
    }
}
