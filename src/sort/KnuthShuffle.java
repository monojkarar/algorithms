package sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *  The type Knuth shuffle.
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
 */
public final class KnuthShuffle {

    /**
     * This class should not be instantiated.
     */
        private KnuthShuffle() { }
    /**
     * SwapValues.
     * @param array the array
     * @param indexOne the indexOne
     * @param indexTwo the index Two
     */
    private static void exch(final Object[] array, final int indexOne, final
    int indexTwo) {

        Object temp = array[indexOne];
        array[indexOne] = array[indexTwo];
        array[indexTwo] = temp;
    }

    /**
     * Shuffle.
     *
     * @param array the array
     */
    private static void shuffle(final Object[] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            int r = i + (int) (Math.random() * (size - i));
            exch(array, i, r);
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
