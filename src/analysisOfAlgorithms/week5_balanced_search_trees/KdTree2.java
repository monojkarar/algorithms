package analysisOfAlgorithms.week5_balanced_search_trees;

//package analysisOfAlgorithms.week5_balanced_search_trees;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.List;


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
public class KdTree2 {
    private Node root;
    private int  size = 0;

    /**
     * Helper Node class that will have an d
     *
     * @author Mark Poko
     *
     */
    private static class Node {
        private Point2D p;
        private Node    left;
        private Node    right;
        private RectHV  rect;

        public Node(Point2D p) {
            this.p = p;
        }

        public int compareTo(Point2D p, int depth) {
            if (compareX(depth)) {
                return Double.compare(p.x(), this.p.x());
            } else
                return Double.compare(p.y(), this.p.y());
        }
    }

    private static boolean compareX(int depth) {
        return depth % 2 == 0;
    }

    /**
     * Find the number of elements in the KdTree
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Check to see if the KdTree is empty. We can utilize that the root will
     * equal null when it's empty.
     *
     * @return True or False
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Insert a point into the BST tree. This is a public method that will
     * ultimately call our private recursive method.
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p);
            root.rect = new RectHV(0, 0, 1, 1);
            size++;
            return;
        }

        root = put(root, p, 0);
    }

    /**
     * Private method Put to facilitate recursive calls. Since KDTree alternates
     * which key to use at each level, it's necessary to supply the depth and
     * increment it each time we call put. Once we reach a point where x is
     * null, we're complete and can return a new Node. The node will then be
     * inserted at the corresponding point in the BST.
     *
     * @param x
     * @param p
     * @param depth
     * @return
     */
    private Node put(Node x, Point2D p, int depth) {
        // base case
        if (x == null) {
            size++;
            return new Node(p);
        }

        if (x.p.equals(p)) {
            return x;
        }
        int childDepth = depth + 1;
        if (x.compareTo(p, depth) < 0) {

            x.left = put(x.left, p, childDepth);

            if (x.left.rect == null) {
                double xmin = x.rect.xmin();
                double xmax = x.rect.xmax();
                double ymin = x.rect.ymin();
                double ymax = x.rect.ymax();
                if (compareX(depth)) {
                    xmax = x.p.x();

                } else {
                    ymax = x.p.y();

                }
                x.left.rect = new RectHV(xmin, ymin, xmax, ymax);
            }

        } else {
            x.right = put(x.right, p, childDepth);
            if (x.right.rect == null) {
                double xmin = x.rect.xmin();
                double xmax = x.rect.xmax();
                double ymin = x.rect.ymin();
                double ymax = x.rect.ymax();
                if (compareX(depth)) {
                    xmin = x.p.x();
                } else {
                    ymin = x.p.y();

                }
                x.right.rect = new RectHV(xmin, ymin, xmax, ymax);
            }
        }
        return x;
    }

    /**
     * Check to see if the KDTree contains a given point
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (isEmpty()) {
            return false;
        }
        return get(root, p, 0);
    }

    /**
     * Private helper method to find if the KDTree contains an element. It
     * utilizes a standard BST implementation except that it uses the correct
     * key for proper comparison
     *
     * @param x
     * @param p
     * @param depth
     * @return True if the element exists
     */
    private boolean get(Node x, Point2D p, int depth) {
        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        int nextDepth = depth + 1;
        if (x.compareTo(p, depth) < 0) {
            return get(x.left, p, nextDepth);
        } else {
            return get(x.right, p, nextDepth);
        }
    }

    /**
     * Iterate through the BST and draw the corresponding nodes.
     */
    public void draw() {
        draw(root, 0);
    }

