package sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Insertion;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;

/**
 *  The {@code Quick} class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *
 *  In most situations QuickSort is the fastest sorting algorithm.
 *  QuickSort works by partitioning arrays so that the smaller numbers
 *  are on the left and the larger are on the right.  It then recursively
 *  sends small parts of larger arrays to itself and partitions again.
 *  QuickSort just compares and moves a pointer. It doesn't move items in and
 *  out of an auxiliary array.
 *
 *  Basic plan.
 *  - Shuffle the array.
 *  - Pick a partitioning element.
 *  - Partition so that, for some j
 *    - entry a[j] is in place
 *    - no larger entry to the left of j
 *    - no smaller entry to the right of j
 *  - Sort each piece recursively.
 *
 *  Phase 1. Repeat until i and j pointers cross
 *  - Scan i from left to right so long as (a[i] < a[lo])
 *  - Scan j from right to left so long as (a[j] > a[lo]).
 *  - Exchange a[i] with a[j]
 *
 *  Phase II. When pointers cross.
 *  - exchange a[lo] with a[j]
 *
 *  Runtime analysis
 *  Best        Average     Worst
 *  O(n log n)  O(n log n)  O(n^2)
 *
 *  Worst-case space complexity
 *  O(n) auxiliary (naive)
 *  0(log n) auxiliary
 *
 *  Partitioning in place.
 *  It sorts in place unlike MergeSort which uses and extra array.
 *
 *  Terminating the loop.
 *  Testing whether the pointers cross is a bit trickier than it might seem.
 *
 *  Staying bounds.
 *  The (j == left) test is redundant. Why? Because j will stop when it hits
 *  the partition element at index 0. but the (i == right) test is not.
 *
 *  Preserving randomness.
 *  Shuffling is needed for performance guarantee. Probabilistic guarantee
 *  against worst case.  Basis for math model that can be validated with
 *  experiments.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 */
public final class QuickSort {
    /** A variable to determine when number of items to sort is small. */
    private static final int CUTOFF = 10;

    /** This class should not be instantiated. */

    private QuickSort() { }

    /**
     *  The Quicksort algorithm.
     *  @param array the array to sort
     */
    private static void quickSort(final Comparable[] array) {

        // shuffle needed for performance guarantee.
        StdRandom.shuffle(array);
        sort(array, 0, array.length - 1);
        assert isSorted(array);
    }
    /**.
     * Quicksort the subarray from a[lo] to a[hi]
     * @param array the array to sort
     * @param lo the left
     * @param hi the right
     */
    private static void sort(final Comparable[] array,
                                  final int lo,
                                  final int hi) {

        if (hi <= lo) {
            return;         // Everything is sorted
        }

        // performance improvement
        if (hi <= lo + CUTOFF - 1) {
            Insertion.sort(array, lo, hi);
        } else {
            // performance improvement.
            int pivot = medianOf3(array, lo, hi);
            exch(array, lo, pivot);

            int j = partition(array, lo, hi);

            sort(array, lo, j - 1); // Sorts the left side
            sort(array, j + 1, hi);
            assert isSorted(array, lo, hi);
        }
    }

    /**
     * Phase 1: Repeat until leftPtr and rightPtr cross.
     * - Scan leftPtr from left to right so long as (a[leftPtr] < a[lo]).
     * - Scan rightPtr from right to left so long as (a[rightPtr] > a[lo]).
     * - Exchange a[i] with a[j].
     *
     * Phase 2: When pointers cross.
     * - Exchange a[lo] with a[j].
     *
     * @param array the array to sort
     * @param lo the left pointer
     * @param hi the right pointer
     * @return the left pointer
     */
    private static int partition(final Comparable[] array, final int lo,
                               final int hi) {

        int i = lo;
        int j = hi + 1;

        while (true) {
            // find item on lo to swap
            while (less(array[++i], array[lo])) {
                if (i == hi) {
                    break;
                }
            }
            // find item on hi to swap
            while (less(array[lo], array[--j])) {
                if (j == lo) {  // redundant since a[lo] acts as a sentinal.
                    break;
                }
            }

            if (i >= j) {       // check if pointers cross.
                break;
            }
            exch(array, i, j);  // swap a[lo] with a[hi]
        }

        exch(array, lo, j);     // swap with partitioning item.
        return j;               // return index of item known to be in place.
    }

    /**
     * Rearranges the array so that a[k] contains the kth smallest key;
     * a[0] through a[k-1] are less than (or equal to) a[k]; and
     * a[k+1] through a[n-1] are greater than (or equal to) a[k].
     *
     * @param  a the array
     * @param  k the rank of the key
     * @return the key of rank k
     */
    public static Comparable select(final Comparable[] a, final int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected item out of bounds");
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if      (i > k) {
                hi = i - 1;
            } else if (i < k) {
                lo = i + 1;
            } else {
                return a[i];
            }
        }
        return a[lo];
    }

    //**************************************************************************
    //  Helper sorting function.
    //*************************************************************************/

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
     * Exchange a[i] and a[j].
     * @param array the array
     * @param i index 1
     * @param j index 2
     */
    private static void exch(final Object[] array, final int i, final int j) {

        Object swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    /**
     * Returns the median array.
     * @param array the array to sort
     * @param left the left endpoint of array
     * @param right the right endpoint of array
     * @return the median of the array
     */
    private static int medianOf3(final Comparable[] array, final int left,
                                       final int right) {
        int center = (left + right) >>> 1;

        if (less(array[center], array[left])) {
            exch(array, left, center);
        }
        if (less(array[right], array[left])) {
            exch(array, left, right);
        }
        if (less(array[right], array[center])) {
            exch(array, center, right);
        }
        return center;
    }

    //**************************************************************************
    //  Check if array is sorted - useful for debugging.
    //*************************************************************************/

    /**
     * Is array sorted?
     * @param a the array
     * @return true of array is sorted; false otherwise.
     */
    private static boolean isSorted(final Comparable[] a) {

        return isSorted(a, 0, a.length - 1);
    }

    /**
     *  Our strategy will be to compare every element to its successor.
     *  The array is considered unsorted. if a successor has a greater value
     *  than its predecessor. If we reach the end of the loop without finding
     *  that the array is unsorted, then it must be sorted instead.
     *  @param a the array to check.
     *  @param lo the start.
     *  @param hi the end.
     *  @return true if sorted; false otherwise.
     */
    private static boolean isSorted(final Comparable[] a,
                                    final int lo,
                                    final int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) {
                return false; // It is proven that the array is not sorted.
            }
        }
        return true; // If this part has been reached, the array must be sorted.
    }

    /**
     * Print array to standard output.
     * @param a the array
     */
    private static void show(final Comparable[] a) {
        System.out.println(Arrays.toString(a));
    }

    /**
     * Unit tests the QuickSort data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        long startTime;
        long endTime;

        // The array of unsorted elements.
        Comparable[] array = new Comparable[Integer.parseInt(args[0])];
        generateRandomArray(array, array.length);

        show(array);

        startTime = System.currentTimeMillis();
        quickSort(array);
        endTime = System.currentTimeMillis();

        System.out.println("FINAL SORTED ARRAY:");
        show(array);
        System.out.println();
        System.out.println("Quicksort took " + (endTime - startTime)
                + " milliseconds.");
    }
}
