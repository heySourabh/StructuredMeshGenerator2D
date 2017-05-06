package main;

import mesh.TransfiniteInterpolator;
import geometry.Point;
import geometry.Geometry;
import geometry.SimpleQuadGeometry;
import io.Su2MeshWriter;
import java.io.IOException;
import mesh.MeshDefinition;
import mesh.StructuredMesh;
import mesh.UnstructuredMesh;

/**
 * Code for 2D structured mesh generation using Transfinite Interpolation
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class StructuredMeshGenerator2D {

    public static void main(String[] args) throws IOException {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0.5, 0);
        Point p3 = new Point(0.5, 0.5);
        Point p4 = new Point(1.5, 0.5);
        Point p5 = new Point(1.5, 1);
        Point p6 = new Point(0.5, 1);
        Point p7 = new Point(0, 1);
        Point p8 = new Point(0, 0.5);

        String markerInlet = "Inlet";
        String markerOutlet = "Outlet";
        String markerBottomWall = "Bottom Wall";
        String markerTopWall = "Top Wall";

        Geometry geom1 = new SimpleQuadGeometry(p1, p2, p3, p8);
        int numXiNodes1 = 30;
        int numEtaNodes1 = 30;
        MeshDefinition meshDefBlock1 = new MeshDefinition(geom1, numXiNodes1, numEtaNodes1, markerInlet, markerBottomWall, markerBottomWall, null);
        StructuredMesh meshBlock1 = new TransfiniteInterpolator(meshDefBlock1).interpolate();

        Geometry geom2 = new SimpleQuadGeometry(p8, p3, p6, p7);
        int numXiNodes2 = numXiNodes1;
        int numEtaNodes2 = 30;
        MeshDefinition meshDefBlock2 = new MeshDefinition(geom2, numXiNodes2, numEtaNodes2, markerInlet, null, null, markerTopWall);
        StructuredMesh meshBlock2 = new TransfiniteInterpolator(meshDefBlock2).interpolate();

        Geometry geom3 = new SimpleQuadGeometry(p3, p4, p5, p6);
        int numXiNodes3 = 60;
        int numEtaNodes3 = numEtaNodes2;
        MeshDefinition meshDefBlock3 = new MeshDefinition(geom3, numXiNodes3, numEtaNodes3, null, markerOutlet, markerBottomWall, markerTopWall);
        StructuredMesh meshBlock3 = new TransfiniteInterpolator(meshDefBlock3).interpolate();

        UnstructuredMesh combinedMesh = new UnstructuredMesh(meshBlock1)
                .stitch(new UnstructuredMesh(meshBlock3))
                .stitch(new UnstructuredMesh(meshBlock2));

        Su2MeshWriter.write(combinedMesh, "mesh");
    }
}
