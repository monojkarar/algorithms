package sort;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 * Bubble Sort
 * On each pass: Compare first two elements. If the first is bigger, they
 * exchange places (swap). Compare second and third elements. If second is
 * bigger, exchange them. Repeat until last two elements of  the list  are
 * compared. Repeat this process until a pass completes with no exchanges. At
 * the end of the first pass, the largest element is moved to the end (it is
 * bigger than all its neighbors)
 * <p>
 * At the end of the second pass, the second largest element is moved to
 * just before the last element. The back end (tail) of the list remains
 * sorted. Each pass increases the size of the sorted portion. No exchanges
 * implies each element is smaller than its next neighbor (so the list is
 * sorted).
 * <p>
 * If you change the algorithm to look at only the unsorted part of the
 * array in each pass, it is exactly like the selection sort.
 * <p>
 * Runtime analysis
 * Best    Average     Worst
 * O(n)    O(n*n)      O(n*n)
 * <p>
 * 7 2 3 8 9 1     7 > 2, swap
 * - -
 * 2 7 3 8 9 1     7 > 3, swap
 * - -
 * 2 3 7 8 9 1     !(7 > 8), no swap
 * - -
 * 2 3 7 8 9 1     !(8 > 9), no swap
 * - -
 * 2 3 7 8 9 1     9 > 1, swap
 * - -
 * 2 3 7 8 1 9     finished pass 1, did 3 swaps
 * <p>
 * 2 3 7 8 1 9     2<3<7<8, no swap, !(8<1), swap
 * - -
 * 2 3 7 1 8 9     (8<9) no swap finished pass 2, did one swap
 * <p>
 * 2 3 7 1 8 9     2<3<7, no swap, !(7<1), swap
 * - -
 * 2 3 1 7 8 9     7<8<9, no swap finished pass 3, did one swap
 * 2 3 1 7 8 9     2<3, !(3<1) swap, 3<7<8<9
 * - -
 * 2 1 3 7 8 9     finished pass 4, did one swap
 * 2 1 3 7 8 9     !(2<1) swap, 2<3<7<8<9
 * - -
 * 1 2 3 7 8 9     finished pass 5, did one swap
 * 1 2 3 7 8 9	   1<2<3<7<8<9, no swaps finished pass 6, no swaps, list
 * is sorted!
 */
public class BubbleSort {

    /** The array to sort. */
    private Comparable[] theArray;

    /**
     * The constructor.
     * @param n the n.
     */
    public BubbleSort(final int n) {
        this.theArray = new Comparable[n];
    }
    /**
     *  This bubble sort will sort everything from smallest to largest.
     */
    private void bubbleSort() {
        // i starts at the end of the array. As it is decremented all indexes
        // greater then it are sorted
        for (int i = theArray.length - 1; i > 1; i--) {
            // The inner loop starts at the beginning of the array and compares
            // each value next to each other. If the value is greater then
            // they are swapped.
            for (int j = 0; j < i; j++) {
                if (less(theArray[j + 1], theArray[j])) {
                    exch(theArray, j, j + 1);
                }
            }
        }
    }

    /**
     *  This bubble sort will sort everything from largest to smallest.
     */
    private void bubbleSortDescending() {
        // i starts at the beginning of the array
        for (int i = 0; i < theArray.length; i++) {
            // The inner loop starts at the beginning of the array 1 index in
            // more than i.
            for (int j = 1; j < (theArray.length - i); j++) {
                // Here we check if the 1st index is less than the next during
                // the first run through.  Then we just increase the indexes
                // until they have all been checked.
                if (less(theArray[j - 1], theArray[j])) {
                    exch(theArray, j - 1, j);
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
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {

        long startTime;
        long endTime;

		BubbleSort bs = new BubbleSort(Integer.parseInt(args[0]));
        generateRandomArray(bs.theArray, bs.theArray.length);

        startTime = System.currentTimeMillis();
        bs.bubbleSort();
        endTime = System.currentTimeMillis();
        System.out.println("Bubblesort took " + (endTime - startTime)
                + " milliseconds.");
        printHorizontalArray(bs.theArray, bs.theArray.length, -1, -1);

        bs.bubbleSortDescending();
        printHorizontalArray(bs.theArray, bs.theArray.length, -1, -1);
    }
}
