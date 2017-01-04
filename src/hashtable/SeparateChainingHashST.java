/******************************************************************************
 *  Compilation:  javac SeparateChainingHashST.java
 *  Execution:    java SeparateChainingHashST < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/34hash/tinyST.txt
 *
 *  A symbol table implemented with a separate-chaining hash table.
 *
 ******************************************************************************/

package hashtable;

import edu.princeton.cs.algs4.*;

/**
 *  The SeparateChainingHashST class represents a symbol table of generic
 *  key-value pairs.
 *  It supports the usual put get contains, delete size, and is-empty methods.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be null—setting the value associated with a key to null is
 *  equivalent to deleting the key from the symbol table.
 *
 *  This implementation uses a separate chaining hash table. It requires that
 *  the key type overrides the equals() and hashCode() methods. The expected
 *  time per put contains, or remove operation is constant, subject to the
 *  uniform hashing assumption. The size, and is-empty operations take
 *  constant time. Construction takes constant time.
 *
 *  Proposition. Under unifirm assumption, prob. that the number of keys in a
 *  list is within a constant factor of N/M is extremely close to 1.
 *
 *  Consequence. Number of probes(equals() and hashcode()) for search/insert is
 *  proportional to N/M (M times faster than sequential search).
 *  - M to large => too many empty chains.
 *  - M too small => chains too long.
 *  - Typical choice: M ~ N/5 =>  constant-time ops.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/34hash">Section 3.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link BST}, {@link RedBlackBST}, and
 *  {@link LinearProbingHashST},
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 * @param <Key>
 * @param <Value>
 */
public final class SeparateChainingHashST<Key, Value> {
    /** Initial capacity. */
    private static final int INIT_CAPACITY = 4;
    /** Number of key-value pairs. */
    private int n;
    /** Hash table size. */
    private int m;
    /** Array of linked-list symbol tables. */
    private SequentialSearchST<Key, Value>[] st;

    /**
     * Initializes an empty symbol table.
     */
    private SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with {@code m} chains.
     * @param newm the initial number of chains
     */
    private SeparateChainingHashST(final int newm) {
        this.m = newm;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    /**
     * Resize the hash table to have the given number of chains, rehashing all.
     * of the keys
     * @param chains chains
     */
    private void resize(final int chains) {
        SeparateChainingHashST<Key, Value> temp
                = new SeparateChainingHashST<>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }

    /**
     * Hash value between 0 and m-1.
     * @param key the key
     * @return the hash
     */
    private int hash(final Key key) {

        return (key.hashCode() & 0x7fffffff) % m;
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
           throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the specified key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with {@code key} in the symbol table;
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        int i = hash(key);
        return st[i].get(key);
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting
     * the old value with the new value if the symbol table already contains
     * the specified key. Deletes the specified key (and its associated
     * value) from this symbol table if the specified value is null.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(final Key key, final Value val) {
        if (key == null) {
          throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            delete(key);
            return;
        }

        // double table size if average length of list >= 10
        if (n >= 10 * m) {
            resize(2 * m);
        }

        int i = hash(key);
        if (!st[i].contains(key)) {
            n++;
        }
        st[i].put(key, val);
    }

    /**
     * Removes specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }

        int i = hash(key);
        if (st[i].contains(key)) {
            n--;
        }
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2 * m) {
            resize(m / 2);
        }
    }

    /**
     * Return keys in symbol table as an Iterable.
     * @return queue
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

    /**
     * Unit tests the {@code SeparateChainingHashST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        SeparateChainingHashST<String, Integer> st
                                            = new SeparateChainingHashST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
