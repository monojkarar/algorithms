package analysisOfAlgorithms.week5_balanced_search_trees;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

/**
 *  KdTree implementation to help solve problems in a 2D space. Even Depths
 *  will compare against the Y coordinate, even Depths will compare
 *  against the X coordinates.
 *
 *  The prime advantage of a 2d-tree over a BST is that it supports efficient
 *  implementation of range search and nearest neighbor search. Each node
 *  corresponds to an axis-aligned rectangle in the unit square, which encloses
 *  all of the points in its subtree. The root corresponds to the unit square;
 *  the left and right children of the root corresponds to the two rectangles
 *  split by the x-coordinate of the point at the root; and so forth.
 */
public final class KdTree {

    /** Root of KdTree. */
    private Node root;

    /** Distance to nearest neighbor. */
    private double minDistance;

    /** Node with the point that is the nearest neighbor. */
    private Node nearestNeighbor;

    /** KdTree helper Node data type. */
    private final class Node {

        /** Sorted by Point2D. */
        private Point2D point;

        /** Left and right subtree. */
        private Node left, right;

        /** parent of node. */
        private Node parent;

        /** Rectangle. */
        private RectHV rectangle;

        /**Number of nodes in subtree. */
        private int size;

        /** Level a node is at. */
        private int level;

        /**
         *  KdTree helper Node constructor.
         *  @param p the data type
         *  @param newSize size of subtree
         *  @param lvl the level of the node in the tree
         */
        private Node(final Point2D p, final int newSize, final int lvl) {

            this.point = p;
            this.rectangle = new RectHV(0, 0, 1, 1);
            this.size = newSize;
            this.level = lvl;
            if (this.level == 1) {
                this.parent = this;
            }
        }

        /**
         *  Compares two points. Comparison is done by x-coordinate if
         *  level is odd and by y-coordinate if level is even.
         *  Formally, the invoking point (x0, y0) is less than the argument
         *  point (x1, y1) if and only if  x0 < x1 if level is odd or if
         *  y0 < y1 and if level is even.
         *
         *  @param p the point to compare against.
         *  @return -1 if l less than, 1 if greater than, 0 if equal
         */
        private int compareTo(final Point2D p) {

            if (this.getLevel() % 2 == 1) {
                return Double.compare(p.x(), this.point.x());
            } else {
                return Double.compare(p.y(), this.point.y());
            }
        }

        /**
         *  Compares a point and a rectangle. Comparison is done by
         *  x-coordinate if level is odd and by y-coordinate if level
         *  is even. Formally, the invoking point (x0, y0) is less than
         *  the rectangle if and only if x0 < rectangle.xmin() if level
         *  is odd or if y0 < rectangle.ymin() if level is even.
         *
         *  @param coordinate the rectangle to compare against.
         *  @return -1 if l less than, 1 if greater than, 0 if equal
         */
        private int compareTo(final Double coordinate) {

            if (this.getLevel() % 2 == 1) {
                return Double.compare(coordinate, this.point.x());
            } else {
                return Double.compare(coordinate, this.point.y());
            }
        }

        /**
         * Getter for level.
         * @return thelefvel
         */
        int getLevel() {
            return level;
        }
    }

    /** Initializes an empty symbol table. */
    KdTree() {

        minDistance = -1.0;
        nearestNeighbor = root;
    }

    /**
     *  Returns true if this symbol table is empty.
     *  @return {true if this symbol table is empty; false otherwise
     */
    public boolean isEmpty() {

        return root == null;
    }

    /**
     *  Get the number of points in the set.
     *  @return number of points in the set.
     */
    public int size() {

        return size(root);
    }

    /**
     *  Return number of points in KdTree rooted at x.
     *  @param x the node
     *  @return the number of points in KdTree
     */
    private int size(final Node x) {

        if (x == null) {
            return 0;
        }
        return x.size;
    }

    /**
     *  Add a point to the set(if it is not already int the set.
     *  @param point the point to add
     *  @throws NullPointerException point is null;
     */
    public void insert(final Point2D point) {

        if (point == null) {
            throw new NullPointerException("Item is null");
        }

        root = insert(root, point, 1);
    }

