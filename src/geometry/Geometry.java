package geometry;

import main.Parameter;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public interface Geometry {

    public Point xi_0(Parameter eta);

    public Point xi_1(Parameter eta);

    public Point eta_0(Parameter xi);

    public Point eta_1(Parameter xi);
}
