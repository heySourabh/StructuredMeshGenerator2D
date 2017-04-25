package main;

import geometry.Geometry;
import geometry.Point;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class TransfiniteInterpolator {

    private TransfiniteInterpolator() {
    }

    public static Point interpolate(Geometry geom, Parameter xi, Parameter eta) {
        return geom.xi_0(eta).mult((1 - xi.value))
                .add(geom.xi_1(eta).mult(xi.value))
                .add(geom.eta_0(xi).mult(1 - eta.value))
                .add(geom.eta_1(xi).mult(eta.value))
                .sub(geom.eta_0(new Parameter(0)).mult((1 - xi.value) * (1 - eta.value)))
                .sub(geom.eta_1(new Parameter(0)).mult(eta.value * (1 - xi.value)))
                .sub(geom.eta_0(new Parameter(1)).mult((1 - eta.value) * xi.value))
                .sub(geom.eta_1(new Parameter(1)).mult(xi.value * eta.value));
    }
}