    /**
     *  Insert the point in the subtree rooted at h.
     *  @param node the node h
     *  @param point the point
     *  @param level the level in the tree of the node containing the point
     *  @return the node h
     */
    private Node insert(final Node node, final Point2D point, final int level) {

        if (node == null) {
            return new Node(point, 1, level);
        }

        int cmp = node.compareTo(point);
        if (cmp < 0) {
            node.left = insert(node.left, point, level + 1);
            node.left.parent = node;
        } else if (cmp > 0) {
            node.right = insert(node.right, point, level + 1);
            node.right.parent = node;
        } else {
            node.point = point;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**
     *  Does the KdTree contain the point p?
     *  @param point the point
     *  @return true if KdTree contains point p; false otherwise
     *  @throws NullPointerException if point is null
     */
    public boolean contains(final Point2D point) {

        if (point == null) {
            throw new NullPointerException("Item is null");
        }

        return !isEmpty() && get(root, point) != null;
    }

    /**
     *  Returns the node containing the given point.
     *  @param node the node
     *  @param  point the point
     *  @return the value associated with the given key if the key is in the
     *          symbol table and null if the key is not in the symbol table
     *  @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private Node get(final Node node, final Point2D point) {

        if (node == null) {
            return null;
        }

        int cmp = node.compareTo(point);
        if (cmp < 0) {
            return get(node.left, point);
        } else if (cmp > 0) {
            return get(node.right, point);
        } else {
            return node;
        }
    }

    /**
     *  A 2d-tree divides the unit square in a simple way: all the points to the
     *  left of the root go in the left subtree; all those to the right go in
     *  the right subtree; and so forth, recursively. Your draw() method should
     *  draw all of the points to standard draw in black and the subdivisions
     *  in red (for vertical splits) and blue (for horizontal splits). This
     *  method need not be efficient—it is primarily for debugging.
     */
    public void draw() {
        draw(root);
    }

    /**
     *  Helper function to draw all the points and the subdivisions.
     *  @param node a node that contains a point
     */
    private void draw(final Node node) {

        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        draw(node.left);
        draw(node.right);
    }

    /**
     *  Find all points inside a given rectangle.
     *
     *  Typical case. R + Log N                      _
     *  Worst case (assuming tree is balanced). R + /N (all points could be
     *  arranged in a circle, other types of problems might occur).
     *  @param rectangle the rectangle
     *  @return all points that are inside the rectangle.
     */
    public Iterable<Point2D> range(final RectHV rectangle) {

        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        if (!isEmpty()) {
            range(root, rectangle, pointsInRange);
        }
        return pointsInRange;
    }

    /**
     *  Helper function to store all points found to be within a rectangle. Even
     *  rows compare agains x-coordinates, and odd rows compare against
     *  y-coordinates.
     *  @param node the node
     *  @param rectangle the rectangle to check
     *  @param points the points in the rectangle
     */
    private void range(final Node node, final RectHV rectangle,
                       final ArrayList<Point2D> points) {

        if (node == null) {
            return;
        }
        if (rectangle.contains(node.point)) {
            points.add(node.point);
        }

        if (node.getLevel() % 2 == 1) {
            if (node.compareTo(rectangle.xmin()) < 0) {
                range(node.left, rectangle, points);
            }
            if (node.compareTo(rectangle.xmax()) > 0) {
                range(node.right, rectangle, points);
            }
        } else {
            if (node.compareTo(rectangle.ymin()) < 0) {
                range(node.left, rectangle, points);
            }
            if (node.compareTo(rectangle.ymax()) > 0) {
                range(node.right, rectangle, points);
            }
        }
    }

    /**
     * Find teh point closest to the passed in point.
     * @param point the point
     * @return the nearest neighbor to p
     */
    public Point2D nearest(final Point2D point) {

        if (point == null) {
            throw new NullPointerException("Item is null");
        }

        if (isEmpty()) {
            return null;
        }

        nearest(root, point);
        return nearestNeighbor.point;
    }

    /**
     *  A nearest neighbor in the set to point p; null if the set is empty.
     *  Nearest neighbor search. To find a closest point to a given query point,
     *  start at the root and recursively search in both subtrees using the
     *  following pruning rule: if the closest point discovered so far is closer
     *  than the distance between the query point and the rectangle
     *  corresponding to a node, there is no need to explore that node (or
     *  its subtrees).
     *
     *  That is, a node is searched only if it might contain a point that is
     *  closer than the best one found so far. The effectiveness of the pruning
     *  rule depends on quickly finding a nearby point. To do this, organize
     *  your recursive method so that when there are two possible subtrees to
     *  go down, you always choose the subtree that is on the same side of
     *  the splitting line as the query point as the first subtree to
     *  explore—the closest point found while exploring the first subtree may
     *  enable pruning of the second subtree.
     *
     *  - Check distance from point in node to query point.
     *  - Recursively search left/bottom (if it could contain a closer point).
     *  - Recursively search right/top (if it could contain a closer point).
     *  - Organize method so that it begins by searching for query point.
     *
     *  Typical case. log N.
     *  Worst case (even if tree is balanced). N.
     *
     * @param node the node to check if nearest neighbor
     * @param point the point
     */
    private void nearest(final Node node, final Point2D point) {

        if (node == null) {
            return;
        }

        double distance = node.point.distanceTo(point);
        if (distance < minDistance) {
            minDistance = distance;
            nearestNeighbor = node;
        }

        int cmp = node.compareTo(point);
        if (cmp < 0) {
            nearest(node.left, point);
        } else if (cmp > 0) {
            nearest(node.right, point);
        }
    }

    public static void main(final String[] args) {

        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            StdOut.printf("%8.6f %8.6f\n", x, y);
        }

        Point2D p1 = new Point2D(0.278547, 0.212465);
        if (kdtree.contains(p1)) {
            System.out.println("Point is in the tree");
        } else {
            System.out.println("Point is not in the tree");
        }

        Point2D p = new Point2D(0.1, 0.1);
        if (kdtree.contains(p)) {
            System.out.println("Point is in the tree");
        } else {
            System.out.println("Point is not in the tree");
        }
    }
}
