package geometry.builder;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Angle {

    private final double radians;

    private Angle(double radians) {
        this.radians = radians;
    }

    public static Angle inRadians(double radians) {
        return new Angle(radians);
    }

    public static Angle inDegrees(double degrees) {
        return new Angle(Math.toRadians(degrees));
    }

    public double inRadians() {
        return radians;
    }

    public double inDegrees() {
        return Math.toDegrees(radians);
    }
}
