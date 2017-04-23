package main;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
@FunctionalInterface
public interface ParameterizedCurve {

    public Point getPoint(Parameter parameter);
}
