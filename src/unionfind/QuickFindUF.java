package unionfind;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Quick-find is too slow.
 * Cost model. Number of array access (for read and write)
 * <p>
 * algorithm   initialize  union   find
 * quick-find  N           N       2
 * -------------------------------------
 * order of growth of number of accesses
 * <p>
 * Union is too expensive. It takes N*N array accesses to process a sequencer of N union commands on N objects.
 * Quadratic algorithms don't scale with technology. As computers get faster and bigger, quadratic algorithms
 * actually get slower. People have computers that can run billions of operations per second. They have billions of
 * entries in main memory. We could have billions of objects and hope to do billions of union commands on them.
 * QuickFind would take pow(10, 18) operations or 30 years of computer time.
 * <p>
 * Rough Standard
 * 9                         9
 * 10 operations per second. 10 words of main memory. Touch all words in approximately 1 second.
 * <p>
 * Ex. Huge problem for quick-find
 * 9                    9                                      18
 * 10 union commands on 10 objects. Quick-find takes more than 10 operations.30 years of computer time!
 */
public class QuickFindUF {
    private static final String file = "src\\unionfind\\tinyuf.txt";
    private int[] id;
    private static int N = 0;

    /**
     * Instantiates a new Quick find uf.
     *
     * @param N the n
     */
    private QuickFindUF(int N) {
        id = new int[N];

        // set id of each object to itself (N array accesses)
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    /**
     * Check whether p and q are in the same component (2 array accesses).
     *
     * @param p the p
     * @param q the q
     * @return the boolean
     */
    private boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    /**
     * Change all entries with id[p] to id[q] (at most @N + 2 array accesses.
     *
     * @param p the p
     * @param q the q
     */
    private void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++)
            if (id[i] == pid) id[i] = qid;
    }

    /**
     * Component identifier for p (0 to N-1)
     *
     * @param p the p
     */
    public int find(int p) {
        return 0;
    }

    /**
     * Count the number of components.
     */
    public int count() {
        return 0;
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new FileReader(file));

            // Get the number of objects to be processed
            if (in.hasNextInt())
                QuickFindUF.N = in.nextInt();

            QuickFindUF qf = new QuickFindUF(QuickFindUF.N);

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
