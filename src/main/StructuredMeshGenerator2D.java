package main;

import io.MeshFileWriter;
import geometry.Point;
import geometry.SimpleQuadGeometry;
import geometry.Geometry;
import geometry.Angle;
import geometry.GeometryFromFile;
import geometry.builder.GeometryBuilder;
import java.io.File;
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
//
//        Geometry geom = GeometryBuilder
//                .beginFrom(new Point(1, 1.25))
//                .curveToCorner2(Angle.inDegrees(60), new Point(5, 0.25), Angle.inDegrees(180))
//                .curveToCorner3(new Point(5, 3.75))
//                .curveToCorner4(Angle.inDegrees(180), new Point(1, 2.75), Angle.inDegrees(-60))
//                .close(Optional.of(Angle.inDegrees(260)), Optional.of(Angle.inDegrees(100)));

        Geometry geom = new GeometryFromFile(new File("geometry.dat"));

        int numXiNodes = 100;
        int numEtaNodes = 40;

        double dXi = 1.0 / (numXiNodes - 1);
        double dEta = 1.0 / (numEtaNodes - 1);

        Point[][] points = new Point[numXiNodes][numEtaNodes];
        for (int i = 0; i < numXiNodes; i++) {
            Parameter xi = new Parameter(i * dXi);
            for (int j = 0; j < numEtaNodes; j++) {
                Parameter eta = new Parameter(j * dEta);
                points[i][j] = TransfiniteInterpolator.interpolate(geom, xi, eta);
            }
        }

        MeshFileWriter fileWriter = new MeshFileWriter(points, "mesh.dat");
        fileWriter.write();
    }
}
