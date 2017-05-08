package io;

import geometry.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import mesh.Element;
import mesh.Face;
import mesh.Node;
import mesh.UnstructuredMesh;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Su2MeshWriter {

    public static void write(UnstructuredMesh mesh, String fileNamePrefix) throws IOException {
        IntStream.range(0, mesh.nodeList.size())
                .forEach(i -> mesh.nodeList.get(i).index = i);

        final int NUM_ELEMS = mesh.elemList.size();
        final int NUM_NODES = mesh.nodeList.size();
        final int NUM_BNDRS = mesh.boundaries.size();
        final int VTK_QUAD = 9;

        System.out.print("Writing unstructured mesh file in su2 format: " + fileNamePrefix + ".su2" + " ... ");
        try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(fileNamePrefix + ".su2"),
                StandardCharsets.UTF_8)) {
            writeln(fileWriter, "NDIME= 2");

            // Write elements
            writeln(fileWriter, String.format("NELEM= %d", NUM_ELEMS));
            for (int i = 0; i < NUM_ELEMS; i++) {
                Element elem = mesh.elemList.get(i);
                writeln(fileWriter, String.format("%-10d %-10d %-10d %-10d %-10d %d", VTK_QUAD,
                        elem.nodes[0].index, elem.nodes[1].index, elem.nodes[2].index, elem.nodes[3].index, i));
            }

            // Write nodes
            writeln(fileWriter, String.format("NPOIN= %d", NUM_NODES));
            for (int i = 0; i < NUM_NODES; i++) {
                Node node = mesh.nodeList.get(i);
                Point p = node.point;
                writeln(fileWriter, String.format("%-20f %-20f %d", p.x, p.y, node.index));
            }

            // Write boundaries
            final int VTK_LINE = 3;
            writeln(fileWriter, String.format("NMARK= %d", NUM_BNDRS));
            Set<String> markers = mesh.boundaries.keySet();
            for (String marker : markers) {
                writeln(fileWriter, String.format("MARKER_TAG= %s", marker));

                List<Face> bndryFaces = mesh.boundaries.get(marker);
                int numBndryFaces = bndryFaces.size();

                writeln(fileWriter, String.format("MARKER_ELEMS= %d", numBndryFaces));

                for (int i = 0; i < numBndryFaces; i++) {
                    Node node1 = bndryFaces.get(i).node1;
                    Node node2 = bndryFaces.get(i).node2;
                    writeln(fileWriter, String.format("%-10d %-10d %-10d", VTK_LINE, node1.index, node2.index));
                }
            }
        }

        System.out.println(" Done.");
    }

    private static void writeln(OutputStreamWriter fileWriter, String str) throws IOException {
        fileWriter.write(str + "\n");
    }
}
