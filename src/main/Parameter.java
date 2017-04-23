package main;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class Parameter {

    public final double parameter;

    public Parameter(double parameter) {
        if (parameter < 0.0 || parameter > 1.0) {
            throw new IllegalArgumentException("The parameter must be within the range 0.0 and 1.0");
        }
        this.parameter = parameter;
    }
}
