package io;

import geometry.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class GeometryFileReader {

    public enum SideLabel {
        xi_0, xi_1, eta_0, eta_1
    }

    private GeometryFileReader() {
    }

    public static Point[] readPoints(File file, SideLabel label) throws FileNotFoundException {
        Point[] points;
        try (Scanner fileScanner = new Scanner(file, "UTF-8")) {
            int numPoints = 0;
            String nextValidLine;
            while ((nextValidLine = getNextValidLine(fileScanner)) != null) {
                if (nextValidLine.startsWith(label.toString())) {
                    String[] tokens = nextValidLine.split("[\\s:=]+");
                    try {
                        numPoints = Integer.parseInt(tokens[tokens.length - 1]);
                    } catch (NumberFormatException ex) {
                        numPoints = 0;
                    }
                    break;
                }
            }

            points = new Point[numPoints];
            for (int i = 0; i < numPoints; i++) {
                String nextLine = getNextValidLine(fileScanner);
                String[] coordinates = nextLine.split("[\\s]+");
                double x = Double.parseDouble(coordinates[0].trim());
                double y = Double.parseDouble(coordinates[1].trim());
                points[i] = new Point(x, y);
            }
        }

        return points;
    }

    private static String getNextValidLine(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            String nextLine = fileScanner.nextLine().split("#")[0].trim();
            if (!nextLine.isEmpty()) {
                return nextLine;
            }
        }

        return null;
    }
}
