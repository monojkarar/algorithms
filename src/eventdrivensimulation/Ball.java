package eventdrivensimulation;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Ball class.
 */
public final class Ball {

    /** Position of the ball. */
    private double rx, ry;
    /** Position of the velocity of ball. */
    private double vx, vy;
    /** Radius of the ball. */
    private double radius;

    /** Constructor. */
    Ball() {
        rx     = StdRandom.uniform(0.0, 1.0);
        ry     = StdRandom.uniform(0.0, 1.0);
        vx     = StdRandom.uniform(-0.005, 0.005);
        vy     = StdRandom.uniform(-0.005, 0.005);
        radius = 0.01;
    }

    /**
     * Move method.
     * @param dt the change in time interval
     */
    public void move(final double dt) {
        if ((rx + vx * dt < radius) || (rx + vx * dt > 1.0 - radius)) {
            vx = -vx;
        }
        if ((ry + vy * dt < radius) || (ry + vy * dt > 1.0 - radius)) {
            vy = -vy;
        }
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    /**
     * Draw method.
     */
    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }
}
