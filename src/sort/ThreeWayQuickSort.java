package sort;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;

/**
 *  3-Way partitioning
 *  Goal. Partition array into 3 parts so that:
 *  - Entries between lt and gt equal to partition item v.
 *  - No larger entries to left of lt.
 *  - No smaller entries to right of gt.
 *
 * `Runtime analysis
 *  Best        Average     Worst
 *  O(n)        O(n)        O(n log n ) when all keys are distinct
 *                          O(n)        when only a constant # of distinct keys
 */
public final class ThreeWayQuickSort {
    /** The array to sort. */
    private  Comparable[] theArray;

    /**
     *  This class should not be instantiated.
     *  @param n size of teh array.
     */
    private ThreeWayQuickSort(final int n) {
        theArray = new Comparable[n];
        generateRandomArray(this.theArray, this.theArray.length);
    }

    /**
     *  Partition array into 3 parts so that:
     *  - Entries between lt and gt equal to partition item v.
     *  - No larger entries to left of lt.
     *  - No smaller entries to right of gt.
     *
     *  Repeat in one subarray, depending on j; finished when j equals k.
     *
     * @param a the array
     * @param lo the low
     * @param hi the hi
     */
    public static void sort(final Comparable[] a, final int lo, final int hi) {

        if (hi <= lo) {
            return;
        }
        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) {
                exch(a, lt++, i++);
            } else if (cmp > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
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
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        ThreeWayQuickSort array = new ThreeWayQuickSort(Integer.parseInt(args[0]));
        System.out.println(Arrays.toString(array.theArray));
        long startTime = System.currentTimeMillis();
        ThreeWayQuickSort.sort(array.theArray, 0, array.theArray.length - 1);
        long endTime = System.currentTimeMillis();
        System.out.println(Arrays.toString(array.theArray));
        System.out.println();
        System.out.println("ThreeWayQuickSort took " + (endTime - startTime)
                + " milliseconds.");
    }
}
