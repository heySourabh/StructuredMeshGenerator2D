package geometry.builder;

import geometry.ParametricCurvesGeometry;
import geometry.Angle;
import geometry.Geometry;
import java.util.List;
import java.util.Optional;
import main.Parameter;
import geometry.Point;
import geometry.ParametricCurve;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Corner4 {

    private final Point point4;
    private final List<ParametricCurve> listCurves;

    Corner4(Point point4, List<ParametricCurve> listCurves) {
        this.point4 = point4;
        this.listCurves = listCurves;
    }

    public Geometry close() {
        return close(Optional.empty(), Optional.empty());
    }

    public Geometry close(Optional<Angle> startAngle, Optional<Angle> endAngle) {
        Point point1 = listCurves.get(0).getPoint(new Parameter(0));

        listCurves.add(new CubicCurve(point4, point1, startAngle, endAngle));

        return new ParametricCurvesGeometry(listCurves);
    }
}
