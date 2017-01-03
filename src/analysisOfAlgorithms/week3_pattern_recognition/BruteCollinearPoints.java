package analysisOfAlgorithms.week3_pattern_recognition;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * analysisOfAlgorithms.week3_pattern_recognition.BruteCollinearPoints.
 *
 * Examines 4 points at a time and checks whether they all lie on the same
 * line segment, returning all such line segments.  To check whether the 4
 * points p, q, r, and s are collinear, check whether the three slopes
 * between p and q, between p and r, and between p and s are equal.
 */
public class BruteCollinearPoints {
    /** Array of LineSegments. */
    private LineSegment[] lineSegments;
    /** Dynamic array of found collinear line segments found. */
    private ArrayList<LineSegment> segments;
    /**
     * Finds all line segments containing 4 points.
     * @param points the points
     */
    public BruteCollinearPoints(final Point[] points) {

        if (points == null) {
            throw new NullPointerException("Array of points of null");
        }
        for (Point point: points) {
            if (point == null) {
                throw new NullPointerException("analysisOfAlgorithms.week3_pattern_recognition.Point "
                        + "is null.");
            }
        }
        segments = new ArrayList<>();
        checkForDuplicates(points);

        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = p + 2; r < points.length - 1; r++) {
                    for (int s = p + 3; s < points.length; s++) {
                        if (isCollinear(points, p, q, r, s)) {
                            segments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
        lineSegments = segments.toArray(new LineSegment[segments.size()]);
    }

    /**
     * Get the line segments.
     * @return the array of line segments
     */
    private LineSegment[] getLineSegments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    /**
     * Return true if points are collinear; false otherwise.
     * To check whether the 4 points p, q, r, and s are collinear, check
     * whether the three slopes between p and q, between p and r, and between
     * p and s are equal
     *
     * @param points the array of points to check.
     * @param p first point
     * @param q second point
     * @param r third point
     * @param s fourth point
     * @return true if points are collienar; false otherwise
     */
    private boolean isCollinear(final Point[] points, final int p, final int q,
                                                     final int r, final int s) {
        return (points[p].slopeTo(points[q]) == points[q].slopeTo(points[r]))
            && (points[q].slopeTo(points[r]) == points[r].slopeTo(points[s]));
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
     * The order of growth of the the running time should be n^4 in the worst
     * case and it should use space proportional to the n plus the number of
     * segments returned.
     * @return an array of collinear line segments.
     */
    public LineSegment[] segments() {
        return this.getLineSegments();
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
}
