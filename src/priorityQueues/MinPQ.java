/******************************************************************************
 *  Compilation:  javac MinPQ.java
 *  Execution:    java MinPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *
 *  Generic min priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order.
 *
 *  % java MinPQ < tinyPQ.txt
 *  E A E (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges
 *  (ala insertion sort).
 *
 ******************************************************************************/

package priorityqueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The MinPQ class represents a priority queue of generic keys.
 *  It supports the usual insert and delete-the-minimum operations, along
 *  with methods for peeking at the minimum key, testing if the priority
 *  queue is empty, and iterating through the keys.
 *
 *  This implementation uses a binary heap.
 *  The insert and delete-the-minimum operations take logarithmic amortized
 *  time. The min, size, and is-empty operations take constant time.
 *  Construction takes time proportional to the specified capacity or the
 *  number of items used to initialize the data structure.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Key> the generic type of key on this priority queue
 */
public class MinPQ<Key> implements Iterable<Key> {

    /** Store items at indices 1 to n. */
    private Key[] pq;
    /** Number of items on priority queue. */
    private int n;
    /** Optional comparator. */
    private Comparator<Key> comparator;

    /**
     * Initializes an empty priority queue with the given initial capacity.
     * @param  initCapacity the initial capacity of this priority queue
     */
    MinPQ(final int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    /**
     * Initializes an empty priority queue.
     */
    private MinPQ() {
        this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * using the given comparator.
     * @param  initCapacity the initial capacity of this priority queue
     * @param  newComparator the order to use when comparing keys
     */
    private MinPQ(final int initCapacity, final Comparator<Key> newComparator) {
        this.comparator = newComparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    /**
     * Initializes an empty priority queue using the given comparator.
     * @param  newComparator the order to use when comparing keys
     */
    public MinPQ(final Comparator<Key> newComparator) {

        this(1, newComparator);
    }

    /**
     * Initializes a priority queue from the array of keys.
     * Takes time proportional to the number of keys, using sink-based heap
     * construction.
     *
     * @param  keys the array of keys
     */
    public MinPQ(final Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
            pq[i + 1] = keys[i];
        }
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMinHeap();
    }

    /**
     * Returns true if this priority queue is empty.
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {

        return n == 0;
    }

    /**
     * Returns the number of keys on this priority queue.
     * @return the number of keys on this priority queue
     */
    public int size() {

        return n;
    }

    /**
     * Returns a smallest key on this priority queue.
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return pq[1];
    }

    /**
     * Helper function to double the size of the heap array.
     * @param capacity the capacity
     */
    private void resize(final int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * Adds a new key to this priority queue.
     * @param  x the key to add to this priority queue
     */
    public void insert(final Key x) {
        // double size of array if necessary
        if (n == pq.length - 1) {
            resize(2 * pq.length);
        }

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMinHeap();
    }

    /**
     * Removes and returns a smallest key on this priority queue.
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    Key delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        exch(1, n);
        Key min = pq[n--];
        sink(1);
        pq[n + 1] = null;    // avoid loitering and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4)) {
            resize(pq.length  / 2);
        }
        assert isMinHeap();
        return min;
    }

    //**************************************************************************
    // * Helper functions to restore the heap invariant.
    //*************************************************************************/

    /**
     * Swim function.
     * @param k the k
     */
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * Sink function.
     * @param k the k
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    //**************************************************************************
    //   Helper functions for compares and swaps.
    // ************************************************************************/
    /**
     * True if i > j; false otherwise.
     * @param i item i
     * @param j item j
     * @return true if i > j; false otherwise
     */
    private boolean greater(final int i, final int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        } else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    /**
     * Swap items i and j.
     * @param i the item i
     * @param j the item j
     */
    private void exch(final int i, final int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    /**
     * Is pq[1..N[ a min heap?
     * @return true if min heap; false otherwise
     */
    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    /**
     * Is subtree of pq[1..n] rooted at k a min heap?
     * @param k the root k
     * @return true if subtree of pq[1..n] rooted at k a min heap
     */
    private boolean isMinHeap(final int k) {
        if (k > n) {
            return true;
        }
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left  <= n && greater(k, left))  {
            return false;
        }
        if (right <= n && greater(k, right)) {
            return false;
        }
        return isMinHeap(left) && isMinHeap(right);
    }

    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in ascending order. The iterator doesn't implement remove()
     * since it's optional.
     *
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    /**
     *  Class HeapIterator.
     */
    private class HeapIterator implements Iterator<Key> {
        /** Create a new pq. */
        private MinPQ<Key> copy;

        /** Add all items to copy of heap.
         *  Takes linear time since already in heap order so no keys move.
         */
        HeapIterator() {
            if (comparator == null) {
                copy = new MinPQ<Key>(size());
            } else {
                copy = new MinPQ<Key>(size(), comparator);
            }
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i]);
            }
        }

        /**
         * Return true if there is a next item.
         * @return true if there is a next item; false otherwise.
         */
        public boolean hasNext() {

            return !copy.isEmpty();
        }

        /** Function not supported. */
        public void remove() {

            throw new UnsupportedOperationException();
        }

        /**
         * Return the next minimum item.
         * @return the next minimum item
         */
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMin();
        }
    }

    /**
     * Unit tests the MinPQ data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        MinPQ<String> pq = new MinPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                pq.insert(item);
            } else if (!pq.isEmpty()) {
                StdOut.print(pq.delMin() + " ");
            }
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
}
