package sort;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 *  The Insertion sort class provides static methods for sorting an
 *  array using insertion sort. In iteration i, swap a[i] with each larger
 *  entry to its left.
 *
 *  This implementation makes ~ 1/2 n^2 compares and exchanges in
 *  the worst case, so it is not suitable for sorting large arbitrary arrays.
 *  More precisely, the number of exchanges is exactly equal to the number
 *  of inversions. So, for example, it sorts a partially-sorted array
 *  in linear time.
 *
 *  The sorting algorithm is stable and uses O(1) extra memory.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Runtime analysis
 *  Best    Average     Worst
 *  O(n)    O(n*n)      O(n*n)
 *
 *  Insertion sort is going to be about twice as fast as selection sort.
 *
 *  The basic action of insertion sort is to sort the elements in positions 0
 *  through p (where p ranges from 1 through N-1). In each state p increases
 *  by 1. That is what the outer loop is controlling.
 *
 *  When the body of the for loop is entered we are guaranteed that the
 *  elements in array positions 0 through p-1 have already been sorted and
 *  that we need to extend this to positions 0 to p.
 *
 *  At each step the underlined element needs to be added to the previously
 *  sorted part of the array. We can easily do that by placing it in a
 *  temporary variable and sliding all the elements that ARE LARGER THAN
 *  IT on position to the right. We then copy the temporary variable into
 *  the former position of the leftmost relocated element. We keep a
 *  counter j, which is the position in which the temporary variable should
 *  be written back. Every time an element is slid, j decreases by 1.
 *
 *  Array Position           0   1   2   3   4   5
 *  ----------------------------------------------
 *  Initial state            8|  5   9   2   6   3   temp = 5
 *                              -
 *  After a[0..1] is sorted  5   8|  9   2   6   3   temp = 9
 *                                  -
 *  After a[0..2] is sorted  5   8   9|  2   6   3   temp = 2
 *                                      -
 *  After a[0..3] is sorted  2   5   8   9|  6   3   temp = 6
 *                                          -
 *  After a[0..4] is sorted  2   5   6   8   9|  3   temp = 3
 *                                              -
 *  After a[0..5] is sorted  2   3   5   6   8   9|
 */
public final class InsertionSort {

    /** The array to sort. */
    private Comparable[] theArray;

    /**
     * The constructor.
     * @param n the size of the array
     */
    private InsertionSort(final int n) {
        theArray = new Comparable[n];
    }

    /**
     *  Rearranges the array in ascending order, using the natural order.
     *  @param array the array to sort.
     */
    static void insertionSort(final Comparable[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(array[j], array[j - 1])) {
                    exch(array, j, j - 1);
                } else {
                    break;
                }
                //printHorizontalArray(array, array.length, i, j);
            }
            assert isSorted(array, 0, i);
        }
        assert isSorted(array);
    }

    /**
     * Rearranges the subarray a[lo..hi] in ascending order, using the natural
     * order.
     * @param a the array to be sorted
     * @param lo left endpoint
     * @param hi right endpoint
     */
    public static void sort(final Comparable[] a, final int lo, final int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
        assert isSorted(a, lo, hi);
    }

    /**
     * Rearranges the array in ascending order, using a comparator.
     * @param a the array
     * @param comparator the comparator specifying the order
     */
    public static void sort(final Object[] a, final Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); j--) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i, comparator);
        }
        assert isSorted(a, comparator);
    }

    /**
     * Rearranges the subarray a[lo..hi] in ascending order, using a comparator.
     * @param a the array
     * @param lo left endpoint
     * @param hi right endpoint
     * @param comparator the comparator specifying the order
     */
    public static void sort(final Object[] a, final int lo,
                            final int hi, final Comparator comparator) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], comparator); j--) {
                exch(a, j, j - 1);
            }
        }
        assert isSorted(a, lo, hi, comparator);
    }


    // return a permutation that gives the elements in a[] in ascending order
    // do not change the original array a[]
    /**
     * Returns permutation that gives the elements in array in ascending order.
     * @param a the array
     * @return a permutation p[] such that a[p[0]], a[p[1]], ..., a[p[n-1]]
     * are in ascending order
     */
    public static int[] indexSort(final Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--) {
                exch(index, j, j - 1);
            }
        }

        return index;
    }

    //**************************************************************************
    //  Helper sorting functions.
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
     *
     * @param v the Object v
     * @param w the Object w
     * @param comparator the oomparator
     * @return boolean true if v < y; false otherwise.
     */
    private static boolean less(final Object v,
                                final Object w,
                                final Comparator comparator) {
        return comparator.compare(v, w) < 0;
    }

    /**
     * exchange a[i] and a[j].
     * @param a the array
     * @param i the indexOne
     * @param j the index Two
     */
    private static void exch(final Object[] a, final int i, final int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /**
     * exchange a[i] and a[j]  (for indirect sort).
     * @param a the array
     * @param i the indexOne
     * @param j the index Two
     */
    private static void exch(final int[] a, final int i, final int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
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
    private static boolean isSorted(final Comparable[] a,
                                    final int lo,
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
        return isSorted(a, 0, a.length - 1, comparator);
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
                                    final int lo,
                                    final int hi,
                                    final Comparator comparator
                                    ) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1], comparator)) {
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
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {

        InsertionSort is = new InsertionSort(Integer.parseInt(args[0]));
        generateRandomArray(is.theArray, is.theArray.length);
        printHorizontalArray(is.theArray, is.theArray.length, -1, -1);

        InsertionSort.insertionSort(is.theArray);
        printHorizontalArray(is.theArray, is.theArray.length, -1, -1);

        show(is.theArray);
    }
}
