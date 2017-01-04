/******************************************************************************
 *  Compilation:  javac SequentialSearchST.java
 *  Execution:    java SequentialSearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java SequentialSearchST < tiny.txt 
 *  L 11
 *  P 10
 *  M 9
 *  X 7
 *  H 5
 *  C 4
 *  R 3
 *  A 8
 *  E 12
 *  S 0
 *
 ******************************************************************************/

package hashtable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *  The SequentialSearchST class represents an (unordered) symbol table of
 *  generic key-value pairs. It supports the usual put, get, contains,
 *  delete, size, and is-empty methods. It also provides a keys method for
 *  iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  The class also uses the convention that values cannot be null. Setting the
 *  value associated with a key to null is equivalent to deleting the key
 *  from the symbol table.
 *
 *  This implementation uses a singly-linked list and sequential search.
 *  It relies on the equals() method to test whether two keys are equal. It
 *  does not call either the compareTo() or hashCode() method.
 *  The put and delete operations take linear time; the get and contains
 *  operations takes linear time in the worst case. The size, and is-empty
 *  operations take constant time. Construction takes constant time.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 * @param <Key>
 * @param <Value>
 */
public final class SequentialSearchST<Key, Value> {
    /** number of key-value pairs. */
    private int n;
    /** The linked list of key-value pairs. */
    private Node first;

    // a helper linked list data type

    /** A helper linked list data type. */
    private class Node {
        /** The key. */
        private Key key;
        /** The value. */
        private Value val;
        /** The next node. */
        private Node next;

        /**
         * The Node class.
         * @param newKey the key
         * @param newVal the value
         * @param newNext the next node
         */
        Node(final Key newKey, final Value newVal, final Node newNext) {
            this.key  = newKey;
            this.val  = newVal;
            this.next = newNext;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    SequentialSearchST() {
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to "
                    + "contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the
     *         symbol table and {@code null} if the key is not in the symbol
     *         table
     * @throws IllegalArgumentException if key is null
     */
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get"
                    +  "() is null");
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {

                return x.val;
            }
        }
        return null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting
     * the old value with the new value if the symbol table already contains
     * the specified key. Deletes the specified key (and its associated
     * value) from this symbol table if the specified value is null.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if key is null
     */
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument "
                    + "to put() is null");
        }
        if (val == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        first = new Node(key, val, first);
        n++;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        first = delete(first, key);
    }

    /**
     *  Delete key in linked list beginning at Node x warning: function call
     *  stack too large if table is large.
     *  @param x the node
     *  @param key the key
     *  @return the next node
     */
    private Node delete(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        if (key.equals(x.key)) {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys()  {
        Queue<Key> queue = new Queue<>();
        for (Node x = first; x != null; x = x.next) {
            queue.enqueue(x.key);
        }
        return queue;
    }


    /**
     * Unit tests the {@code SequentialSearchST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
