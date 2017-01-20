/******************************************************************************
 *  Compilation:  javac QuickSelect.java
 *  Execution:    java  QuickSelect < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/21sort/tiny.txt
 *                http://algs4.cs.princeton.edu/21sort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using selection sort.
 *  Goal: Given an array of N items, find the kth largest.
 *  Ex: Find Min (k=0), max (k = N-1), median (k = N/2)
 *
 *  Partition array so that:
 *  - Entry a[j] is in place.
 *  - No larger entry to the left of j.
 *  - No smaller entry to the right of j.
 *
 *  Repeat in one subarray, depending on j; finished when j equals k.
 *
 *  Runtime analysis
 *  Best        Average     Worst
 *  O(n)        O(n)        O(n^2) ->low probability if array is shuffled
 *
 *   Applications.
 *   - Order statistics.
 *   - Find the 'top k".
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java QuickSelect < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java QuickSelect < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *****************************************************************************/
package sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

import static sort.SortUtility.generateRandomArray;

/**
 *  The QuickSelect class provides static methods for sorting an array using
 *  selection sort.
 *
 *  Goal. Given an array of N items, find the kth largest.
 *  Ex. Min(k=0), max(k=N-1), median(k=N/2)
 *
 *  Applications
 *  order statistics
 *  Find the 'top k'
 *
 *  - Easy N log N upper bound. How?
 *  - Eay N upper bound for k = 1, 2, 3. How?
 *  - Easy n lower bound. Why?
 *
 *  Partition array so that:
 *  - Entry a[j] is in place.
 *  - No larger entry to the left of j.
 *  - No smaller entry to the right of j.
 *
 * Repeat in one sub-array, depending on j; finished when j equals k.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class QuickSelect {

    /** The array to sort. */
    private  Comparable[] theArray;

    /**
     * This class should not be instantiated.
     * @param n the n
     */
    private QuickSelect(final int n) {
        theArray = new Comparable[n];
        generateRandomArray(this.theArray, this.theArray.length);
    }

    /**
     * `Select algorithm. Partition array so that:
     *  - Entry a[j] is in place.
     *  - No larger entry to the left of j.
     *  - No smaller entry to the right of j.
     *
     *  Repeat in one sub-array, depending on j; finished when j equals k.
     *
     * @param a the array
     * @param k the item to select
     * @return the item selected
     */
    private static Comparable select(final Comparable[] a, final int k) {

        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length - 1;

        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                return a[k];
            }
        }
        return a[k];
    }

    /**
     * Phase 1: Repeat until leftPtr and rightPtr cross.
     * - Scan leftPtr from left to right so long as (a[leftPtr] < a[lo]).
     * - Scan rightPtr from right to left so long as (a[rightPtr] > a[lo]).
     * - Exchange a[i] with a[j].
     *
     * Phase 2: When pointers cross.
     * - Exchange a[lo] with a[j].
     *
     * @param array the array to sort
     * @param left the left pointer
     * @param right the right pointer
     * @return the left pointer
     */
    private static int partition(final Comparable[] array, final int left,
                                 final int right) {

        int i = left;
        int j = right + 1;

        // find item on left to swap
        while (true) {
            while (less(array[++i], array[left])) {
                if (i == right) {
                    break;
                }
            }
            // find item on right to swap
            while (less(array[left], array[--j])) {
                if (j == left) {
                    break;
                }
            }

            if (i >= j) {       // check if pointers cross.
                break;
            }
            exch(array, i, j);  // swap.
        }

        exch(array, left, j);   // swap with partitioning item.
        return j;               // return index of item known to be in place.
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

        QuickSelect selection = new QuickSelect(Integer.parseInt(args[0]));
        System.out.println(Arrays.toString(selection.theArray));
        long startTime = System.currentTimeMillis();
        System.out.println("QuickSelect: "
                + QuickSelect.select(selection.theArray, (selection.theArray
                        .length - 1) / 2).toString());
        long endTime = System.currentTimeMillis();
        System.out.println(Arrays.toString(selection.theArray));
        System.out.println();
        System.out.println("QuickSelect took " + (endTime - startTime)
                + " milliseconds.");
    }
}
