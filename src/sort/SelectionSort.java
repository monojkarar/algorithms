package sort;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 *  Selection Sort is normally the best of the elementary sorts.
 *  There is a pass for each position (0..size-1).
 *  On each pass, the smallest (minimum) element in the rest of the list is
 *  exchanged (swapped) with element at the current position. The first part
 *  of the list (already processed) is always sorted. Each pass increases the
 *  size of the sorted portion.
 *
 *  Runtime analysis
 *  Best    Average     Worst
 *  O(n*n)  O(n*n)      O(n*n)
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
     * Return true if v < w; false otherwise.
     * @param v the variable v
     * @param w tje variable y
     * @return boolean true if v < y; false otherwise.
     */
    private boolean less(final Comparable v, final Comparable w) {
            return v.compareTo(w) < 0;
    }
    /**
     * On each pass, the smallest (minimum) element in the rest of the list is
     * exchanged (swapped) with element at the current position. and then
     * repeats searching through the entire array each time
     * @param array the array
     */

    private void selectionSort(final Comparable[] array) {

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
     * The entry point.
     * @param args the args
     */
    public static void main(final String[] args) {

        SelectionSort ss = new SelectionSort(Integer.parseInt(args[0]));
        generateRandomArray(ss.theArray, ss.theArray.length);
        printHorizontalArray(ss.theArray, ss.theArray.length, -1, -1);
        ss.selectionSort(ss.theArray);
    }
}
