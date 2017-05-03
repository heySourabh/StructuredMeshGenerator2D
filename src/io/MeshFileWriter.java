package io;

import geometry.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;
import mesh.Element;
import mesh.Node;
import mesh.UnstructuredMesh;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class MeshFileWriter {

    private MeshFileWriter() {
    }

    public static void write(Point[][] nodes, String fileName) throws IOException {
        if (!fileName.endsWith(".dat")) {
            fileName += ".dat";
        }
        System.out.println("Writing structured mesh file: " + fileName);
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
        System.out.println("Writing structured mesh file in vtk format: " + fileName);

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

    public static void writeVtkFormat(UnstructuredMesh mesh, String fileName) throws IOException {
        IntStream.range(0, mesh.nodeList.size())
                .forEach(i -> mesh.nodeList.get(i).index = i);

        if (!fileName.endsWith(".vtk")) {
            fileName += ".vtk";
        }
        System.out.print("Writing unstructured mesh file in vtk format: " + fileName + " ... ");
        try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(fileName),
                StandardCharsets.UTF_8)) {

            // Header
            fileWriter.write("# vtk DataFile Version 2.0" + "\n");

            // Info string max 256 characters allowed
            String title = "Block Mesh";
            fileWriter.write((title.length() > 255 ? title.substring(0, 255) : title) + "\n");

            // File type
            fileWriter.write("ASCII" + "\n");

            // Data type
            fileWriter.write("DATASET UNSTRUCTURED_GRID" + "\n");

            // Point data
            fileWriter.write("POINTS " + mesh.nodeList.size() + " double" + "\n");
            for (Node node : mesh.nodeList) {
                fileWriter.write(String.format("%-20f %-20f %-20f\n",
                        node.point.x, node.point.y, 0.0));
            }
            fileWriter.write("\n");

            // Cell connectivity
            final int NUM_CELLS = mesh.elemList.size();
            int totalConnectNodes = mesh.elemList.stream()
                    .mapToInt(cell -> cell.nodes.length)
                    .sum();
            fileWriter.write("CELLS " + NUM_CELLS + " " + (NUM_CELLS + totalConnectNodes) + "\n");
            for (Element cell : mesh.elemList) {
                fileWriter.write(cell.nodes.length + " ");

                for (Node node : cell.nodes) {
                    fileWriter.write(node.index + " ");
                }

                fileWriter.write("\n");
            }
            fileWriter.write("\n");

            // Cell Types
            fileWriter.write("CELL_TYPES " + NUM_CELLS + "\n");
            int VTK_QUAD = 9;
            for (Element cell : mesh.elemList) {
                fileWriter.write(VTK_QUAD + "\n");
            }
            fileWriter.write("\n");
        }
        System.out.println(" Done.");
    }
}
