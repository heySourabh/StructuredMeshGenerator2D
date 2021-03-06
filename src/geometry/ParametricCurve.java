package geometry;

import mesh.Parameter;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
@FunctionalInterface
public interface ParametricCurve {

    public Point getPoint(Parameter parameter);

    default ParametricCurve reverse() {
        return parameter -> getPoint(new Parameter(1.0 - parameter.value));
    }
}
