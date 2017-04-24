package geometry.builder;

import geometry.Angle;
import java.util.List;
import java.util.Optional;
import geometry.Point;
import geometry.ParametricCurve;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Corner2 {

    private final Point point2;
    private final List<ParametricCurve> listCurves;

    Corner2(Point point2, List<ParametricCurve> listCurves) {
        this.listCurves = listCurves;
        this.point2 = point2;
    }

    public Corner3 curveToCorner3(Point point3) {
        return curveToCorner3(point3, Optional.empty(), Optional.empty());
    }

    public Corner3 curveToCorner3(Angle startAngle, Point point3) {
        return curveToCorner3(point3, Optional.of(startAngle), Optional.empty());
    }

    public Corner3 curveToCorner3(Point point3, Angle endAngle) {
        return curveToCorner3(point3, Optional.empty(), Optional.of(endAngle));
    }

    public Corner3 curveToCorner3(Angle startAngle, Point point3, Angle endAngle) {
        return curveToCorner3(point3, Optional.of(startAngle), Optional.of(endAngle));
    }

    private Corner3 curveToCorner3(Point point3, Optional<Angle> startAngle, Optional<Angle> endAngle) {
        listCurves.add(new CubicCurve(point2, point3, startAngle, endAngle));

        return new Corner3(point3, listCurves);
    }
}
