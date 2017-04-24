package main;

import io.MeshFileWriter;
import geometry.Point;
import geometry.SimpleQuadGeometry;
import geometry.Geometry;
import geometry.Angle;
import geometry.builder.GeometryBuilder;
import java.io.IOException;
import java.util.Optional;

/**
 * Code for 2D structured mesh generation using Transfinite Interpolation
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class StructuredMeshGenerator2D {

    public static void main(String[] args) throws IOException {
//        Geometry geom = new SimpleQuadGeometry(new Point(1, 0), new Point(5, 1),
//                new Point(4, 4), new Point(0.5, 0.5));
        Geometry geom = GeometryBuilder
                .beginFrom(new Point(1, 1.25))
                .curveToCorner2(Angle.inDegrees(60), new Point(5, 0.25), Angle.inDegrees(180))
                .curveToCorner3(new Point(5, 3.75))
                .curveToCorner4(Angle.inDegrees(180), new Point(1, 2.75), Angle.inDegrees(-60))
                .close(Optional.of(Angle.inDegrees(260)), Optional.of(Angle.inDegrees(100)));

        int numXiNodes = 100;
        int numEtaNodes = 40;

        double dXi = 1.0 / (numXiNodes - 1);
        double dEta = 1.0 / (numEtaNodes - 1);

        Point[][] points = new Point[numXiNodes][numEtaNodes];
        for (int i = 0; i < numXiNodes; i++) {
            double xi = i * dXi;
            for (int j = 0; j < numEtaNodes; j++) {
                double eta = j * dEta;
                points[i][j] = geom.xi_0(new Parameter(eta)).mult((1 - xi))
                        .add(geom.xi_1(new Parameter(eta)).mult(xi))
                        .add(geom.eta_0(new Parameter(xi)).mult(1 - eta))
                        .add(geom.eta_1(new Parameter(xi)).mult(eta))
                        .sub(geom.eta_0(new Parameter(0)).mult((1 - xi) * (1 - eta)))
                        .sub(geom.eta_1(new Parameter(0)).mult(eta * (1 - xi)))
                        .sub(geom.eta_0(new Parameter(1)).mult((1 - eta) * xi))
                        .sub(geom.eta_1(new Parameter(1)).mult(xi * eta));
            }
        }

        MeshFileWriter fileWriter = new MeshFileWriter(points, "mesh.dat");
        fileWriter.write();
    }
}
