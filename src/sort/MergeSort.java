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
     * @param aux the aux
     * @param low the lo
     * @param high the hi
     */
    private void mergesort(final Comparable[] array, final Comparable[] aux,
                           int low, final int high) {

        int middle = (low + high) / 2;
        mergesort(array, low, middle);
        mergesort(array, middle + 1, high);

        // copy to aux[]
        for (int k = low; k <= high; k++) {
            aux[k] = array[k];
        }

        // merge back to a[]
        int i = low, j = middle + 1;
        for (int k = low; k <= high; k++) {
            if      (i > middle)           array[k] = aux[j++];
            else if (j > high)             array[k] = aux[i++];
            else if (less(aux[j], aux[i])) array[k] = aux[j++];
            else                           array[k] = aux[i++];
        }
    }

    /**
     * Merge sort algorithm.
     * @param array the array to sort
     * @param lo the lo
     * @param hi the hi
     */
    private void mergesort(final Comparable[] array, final int lo, final int hi) {
        Comparable[] aux = new Comparable[array.length];
        if (lo >= hi) { return; }
        mergesort(array, aux, lo, hi);
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
