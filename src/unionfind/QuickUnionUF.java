package unionfind;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Quick-union is also too slow;
 * <p>
 * Cost Model: Number of array accesses (for read or write).
 * <p>
 * algorithm   initialize  union   find
 * quick-find  N           N       1
 * quick-union N           N*      N       <- worst case, * includes cost of finding roots
 * <p>
 * Quick-find defect
 * - Union too expensive (N array accesses)
 * - Trees are flat, but too expensive to keep them flat.
 * <p>
 * Quick-union defect
 * - Trees can get tall.
 * - Find too expensive (could be N array accesses).
 */
public class QuickUnionUF {

    private static final String file = "src\\unionfind\\docs\\tinyuf.txt";
    private int[] parent;       // parent[i] = parent of i
    private int count;          // number of components

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    private QuickUnionUF(int n) {
        parent = new int[n];
        count = n;

        // set id of each object to itself (N array accesses)
        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code N})
     */
    private int count() {
        return this.count;
    }

    /**
     * Chase parent pointers until reach root  (depth of i array accesses)
     *
     * @param i the integer representing one object
     * @return the component identifier for the component containing site {@code p}
     * @throws IndexOutOfBoundsException unless {@code 0 <= p < n}
     */
    private int find(int i) {
        int n = parent.length;
        if (i < 0 || i >= n)
            throw new IndexOutOfBoundsException("index " + i + " is not between 0 and " + (n - 1));

        while (i != parent[i])
            i = parent[i];
        return i;
    }

    /**
     * Check whether p and q have the same root (depth of p and q arrray accesses)
     *
     * @param p the p
     * @param q the q
     * @return the boolean
     */
    private boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IndexOutOfBoundsException unless both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        count--;
    }

    /**
     * Reads in a sequence of pairs of integers (between 0 and n-1) from a file,
     * where each integer represents some object;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new FileReader(file));

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
