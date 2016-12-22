package pattern_recognition;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/*************************************************************************
 *  An immutable data type for Line segments in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *************************************************************************/
public class LineSegment {
    private static final String file =
            "src\\pattern_recognition\\collineartests\\input6.txt";
    /** One endpoint of this line segment. */
    private final Point p;
    /** The other endpoint of this line segment. */
    private final Point q;

    /**
     * Initializes a new line segment.
     *
     * @param  p one endpoint
     * @param  q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *         is <tt>null</tt>
     */
    public LineSegment(final Point p, final Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("Argument is null");
        }
        this.p = p;
        this.q = q;
    }

    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this line segment
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Unit tests the Point data type..
     *  @param args
     */
    public static void main(final String[] args) {
        In in = new In(file);

        int n = in.readInt();
        Point[] points = new Point[n];

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        int i;
        for (i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();

            points[i] = new Point(x, y);
            points[i].draw();
        }

        // draw line segments from p to each point, one at a time
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int j = 0; j < n; j++) {
            for (int k = 1; k < n; k++) {
                LineSegment ls = new LineSegment(points[j], points[k]);
                ls.draw();
                StdDraw.show();
                StdDraw.pause(100);
            }
        }
    }
}
