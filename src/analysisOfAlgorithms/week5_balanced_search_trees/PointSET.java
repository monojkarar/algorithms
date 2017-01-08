package analysisOfAlgorithms.week5_balanced_search_trees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

/**
 * PointSET is a mutable data type that represents a set of point in the unit
 * square.  Brute force algorithm used to solve the problem of whether or not
 * a set of points fall within a rectangle. Will take advantage of the
 * built-in data types SET, Point2D, and RectHV.
 */
public class PointSET {
    /** A set of 2D points. */
    private SET<Point2D> points;

    /** Default constructor that instantiates and empty set of points. */
    public PointSET() {

        points = new SET<>();
    }

    /**
     * Is the set empty?
     * @return true if the set is empty; false otherwise.
     */
    public boolean isEmpty() {

        return points.isEmpty();
    }

    /**
     * Get the number of points in the set.
     * @return number of points in the set.
     */
    public int size() {

        return points.size();
    }

    /**
     * Add a point to the set(if it is not already int the set.
     * @param point the point to add
     * @throws NullPointerException point is null;
     */
    public void insert(final Point2D point) {

        if (point == null) {
            throw new NullPointerException("Item is null");
        }
        points.add(point);
    }

    /**
     * Does the set contain point p?
     * @param point the point
     * @return true if set contains point p; false otherwise
     */
    public boolean contains(final Point2D point) {
        if (point == null) {
            throw new NullPointerException("Item is null");
        }
        return points.contains(point);
    }

    /**
     * Draw all points to standard draw.
     */
    public void draw() {

        for (Point2D point : points) {
            point.draw();
        }
    }

    /**
     * All points that are inside the rectangle.
     * @param rect the rectangle
     * @return all points that are inside teh rectangle.
     */
    public Iterable<Point2D> range(final RectHV rect) {

        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                pointsInRange.add(point);
            }
        }
        return pointsInRange;
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty.
     * @param point the point
     * @return the nearest neighbor to p
     */
    public Point2D nearest(final Point2D point) {

        if (point == null) {
            throw new NullPointerException("Item is null");
        }

        Point2D nearest = null;

        if (points.isEmpty()) {
            return nearest;
        }

        double min = -1;
        for (Point2D p: points) {
            double distance = point.distanceTo(p);
            if (min == -1 || distance < min) {
                min = distance;
                nearest = p;
            }
        }
        return nearest;
    }
}

