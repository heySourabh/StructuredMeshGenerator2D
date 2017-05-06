package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import mesh.Node;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class MeshFileWriter {

    private MeshFileWriter() {
    }

    public static void write(Node[][] nodes, String fileName) throws IOException {
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
                    fileWriter.write(String.format("%-20f %-20f\n", nodes[i][j].point.x, nodes[i][j].point.y));
                }
            }
        }
    }
}
