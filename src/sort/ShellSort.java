package sort;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;

/**
 *  The ShellSort class provides static methods for sorting an
 *  array using Shellsort with Knuth's increment sequence (1, 4, 13, 40, ...).
 *  Idea. Move entries more than one position at a time by h-sorting the
 *  array (h being the gap).  Shell sort is known as a diminishing gap sort.
 *  When gap is 1 the loop is identical to an insertion sort.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * Shell sort is a sub quadratic algorithm whose code is only slightly longer
 * than the insertion sort, making it the simplest of the faster algorithms.
 * It compares elements that are far apart and then by comparing elements that
 * are less far apart, and so on, gradually shrinking toward the basic
 * insertion sort.
 *
 * The running time of Shell sort depends heavily on the choice of increment
 * sequences.  A sequence which performs well in practice but has no
 * theoretical basis, is to have a starting gap of N/2.2. This divisor
 * appears to bring the average running time to below O(N^5/4).  For
 * 100,000, to 1,000,000 items, it typically improves performance by about
 * 25 to 35 percent over Shell's Increments, although nobody knows why. The
 * performance of Shell sort is quite acceptable in practice, even for N in
 * the tens of thousands. The simplicity of the code makes it the algorithm
 * of choice for sorting up to moderately large input.  It is also a fine
 * example of a very simple algorithm with an extremely complex analysis.
 *
 * Useful in practice.
 * - Fast unless array size is huge.
 * - Tiny, fixed footprint for code(used in embedded systems).
 * - Hardware sort prototype.
 *
 * Runtime analysis
 * Best        Average         Worst
 * O(n log n)  depends on gap  O(n log n ^2)
 *
 * Original        81  94  11  96  12  35  17  95  28  58  41  75  15
 * After 5-sort    35  17  11  28  12  41  75  15  96  58  81  94  95
 * --                  --                  --
 * After 3-sort    28  12  11  35  15  41  58  17  94  75  81  96  95
 * --          --          --          --          --
 * After 1-sort    11  12  15  17  28  35  41  58  75  81  94  95  96
 */
public final class ShellSort {

    /** The constructor. */
    private ShellSort() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param theArray the array to be sorted
     */
    private static void shellsort(final Comparable[] theArray) {
        Comparable temp;
        int j;

        // Keep looping until interval is 1 which then becomes an insertion sort
        for (int gap = theArray.length / 2; gap > 0; gap = (gap == 2) ? 1
                : (new Double(gap / 2.2)).intValue()) {
            for (int i = gap; i < theArray.length; i++) {
                // While there is a number bigger than temp move it further up
                // in the array
                for (j = i; j >= gap && less(theArray[j], theArray[j - gap]); j -= gap) {
                    exch(theArray, j, j - gap);
                }
            }
        }
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
     * @param a the array
     * @param i the first index
     * @param j the second index
     */
    private static void exch(final Object[] a, final int i, final int j) {

        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    //**************************************************************************
    //  Check if array is sorted - useful for debugging.
    //*************************************************************************/

    /**
     * Is the array a[] sorted?
     * @param a the array
     * @return true if array is sorted; false otherwise
     */
    private static boolean isSorted(final Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is the array h sorted?
     * @param a the array
     * @param h the h
     * @return true if array is sorted; false otherwise
     */
    private static boolean isHsorted(final Comparable[] a, final int h) {
        for (int i = h; i < a.length; i++) {
            if (less(a[i], a[i - h])) {
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
     * Unit tests the ShellSort data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        // The array of unsorted elements.
        Comparable[] array = new Comparable[Integer.parseInt(args[0])];
        generateRandomArray(array, array.length);

        long startTime;
        long endTime;

        System.out.println(Arrays.toString(array));
        System.out.println();

        startTime = System.currentTimeMillis();
        shellsort(array);
        endTime = System.currentTimeMillis();
        System.out.println("Shellsort took " + (endTime - startTime)
                + " milliseconds.");

        System.out.println();
        System.out.println(Arrays.toString(array));
    }
}
