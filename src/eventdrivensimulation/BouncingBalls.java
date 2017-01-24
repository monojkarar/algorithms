package eventdrivensimulation;

import edu.princeton.cs.algs4.StdDraw;

/**
 * BouncingBalls class.
 */
public final class BouncingBalls {

    /** Constructor. */
    private BouncingBalls() { }

    /**
     * Unit tests for BouncingBalls class.
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        int n = Integer.parseInt(args[0]);
        Particle[] balls = new Particle[n];
        for (int i = 0; i < n; i++) {
            balls[i] = new Particle();
        }
        while (true) {
            //StdDraw.clear();
            for (int j = 0; j < n; j++) {
                balls[j].move(0.5);
                balls[j].draw();
            }
            StdDraw.show(50);
        }
    }
}
