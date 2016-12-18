package sort;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 * The type Merge sort.
 */
public final class MergeSort {
    /** The array to sort. */
    private Comparable[] theArray;
    /**
     *  The constructor.
     *  @param n the number of items in array.
     */
    private MergeSort(final int n) {
        theArray = new Comparable[n];
        generateRandomArray(this.theArray, this.theArray.length);
    }
    /**
     * Merge sort algorithm.
     * @param array the array to sort
     * @param lo the lo
     * @param n the n
     */
    private void mergesort(final Comparable[] array, final int lo,
                                  final int n) {
        int low = lo;
        int high = n;

        if (low >= high) {
            return;
        }
        int middle = (low + high) / 2;
        mergesort(array, low, middle);
        mergesort(array, middle + 1, high);
        int endLow = middle;
        int startHigh = middle + 1;

        // If the lowest index is <= the bottom arrays highest index & the
        // lowest index of the 2nd array is <= to its highest index
        while ((lo <= endLow) && (startHigh <= high)) {
            // If value in 1st index of 1st array is < the value in the 1st
            // index of the 2nd array
            if (less(array[low], array[startHigh])) {
                low++; // Increment to the next index in the 1st array
            } else {
                Comparable temp = array[startHigh];
                // Decrement backwards through the first array starting at the
                // last index in the first array
                for (int k = startHigh - 1; k >= low; k--) {
                    array[k + 1] = array[k];
                }
                array[low] = temp;
                low++;
                endLow++;
                startHigh++;
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
     *  Our strategy will be to compare every element to its successor.
     *  The array is considered unsorted. if a successor has a greater value
     *  than its predecessor. If we reach the end of the loop without finding
     *  that the array is unsorted, then it must be sorted instead.
     *  @param a the array to check.
     *  @return true if sorted; false otherwise.
     */
    private boolean isSorted(final Comparable[] a) {

        for (int i = 0; i < a.length - 1; i++) {
            if (less(a[i + 1], a[i])) {
                return false; // It is proven that the array is not sorted.
            }
        }

        return true; // If this part has been reached, the array must be sorted.
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {

        long startTime;
        long endTime;

        MergeSort ms = new MergeSort(Integer.parseInt(args[0]));

        System.out.println("STARTING ARRAY\n");
        printHorizontalArray(ms.theArray, ms.theArray.length, -1, -1);
        System.out.println();

        startTime = System.currentTimeMillis();
        // Send the array, 0 and the array size
        ms.mergesort(ms.theArray, 0, ms.theArray.length - 1);
        endTime = System.currentTimeMillis();

        System.out.print("FINAL SORTED ARRAY\n");
        printHorizontalArray(ms.theArray, ms.theArray.length, -1, -1);
        System.out.println();
        System.out.println("Mergesort took " + (endTime - startTime)
                + " milliseconds.");
    }
}
