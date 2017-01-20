package sort;

import edu.princeton.cs.algs4.MergeX;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 *  The MergeSort class provides static methods for sorting an  array using
 *  merge sort. MergeSort is a divide and conquer algorithm that is a  an
 *  efficient, general-purpose, comparison-based sorting algorithm
 *
 *  Basic plan.
 *  - Copy original array to auxiliary array, sort it and copy it back to
 *    original array.
 *  - Maintain three indices:
 *    - i, current entry on left half
 *    - j, current etnry on right half
 *    - k, the entry in the sorted result
 *  - Divide array into two halves.
 *  - Recursively sort each half.
 *  - Merge two halves.
 *
 *  Abstract in-place merge
 *  Goal. Given two sorted subarrays a[lo] to a[mid[ and a[mid+1] to a[hi},
 *  replace with sorted subarray a[lo[ to a[hi].
 *
 *  Proposition: MergeSort uses at most N lg N compares and 6 N lg N array
 *  accesses to sort any array of size N. Extra cost of
 *  merge =2^K * (N/2^K) lg N (# of stages).
 *
 *  Can enable or disable assert at runtime. No cost in production code.
 *  By default assertions are disabled.
 *
 *  - java -ea MyProgram    // enable assertions
 *  - java -da MyProgram    // disable assertions(default)
 *
 *  Runtime analysis
 *  Best        Average     Worst
 *  O(n log n)  O(n log n)  O(n log n)
 *
 *  Worst-case space complexity O(n) total, O(n) auxiliary
 *
 *  Upper bound is an algorithm guaranteed to get the sort done in time
 *  proportional to worst case running time.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/22mergesort">Section 2.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For an optimized version, see {@link MergeX}.
 */
public final class MergeSort {

    /** A variable to determine when number of items to sort is small. */
    private static final int CUTOFF = 7;

    /** This class should not be instantiated. */
    private MergeSort() { }

    /**
     * Merge sort algorithm.
     * stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
     * @param array the array to sort
     * @param aux the copy of the array
     * @param low the low index of the array
     * @param middle the middle index of the array
     * @param high the high index of the array
     */
    private static void merge(final Comparable[] array,
                              Comparable[] aux,
                              final int low,
                              final int middle,
                              final int high) {

        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert isSorted(array, low, middle);
        assert isSorted(array, middle + 1, high);

        // copy to aux[]
        aux = Arrays.copyOf(array, array.length);

        // Is biggest item in 1st half <= smallest item in 2nd half? If so stop.
        if (!less(array[middle + 1], array[middle])) {
            return;
        }

        // merge back to a[]
        int i = low, j = middle + 1;
        for (int k = low; k <= high; k++) {
            if      (i > middle) {
                array[k] = aux[j++];
            } else if (j > high) {
                array[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                array[k] = aux[j++];
            } else {
                array[k] = aux[i++];
            }
        }

        // postcondition: a[lo .. hi] is sorted
        assert (isSorted(array, low, high));
    }

    /**
     *  Mergesort a[lo..hi] using auxiliary array aux[lo..hi].
     *  @param array the array to sort
     *  @param aux the copy of the array
     *  @param low the low index of the array
     *  @param high the high index of the array
     */
    private static void sort(final Comparable[] array,
                             final Comparable[] aux,
                             final int low,
                             final int high) {
        //if (high <= low) return;
        if (high <= low + CUTOFF) {
            // performance improvement. Use insertion sort for small sub-arrays.
            InsertionSort.insertionSort(array);
            return;
        }

        int middle = low + ((high - low) >>> 1);
        sort(array, aux, low, middle);
        sort(array, aux, middle + 1, high);
        // performance improvement. Stop if already sorted.
        // If biggest element in first half is <= smallest item in second half.
        if (!less(array[middle + 1], array[middle])) {
            System.arraycopy(array, low, aux, low, high - low + 1);
            return;
        }
        merge(array, aux, low, middle, high);
    }

    /**
     *  Merge sort algorithm.
     *  @param array the array to sort
     */
    private static void mergesort(final Comparable[] array) {

        // Do not create aux array in recursive routine because of extra cost
        Comparable[] aux = new Comparable[array.length];
        sort(array, aux, 0, array.length - 1);
        assert isSorted(array);
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
     *  @param start the start.
     *  @param end the end.
     *  @return true if sorted; false otherwise.
     */
    private static boolean isSorted(final Comparable[] a, final int start,
                             final int end) {

        for (int i = start; i < end - 1; i++) {
            if (less(a[i + 1], a[i])) {
                return false; // It is proven that the array is not sorted.
            }
        }

        return true; // If this part has been reached, the array must be sorted.
    }

    /**
     * Unit tests the MergeSort data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        long startTime;
        long endTime;

        // The array of unsorted elements.
        Comparable[] array = new Comparable[Integer.parseInt(args[0])];
        generateRandomArray(array, array.length);

        System.out.println("STARTING ARRAY");
        printHorizontalArray(array, array.length, -1, -1);

        startTime = System.currentTimeMillis();
        // Send the array, 0 and the array size
        MergeSort.mergesort(array);
        endTime = System.currentTimeMillis();

        System.out.println("FINAL SORTED ARRAY");
        printHorizontalArray(array, array.length, -1, -1);
        System.out.println("Mergesort took " + (endTime - startTime)
                + " milliseconds.");
    }
}
