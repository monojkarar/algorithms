package sort;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Insertion;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;

/**
 *  In most situations the sort.QuickSort is the fastest sorting algorithm.
 *  The sort.QuickSort works by partitioning arrays that the smaller numbers
 *  are on the left and the larger are on the right.  It then recursively
 *  sends small parts of larger arrays to itself and partitions again.
 *  QuickSort just compares and moves a pointer. It doesn't move items in and
 *  out of an auxiliary array.
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
 *  Shuffling is needed for performance guarantee. Probablistic guarantee
 *  against worst case.  Basis for math model that can be validiated with
 *  experiments.
 *
 *  Basic plan.
 *  - Shuffle the array.
 *  - Partition so that, for some j
 *    - entry[j] is in place
 *    - no larger entry to the left of j
 *    - no smaller entry to the right of j
 *  - Sort each piece recursively.
 */
public final class QuickSort {
    /** A variable to determine when number of items to sort is small. */
    private static final int CUTOFF = 10;
    /** The array to sort. */
    private  Comparable[] theArray;
    /**
     *  The constructor.
     *  @param n the number of items in array.
     */
    private QuickSort(final int n) {
        theArray = new Comparable[n];
        generateRandomArray(this.theArray, this.theArray.length);
    }

    /**
     *  The Quicksort algorithm.
     *  @param array the array to sort
     */
    private static void quickSort(final Comparable[] array) {
        // shuffle needed for performance guarantee.
        StdRandom.shuffle(array);
        quickSort(array, 0, array.length - 1);
    }
    /**
     * @param array the array to sort
     * @param left the left
     * @param right the right
     */
    private static void quickSort(final Comparable[] array, final int left,
                      final int right) {

        if (right <= left) {
            return;         // Everything is sorted
        }

        // performance improvement
        if (right <= left + CUTOFF - 1) {
            Insertion.sort(array, left, right);
        } else {
            // performance improvement.
            int pivot = medianOf3(array, left, right);
            exch(array, left, pivot);

            int j = partition(array, left, right);

            quickSort(array, left, j - 1); // Sorts the left side
            quickSort(array, j + 1, right);
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
     * @param left the left pointer
     * @param right the right pointer
     * @return the left pointer
     */
    private static int partition(final Comparable[] array, final int left,
                               final int right) {

        int i = left;
        int j = right + 1;

        // find item on left to swap
        while (true) {
            while (less(array[++i], array[left])) {
                if (i == right) {
                    break;
                }
            }
            // find item on right to swap
            while (less(array[left], array[--j])) {
                if (j == left) {
                    break;
                }
            }

            if (i >= j) {       // check if pointers cross.
                break;
            }
            exch(array, i, j);  // swap.
        }

        exch(array, left, j);   // swap with partitioning item.
        return j;               // return index of item known to be in place.
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
     * Returns the median array.
     * @param array the array to sort
     * @param left the left endpoint of array
     * @param right the right endpoint of array
     * @return the median of the array
     */
    private static int medianOf3(final Comparable[] array, final int left,
                                       final int right) {
        int center = (left + right) / 2;

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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        long startTime;
        long endTime;

        QuickSort theSort = new QuickSort(Integer.parseInt(args[0]));
        System.out.println(Arrays.toString(theSort.theArray));
        startTime = System.currentTimeMillis();
        QuickSort.quickSort(theSort.theArray);
        endTime = System.currentTimeMillis();
        System.out.println(Arrays.toString(theSort.theArray));
        System.out.println();
        System.out.println("Quicksort took " + (endTime - startTime)
                + " milliseconds.");
    }
}
