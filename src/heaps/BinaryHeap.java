package heaps;

import search.BinarySearch;

import java.util.*;

import static java.lang.Math.floor;

/**
 *  A Binary BinaryHeap is based on the idea of a complete binary tree.
 *  Binary tree. Empty or node with links to left and right binary trees.
 *  Complete binary tree. Perfectly balanced except for bottom level.
 *  Property. Height of complete tree with N nodes is floor(lg N).
 *  Pf. Height only increases when N is a power of 2.
 *  Binary heap. Array representation of a heap-ordered complete binary tree.
 *
 *  BinaryHeap-ordered binary tree.
 *  - Keys in nodes.
 *  - Parent's key no smaller than children's keys.
 *
 *  Array representation.
 *  - Indices start at 1.
 *  - Take nodes in level order.
 *  - No explict links needed.
 *
 *  Insert: 1 _ lg N compares.
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
 *  Immutability: implementing in Java
 *  Data type. Set values and operations on those values.
 *  Immutable data type. Can't change the data type value once created.
 *  - Use final class so can't override instance methods
 *  - All instance variables private and final
 *  - Instance methods don't change instance variables.
 *  - Defensive copy of mutable instance variables.
 *    Example:
 *    public Vector(double[] data) {
 *        this.N = data.length;
 *        this.data = new double(N);
 *        for (int i = 0; i < N; i++)
 *          this.data[i] = data[i];
 *    }
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
 *
 * @param <Key> the Key
 */
