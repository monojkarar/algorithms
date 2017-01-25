/******************************************************************************
 *  Compilation:  javac BTree.java
 *  Execution:    java BTree
 *  Dependencies: StdOut.java
 *
 *  B-tree.
 *
 *  Limitations
 *  -----------
 *   -  Assumes M is even and M >= 4
 *   -  should b be an array of children or list (it would help with
 *      casting to make it a list)
 *
 ******************************************************************************/

package symboltable;

import edu.princeton.cs.algs4.StdOut;

/**
 *  The BTree class represents an ordered symbol table of generic key-value
 *  pairs. BTree is a generalization of balanced trees. Generalize 2-3 trees
 *  by allowing up to M-1 key-link pairs per node.
 *  - At least 2 key-link pairs at root.
 *  - At least M/2 key-link pairs in other nodes.
 *  - External nodes contain client keys.
 *  - Internal nodes contain copies of keys to guide search.
 *
 *  It supports the put, get, contains, size, and is-empty methods.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the value associated with a key to
 *  null is equivalent to deleting the key from the symbol table.
 *
 *  This implementation uses a B-tree. It requires that the key type
 *  implements the Comparable interface and calls the compareTo() and method
 *  to compare two keys. It does not call either equals() or hashCode().
 *  The get, put, and contains operations each make log<sub>m</sub>(n) probes
 *  in the worst case, where nis the number of key-value pairs and m
 *  is the branching factor.
 *  The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/62btree">Section 6.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @param <Key> the Key
 * @param <Value> the Value
 */
public final class BTree<Key extends Comparable<Key>, Value> {

    /** max children per B-tree node = M-1(must be even and greater than 2).*/
    private static final int M = 4;
    /** Root of the B-tree. */
    private Node root;
    /** Height of the B-tree. */
    private int height;
    /** Number of key-value pairs in the B-tree. */
    private int n;

    /** Helper B-tree node data type. */
    private static final class Node {

        /** Number of children. */
        private int m;
        /** The array of children. */
        private Entry[] children = new Entry[M];

        /**
         * Create a node with k children.
         * @param k the number of children
         */
        private Node(final int k) {
            m = k;
        }
    }

    /**
     * Internal nodes: only use key and next.
     * External nodes: only use key and value.
     */
    private static class Entry {
        /** Key. */
        private Comparable key;
        /** Value. */
        private final Object val;
        /** Helper field to iterate over array entries. */
        private Node next;

        /**
         * Constructor.
         * @param newKey the key
         * @param newVal the value
         * @param newNext the next key
         */
        Entry(final Comparable newKey, final Object newVal, final Node newNext) {
            this.key = newKey;
            this.val = newVal;
            this.next = newNext;
        }
    }

    /** Initializes an empty B-tree. */
    private BTree() {

        root = new Node(0);
    }

    /**
     * Returns true if this symbol table is empty.
     * @return true if this symbol table is empty; false otherwise
     */
    public boolean isEmpty() {

        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {

        return n;
    }

    /**
     * Returns the height of this B-tree (for debugging).
     * @return the height of this B-tree
     */
    private int height() {
        return height;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the
     * symbol table and null if the key is not in the symbol table
     * @throws IllegalArgumentException if key is null
     */
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        return search(root, key, height);
    }

    /**
     * Search.
     * @param x the node x
     * @param key the key
     * @param ht the ht
     * @return the Value
     */
    private Value search(final Node x, final Key key, final int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) {
                    return (Value) children[j].val;
                }
            }
        } else { // internal node
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key)) {
                    return search(children[j].next, key, ht - 1);
                }
            }
        }
        return null;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old
     * value with the new value if the key is already in the symbol table.
     * If value is null, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to put() is null");
        }
        Node u = insert(root, key, val, height);
        n++;
        if (u == null) {
            return;
        }

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    /**
     * Insert.
     * @param h the node h
     * @param key the key
     * @param val the value
     * @param ht the ht
     * @return the node
     */
    private Node insert(final Node h, final Key key,
                        final Value val, final int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) {
                    break;
                }
            }
        } else { // internal node
            for (j = 0; j < h.m; j++) {
                if ((j + 1 == h.m) || less(key, h.children[j + 1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht - 1);
                    if (u == null) {
                        return null;
                    }
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) {
            h.children[i] = h.children[i - 1];
        }
        h.children[j] = t;
        h.m++;
        if (h.m < M) {
            return null;
        } else {
            return split(h);
        }
    }

    // split node in half

    /**
     * Split node in half.
     * @param h the node h
     * @return Node
     */
    private Node split(final Node h) {
        Node t = new Node(M / 2);
        h.m = M / 2;
        for (int j = 0; j < M / 2; j++) {
            t.children[j] = h.children[M / 2 + j];
        }
        return t;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.
     */
    public String toString() {

        return toString(root, height, "") + "\n";
    }

    /**
     * toStriing.
     * @param h the node h
     * @param ht the ht
     * @param indent the indent
     * @return String
     */
    private String toString(final Node h, final int ht, final String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " " + children[j].val
                        + "\n");
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) {
                    s.append(indent + "(" + children[j].key + ")\n");
                }
                s.append(toString(children[j].next, ht - 1, indent
                        + "     "));
            }
        }
        return s.toString();
    }

    /**
     * Comparison functions - make Comparable instead of Key to avoid casts.
     * @param k1 the k1
     * @param k2 the k2
     * @return true if k1 < k2; false otherwise
     */
    private boolean less(final Comparable k1, final Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    /**
     * Eq.
     * @param k1 the k1
     * @param k2 the k2
     * @return true if k1 == k2; false otherwise
     */
    private boolean eq(final Comparable k1, final Comparable k2) {
        return k1.compareTo(k2) == 0;
    }


    /**
     * Unit tests the {@code BTree} data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        BTree<String, String> st = new BTree<>();

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu", "128.112.128.15");
        st.put("www.yale.edu", "130.132.143.21");
        st.put("www.simpsons.com", "209.052.165.60");
        st.put("www.apple.com", "17.112.152.32");
        st.put("www.amazon.com", "207.171.182.16");
        st.put("www.ebay.com", "66.135.192.87");
        st.put("www.cnn.com", "64.236.16.20");
        st.put("www.google.com", "216.239.41.99");
        st.put("www.nytimes.com", "199.239.136.200");
        st.put("www.microsoft.com", "207.126.99.140");
        st.put("www.dell.com", "143.166.224.230");
        st.put("www.slashdot.org", "66.35.250.151");
        st.put("www.espn.com", "199.181.135.201");
        st.put("www.weather.com", "63.111.66.11");
        st.put("www.yahoo.com", "216.109.118.65");


        StdOut.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        StdOut.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        StdOut.println("simpsons.com:      " + st.get("www.simpsons.com"));
        StdOut.println("apple.com:         " + st.get("www.apple.com"));
        StdOut.println("ebay.com:          " + st.get("www.ebay.com"));
        StdOut.println("dell.com:          " + st.get("www.dell.com"));
        StdOut.println();

        StdOut.println("size:    " + st.size());
        StdOut.println("height:  " + st.height());
        StdOut.println(st);
        StdOut.println();
    }
}



