/******************************************************************************
 *  Compilation:  javac MaxPQ.java
 *  Execution:    java MaxPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *  
 *  Generic max priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order,
 *  but the generic Key type must still be Comparable.
 *
 *  % java MaxPQ < tinyPQ.txt 
 *  Q X P (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges 
 *  (ala insertion sort).
 *
 ******************************************************************************/

package queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 *  The MaxPQ class represents a priority queue of generic keys.
 *  It supports the usual insert and delete-the-maximum
 *  operations, along with methods for peeking at the maximum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *
 *  This implementation uses a binary heap.
 *  The insert and delete-the-maximum operations take logarithmic amortized
 *  time.
 *  The max, size, and is-empty operations take constant time.
 *  Construction takes time proportional to the specified capacity
 *  or the number of items used to initialize the data structure.
 *
 *  @param <Key> the generic type of key on this priority queue
 *
 *  Runtime analysis: order-of-growth running time for PQ with N items
 *  Algorithm   Average     Worst
 *  Space       O(n)        O(n)
 *  Search      O(n)        O(n)
 *  Insert      O()         O(log n)
 *  Delete Max  O(log n)    O(log n)
 *  Peek        O(1)        O(1)
 *
 *  Immutability of keys.
 *  - Assumption: client does not change keys while they're on the PQ.
 *  - Best practice: use immutable keys.
 *
 *  Underflow and Overflow.
 *  - Underflow: throw exception if deleting from empty PQ.
 *  - Overflow: add no-arg constructor and use resizing array (leads to log N
 *              amortized time per operation)
 *
 *  Minimum-oriented priority queue.
 *  - Replace less() with greater().
 *  - Implement greater().
 *
 *  Other operations. Can implement with sink() or swim().
 *  - Remove an arbitrary item.
 *  - Change the priority of an item.
 *
 *  Immutability: implementing ni Java
 *  Data type. Set values and operations on those values.
 *  Immutable data type. Can't change teh data type value once created.
 *
 *  Immutable. String, Integer, Double, Color, Vector, Transaction, Point2D.
 *  Mutable. StringBuilder, Stack, Counter, Java array.
 *
 *  Advantages.
 *  - Simplifies debugging.
 *  - Safer in presence of hostile code.
 *  - Simplifies concurrent programming.
 *  - Safe to use as key in priority queue or symbol table.
 *
 *  Disadvantage. Must create new object for each data type value.
 */

public class MaxPriorityQueue<Key> implements Iterable<Key> {
    /** Store items at indices 1 to n. */
    private Key[] pq;
    /** Number of items on priority queue. */
    private int n;
    /** Optional comparator. */
    private Comparator<Key> comparator;
    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param  initCapacity the initial capacity of this priority queue
     */
    private MaxPriorityQueue(final int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }
    /**
     * Initializes an empty priority queue.
     */
    private MaxPriorityQueue() {
        this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * using the given comparator.
     *
     * @param  initCapacity the initial capacity of this priority queue
     * @param  comparator the order in which to compare the keys
     */
    private MaxPriorityQueue(final int initCapacity,
                            final Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    /**
     * Initializes an empty priority queue using the given comparator.
     *
     * @param  comparator the order in which to compare the keys
     */
    public MaxPriorityQueue(final Comparator<Key> comparator) {
        this(1, comparator);
    }

    /**
     * Initializes a priority queue from the array of keys.
     * Takes time proportional to the number of keys, using sink-based heap
     * construction.
     *
     * @param  keys the array of keys
     */
    public MaxPriorityQueue(final Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
            pq[i + 1] = keys[i];
        }
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMaxHeap();
    }
    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of keys in this priority queue.
     *
     * @return the number of keys in this priority queue
     */
    public int size() {
        return n;
    }

    /**
     * Returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key max() {
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
        pq = Arrays.copyOf(pq, pq.length);
    }


    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the new key to add to this priority queue
     */
    private void insert(final Key x) {

        // double size of array if necessary
        if (n >= pq.length - 1) {
            resize(2 * pq.length);
        }

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMaxHeap();
    }

    /**
     * Removes and returns the largest key in this priority queue.
     *
     * @return a largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    private Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;     // to avoid loitering and help with garbage
        // collection size is reduced when it's only 1/4 full.
        if ((n > 0) && (n == (pq.length - 1) / 4)) {
            resize(pq.length / 2);
        }
        assert isMaxHeap();
        return max;
    }

    //**************************************************************************
    // Helper functions to restore the heap invariant.
    //**************************************************************************
     /**
     * @param k the k
     */
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * @param k the k
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            // children of node at k are 2k and 2k + 1
            if (j < n && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    //**************************************************************************
    // Helper functions for compares and swaps.
    // *************************************************************************
    /**
     * @param i the i
     * @param j the j
     * @return the return
     */
    private boolean less(final int i, final int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    /**
     * @param i the i
     * @param j the j
     */
    private void exch(final int i, final int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    /**
     * is pq[1..N] a max heap?
     * @return boolean the boolean
     */
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }
    /**
     * Is subtree of pq[1..n] rooted at k a max heap?
     * @param k the k
     * @return the return
     */
    private boolean isMaxHeap(final int k) {
        if (k > n) {
            return true;
        }
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left  <= n && less(k, left))  {
            return false;
        }
        if (right <= n && less(k, right)) {
            return false;
        }
        return isMaxHeap(left) && isMaxHeap(right);
    }


   /***************************************************************************
    * Iterator.
    ***************************************************************************
     * Returns an iterator that iterates over the keys on this priority queue
     * in descending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in descending order
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    /**
     * Class HeapIterator.
     */
    private class HeapIterator implements Iterator<Key> {

        /** create a new pq. */
        private MaxPriorityQueue<Key> copy;
        /**
         * Add all items to copy of heap.
         * Takes linear time since already in heap order so no keys move
         */
        HeapIterator() {
            if (comparator == null) {
                copy = new MaxPriorityQueue<>(size());
            } else {
                copy = new MaxPriorityQueue<>(size(), comparator);
            }
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i]);
            }
        }

        /**
         * @return the return
         */
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        /** Not supported. */
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * @return the return
         */
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMax();
        }
    }

    /**
     * Unit tests the {@code MaxPQ} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        MaxPriorityQueue<String> pq = new MaxPriorityQueue<>(2);

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter in a string(- to Quit: ");

        while (!in.next().equals("-")) {
            String item = in.next();
            if (!(item == "-")) {
                pq.insert(item);
            }
        }
        if (!pq.isEmpty()) {
            for (String string : pq)
                System.out.println(string + " ");
        }
        System.out.print(pq.delMax() + " ");
        System.out.println("(" + pq.size() + " left on pq)");
    }

}