public final class BinaryHeap<Key> implements Iterable<Key> {
    /** Store items at indices 1 to n. */
    Key[] heap;
    /** Number of items on binary heap. */
    private int n;
    /** Optional comparator. */
    private Comparator<Key> comparator;

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param  initCapacity the initial capacity of this priority queue
     */
    private BinaryHeap(final int initCapacity) {

        this.heap = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

     /**
     * Initializes an empty priority queue.
     */
    private BinaryHeap() {

        this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * using the given comparator.
     *
     * @param  initCapacity the initial capacity of this priority queue
     * @param  newComparator the order in which to compare the keys
     */
    private BinaryHeap(final int initCapacity,
                       final Comparator<Key> newComparator) {
        this.comparator = newComparator;
        heap = (Key[]) new Object[initCapacity + 1];
        n = 0;
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
     * Helper function to double the size of the heap array.
     * @param capacity the capacity
     */
    private void resize(final int capacity) {
        assert capacity > n;
        heap = Arrays.copyOf(heap, heap.length);
    }

    /**
     * Adds a new key to this priority queue.
     * Scenario. Child's key becomes larger key than its parent's key.
     * To eliminate this violation
     * - Exchange key in child with key in parent.
     * - Repeat until heap order restored.
     *
     * @param  key the new key to add to this priority queue
     */
    private void insert(final Key key) {
        // double size of array if necessary
        if (n >= heap.length - 1) {
            resize(2 * heap.length);
        }
        int length = heap.length;

        heap[n++] = key;
        swim(n);
        assert isMaxHeap();
    }

    /**
     * Removes and returns the largest key in this priority queue.
     *
     * @return the largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        Key max = heap[1];
        exch(1, n--);
        sink(1);
        heap[n + 1] = null;     // to avoid loitering and help with garbage

        // collection size is reduced when it's only 1/4 full.
        if ((n > 0) && (n == (heap.length - 1) / 4)) {
            resize(heap.length / 2);
        }
        assert isMaxHeap();
        return max;
    }

    //**************************************************************************
    // Helper functions to restore the heap invariant.
    //**************************************************************************
    /**
     * if child's key is larger than parent's key exchange keys.
     * @param k the child node
     */
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) { //parent of node at k is at k/2
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * if parent's key is larger than child's key exchange keys.
     * @param k the parent node
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
            return ((Comparable<Key>) heap[i]).compareTo(heap[j]) < 0;
        } else {
            return comparator.compare(heap[i], heap[j]) < 0;
        }
    }

    /**
     * @param i the i
     * @param j the j
     */
    private void exch(final int i, final int j) {
        Key swap = heap[i];
        heap[i] = heap[j];
        heap[j] = swap;
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

    /**
     * 4 ROW TREE
     * _______1
     * ___1_______1
     * _1___1___1___1
     * 1_1_1_1_1_1_1_1
     * 1st Row Indent 7 Spaces 0
     * 2nd Row Indent 3 Spaces 7
     * 3rd Row Indent 1 Spaces 3
     * 4th Row Indent 0 Spaces 1
     *
     * http://www.wolframalpah.com
     * Indent : 1/2 * (-2+2^n) start with 1
     * Note: need an iterator that starts at 1. Go until no more rows.
     * Spaces : 0 and then whatever Indent was
     *
     * First Index Per Row
     *
     * 0
     * 1 2
     * 3 4 5 6
     * 7 8 9 10 11 12 13 14
     * .5 * (-2 + (Math.pow(2, iteration)))
     *
     * Items Per Row
     * 1, 2, 4, 8
     * Math.pow(2, iteration - 1)
     *
     * Max Index Per Row
     * indexToPrint + itemsPerRow
     *
     * Indent Number
     * Indent Number Space Number
     * Indent Number Space Number Space...
     *
     * I need 1 index followed by multiple numbers & spaces every time
     */
    private void printTree() {

        int rows = (int) floor(Math.log((double) n) / Math.log(2.0)) + 1;
        // Number of spaces between items in tree
        int spaces = 0;
        int iteration = 1;

        // Generate all indents that are needed depending on # of rows to print
        int[] indent = getIndentArray(rows);

        while (iteration <= rows) {

            // Find first Index : .5 * (-2 + 2^n)
            int indexToPrint = (int) (.5 * (-2 + (Math.pow(2, iteration))));

            // Number of Items per Row : 2^(n - 1)
            int itemsPerRow = (int) (Math.pow(2, iteration - 1));
            int maxIndexToPrint = indexToPrint + itemsPerRow;

            // Print the indents needed
            for (int j = 0; j < indent[iteration - 1]; j++) {
                System.out.print(" ");
            }
            // Print all of the index values for each row.
            // indexToPrint represents the first index in the row, while
            // maxIndexToPrint equals the last
            for (int l = indexToPrint; l < maxIndexToPrint; l++) {

                // If array isn't full don't print indexes that don't exist
                if (l < size()) {
                    System.out.print(String.format("%02d", heap[l]));

                    for (int k = 0; k < spaces; k++) {
                        System.out.print(" ");
                    }
                }
            }

            // In a tree the spaces get bigger in the same way that indents get
            // smaller
            spaces = indent[iteration - 1];
            iteration++;
            System.out.println();
        }
    }

    /**
     *  Fix the following problems:
     *  1.  Make calculation of the indent more dynamic by depending up the
     *      array data
     *  2.  Print indexes that don't exist (i.e. when less items in array than
     *      can accommodate the rows
     *  3.  Ability to print single and double digit numbers
     *
     *  Calculate each indent per row for the tree then reverse their order to
     *  go from biggest to smallest
     *
     *  @param rows the number of rows
     *  @return the return
    */
    private int[] getIndentArray(final int rows) {

        int[] indentArray = new int[rows];

        for (int i = 0; i < rows; i++) {
            //Indent : 1/2 * (-2+2^n) start with 1
            indentArray[i] = (int) Math.abs((-2 + (Math.pow(2, i + 1))));
        }
        Arrays.sort(indentArray);
        indentArray = reverseArray(indentArray);
        return indentArray;
    }


    /**
     * Reverse the indent values in the array so that they go from biggest to
     * smallest.
     *
     * @param theArray the array
     * @return the arraay reversed
     */
    private int[] reverseArray(final int[] theArray) {

        // Index of the first element
        int leftIndex = 0;

        // Index of last element
        int rightIndex = theArray.length - 1;

        while (leftIndex < rightIndex) {
            // Exchange the left and right elements
            int temp = theArray[leftIndex];
            theArray[leftIndex] = theArray[rightIndex];
            theArray[rightIndex] = temp;

            // Move the indexes to check towards the middle
            leftIndex++;
            rightIndex--;
        }

        return theArray;
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

        return new BinaryHeap.HeapIterator();
    }

    /**
     * Class HeapIterator.
     */
    private class HeapIterator implements Iterator<Key> {

        /** create a new heap. */
        private BinaryHeap<Key> copy;
        /**
         * Add all items to copy of heap.
         * Takes linear time since already in heap order so no keys move
         */
        HeapIterator() {
            if (comparator == null) {
                copy = new BinaryHeap<>(heap.length);
            } else {
                copy = new BinaryHeap<>(heap.length, comparator);
            }
            for (int i = 1; i <= n; i++) {
                copy.insert(heap[i]);
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
     * Unit tests the BinaryHeap data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        BinaryHeap theHeap = new BinaryHeap(10);
        int length = theHeap.heap.length;

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter in a string(- to Quit: ");

        String item = "";

        // T P R N H O A E I G S*
        while (!(item.equals("-"))) {
            item = in.next();
            if (!(item.equals("-"))) {
                theHeap.insert(item);
            } else {
                break;
            }
        }

        // Print out the array before it is sorted
        System.out.println("Original Array");
        System.out.println(Arrays.toString(theHeap.heap));
        theHeap.printTree();

        theHeap.delMax();
        // T* S R N P O A E I G H
        theHeap.printTree();

        // Print the sorted array
        // S P R N H O A E I G
        System.out.println("\nSorted Array");
        System.out.println(Arrays.toString(theHeap.heap));

        theHeap.delMax();
        // S* R N P O A E I G H
        theHeap.printTree();

        // Print the sorted array
        // R P O N H G A E I
        System.out.println("\nSorted Array");
        System.out.println(Arrays.toString(theHeap.heap));

        theHeap.insert("S");

    }
}
