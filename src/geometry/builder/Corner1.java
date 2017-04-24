package geometry.builder;

import geometry.Angle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import geometry.Point;
import geometry.ParametricCurve;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Corner1 {

    private final Point point1;

    Corner1(Point point1) {
        this.point1 = point1;
    }

    public Corner2 curveToCorner2(Point point2) {
        return curveToCorner2(point2, Optional.empty(), Optional.empty());
    }

    public Corner2 curveToCorner2(Angle startAngle, Point point2) {
        return curveToCorner2(point2, Optional.of(startAngle), Optional.empty());
    }

    public Corner2 curveToCorner2(Point point2, Angle endAngle) {
        return curveToCorner2(point2, Optional.empty(), Optional.of(endAngle));
    }

    public Corner2 curveToCorner2(Angle startAngle, Point point2, Angle endAngle) {
        return curveToCorner2(point2, Optional.of(startAngle), Optional.of(endAngle));
    }

    private Corner2 curveToCorner2(Point point2, Optional<Angle> startAngle, Optional<Angle> endAngle) {
        List<ParametricCurve> listCurves = new ArrayList<>();

        listCurves.add(new CubicCurve(point1, point2, startAngle, endAngle));

        return new Corner2(point2, listCurves);
    }
}
