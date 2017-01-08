package analysisOfAlgorithms.week3_pattern_recognition;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 *  Class analysisOfAlgorithms.week3_pattern_recognition.Point.
 *
 *  To avoid potential complications with integer overflow or floating-point
 *  precision, you may assume that the constructor arguments x and y are each
 *  between 0 and 32,767.
 */
public class Point implements Comparable<Point> {
    /** X-coordinate. */
    private final int x;
    /** Y-coordinate. */
    private final int y;

        /**
         * Initializes a new point (x, y).
         * @param newx the x-coordinate
         * @param newy the y-coordinate
         * @throws IllegalArgumentException if either {@code x} or {@code y}
         *    is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY} or
         *    {@code Double.NEGATIVE_INFINITY}
         */
    Point(final int newx, final int newy) {
        if (Double.isInfinite(newx) || Double.isInfinite(newy)) {
            throw new IllegalArgumentException("Coordinates must be finite");
        }
        if (Double.isNaN(newx) || Double.isNaN(newy)) {
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        }
            this.x = newx;
            this.y = newy;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {

        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(final Point that) {

        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {

        return "(" + x + ", " + y + ")";
    }
    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value 0 if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(final Point that) {

        if (this.y < that.y || (this.y ==  that.y && this.x < that.x)) {
            return -1;
        }
        if (this.x == that.x && this.y == that.y) {
            return 0;
        }
        return 1;
    }
    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(final Point that) {
        // Degenerate line segments
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        }
        double dx = that.x - x;
        double dy = that.y - y;

        // Horizontal line segments
        if (dy == 0.0) {
            return +0.0;
        }
        // Vertical line segments
        if (dx == 0.0) {
            return Double.POSITIVE_INFINITY;
        }
        return dy / dx;
    }

    /**
     * Compares two points by the slopes they make with the invoking point
     * (xo, yo). Formally, the point (x1, y1) is less than the point (x2, y2)
     * if and only if the slope (y1-yo)/(x1-x0) is less than the slope
     * (y2-y0)/(x2-x0). Treat horizontal, vertical, and degenerate line
     * segments as in the slope to method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(final Point point1, final Point point2) {
                double slope1 = slopeTo(point1);
                double slope2 = slopeTo(point2);
                return Double.compare(slope1, slope2);
            }
        };
    }
}
