package geometry;

import java.util.List;
import main.Parameter;
import main.Point;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class ParametricCurvesGeometry implements Geometry {

    private final List<ParametricCurve> listCurves;

    /**
     * Geometry made up of a list of parametric curves.
     *
     * @param listCurves The curves provided must be in clockwise /
     * anti-clockwise direction
     */
    public ParametricCurvesGeometry(List<ParametricCurve> listCurves) {
        if (listCurves.size() != 4) {
            throw new IllegalArgumentException("4 cuves required to build the geometry.");
        }
        this.listCurves = listCurves;
    }

    @Override
    public Point xi_0(Parameter eta) {
        // index 3, curve reversed
        ParametricCurve curve = listCurves.get(3).reverse();
        return curve.getPoint(eta);
    }

    @Override
    public Point xi_1(Parameter eta) {
        // index 1
        ParametricCurve curve = listCurves.get(1);
        return curve.getPoint(eta);
    }

    @Override
    public Point eta_0(Parameter xi) {
        // index 0
        ParametricCurve curve = listCurves.get(0);
        return curve.getPoint(xi);
    }

    @Override
    public Point eta_1(Parameter xi) {
        // index 2, curve reversed
        ParametricCurve curve = listCurves.get(2).reverse();
        return curve.getPoint(xi);
    }
}
