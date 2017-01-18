package sort;

/******************************************************************************
 *  Compilation:  javac GrahamaScan.java
 *  Execution:    java GrahamScan < input.txt
 *  Dependencies: Point2D.java
 *  Data files:   http://algs4.cs.princeton.edu/99hull/rs1423.txt
 *                http://algs4.cs.princeton.edu/99hull/kw1260.txt
 *
 *  Create points from standard input and compute the convex hull using
 *  Graham scan algorithm.
 *
 *  May be floating-point issues if x- and y-coordinates are not integers.
 *
 *  % java GrahamScan < input100.txt
 *  (7486.0, 422.0)
 *  (29413.0, 596.0)
 *  (32011.0, 3140.0)
 *  (30875.0, 28560.0)
 *  (28462.0, 32343.0)
 *  (15731.0, 32661.0)
 *  (822.0, 32301.0)
 *  (823.0, 15895.0)
 *  (1444.0, 10362.0)
 *  (4718.0, 4451.0)
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.*;

import java.util.Arrays;

/**
 *  The GrahamScan data type provides methods for computing the
 *  convex hull of a set of N points in the plane.
 *
 *  The implementation uses the Graham-Scan convex hull algorithm.
 *  It runs in O(N log N) time in the worst case and uses O(N) extra memory.
 *
 *  The convex hull of a set of N points is the smallest perimeter fence
 *  enclosing the points.
 *  Convex hull output. Sequence of vertices in counterclockwise order.
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
 *  A. Define a total order, comparing by y-coordinate.
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
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/99scientific">Section 9.9</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class GrahamScan {

    /** Stack of points. */
    private Stack<Point2D> hull = new Stack<>();

    /**
     * Computes the convex hull of the specified array of points.
     *
     * @param  pts the array of points
     * @throws NullPointerException if points is null or if any entry in
     * points[] is null
     */
    private GrahamScan(final Point2D[] pts) {

        // defensive copy
        int n = pts.length;
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            points[i] = pts[i];
        }
        // preprocess so that points[0] has lowest y-coordinate; break ties by
        // x-coordinate points[0] is an extreme point of the convex hull
        // (alternatively, could do easily in linear time)
        Arrays.sort(points);

        // sort by polar angle with respect to base point points[0],
        // breaking ties by distance to points[0]
        Arrays.sort(points, 1, n, points[0].polarOrder());

        hull.push(points[0]);       // p[0] is first extreme point

        // find index k1 of first point not equal to points[0]
        int k1;
        for (k1 = 1; k1 < n; k1++) {
            if (!points[0].equals(points[k1])) {
                break;
            }
        }
        if (k1 == n) {
            return;        // all points equal
        }

        // find index k2 of first point not collinear with points[0] and
        // points[k1]
        int k2;
        for (k2 = k1 + 1; k2 < n; k2++) {
            if (Point2D.ccw(points[0], points[k1], points[k2]) != 0) {
                break;
            }
        }
        hull.push(points[k2 - 1]);    // points[k2-1] is second extreme point

        // Graham scan; note that points[n-1] is extreme point different from
        // points[0]
        for (int i = k2; i < n; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }

        assert isConvex();
    }

    /**
     * Returns the extreme points on the convex hull in counterclockwise order.
     *
     * @return the extreme points on the convex hull in counterclockwise order
     */
    private Iterable<Point2D> hull() {
        Stack<Point2D> s = new Stack<>();
        for (Point2D p : hull) {
            s.push(p);
        }
        return s;
    }

    /**
     * Check that boundary of hull is strictly convex.
     * @return true if hull is strictly convex; false otherwise
     */
    private boolean isConvex() {
        int n = hull.size();
        if (n <= 2) {
            return true;
        }

        Point2D[] points = new Point2D[n];
        int k = 0;
        for (Point2D p : hull()) {
            points[k++] = p;
        }

        for (int i = 0; i < n; i++) {
            if (Point2D.ccw(points[i], points[(i + 1) % n], points[(i + 2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Unit tests the GrahamScan data type.
     * Reads in an integer n from standard input. n points (specified by
     * their x- and y-coordinates) are randomly generated; computes their convex
     * hull; and prints out the points on the convex hull to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        int n = Integer.parseInt(args[0]);

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
