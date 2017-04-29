package io;

import geometry.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class MeshFileWriter {

    private MeshFileWriter() {
    }

    public static void write(Point[][] nodes, String fileName) throws IOException {
        if(!fileName.endsWith(".dat")) {
            fileName += ".dat";
        }
        System.out.println("Writing mesh file: " + fileName);
        try (OutputStreamWriter fileWriter
                = new OutputStreamWriter(new FileOutputStream(fileName),
                        StandardCharsets.UTF_8)) {
            int numXiNodes = nodes.length;
            int numEtaNodes = nodes[0].length;

            fileWriter.write(String.format("dimension=%d\n", 2));
            fileWriter.write(String.format("xi=%d\n", numXiNodes));
            fileWriter.write(String.format("eta=%d\n", numEtaNodes));
            fileWriter.write(String.format("%-20s %-20s\n", "x", "y"));
            for (int i = 0; i < numXiNodes; i++) {
                for (int j = 0; j < numEtaNodes; j++) {
                    fileWriter.write(String.format("%-20f %-20f\n", nodes[i][j].x, nodes[i][j].y));
                }
            }
        }
    }

    public static void writeVtkFormat(Point[][] nodes, String fileName) throws IOException {
        if (!fileName.endsWith(".vtk")) {
            fileName += ".vtk";
        }
        System.out.println("Writing mesh file in vtk format: " + fileName);

        try (OutputStreamWriter fileWriter
                = new OutputStreamWriter(new FileOutputStream(fileName),
                        StandardCharsets.UTF_8)) {
            int numXiNodes = nodes.length;
            int numEtaNodes = nodes[0].length;
            int numZetaNodes = 1;

            int totalNodes = numXiNodes * numEtaNodes * numZetaNodes;

            fileWriter.write("# vtk DataFile Version 2.0" + "\n");
            fileWriter.write("Structured mesh file." + "\n");
            fileWriter.write("ASCII" + "\n");

            fileWriter.write("DATASET " + "STRUCTURED_GRID" + "\n");
            fileWriter.write(String.format("DIMENSIONS %d %d %d", numXiNodes, numEtaNodes, numZetaNodes) + "\n");

            fileWriter.write(String.format("POINTS %d %s", totalNodes, "double") + "\n");

            for (int k = 0; k < numZetaNodes; k++) {
                for (int j = 0; j < numEtaNodes; j++) {
                    for (int i = 0; i < numXiNodes; i++) {
                        fileWriter.write(String.format("%-20f %-20f %-20f", nodes[i][j].x, nodes[i][j].y, 0.0) + "\n");
                    }
                }
            }
        }
    }
}
