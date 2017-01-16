package unionfind;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Dynamic connectivity. Given a set of N Object.
 * - Union command: connect two objects.
 * - Find/connected query: is there a path connecting the two objects?
 *
 * Quick-find is too slow.
 * Cost model. Number of array access (for read and write)
 *
 * algorithm   initialize  union   find
 * quick-find  N           N       2
 * -------------------------------------
 * order of growth of number of accesses
 *
 * Union is too expensive. It takes N*N array accesses to process a sequencer
 * of N union commands on N objects. Quadratic algorithms don't scale with
 * technology. As computers get faster and bigger, quadratic algorithms
 * actually get slower. People have computers that can run billions of
 * operations per second. They have billions of entries in main memory. We
 * could have billions of objects and hope to do billions of union commands on
 * them. QuickFind would take pow(10, 18) operations or 30 years of computer
 * time.
 *
 * Rough Standard
 * 9                         9
 * 10 operations per second. 10 words of main memory. Touch all words in
 * approximately 1 second.
 *
 * Ex. Huge problem for quick-find
 * 9                    9                                      18
 * 10 union commands on 10 objects. Quick-find takes more than 10 operations.
 * 30 years of computer time!
 */
public final class QuickFindUF {
    /** Test file. */
    private static final String FILE = "src\\unionfind\\test\\tinyuf.txt";
    /** id. */
    private int[] id;
    /** The number of items. */
    private static int n = 0;

    /**
     * Instantiates a new Quick find uf.
     *
     * @param N the n
     */
    private QuickFindUF(final int N) {
        id = new int[N];

        // set id of each object to itself (N array accesses)
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /**
     * Check whether p and q are in the same component (2 array accesses).
     *
     * @param p the first object
     * @param q the second object
     * @return true if p and q are connected; false otherwise.
     */
    private boolean connected(final int p, final int q) {
        return id[p] == id[q];
    }

    /**
     * Change all entries with id[p] to id[q]
     * (at most @N + 2 array accesses.
     *
     * @param p the first object
     * @param q the second object
     */
    private void union(final int p, final int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    /**
     * Component identifier for p (0 to N-1).
     *
     * @param p the p
     * @return the return.
     */
    public int find(final int p) {
        return 0;
    }

    /**
     * Count the number of components.
     * @return the count
     */
    public int count() {
        return 0;
    }

    /**
     * Unit tests the QuickFindUF data type.
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
