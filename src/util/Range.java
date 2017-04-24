package util;

/**
 *
 * @author Sourabh Bhat
 */
public class Range {

    private final double a, b;
    private final double width;

    public Range(double a, double b) {
        this.a = a;
        this.b = b;
        this.width = b - a;
    }

    public double getMin() {
        return Math.min(a, b);
    }

    public double getMax() {
        return Math.max(a, b);
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public static double map(double value, Range mapFrom, Range mapTo) {
        return mapTo.a + mapTo.width / mapFrom.width * (value - mapFrom.a);
    }

    public static double clamp(double value, Range limits) {
        double min = limits.getMin();
        double max = limits.getMax();
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Width of the range. The width will be negative if <code>a</code> &gt
     * <code>b</code>.
     *
     * @return <code>b</code> - <code>a</code>
     */
    public double width() {
        return width;
    }

    public boolean inRange(double value) {
        return value > getMin() && value < getMax();
    }
}
