package sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 *  The KnuthShuffle class provides a client for reading in a
 *  sequence of strings and shuffling them using the Knuth (or Fisher-Yates)
 *  shuffling algorithm. This algorithm guarantees to rearrange the elements
 *  in uniformly random order, under the assumption that Math.random()
 *  generates independent and uniformly distributed numbers between 0 and 1.
 *
 *  In iteration i, pick r between 0 and i uniformly at random.
 *  Swap a[i] and a[r].
 *  - Common bug: between 0 and N-1.
 *  - Correct variant: between i and N-1.
 *
 *  Best practices for shuffling (if your business depends on it).
 *  - Use a hardware random-number generator that has passes both the FIPS 140-2
 *    and the NIST statistical test suites.
 *  - Continuously monitor statistic properties:
 *    hardware random-number generators are fragile and fail silently
 *  - Use an unbiased shuffling algorithm.
 *
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  See {@link StdRandom} for versions that shuffle arrays and
 *  subarrays of objects, doubles, and ints.
 */
public final class KnuthShuffle {

    /**
     * This class should not be instantiated.
     */
        private KnuthShuffle() { }

    /**
     * Rearranges an array of objects in uniformly random order
     * (under the assumption that Math.random() generates independent
     * and uniformly distributed numbers between 0 and 1).
     * Common bug: r between 0 anbd N-1.
     * Correct variant: r between i and N-1.
     * @param array the array to be shuffled
     */
    private static void shuffle(final Object[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            // choose index uniformly in [i, n-1]
            int r = i + (int) (Math.random() * (n - i));
            Object swap = array[r];
            array[r] = array[i];
            array[i] = swap;
        }
    }

    /**
     * Reads in a sequence of strings from standard input, shuffles
     * them, and prints out the results.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        // read in the data
        String[] a = StdIn.readAllStrings();

        // shuffle the array
        KnuthShuffle.shuffle(a);

        // print results.
        for (String s: a) {
            StdOut.println(s);
        }
    }
}
