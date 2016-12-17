package sort;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorzArray;

/**
 * Shell sort is a sub quadratic algorithm whose code is only slightly longer
 * than the insertion sort, making it the simplest of the faster algorithms.
 * It compares elements that are far apart and then by comparing elements that
 * are less far apart, and so on, gradually shrinking toward the basic
 * insertion sort.  Shell sort is known as a diminishing gap sort. When gap
 * is 1 the loop is identical to an insertion sort.
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
 * Runtime analysis
 * Best        Average         Worst
 * O(n log n)  depends on gap  O(n log n * log n)
 *
 * Original        81  94  11  96  12  35  17  95  28  58  41  75  15
 * After 5-sort    35  17  11  28  12  41  75  15  96  58  81  94  95
 * --                  --                  --
 * After 3-sort    28  12  11  35  15  41  58  17  94  75  81  96  95
 * --          --          --          --          --
 * After 1-sort    11  12  15  17  28  35  41  58  75  81  94  95  96
 */
public final class ShellSort {

    /** The array of unsorted elements. */
    private Comparable[] theArray;

    /**
     *  The constructor.
     *  @param n the number of items.
     */
    private ShellSort(final int n) {

        theArray = new Comparable[n];
        generateRandomArray(this.theArray, this.theArray.length);
    }

    /**
     * The shellsort algorithm.
     */
    private void shellsort() {
        Comparable temp;
        int j;

        // Keep looping until interval is 1 which then becomes an insertion sort
        for (int gap = theArray.length / 2; gap > 0; gap = (gap == 2) ? 1
                : (new Double(gap / 2.2)).intValue()) {
            for (int i = gap; i < theArray.length; i++) {
                // While there is a number bigger than temp move it further up
                // in the array
                for (j = i; j >= gap && less(theArray[j], theArray[j - gap]); j -= gap)
                    exch(theArray, j, j - gap);

                //System.out.println("inner= " + (i - gap) + " outer= " + i
                // + " temp= " + temp + " interval= " + gap);
                //printHorzArray(theArray, j, i, gap);
            }
        }
    }

    /**
     * Return true if v < w; false otherwise.
     * @param v the variable v
     * @param w tje variable y
     * @return boolean true if v < y; false otherwise.
     */
    private boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * SwapValues.
     * @param array the array
     * @param indexOne the indexOne
     * @param indexTwo the index Two
     */
    private void exch(final Comparable[] array, final int indexOne, final
    int indexTwo) {

        Comparable temp = array[indexOne];
        array[indexOne] = array[indexTwo];
        array[indexTwo] = temp;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        long startTime;
        long endTime;

        ShellSort theSort = new ShellSort(Integer.parseInt(args[0]));
        System.out.println(Arrays.toString(theSort.theArray));
        System.out.println();

        startTime = System.currentTimeMillis();
        theSort.shellsort();
        endTime = System.currentTimeMillis();
        System.out.println("Shellsort took " + (endTime - startTime)
                + " milliseconds.");

        System.out.println();
        System.out.println(Arrays.toString(theSort.theArray));
    }
}
