/******************************************************************************
 *  Compilation:  javac RedBlackBST.java
 *  Execution:    java RedBlackBST < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/33balanced/tinyST.txt
 *
 *  Red Black Binary Search Tree is a simple data structure that allows us to
 *  implement 2-3 Trees with very little extra code beyond the basic binary
 *  search tree.
 *
 *  A symbol table implemented using a left-leaning red-black BST.
 *  This is the 2-3 version.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java RedBlackBST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 ******************************************************************************/

package search;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.BST;

import java.util.NoSuchElementException;

/**
 *  The BST class represents an ordered symbol table of generic key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty
 *  methods.
 *  It also provides ordered methods for finding the minimum, maximum, floor,
 *  and ceiling. It also provides a keys method for iterating over all of the
 *  keys. A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be null—setting the value associated with a key to null is
 *  equivalent to deleting the key from the symbol table.
 *
 *  This implementation uses a left-leaning red-black BST. It requires that
 *  the key type implements the Comparable interface and calls the
 *  compareTo() and method to compare two keys. It does not call either
 *  equals() or hashCode().
 *  The put, contains, remove, minimum, maximum, ceiling, and floor
 *  operations each take logarithmic time in the worst case, if the tree
 *  becomes unbalanced.
 *  The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations of the same API, see {@link ST},
 *  {@link BinarySearchST}, {@link SequentialSearchST}, {@link BST},
 *  {@link SeparateChainingHashST}, {@link LinearProbingHashST}, and
 *  {@link AVLTreeST}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 * @param <Key>
 * @param <Value>
 */
public final class RedBlackBST<Key extends Comparable<Key>, Value> {
    /** Red. */
    private static final boolean RED   = true;
    /** Black. */
    private static final boolean BLACK = false;

