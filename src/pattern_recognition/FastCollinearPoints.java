package pattern_recognition;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


/**
 *  FastCollinearPoints.
 *
 *  Examines 4 points at a time and checks whether they all lie on the same
 *  line segment, returning all such line segments.  To check whether the 4
 *  points p, q, r, and s are collinear, check whether the three slopes
 *  between p and q, between p and r, and between p and s are equal.
 *
 *  Given a point p, the following method determines whether p participates
 *  in a set of 4 or more collinear points.
 *  - Think of p as the origin.
 *  - For each other point q, determine the slope it makes with p.
 *  - Sort the points according to the slopes they makes with p.
 *  - Check if any 3 (or more) adjacent points in the sorted order have equal
 *  slopes with respect to p. If so, these points, together with p, are
 *  collinear.
 *
 *  Applying this method for each of the n points in turn yields an efficient
 *  algorithm to the problem. the algorithm solves teh problem because points
 *  that have equal slopes with respect to p are collinear, and sorting
 *  brings such points together. The algorithm is fast because the bottleneck
 *  operation is sorting.
 */
public class FastCollinearPoints {
    /** List of line segments. */
    private LineSegment[] lineSegments;
    /** Dynamic array of found collinear line segments found. */
    private ArrayList<LineSegment> segments;
    /** Array of sorted points. */
    private Point[] sortedPoints;
    /** Last point. */
    private Point lastPoint = null;

    /**
     * Finds all line segments containing 4 points.
     * @param points the points
     */
    public FastCollinearPoints(final Point[] points) {

        checkForDuplicates(points);
        if (points == null) {
            throw new NullPointerException("Array of points of null");
        }
        for (Point point: points) {
            if (point == null) {
                throw new NullPointerException("Point is null.");
            }
        }

        segments = new ArrayList<>();

        for (int k = 0; k < points.length; k++) {
            int low = 0;
            int high = 0;
            sortedPoints = Arrays.copyOf(points, points.length);
            Arrays.sort(sortedPoints, points[k].slopeOrder());
            double previousSlope = points[k].slopeTo(sortedPoints[k]);

            for (int i = 1; i < sortedPoints.length; i++) {
                double currentSlope = points[k].slopeTo(sortedPoints[i]);
                if (currentSlope == previousSlope) {
                    high++;
                } else {
                    if ((high - low) >= 2 && sortedPoints[high] != lastPoint) {
                        lastPoint = sortedPoints[high];
                        segments.add(new LineSegment(points[k],
                                sortedPoints[high]));
                    }
                    low = i;
                    high = i;
                    previousSlope = currentSlope;
                }
            }

            if ((high - low) >= 2 && sortedPoints[high] != lastPoint) {
                lastPoint = sortedPoints[high];
                segments.add(new LineSegment(points[k],
                        sortedPoints[high]));
            }
        }

        lineSegments = segments.toArray(new LineSegment[segments.size()]);
    }

    /**
     * Checks for duplicate points in an array of points.
     * @param points the array of points to check.
     */
    private void checkForDuplicates(final Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate entries.");
                }
            }
        }
    }

    /**
     *  Return the number of collinear line segments.
     *  @return the number of collinear line segments.
     */
    public int numberOfSegments() {
        return this.segments.size();
    }

    /**
     * Includes each line segment containing 4 points exactly one. If 4 points
     * appear on a line segment in the order p->q->r->s, then you should
     * include either the line segment p->s or s->p (but not both) and you
     * should not include subsegments such as p->r or q->r. For simplicity, we
     * will not supply any input that has 5 or more collinear points.
     *
     * @return an array of collinear line segments.
     */
    public LineSegment[] segments() {
        return null;
    }

    /**
     * Unit tests for the BruteCollinearPoints data type.
     * @param args the args.
     */
    public static void main(final String[] args) {
        In in = new In(args[0]);

        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();

            points[i] = new Point(x, y);
        }

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.enableDoubleBuffering();

        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        FastCollinearPoints bcpoints = new FastCollinearPoints(points);
        StdOut.println("The number of collinear line segments is " + bcpoints
                .numberOfSegments());
        for (LineSegment segment : bcpoints.lineSegments) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
        }
    }
}
