package mesh;

import geometry.Geometry;
import geometry.Point;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class TransfiniteInterpolator {

    private final MeshDefinition meshDef;

    public TransfiniteInterpolator(MeshDefinition meshDef) {
        this.meshDef = meshDef;
    }

    public Point[][] interpolate() {
        int numXiNodes = meshDef.numXiNodes;
        int numEtaNodes = meshDef.numEtaNodes;
        Geometry geom = meshDef.geometry;

        double dXi = 1.0 / (numXiNodes - 1);
        double dEta = 1.0 / (numEtaNodes - 1);

        Point[][] points = new Point[numXiNodes][numEtaNodes];
        for (int i = 0; i < numXiNodes; i++) {
            Parameter xi = new Parameter(i * dXi);
            for (int j = 0; j < numEtaNodes; j++) {
                Parameter eta = new Parameter(j * dEta);
                points[i][j] = TransfiniteInterpolator.this.interpolate(geom, xi, eta);
            }
        }

        return points;
    }

    private static Point interpolate(Geometry geom, Parameter xi, Parameter eta) {
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
