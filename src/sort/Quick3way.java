/******************************************************************************
 *  Compilation:  javac Quick3way.java
 *  Execution:    java Quick3way < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                http://algs4.cs.princeton.edu/23quicksort/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using 3-way quicksort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Quick3way < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java Quick3way < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

package sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

/**
 *  The Quick3way class provides static methods for sorting an array using
 *  quicksort with 3-way partitioning.
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class Quick3way {

    /** This class should not be instantiated. */
    private Quick3way() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(final Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray a[lo .. hi] using 3-way partitioning

    /**.
     * quicksort the subarray a[lo .. hi] using 3-way partitioning
     * @param a the array to sort
     * @param lo the left
     * @param hi the right
     */
    private static void sort(final Comparable[] a,
                             final int lo,
                             final int hi) {
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

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
        assert isSorted(a, lo, hi);
    }

    //**************************************************************************
    //  Helper sorting function.
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
     * Exchange a[i] and a[j].
     * @param a the array
     * @param i index 1
     * @param j index 2
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
     * Is array sorted?
     * @param a the array
     * @return true of array is sorted; false otherwise.
     */
    private static boolean isSorted(final Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    /**
     *  Our strategy will be to compare every element to its successor.
     *  The array is considered unsorted. if a successor has a greater value
     *  than its predecessor. If we reach the end of the loop without finding
     *  that the array is unsorted, then it must be sorted instead.
     *  @param a the array to check.
     *  @param lo the start.
     *  @param hi the end.
     *  @return true if sorted; false otherwise.
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
     * Print array to standard output.
     * @param a the array
     */
    private static void show(final Comparable[] a) {

        System.out.println(Arrays.toString(a));
    }

    /**
     * Reads in a sequence of strings from standard input; 3-way
     * quicksorts them; and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        String[] a = StdIn.readAllStrings();
        Quick3way.sort(a);
        show(a);
    }

}
