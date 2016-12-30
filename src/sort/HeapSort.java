package sort;

import edu.princeton.cs.algs4.Heap;
import edu.princeton.cs.algs4.StdOut;

/**
 *  The {BinaryHeapclass provides a static methods for heapsorting an array.
 *
 *  Runtime analysis
 *  Best        Average     Worst
 *  O(n log n)  O(n log n)  O(n log n)
 *
 *  Worst-case space complexity O(1) auxiliary
 *
 *  Proposition. BinaryHeap construction uses <= 2 N compares and exchanges.
 *  Proposition. Heapsort uses <= 2 N lg N compares and exchanges.
 *
 *  Significance. In-place sorting algorithm with N log N worst case.
 *  - MergeSort: no, linear extra apace.
 *  - QuickSort: no, quadratic time in worst case.
 *  - Heapsort: yes!
 *
 *  An in place sorting algorithm that's guaranteed O(n log n).
 *
 *  Bottom line. Heapsort is optimal for both time and space, but:
 *  - Inner loop longer than quickSort's.
 *  - Makes poor use of cache memory.
 *  - Not stable.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public final class HeapSort {
    /**
     * Initializes an empty heap.
     */
    private HeapSort() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param heap the array to be sorted
     */
    public static void sort(final Comparable[] heap) {
        int n = heap.length;
        // First pass. Build heap using bottom up method.
        for (int k = n / 2; k >= 1; k--) {
            sink(heap, k, n);
        }
        // Second pass. Remove the max one at a time. Leave in array instead
        // of nulling out.
        while (n > 1) {
            exch(heap, 1, n--);
            sink(heap, 1, n);
        }
    }

    /**
     *  Helper functions to restore the heap invariant.
     *  @param heap the array pq
     *  @param k the k
     *  @param n the n
     */
    private static void sink(final Comparable[] heap, int k, final int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(heap, j, j + 1)) {
                j++;
            }
            if (!less(heap, k, j)) {
                break;
            }
            exch(heap, k, j);
            k = j;
        }
    }

    //**************************************************************************
    //  Helper functions to restore the heap invariant.
    //*************************************************************************/

    /**
     * Return true if pq[i - 1] < pq[j - 1].
     * Convert from 1-based indexing to 0-base indexing.
     * @param heap the array pq
     * @param i the variable i
     * @param j the variable j
     * @return boolean true if pq[i - 1] < pq[j - 1]
     */
    private static boolean less(final Comparable[] heap, final int i,
                                final int j) {
        return heap[i - 1].compareTo(heap[j - 1]) < 0;
    }

    /**
     * Return true if v < w; false otherwise.
     * Convert from 1-based indexing to 0-base indexing.
     * @param v the variable v
     * @param w tje variable y
     * @return boolean true if v < y; false otherwise.
     */
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * Swap the values at pq[i] with the value at pq[j].
     * Convert from 1-based indexing to 0-base indexing.
     * @param pq the array
     * @param i the index i
     * @param j the index j
     */
    private static void exch(final Object[] pq, final int i, final int j) {
        Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    //**************************************************************************
    //  Check if array is sorted - useful for debugging.
    //*************************************************************************/

    /**
     * Is the array sorted?
     * @param heap the arrray
     * @return true if array is sorted; false otherwise
     */
    private static boolean isSorted(final Comparable[] heap) {
        for (int i = 1; i < heap.length; i++) {
            if (less(heap[i], heap[i - 1])) {
                return false;
            }
        }
        return true;
    }
    /**
     * Print array to standard output.
     * @param heap the array
     */
    private static void show(final Comparable[] heap) {
        for (Comparable item : heap) {
            StdOut.print(item + " ");
        }
        StdOut.println();
    }

    /**
     * Reads in a sequence of strings from standard input; heapsorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        //String[] a = StdIn.readAllStrings();
        String[] heap = {"X", "T", "S", "P", "L", "R", "A", "M", "O", "E", "E"};
        show(heap);
        Heap.sort(heap);
        show(heap);
        assert isSorted(heap);
    }
}
