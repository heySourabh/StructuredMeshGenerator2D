package geometry;

import io.GeometryFileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.IntStream;
import main.Parameter;
import util.Range;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class GeometryFromFile implements Geometry {

    private static final double TOLERANCE = 1e-8;

    private final Point[] points_xi_0;
    private final Point[] points_xi_1;
    private final Point[] points_eta_0;
    private final Point[] points_eta_1;

    private final double[] xi_0_map, xi_1_map, eta_0_map, eta_1_map;

    public GeometryFromFile(File file) throws FileNotFoundException {
        points_xi_0 = GeometryFileReader.readPoints(file, GeometryFileReader.SideLabel.xi_0);
        points_xi_1 = GeometryFileReader.readPoints(file, GeometryFileReader.SideLabel.xi_1);
        points_eta_0 = GeometryFileReader.readPoints(file, GeometryFileReader.SideLabel.eta_0);
        points_eta_1 = GeometryFileReader.readPoints(file, GeometryFileReader.SideLabel.eta_1);

        if (points_xi_0.length < 2 || points_xi_1.length < 2
                || points_eta_0.length < 2 || points_eta_1.length < 2) {
            throw new IllegalArgumentException("The number of points per side must be at least 2.");
        }

        // Check continuity of geometry
        if (!overlapping(points_xi_0[0], points_eta_0[0])
                || !overlapping(points_xi_0[points_xi_0.length - 1], points_eta_1[0])
                || !overlapping(points_xi_1[0], points_eta_0[points_eta_0.length - 1])
                || !overlapping(points_xi_1[points_xi_1.length - 1], points_eta_1[points_eta_1.length - 1])) {
            throw new IllegalArgumentException("The geometry points supplied do not overlap properly.");
        }

        // create the mapping between points and parameters
        xi_0_map = calculateParameterMapping(points_xi_0);
        xi_1_map = calculateParameterMapping(points_xi_1);
        eta_0_map = calculateParameterMapping(points_eta_0);
        eta_1_map = calculateParameterMapping(points_eta_1);
    }

    private double[] calculateParameterMapping(Point[] points) {
        double[] lengths = IntStream.range(1, points.length)
                .mapToDouble(i -> points[i].dist(points[i - 1]))
                .toArray();

        double[] cumLen = new double[points.length];
        cumLen[0] = 0.0;
        for (int i = 1; i < points.length; i++) {
            cumLen[i] = cumLen[i - 1] + lengths[i - 1];
        }

        for (int i = 0; i < cumLen.length; i++) {
            cumLen[i] = cumLen[i] / cumLen[cumLen.length - 1];
        }

        return cumLen;
    }

    private boolean overlapping(Point p1, Point p2) {
        return p1.dist(p2) < TOLERANCE;
    }

    private Point getPoint(Parameter parameter, double[] paramter_map, Point[] points) {
        // where does the parameter lie
        double parVal = parameter.value;
        int indexR = IntStream.range(1, paramter_map.length)
                .filter(i -> (paramter_map[i - 1] - TOLERANCE) < parVal && (paramter_map[i] + TOLERANCE) > parVal)
                .findFirst().getAsInt();
        int indexL = indexR - 1;
        double parL = paramter_map[indexL];
        double parR = paramter_map[indexR];
        Point pointL = points[indexL];
        Point pointR = points[indexR];

        double x = Range.map(parVal, new Range(parL, parR), new Range(pointL.x, pointR.x));
        double y = Range.map(parVal, new Range(parL, parR), new Range(pointL.y, pointR.y));
        return new Point(x, y);
    }

    @Override
    public Point xi_0(Parameter eta) {
        return getPoint(eta, xi_0_map, points_xi_0);
    }

    @Override
    public Point xi_1(Parameter eta) {
        return getPoint(eta, xi_1_map, points_xi_1);
    }

    @Override
    public Point eta_0(Parameter xi) {
        return getPoint(xi, eta_0_map, points_eta_0);
    }

    @Override
    public Point eta_1(Parameter xi) {
        return getPoint(xi, eta_1_map, points_eta_1);
    }
}
