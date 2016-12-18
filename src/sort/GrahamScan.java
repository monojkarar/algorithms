package sort;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Point2D;

import java.util.Arrays;

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
public class GrahamScan {

}