    private void draw(Node x, int depth) {
        if (x == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        if (compareX(depth)) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        depth += 1;
        draw(x.left, depth);
        draw(x.right, depth);
    }

    /**
     * Find all points within given rectangle.
     *
     * @param rect
     * @return Iterable List of Points that are within the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();
        if (!isEmpty()) {
            findPoints(root, rect, queue);
        }
        return queue;
    }

    /**
     * Helper function to enqueue items found to be within the rectangle. It is
     * necessary to pass depth to ensure that we're doing the proper key
     * comparisons. Even Rows compare against x coordinates, and the Odd rows
     * compare against the Y coordinates
     *
     * @param x
     * @param rect
     * @param queue
     * @param depth
     */
    private void findPoints(Node x, RectHV rect, Queue<Point2D> queue) {
        if (!rect.intersects(x.rect)) {
            return;
        }
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
        }
        if (x.left != null) {
            findPoints(x.left, rect, queue);
        }
        if (x.right != null) {
            findPoints(x.right, rect, queue);
        }
    }

    /**
     * Find the point closest to the passed in point.
     *
     * @param p
     * @return Point2D that is the closest point
     */
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        return findNearest(root, p, root.p, Double.MAX_VALUE, 0);
    }

    /**
     * Private helper method to recursively find the closest point. We make a
     * number of assumptions within this method. If x == null, we've gone as far
     * down as possible and whatever point is currently the closest, is the
     * closest in that subtree. This doesn't guarantee that it's the closest
     * point.
     *
     * Initially we compute the distance between the query and the current node.
     * If this distance is closer then the current closest, we reassign the
     * Point closest and the closest distance. Then we need to check the
     * respective subtrees iff there's a possibility of having a point closer.
     * Depending on the current depth, we will compare against the x or y values
     * first. We intuitively have an idea about which direction to go into as we
     * share the same logic as insertion. If compareKey is less then the current
     * nodes compare key, we go left first, otherwise we go right.
     *
     * Once we know which node to search first, we check to see if the rectangle
     * has any points closer then the current closest distance. If so, we search
     * that subtree and repeat the process. Afterwards, we reassign
     * closestDistance to the distance between the point returned and the query
     * since we know this is the closest element in the respective subtree. Then
     * we repeat this process for the right subtree, and if we get an element
     * closer then we can assume that it's the closest point in the tree and
     * return it.
     *
     * This entire process hinges on insert being performed correctly and that
     * the respective rectangle is set up properly for the given node. If we
     * chose to make this implementation more generic and remove the rectangle,
     * we would need to compute the geometric object in time and then compute
     * the distance squared to the given query. This is not entirely difficult,
     * but the given problem doesn't require a true KD tree, just a 2D k tree.
     *
     * @param x
     * @param query
     * @param nearest
     * @param nearestDistance
     * @param depth
     * @return
     */
    private Point2D findNearest(Node x, Point2D query, Point2D nearest,
                                double nearestDistance, int depth) {
        if (x == null) {
            return nearest;
        }
        Point2D closest = nearest;
        double closestDistance = nearestDistance;
        double distance = x.p.distanceSquaredTo(query);
        if (distance < nearestDistance) {
            closest = x.p;
            closestDistance = distance;
        }
        Node first, second;
        if (compareX(depth)) {
            if (query.x() < x.p.x()) {
                first = x.left;
                second = x.right;
            } else {
                first = x.right;
                second = x.left;
            }
        } else {
            if (query.y() < x.p.y()) {
                first = x.left;
                second = x.right;
            } else {
                first = x.right;
                second = x.left;
            }
        }
        int nextDepth = depth + 1;
        if (first != null
                && first.rect.distanceSquaredTo(query) < closestDistance) {
            closest = findNearest(first, query, closest, closestDistance,
                    nextDepth);
            closestDistance = closest.distanceSquaredTo(query);
        }
        if (second != null
                && second.rect.distanceSquaredTo(query) < closestDistance) {
            closest = findNearest(second, query, closest, closestDistance,
                    nextDepth);
        }

        return closest;
    }

    public static void main(final String[] args) {

        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        KdTree2 kdtree = new KdTree2();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //StdOut.printf("%8.6f %8.6f\n", x, y);
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

        System.out.println("Size of tree is " + kdtree.size());
    }
}