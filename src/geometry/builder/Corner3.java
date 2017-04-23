package geometry.builder;

import java.util.List;
import java.util.Optional;
import main.Point;
import geometry.ParametricCurve;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Corner3 {

    private final Point point3;
    private final List<ParametricCurve> listCurves;

    Corner3(Point point3, List<ParametricCurve> listCurves) {
        this.point3 = point3;
        this.listCurves = listCurves;
    }

    public Corner4 curveToCorner4(Point point4) {
        return curveToCorner4(point4, Optional.empty(), Optional.empty());
    }

    public Corner4 curveToCorner4(Point point4, Optional<Angle> angleStart, Optional<Angle> angleEnd) {
        listCurves.add(new CubicCurve(point3, point4, angleStart, angleEnd));

        return new Corner4(point4, listCurves);
    }
}
