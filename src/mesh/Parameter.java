package mesh;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Parameter {

    public final double value;

    /**
     * A value between 0 and 1.
     * @param value Saves a double restricted in closed range [0, 1].
     */
    public Parameter(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("The parameter must be within the range 0.0 and 1.0");
        }
        this.value = value;
    }
}
