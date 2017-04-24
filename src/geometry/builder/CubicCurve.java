package geometry.builder;

import geometry.Angle;
import java.util.Optional;
import main.Parameter;
import geometry.Point;
import util.Range;
import geometry.ParametricCurve;

/**
 *
 * @author Sourabh Bhat <sourabh.bhat@iitb.ac.in>
 */
public class CubicCurve implements ParametricCurve {

    private final Point p1, p2;
    private final Optional<Angle> startAngle, endAngle;

    public CubicCurve(Point p1, Point p2, Optional<Angle> startAngle, Optional<Angle> endAngle) {
        this.p1 = p1;
        this.p2 = p2;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    @Override
    public Point getPoint(Parameter parameter) {
        // Both angles not defined so just a straight line
        if (!startAngle.isPresent() && !endAngle.isPresent()) {
            return new Point(
                    Range.map(parameter.value, new Range(0, 1), new Range(p1.x, p2.x)),
                    Range.map(parameter.value, new Range(0, 1), new Range(p1.y, p2.y))
            );
        } else if (startAngle.isPresent() && !endAngle.isPresent()) {
            // Quadratic curve using start angle
            // eqn x    :   ax0 + ax1 * t + ax2 * t^2   = x
            // eqn dx/dt:         ax1     + ax2 * 2 * t = dx/dt = cos(ang) * curve_length
            // eqn y    :   ay0 + ay1 * t + ay2 * t^2   = y
            // eqn dy/dt:         ay1     + ay2 * 2 * t = dy/dt = sin(ang) * curve_length

            // @t=0, x=p1.x                    => ax0 = p1.x
            // @t=0, y=p1.y                    => ay0 = p1.y
            // @t=0, dx/dt=cos(startAng)*l     => ax1 = cos(startAng)*l
            // @t=0, dy/dt=sin(startAng)*l     => ay1 = sin(startAng)*l
            // @t=1, ax0 + ax1 + ax2 = p2.x    => ax2 = p2.x - (ax0 + ax1)
            // @t=1, ay0 + ay1 + ay2 = p2.y    => ay2 = p2.y - (ay0 + ay1)
            double l = p1.dist(p2); // Approximate curve length

            double ax0 = p1.x;
            double ax1 = Math.cos(startAngle.get().inRadians()) * l;
            double ax2 = p2.x - (ax0 + ax1);

            double ay0 = p1.y;
            double ay1 = Math.sin(startAngle.get().inRadians()) * l;
            double ay2 = p2.y - (ay0 + ay1);

            double t = parameter.value;

            double x = ax0 + ax1 * t + ax2 * t * t;
            double y = ay0 + ay1 * t + ay2 * t * t;

            return new Point(x, y);
        } else if (!startAngle.isPresent() && endAngle.isPresent()) {
            // Quadratic curve using end angle
            // eqn x    :   ax0 + ax1 * t + ax2 * t^2   = x
            // eqn dx/dt:         ax1     + ax2 * 2 * t = dx/dt = -cos(ang) * curve_length
            // eqn y    :   ay0 + ay1 * t + ay2 * t^2   = y
            // eqn dy/dt:         ay1     + ay2 * 2 * t = dy/dt = -sin(ang) * curve_length

            // @t=0, x=p1.x                    => ax0 = p1.x
            // @t=0, y=p1.y                    => ay0 = p1.y
            // @t=1, dx/dt= -cos(ang)*l        => ax1 + 2 * ax2 = -cos(endAng)*l
            // @t=1, ax0 + ax1 + ax2 = p2.x    => ax1 = 2 * (p2.x - ax0 + cos(endAng)*l / 2)
            // @t=1, ax0 + ax1 + ax2 = p2.x    => ax2 = p2.x - (ax0 + ax1)
            // Similiarly,
            //                                 => ay1 = 2 * (p2.y - ay0 + sin(endAng)*l / 2)
            //                                 => ay2 = p2.y - (ay0 + ay1)
            double l = p1.dist(p2); // Approximate curve length

            double ax0 = p1.x;
            double ax1 = 2.0 * (p2.x - ax0 + Math.cos(endAngle.get().inRadians()) * l / 2.0);
            double ax2 = p2.x - (ax0 + ax1);

            double ay0 = p1.y;
            double ay1 = 2.0 * (p2.y - ay0 + Math.sin(endAngle.get().inRadians()) * l / 2.0);
            double ay2 = p2.y - (ay0 + ay1);

            double t = parameter.value;

            double x = ax0 + ax1 * t + ax2 * t * t;
            double y = ay0 + ay1 * t + ay2 * t * t;

            return new Point(x, y);
        } else {
            // Cubic curve using both the start and the end angle
            // eqn x    :   ax0 + ax1 * t + ax2 * t^2   + ax3 * t^3     = x
            // eqn dx/dt:         ax1     + ax2 * 2 * t + ax3 * 3 * t^2 = dx/dt = (-)cos(ang) * curve_length
            // eqn y    :   ay0 + ay1 * t + ay2 * t^2   + ay3 * t^3     = y
            // eqn dy/dt:         ay1     + ay2 * 2 * t + ay3 * 3 * t^2 = dy/dt = (-)sin(ang) * curve_length
            // Solving the above equations results in:
            // ax0 = p1.x
            // ax1 = cos(startAng) * l
            // ax2 = 3 * p2.x - 3 * ax0 - 2 * ax1 - cos(endAng) * l
            // ax3 = p2.x - (ax0 + ax1 + ax2)

            double l = p1.dist(p2); // Approximate curve length

            double ax0 = p1.x;
            double ax1 = Math.cos(startAngle.get().inRadians()) * l;
            double ax2 = 3.0 * p2.x - 3.0 * ax0 - 2.0 * ax1 + Math.cos(endAngle.get().inRadians()) * l;
            double ax3 = p2.x - (ax0 + ax1 + ax2);

            double ay0 = p1.y;
            double ay1 = Math.sin(startAngle.get().inRadians()) * l;
            double ay2 = 3.0 * p2.y - 3.0 * ay0 - 2.0 * ay1 + Math.sin(endAngle.get().inRadians()) * l;
            double ay3 = p2.y - (ay0 + ay1 + ay2);

            double t = parameter.value;

            double x = ax0 + ax1 * t + ax2 * t * t + ax3 * t * t * t;
            double y = ay0 + ay1 * t + ay2 * t * t + ay3 * t * t * t;

            return new Point(x, y);
        }
    }
}
