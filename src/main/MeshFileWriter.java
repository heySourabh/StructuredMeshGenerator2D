package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
class MeshFileWriter {

    private final File meshFile;
    private final Point[][] nodes;

    MeshFileWriter(Point[][] nodes, String fileName) {
        this.nodes = nodes;
        this.meshFile = new File(fileName);
    }

    void write() throws IOException {
        try (FileWriter fileWriter = new FileWriter(meshFile)) {
            int numXiNodes = nodes.length;
            int numEtaNodes = nodes[0].length;

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
}
