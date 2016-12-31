package sort;

import edu.princeton.cs.algs4.GrahamScan;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 *  Graham Scan
 *
 *  Running time N log N for sorting and linear for rest.
 *  Pf. N log N for sorting; each point pushed and popped at most once.
 *
 *  The convex hull of a set of N points is the smallest perimeter fence
 *  enclosing the points.
 *
 *  Equivalent definitions.
 *  - Smallest convex set containing all the points.
 *  - Smallest area convex polygon enclosing the points.
 *  - Convex polygon enclosing the points, whose vertices are points in set.
 *
 *  Convex hull output. Sequence of vertices in counterclockwise order.
 *
 *  Graham scan: implementation challenges
 *  Q. How to find point p with smallest y-coordinate?
 *  A. Define a toal order, comparing by y-coordinate.
 *
 *  Q. How to sort points by polar angle with respect to p?
 *  A. Define a total order for each point p.
 *
 *  Q. How to determine whether p1->p2->p3 is a counterclockwise turn?
 *  A. Computational geometry.
 *
 *  Q. How to sort efficiently?
 *  A. Mergesort sorts in N log N time.
 *
 *  Q. How to handle degeneracies (three or more points on a line)?
 *  A. Requires some care but not hard.
 *
 *  Lesson. Geometric primitives are tricky to implement.
 *  - Dealing with degenerate cases.
 *  - Coping with floatig-point precision.
 */
public class TestGrahamScan {
    /**
     * Unit tests the {@code GrahamScan} data type.
     * Reads in an integer {@code n} and {@code n} points (specified by
     * their <em>x</em>- and <em>y</em>-coordinates) from standard input;
     * computes their convex hull; and prints out the points on the
     * convex hull to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        int n = Integer.parseInt(args[2]);

        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point2D(x, y);
        }
        GrahamScan graham = new GrahamScan(points);
        for (Point2D p : graham.hull()) {
            StdOut.println(p);
        }
    }
}
