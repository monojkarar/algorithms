package symbolTable;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 *  The SymbolTable class represents an ordered symbol table of generic key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty
 *  methods.
 *
 *  It also provides ordered methods for finding the minimum, maximum, floor,
 *  and ceiling. It also provides a keys method for iterating over all of the
 *  keys. A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be null—setting the value associated with a key to null is
 *  equivalent to deleting the key from the symbol table.
 *
 *  This implementation uses a balanced binary search tree. It requires that
 *  the key type implements the Comparable interface and calls the
 *  compareTo() and method to compare two keys. It does not call either
 *  equals() or hashCode().
 *  The put, contains, remove, minimum, maximum, ceiling, and floor
 *  operations each take logarithmic time in the worst case.
 *  The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/35applications">Section 3.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Key> the generic type of keys in this symbol table
 *  @param <Value> the generic type of values in this symbol table
 */
public final class SymbolTable<Key extends Comparable<Key>, Value> implements
        Iterable<Key> {
    /** The st. */
    private TreeMap<Key, Value> st;
    /**
     * Initializes an empty symbol table.
     */
    private SymbolTable() {
        st = new TreeMap<>();
    }
    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in this
     *         symbol table;
     *         null if the key is not in this symbol table
     * @throws IllegalArgumentException if key is null
     */
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called get() with "
                    + "null key");
        }
        return st.get(key);
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
    private void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("called put() with "
                    + "null key");
        }
        if (val == null) {
            st.remove(key);
        } else {
            st.put(key, val);
        }
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called delete() "
                    + "with null key");
        }
        st.remove(key);
    }

    /**
     * Returns true if this symbol table contain the given key.
     *
     * @param  key the key
     * @return true if this symbol table contains key and
     *         false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called contains() with null "
                    + "key");
        }
        return st.containsKey(key);
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return st.size();
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return true if this symbol table is empty and false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns all keys in this symbol table.
     *
     * To iterate over all of the keys in the symbol table named st,
     * use the foreach notation: for Key key : st.keys().
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {
        return st.keySet();
    }

    /**
     * Returns all of the keys in this symbol table.
     * To iterate over all of the keys in a symbol table named st, use
     * the foreach notation: for Key key : st.
     *
     * This method is provided for backward compatibility with the version from
     * Introduction to Programming in Java: An Interdisciplinary Approach.
     *
     * @return     an iterator to all of the keys in this symbol table
     * @deprecated Replaced by {@link #keys()}.
     */
    @Deprecated
    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol "
                    + "table");
        }
        return st.firstKey();
    }

    /**
     * Returns the largest key in this symbol table.
     *
     * @return the largest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol "
                    + "table");
        }
        return st.lastKey();
    }

    /**
     * Returns smallest key in this symbol table greater than or equal to key.
     *
     * @param  key the key
     * @return smallest key in this symbol table greater than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called ceiling() with null "
                    + "key");
        }
        Key k = st.ceilingKey(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are less than " + key);
        }
        return k;
    }

    /**
     * Returns the largest key in this symbol table less than or equal to key.
     *
     * @param  key the key
     * @return the largest key in this symbol table less than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Key floor(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called floor() with null key");
        }
        Key k = st.floorKey(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are greater than "
                    + key);
        }
        return k;
    }

    /**
     * Return true if v < w; false otherwise.
     * @param v the variable v
     * @param w tje variable y
     * @return boolean true if v < y; false otherwise.
     */
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * Return true if v == w; false otherwise.
     * @param v the variable v
     * @param w tje variable y
     * @return boolean true if v == y; false otherwise.
     */
    private static boolean equal(final Comparable v, final Comparable w) {
        return v.compareTo(w) == 0;
    }

    /**
     * SwapValues.
     * @param array the array
     * @param indexOne the indexOne
     * @param indexTwo the index Two
     */
    private static void exch(final Comparable[] array, final int indexOne, final
    int indexTwo) {

        Comparable temp = array[indexOne];
        array[indexOne] = array[indexTwo];
        array[indexTwo] = temp;
    }
    /**
     * Unit tests the SymbolTable data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        SymbolTable<String, Integer> st = new SymbolTable<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
