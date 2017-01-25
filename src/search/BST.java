/******************************************************************************
 *  Compilation:  javac BST.java
 *  Execution:    java BST
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/32bst/tinyST.txt
 *
 *  A symbol table implemented with a binary search tree.
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java BST < tinyST.txt
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

import java.util.NoSuchElementException;

/**
 *  The BST class represents an ordered symbol table of generic key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty
 *  methods. It also provides ordered methods for finding the minimum, >maximum,
 *  floor, select, ceiling.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be null—setting the value associated with a key to null is
 *  equivalent to deleting the key from the symbol table.
 *
 *  This implementation uses an (unbalanced) binary search tree. It requires
 *  that the key type implements the Comparable interface and calls the
 *  compareTo() and method to compare two keys. It does not call either
 *  equals() or hashCode(). The put, contains, remove, minimum, maximum,
 *  ceiling, floor, select, and rank operations each take linear time in the
 *  worst case, if the tree becomes unbalanced. The size, and is-empty
 *  operations take constant time. Construction takes constant time.
 *
 *  operation           running time
                        (unbalanced tree)
 *  put                 N
 *  contains            N
 *  delete              N
 *  min/max             N
 *  floor/ceiling       N
 *  rank                N
 *  select              N
 *  size                1
 *  is-empty            1
 *  construction        1
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/32bst">Section 3.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link RedBlackBST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 * Class BST. A BST is a binary tree in symmetric order.  the tree shapes
 * depends on the order the data comes in.Binary search trees correspond to
 * Quicksort partitioning.
 *
 * Problem: We don't get to randomize the order because the client is
 * providing the keys.
 *
 * @param <Key> the Key
 * @param <Value> teh Value
 */
public final class BST<Key extends Comparable<Key>, Value> {
    /** Root of BST. */
    private Node root;

    /**
     * The Node class.
     */
    private class Node {
        /** Sorted by key. */
        private Key key;
        /** Associated data. */
        private Value val;
        /** Left and right subtree. */
        private Node left, right;
        /** number of nodes in subtree. */
        private int size;

        /**
         * the Constructor.
         * @param newKey the key
         * @param newVal the value
         * @param newSize the size
         */
        Node(final Key newKey, final Value newVal, final int newSize) {
            this.key = newKey;
            this.val = newVal;
            this.size = newSize;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    private BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     * @return {true if this symbol table is empty; false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {

        return size(root);
    }

    /**
     * Return number of key-value pairs in BST rooted at x.
     * @param x the x
     * @return the int
     */
    private int size(final Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is "
                    + "null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the
     *         symbol table and {@code null} if the key is not in the symbol
     *         table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(final Key key) {
        return get(root, key);
    }

    /**
     * Get.
     * @param x the x
     * @param key the key
     * @return the Value
     */
    private Value get(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting
     * the old value with the new value if the symbol table already contains
     * the specified key. Deletes the specified key (and its associated
     * value) from this symbol table if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
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
        assert check();
    }

    /**
     * Put.
     * @param x the x
     * @param key the key
     * @param val the val
     * @return the Node
     */
    private Node put(final Node x, final Key key, final Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left  = put(x.left,  key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val   = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMin(root);
        assert check();
    }

    /**
     * the Node.
     * @param x the x
     * @return the Node
     */
    private Node deleteMin(final Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMax(root);
        assert check();
    }

    /**
     * Delete the max item.
     * @param x the x
     * @return the Node
     */
    private Node deleteMax(final Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
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
        root = delete(root, key);
        assert check();
    }

    /**
     * The delete using Hibbard deletion. Not symmetric. Height of tree
     * becomes sqrt(N). Longstanding open problem. Simple and efficient
     * delete for BSTs.
     * @param x the x
     * @param key the key
     * @return the Node
     */
    private Node delete(Node x, final Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {                          // search for key.
            x.left  = delete(x.left,  key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {              // no right child.
                return x.left;
            }
            if (x.left  == null) {              // no left child.
                return x.right;
            }
            Node t = x;
            x = min(t.right);                   // replace with successor.
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1; // update subtree counts.
        return x;
    }
    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    private Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol "
                    + "table");
        }
        return min(root).key;
    }
    /**
     * The Min.
     * @param x the x
     * @return the Node
     */
    private Node min(final Node x) {
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }
    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol "
                    + "table");
        }
        return max(root).key;
    }

    /**
     * The max.
     * @param x the x
     * @return the Node
     */
    private Node max(final Node x) {
        if (x.right == null) {
            return x;
        } else {
            return max(x.right);
        }
    }

    /**
     * Returns the largest key in the symbol table less than or equal to key.
     *
     * @param  key the key
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
     * The floor.
     * @param x the node x
     * @param key the key
     * @return the Node that is the floor
     */
    private Node floor(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp <  0) {
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
     * Returns smallest key in the symbol table greater than or equal to key.
     *
     * @param  key the key
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
     * The ceiling.
     * @param x the x
     * @param key the key
     * @return the Node
     */
    private Node ceiling(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) {
                return t;
            } else {
                return x;
            }
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the kth smallest key in the symbol table.
     *
     * @param  k the order statistic
     * @return the kth smallest key in the symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>N</em> &minus; 1
     */
    private Key select(final int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException();
        }
        Node x = select(root, k);
        return x.key;
    }
    /**
     * Return key of rank k.
     * @param x the x
     * @param k the k
     * @return the Node
     */
    private Node select(final Node x, final int k) {
        if (x == null) {
            return null;
        }
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
     *
     * @param  key the key
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
     * Number of keys in teh subtree less than key.
     * @param key the key
     * @param x the x
     * @return the rank
     */
    private int rank(final Key key, final Node x) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    private Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    private Iterable<Key> keys(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is "
                    + "null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() "
                    + "is+null");
        }
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    /**
     * The keys.
     * @param x the x
     * @param queue the queue
     * @param lo the lo
     * @param hi the hi
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
     * @return the number of keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public int size(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is "
                    + "null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is "
                    + "null");
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

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    /**
     * The height.
     * @param x teh x
     * @return the int
     */
    private int height(final Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    private Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<>();
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    //************************************************************************
    //   Check integrity of BST data structure.
    //************************************************************************/

    /**
     * The check.
     * @return the boolean
     */
    private boolean check() {
        if (!isBST())            {
            StdOut.println("Not in symmetric order");
        }
        if (!isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent");
        }
        if (!isRankConsistent()) {
            StdOut.println("Ranks not consistent");
        }
        return isBST() && isSizeConsistent() && isRankConsistent();
    }
    /**
     * Does this binary tree satisfy symmetric order?
     * Note: this test also ensures that data structure is a binary tree since
     * order is strict
     * @return the boolean
     */
    private boolean isBST() {
        return isBST(root, null, null);
    }
    /**
     *  Is the tree rooted at x a BST with all keys strictly between min and
     *  max (if min or max is null, treat as empty constraint) Credit: Bob
     *  Dondero's elegant solution.
     * @param x the x
     * @param min the min
     * @param max the max
     * @return the boolean
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
     * @return the boolean
     */
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    /**
     * isSizeConsistent.
     * @param x the x
     * @return the boolean
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
     * @return boolean
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
     * Unit tests the {@code BST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        BST<String, Integer> st = new BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st.levelOrder()) {
            StdOut.println(s + " " + st.get(s));
        }
        StdOut.println();

        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}



