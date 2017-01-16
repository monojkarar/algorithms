package unionfind;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

/**
 * Dynamic connectivity. Given a set of N Object.
 * - Union command: connect two objects.
 * - Find/connected query: is there a path connecting the two objects?
 *
 * Quick-find [eager approach]
 * Data Structure.
 * - Integer array id[] of size N.
 * - Interpretation. p and q are connected iff they have the same id.
 *
 *      0   1   2   3   4   5   6   7   8   9
 * id[] 0   1   1   8   8   0   0   1   8   8
 *
 * 0, 5,, 6 & 1,2,7 & 3,4,8,9 are connected.
 *
 * Quick-find is too slow.
 * Cost model. Number of array access (for read and write)
 *
 * algorithm   initialize  union   find
 * quick-find  N           N       1
 * -------------------------------------
 * order of growth of number of accesses
 *
 * Union is too expensive. It takes N^2 array accesses to process a sequence
 * of N union commands on N objects.
 *
 * Quadratic algorithms don't scale with technology. As computers get faster
 * and bigger, quadratic algorithms actually get slower. People have
 * computers that can run billions of operations per second. They have
 * billions of entries in main memory. We could have billions of objects and
 * hope to do billions of union commands on them. QuickFind would take pow
 * (10, 18) operations or 30 years of computer time.
 *
 * Rough Standard
 * 10^9 operations per second. 10^9 words of main memory. Touch all words in
 * approximately 1 second.
 *
 * Ex. Huge problem for quick-find
 * 10^9 union commands on 10^9 objects. Quick-find takes more than 10^18
 * operations. 30 years of computer time!
 */
public final class QuickFindUF {

    /** Test file. */
    private static final String FILE = "src\\unionfind\\test\\tinyuf.txt";
    /** id. */
    private int[] id;
    /** The number of items. */
    private static int n = 0;

    /**
     * Instantiate union-find data structure with N objects( 0 to N-1).
     *
     * @param N the number of  sites
     * @throws IllegalArgumentException if n < 0
     */
    private QuickFindUF(final int N) {

        if (N < 0) {
            throw new IllegalArgumentException("Number of sites less than 0.");
        }

        id = new int[N];

        // set id of each object to itself (N array accesses)
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /**
     * Add a connection between p and q (at most N + 2 array accesses).
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IndexOutOfBoundsException unless both 0 <= p < n and 0 <= q < n
     */
    private void union(final int p, final int q) {

        int pid = id[p];
        int qid = id[q];

        // change all entries with id[p] to id[q](at mos 2N -2 array accesses)
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) { // don't put id[p] here
                id[i] = qid;
            }
        }
    }

    /**
     * Are p and q in the same component? (depth of p & q array accesses).
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return true if p and q are connected; false otherwise.
     */
    private boolean connected(final int p, final int q) {

        return id[p] == id[q];
    }

    /**
     * Chase parent pointers until reach root  (depth of i array accesses).
     * Component identifier for p (0 to N-1).
     *
     * @param i the integer representing one object
     * @return the component identifier for the component containing site p
     * @throws IndexOutOfBoundsException unless 0 <= p < n
     */
    public int find(final int i) {
        return 0;
    }

    /**
     * Count the number of components.
     * @return the number of components between 1 and N
     */
    public int count() {
        return 0;
    }

    /**
     *  Unit tests the QuickFindUF data type.
     *  Reads in a sequence of pairs of integers (between 0 and n-1) from a
     *  file, where each integer represents some object;
     *  if the sites are in different components, merge the two components
     *  and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        try {
            Scanner in = new Scanner(new FileReader(FILE));

            // Get the number of objects to be processed
            if (in.hasNextInt()) {
                QuickFindUF.n = in.nextInt();
            }
            QuickFindUF qf = new QuickFindUF(QuickFindUF.n);

            while (in.hasNextInt()) {
                int p = in.nextInt();
                int q = in.nextInt();
                if (!qf.connected(p, q)) {
                    qf.union(p, q);
                    System.out.println(p + " " + q);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
