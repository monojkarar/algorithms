package sort;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 *  The SelectionSort class provides static methods for sorting an array
 *  using selection sort.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Selection Sort is normally the best of the elementary sorts.
 *  There is a pass for each position (0..size-1).
 *  On each pass, the smallest (minimum) element in the rest of the list is
 *  exchanged (swapped) with element at the current position. The first part
 *  of the list (already processed) is always sorted. Each pass increases the
 *  size of the sorted portion.
 *
 *  Runtime analysis
 *  Best    Average     Worst
 *  O(n^2)  O(n^2)      O(n^2)
 *
 *  Array Position  0   1   2   3   4
 *  Initial State  36   24  10   6  12
 *  After 1st pass  6   24  10  36  12   6 and 36 are swapped.
 *  After 2nd pass  6   10  24  36  12  10 and 24 are swapped.
 *  After 3rd pass  6   10  12  36  24  12 and 24 are swapped.
 *  After 4th pass  6   10  12  24  36  24 and 36 are swapped.
 *
 *  N is the number of elements in the list.
 *  Outer loop executes N-1 times.
 *  Inner loop executes N-1, then N-2, then N-3, ... then once.
 *  Total number of comparisons (in inner loop) is the sum of 1 to N-1 is
 *  N(N + 1) /2.
 *  Efficiency of algorithm is O(N^2).
 */
public final class SelectionSort {

    /** The array to sort. */
    private Comparable[] theArray;

    /**
     * The constructor.
     * @param n the size of the array
     */
    private SelectionSort(final int n) {

        theArray = new Comparable[n];
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * On each pass, the smallest (minimum) element in the rest of the list is
     * exchanged (swapped) with element at the current position. and then
     * repeats searching through the entire array each time
     * @param array the array to be sorted
     */

    private static void selectionSort(final Comparable[] array) {

        for (int i = 0; i < array.length; i++) {
            int min = i;

            for (int j = i; j < array.length; j++) {
                if (less(array[j], array[min])) {
                    min = j;
                }
            }

            exch(array, i, min);
            printHorizontalArray(array, array.length, i, -1);
        }
    }

    /**
     * Rearranges the array in ascending order, using a comparator.
     * @param array the array
     * @param comparator the comparator specifying the order
     */
    public static void sort(final Object[] array, final Comparator comparator) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(comparator, array[j], array[min])) {
                    min = j;
                }
            }
            exch(array, i, min);
            assert isSorted(array, comparator, 0, i);
        }
        assert isSorted(array, comparator);
    }

    //**************************************************************************
    //  Helper sorting functions.
    //**************************************************************************

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
     * Return true if v < w; false otherwise.
     * @param comparator the comparator
     * @param v the variable v
     * @param w tje variable y
     * @return boolean true if v < y; false otherwise.
     */
    private static boolean less(final Comparator comparator, final Object v,
                                final Object w) {
        return comparator.compare(v, w) < 0;
    }

    /**
     * SwapValues.
     * @param array the array
     * @param indexOne the indexOne
     * @param indexTwo the index Two
     */
    private static void exch(final Object[] array, final int indexOne, final
    int indexTwo) {

        Object temp = array[indexOne];
        array[indexOne] = array[indexTwo];
        array[indexTwo] = temp;
    }

    //**************************************************************************
    // Check if array is sorted - useful for debugging.
    //*************************************************************************/

    /**
     * Is the array a[] sorted?
     * @param a the array
     * @return true if array is sorted; false otherwise
     */
    private static boolean isSorted(final Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    /**
     * Is the array sorted from a[lo] to a[hi]?
     * @param a the array
     * @param lo the start of the array
     * @param hi the end of the array
     * @return true if array is sorted; false otherwise
     */
    private static boolean isSorted(final Comparable[] a, final int lo,
                                    final int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is the array a[] sorted?
     * @param a the array
     * @param comparator the comparator
     * @return true if array is sorted; false otherwise
     */
    private static boolean isSorted(final Object[] a,
                                    final Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    /**
     * Is the array sorted from a[lo] to a[hi].
     * @param a the array
     * @param comparator the comparator
     * @param lo the start of the array
     * @param hi the end of the array
     * @return true if array is sorted; false otherwise
     */
    private static boolean isSorted(final Object[] a,
                                    final Comparator comparator,
                                    final int lo,
                                    final int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(comparator, a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Print array to standard output.
     * @param a the array
     */
    private static void show(final Comparable[] a) {
        for (Comparable item : a) {
            StdOut.print(item + " ");
        }
    }

     /**
     * Unit tests the SelectionSort data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        SelectionSort ss = new SelectionSort(Integer.parseInt(args[0]));
        generateRandomArray(ss.theArray, ss.theArray.length);
        printHorizontalArray(ss.theArray, ss.theArray.length, -1, -1);
        selectionSort(ss.theArray);
        show(ss.theArray);
    }
}
