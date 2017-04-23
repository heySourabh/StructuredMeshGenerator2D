package geometry.builder;

import java.util.List;
import java.util.Optional;
import main.Point;
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

    public Corner3 curveToCorner3(Point point3, Optional<Angle> angleStart, Optional<Angle> angleEnd) {
        listCurves.add(new CubicCurve(point2, point3, angleStart, angleEnd));
        
        return new Corner3(point3, listCurves);
    }
}
