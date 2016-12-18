package sort;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

/**
 * TestPoint2D.
 */
public class TestPoint2D {
    /**
     * Unit tests the point data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        int x0 = Integer.parseInt(args[0]);
        int y0 = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point2D(x, y);
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();


        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.polarOrder());
        for (int i = 0; i < n; i++) {
            p.drawTo(points[i]);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }
}
