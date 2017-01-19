/******************************************************************************
 *  Compilation:  javac MergeBU.java
 *  Execution:    java MergeBU < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/22mergesort/tiny.txt
 *                http://algs4.cs.princeton.edu/22mergesort/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using
 *  bottom-up mergesort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java MergeBU < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java MergeBU < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

package sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 *  The {MergeBU class provides static methods for sorting an array using
 *  bottom-up mergesort.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class MergeBU {

    /** This class should not be instantiated. */
    private MergeBU() { }

    /**
     * stably merge a[lo..mid] with a[mid+1..hi] using aux[lo..hi].
     * @param a the array to sort
     * @param aux the copy of the array
     * @param lo the low index of the array
     * @param mid the middle index of the array
     * @param hi the high index of the array
     */
    private static void merge(final Comparable[] a,
                              Comparable[] aux,
                              final int lo,
                              final int mid,
                              final int hi) {

        // copy to aux[]
        aux = Arrays.copyOf(a, a.length);

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid) {
                a[k] = aux[j++];  // this copying is unneccessary
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(final Comparable[] a) {
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n - len; lo += len + len) {
                int mid  = lo + len - 1;
                int hi = Math.min(lo + len + len - 1, n - 1);
                merge(a, aux, lo, mid, hi);
            }
        }
        assert isSorted(a);
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

    //**************************************************************************
    // Check if array is sorted - useful for debugging.
    //*************************************************************************/

    /**
     * Is array sorted?
     * @param a the array
     * @return true of array is sorted; false otherwise.
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
     *  Print array to standard output.
     *  @param a the array
     */
    private static void show(final Comparable[] a) {
        for (Comparable item : a) {
            StdOut.println(item);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; bottom-up
     * mergesorts them; and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        String[] a = StdIn.readAllStrings();
        MergeBU.sort(a);
        show(a);
    }
}

