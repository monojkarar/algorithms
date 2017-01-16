package unionfind;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Dynamic connectivity. Given a set of N Object.
 * - Union command: connect two objects.
 * - Find/connected query: is there a path connecting the two objects?
 *
 * Quick-union is also too slow;
 * Cost Model: Number of array accesses (for read or write).
 *
 * algorithm   initialize  union   find
 * quick-find  N           N       1
 * quick-union N           N*      N <- worst case,
 *                                 *includes cost of finding roots
 *
 * Quick-find defect
 * - Union too expensive (N array accesses)
 * - Trees are flat, but too expensive to keep them flat.
 *
 * Quick-union defect
 * - Trees can get tall.
 * - Find too expensive (could be N array accesses).
 */
public final class QuickUnionUF {
    /** Test file. */
    private static final String FILE = "src\\unionfind\\docs\\tinyuf.txt";
    /** The parent. */
    private int[] parent;       // parent[i] = parent of i
    /** The number of component. */
    private int count;          // number of components

    /**
     * Instantiate union-find data structure with N objects( 0 to N-1).
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if n < 0
     */
    private QuickUnionUF(final int n) {

        if (n < 0) {
            throw new IllegalArgumentException("Number of sites less than 0.");
        }

        parent = new int[n];
        count = n;

        // set id of each object to itself (N array accesses)
        for (int i = 0; i < n; i++) {
            parent[i] = i;
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
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
        parent[rootP] = rootQ;
        count--;
    }

    /**
     * Are p and q in the same component? (depth of p & q array accesses).
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return true if p and q are connected; false otherwise.
     */
    private boolean connected(final int p, final int q) {

        return find(p) == find(q);
    }

    /**
     * Chase parent pointers until reach root  (depth of i array accesses).
     * Component identifier for p (0 to N-1).
     *
     * @param i the integer representing one object
     * @return the component identifier for the component containing site p
     * @throws IndexOutOfBoundsException unless 0 <= p < n
     */
    private int find(int i) {
        int n = parent.length;
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("index " + i + " is not "
                    + "between 0 and " + (n - 1));
        }
        while (i != parent[i]) {
            i = parent[i];
        }
        return i;
    }

    /**
     * Count the number of components.
     *
     * @return the number of components between 1 and N
     */
    private int count() {
        return this.count;
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
            int n = in.nextInt();

            QuickUnionUF uf = new QuickUnionUF(n);

            while (in.hasNextInt()) {
                int p = in.nextInt();
                int q = in.nextInt();
                if (!uf.connected(p, q)) {
                    uf.union(p, q);
                    System.out.println(p + " " + q);
                }
            }
            System.out.println(uf.count() + " count");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
