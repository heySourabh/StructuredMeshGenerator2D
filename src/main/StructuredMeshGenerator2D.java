package main;

import mesh.TransfiniteInterpolator;
import io.MeshFileWriter;
import geometry.Point;
import geometry.Geometry;
import geometry.GeometryFromFile;
import java.io.File;
import java.io.IOException;
import mesh.MeshDefinition;
import mesh.UnstructuredMesh;

/**
 * Code for 2D structured mesh generation using Transfinite Interpolation
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class StructuredMeshGenerator2D {

    public static void main(String[] args) throws IOException {
        Geometry geom1 = new GeometryFromFile(new File("./support/geometry1.dat"));
        int numXiNodes1 = 50;
        int numEtaNodes1 = 40;
        MeshDefinition block1 = new MeshDefinition(geom1, numXiNodes1, numEtaNodes1);
        Point[][] pointsBlock1 = new TransfiniteInterpolator(block1).interpolate();

        Geometry geom2 = new GeometryFromFile(new File("./support/geometry2.dat"));
        int numXiNodes2 = 30;
        int numEtaNodes2 = numEtaNodes1;
        MeshDefinition block2 = new MeshDefinition(geom2, numXiNodes2, numEtaNodes2);
        Point[][] pointsBlock2 = new TransfiniteInterpolator(block2).interpolate();

        Geometry geom3 = new GeometryFromFile(new File("./support/geometry3.dat"));
        int numXiNodes3 = 60;
        int numEtaNodes3 = numEtaNodes2;
        MeshDefinition block3 = new MeshDefinition(geom3, numXiNodes3, numEtaNodes3);
        Point[][] pointsBlock3 = new TransfiniteInterpolator(block3).interpolate();
        
        Geometry geom4 = new GeometryFromFile(new File("./support/geometry4.dat"));
        int numXiNodes4 = numXiNodes1;
        int numEtaNodes4 = 45;
        MeshDefinition block4 = new MeshDefinition(geom4, numXiNodes4, numEtaNodes4);
        Point[][] pointsBlock4 = new TransfiniteInterpolator(block4).interpolate();
        
        Geometry geom5 = new GeometryFromFile(new File("./support/geometry5.dat"));
        int numXiNodes5 = numEtaNodes4;
        int numEtaNodes5 = 35;
        MeshDefinition block5 = new MeshDefinition(geom5, numXiNodes5, numEtaNodes5);
        Point[][] pointsBlock5 = new TransfiniteInterpolator(block5).interpolate();
        
        Geometry geom6 = new GeometryFromFile(new File("./support/geometry6.dat"));
        int numXiNodes6 = numXiNodes2;
        int numEtaNodes6 = numEtaNodes5;
        MeshDefinition block6 = new MeshDefinition(geom6, numXiNodes6, numEtaNodes6);
        Point[][] pointsBlock6 = new TransfiniteInterpolator(block6).interpolate();
        
        Geometry geom7 = new GeometryFromFile(new File("./support/geometry7.dat"));
        int numXiNodes7 = 45;
        int numEtaNodes7 = numEtaNodes6;
        MeshDefinition block7 = new MeshDefinition(geom7, numXiNodes7, numEtaNodes7);
        Point[][] pointsBlock7 = new TransfiniteInterpolator(block7).interpolate();
        
        Geometry geom8 = new GeometryFromFile(new File("./support/geometry8.dat"));
        int numXiNodes8 = numXiNodes3;
        int numEtaNodes8 = numXiNodes7;
        MeshDefinition block8 = new MeshDefinition(geom8, numXiNodes8, numEtaNodes8);
        Point[][] pointsBlock8 = new TransfiniteInterpolator(block8).interpolate();

        UnstructuredMesh combinedMesh = new UnstructuredMesh(pointsBlock1)
                .stitch(new UnstructuredMesh(pointsBlock2))
                .stitch(new UnstructuredMesh(pointsBlock3))
                .stitch(new UnstructuredMesh(pointsBlock4))
                .stitch(new UnstructuredMesh(pointsBlock5))
                .stitch(new UnstructuredMesh(pointsBlock6))
                .stitch(new UnstructuredMesh(pointsBlock7))
                .stitch(new UnstructuredMesh(pointsBlock8));

        MeshFileWriter.writeVtkFormat(combinedMesh, "mesh");
    }
}