    /** Root node. */
    private Node root;     // root of the BST
    /**
     * BST helper node data type.
     */
    private class Node {
        /** Key. */
        private Key key;
        /** Associated data. */
        private Value val;
        /** Links to left and right subtrees. */
        private Node left, right;
        /** Color of parent link. */
        private boolean color;
        /** Subtree count. */
        private int size;
        /**
         * Node constructor.
         * @param key the key
         * @param val the value
         * @param color the color
         * @param size the size
         */
        Node(final Key key, final Value val, final boolean color,
             final int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    private RedBlackBST() {
    }

    //**************************************************************************
    //  Node helper methods.
    //*************************************************************************/
    /**
     * Is node x red; false if x is null?
     * @param x the node
     * @return true if node is read; false otherwise
     */
    private boolean isRed(final Node x) {
        return x != null && x.color == RED;
    }

    /**
     * Number of node in subtree rooted at x; 0 if x is null.
     * @param x a node
     * @return the number of nodes in subtree rooted at x.
     */
    private int size(final Node x) {
        if (x == null)  {
            return 0;
        }
        return x.size;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    //**************************************************************************
    //  Standard BST search.
    //*************************************************************************/

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return  the value associated with the given key if the key is in the
     *          symbol table and {@code null} if the key is not in the symbol
     *          table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        return get(root, key);
    }

    /**
     * Value associated with the given key in subtree rooted at x; null if no.
     * such key.  Search is the same as for elementary BST but runs much
     * faster because of better balance.
     * @param x the node x
     * @param key the key
     * @return value associated with key in subtree rooted at x; null if no
     */
    private Value get(Node x, final Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.val;
            }
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return  {@code true} if this symbol table contains {@code key} and
     *          {@code false} otherwise
     * @throws  IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(final Key key) {
        return get(key) != null;
    }

    //**************************************************************************
    //  Red-black tree insertion.
    //*************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting
     * the old value with the new value if the symbol table already contains
     * the specified key. Deletes the specified key (and its associated
     * value) from this symbol table if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() "
                    + "is null");
        }
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
        // assert check();
    }
    /**
     * Insert the key-value pair in the subtree rooted at h.
     * @param h the node h
     * @param key the key
     * @param val the value
     * @return the node h
     */
    private Node put(Node h, final Key key, final Value val) {

        if (h == null) {
            return new Node(key, val, RED, 1);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left  = put(h.left,  key, val);
        } else if (cmp > 0) {
            h.right = put(h.right, key, val);
        } else {
            h.val   = val;
        }

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left)  &&  isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left)  &&  isRed(h.right)) {
            flipColors(h);
        }
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

    //**************************************************************************
    // Red-black tree deletion.
    // ************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        // assert check();
    }
    /**
     * Delete the key-value pair with the minimum key rooted at h.
     * @param h the node h
     * @return the return
     */
    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        // assert check();
    }

    /**
     * delete the key-value pair with the maximum key rooted at h.
     * @param h the node h
     * @return true if balanced; false otherwise
     */
    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null) {
            return null;
        }
        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);

        return balance(h);
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!contains(key)) {
            return;
        }
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        // assert check();
    }

    /**
     * Delete the key-value pair with the given key rooted at h.
     * @param h the node h
     * @param key the key
     * @return Node
     */
    private Node delete(Node h, final Key key) {
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    //**************************************************************************
    //  Red-black tree helper functions.
    //*************************************************************************/
    /**
     * Invariant: maintains symmetric order and perfect black balance.
     * Make a left-leaning link lean to the right.
     * @param h the node h
     * @return Node
     */
    private Node rotateRight(final Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * Invariant: maintains symmetric order and perfect black balance.
     * Make a right-leaning link lean to the left.
     * @param h the node h
     * @return Node
     */
    private Node rotateLeft(final Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * Flip the colors of a node and its two children.
     *  h must have opposite color of its two children
     * @param h the node h
     */
    private void flipColors(final Node h) {
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //     || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    /**
     * Assuming that h is red and both h.left and h.left.left are black, make
     * h.left or one of its children red.
     * @param h the node h
     * @return Node
     */
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * Assuming that h is red and both h.right and h.right.left are black,
     * make h.right or one of its children red.
     * @param h the node h
     * @return Node
     */
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * Restore red-black tree invariant.
     * @param h the node h
     * @return Node
     */
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    //**************************************************************************
    //  Utility functions.
    //*************************************************************************/

    /**
     * Returns the height of the BST (for debugging).
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    /**
     * Returns the height of the BST (for debugging).
     * @param x the node x
     * @return the height of the BST (a 1-node tree has height 0)
     */
    private int height(final Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(height(x.left), height(x.right));
    }

    //**************************************************************************
    //   Ordered symbol table methods.
    //*************************************************************************/

    /**
     * Returns the smallest key in the symbol table.
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    private Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty "
                    + "symbol table");
        }
        return min(root).key;
    }

    /**
     * The smallest key in subtree rooted at x; null if no such key.
     * @param x the node x
     * @return Node
     */
    private Node min(final Node x) {
        // assert x != null;
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }

    /**
     * Returns the largest key in the symbol table.
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty "
                    + "symbol table");
        }
        return max(root).key;
    }

    /**
     * The largest key in the subtree rooted at x; null if no such key.
     * @param x the node x
     * @return Node
     */
    private Node max(final Node x) {
        // assert x != null;
        if (x.right == null) {
            return x;
        } else {
            return max(x.right);
        }
    }

    /**
     * Returns the largest key in the symbol table less than or equal to key.
     * @param key the key
     * @return the largest key in the symbol table less than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("called floor() with empty "
                    + "symbol table");
        }
        Node x = floor(root, key);
        if (x == null) {
            return null;
        } else {
            return x.key;
        }
    }

    /**
     * The largest key in the subtree rooted at x less than or equal to the
     * given key.
     * @param x the node x
     * @param key the key
     * @return Node
     */
    private Node floor(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0)  {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    /**
     * Returns the smallest key in symbol table greater than or equal to key.
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("called ceiling() with empty "
                    + "symbol table");
        }
        Node x = ceiling(root, key);
        if (x == null) {
            return null;
        } else {
            return x.key;
        }
    }

    /**
     * the smallest key in the subtree rooted at x greater than or equal to
     * the given key.
     * @param x the node x
     * @param key the key
     * @return Node
     */
    private Node ceiling(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp > 0) {
            return ceiling(x.right, key);
        }
        Node t = ceiling(x.left, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    /**
     * Return the kth smallest key in the symbol table.
     * @param   k the order statistic
     * @return  the kth smallest key in the symbol table
     * @throws  IllegalArgumentException unless {@code k} is between 0 and
     *          <em>N</em> &minus; 1
     */
    private Key select(final int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException();
        }
        Node x = select(root, k);
        return x.key;
    }

    /**
     * The key of rank k in the subtree rooted at x.
     * @param x the node x
     * @param k the rank k
     * @return Node
     */
    private Node select(final Node x, final int k) {
        // assert x != null;
        // assert k >= 0 && k < size(x);
        int t = size(x.left);
        if      (t > k) {
            return select(x.left,  k);
        } else if (t < k) {
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }

    /**
     * Return the number of keys in the symbol table strictly less than key.
     * @param key the key
     * @return the number of keys in the symbol table strictly less than key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private int rank(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        return rank(key, root);
    }

    /**
     * Number of keys less than key in the subtree rooted at x.
     * @param key the key
     * @param x the node x
     * @return number of keys less than key in the subtree rooted at x.
     */
    private int rank(final Key key, final Node x) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }

    //**************************************************************************
    //   Range count and range search.
    // ************************************************************************/

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<Key> keys() {
        if (isEmpty()) {
            return new Queue<>();
        }
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the sybol table between {@code lo}
     *    (inclusive) and {@code hi} (inclusive) as an {@code Iterable}
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *    is {@code null}
     */
    public Iterable<Key> keys(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() "
                    + "is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() "
                    + "is null");
        }

        Queue<Key> queue = new Queue<>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
    }

    /**
     * Add the keys between lo and hi in the subtree rooted at x
     * to the queue.
     * @param x the node x
     * @param queue the queue
     * @param lo the lo key
     * @param hi the hi key
     */
    private void keys(final Node x, final Queue<Key> queue, final Key lo,
                      final Key hi) {
        if (x == null) {
            return;
        }
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) {
            keys(x.left, queue, lo, hi);
        }
        if (cmplo <= 0 && cmphi >= 0) {
            queue.enqueue(x.key);
        }
        if (cmphi > 0) {
            keys(x.right, queue, lo, hi);
        }
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return the number of keys in the sybol table between {@code lo}
     *    (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *    is {@code null}
     */
    public int size(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() "
                    + "is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to size() "
                    + "is null");
        }
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }

   //***************************************************************************
   // Check integrity of red-black tree data structure.
   // *************************************************************************/

    /**
     * Check.
     * @return check
     */
    private boolean check() {
        if (!isBST()) {
            StdOut.println("Not in symmetric order");
        }
        if (!isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent");
        }
        if (!isRankConsistent()) {
            StdOut.println("Ranks not consistent");
        }
        if (!is23()) {
            StdOut.println("Not a 2-3 tree");
        }
        if (!isBalanced()) {
            StdOut.println("Not balanced");
        }
        return isBST() && isSizeConsistent() && isRankConsistent() && is23()
                       && isBalanced();
    }

    /**
     * Does this binary tree satisfy symmetric order? Note: this test also
     * ensures that data structure is a binary tree since order is strict.
     * @return true if binary tree; false otherweise
     */
    private boolean isBST() {
        return isBST(root, null, null);
    }

    /**
     * Is the tree rooted at x a BST with all keys strictly between min and max
     * (if min or max is null, treat as empty constraint).
     * Credit: Bob Dondero's elegant solution.
     * @param x the node x
     * @param min the min key
     * @param max the max key
     * @return true if tree rooted at x; false otherwise
     */
    private boolean isBST(final Node x, final Key min, final Key max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
            return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    /**
     * Are the size fields correct?
     * @return true if size fields correct; false otherwise
     */
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    /**
     * Are the size fields correct?
     * @param x the node x
     * @return true if size fields correct; false otherwise
     */
    private boolean isSizeConsistent(final Node x) {
        if (x == null) {
            return true;
        }
        if (x.size != size(x.left) + size(x.right) + 1) {
            return false;
        }
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    /**
     * Check that ranks are consistent.
     * @return true if ranks are consistent; false otherwise
     */
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (Key key : keys()) {
            if (key.compareTo(select(rank(key))) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Does the tree have no red right links, and at most one (left) red
     * links in a row on any path?
     * @return true if tree is 23 tree; false otherwise
     */
    private boolean is23() {
        return is23(root);
    }

    /**
     * Does the tree have no red right links, and at most one (left) red
     * links in a row on any path?
     * @param x the node x
     * @return true if tree is 23 tree; false otherwise
     */
    private boolean is23(final Node x) {
        if (x == null) {
            return true;
        }
        if (isRed(x.right)) {
            return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }
        return is23(x.left) && is23(x.right);
    }

    /**
     * Do all paths from root to leaf have same number of black edges?
     * @return true if tree is balanced; false otherwise
     */
    private boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) {
                black++;
            }
            x = x.left;
        }
        return isBalanced(root, black);
    }

    /**
     *  Does every path from the root to a leaf have the given number of
     *  black links?
     *  @param x the node x
     *  @param black black
     *  @return true if tree balanced; false otherwise
     */
    private boolean isBalanced(final Node x, int black) {
        if (x == null) {
            return black == 0;
        }
        if (!isRed(x)) {
            black--;
        }
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    /**
     * Unit tests the {@code RedBlackBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        RedBlackBST<String, Integer> st = new RedBlackBST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
        StdOut.println();
    }
}
