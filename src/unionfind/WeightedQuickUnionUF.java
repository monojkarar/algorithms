package unionfind;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Weighted Quick-union with path compression:
 * Worst case runtime is N + M lg* N. lg* N is the number of times you have to
 * take log N to get 1. M union-find operations on  set of N objects.
 * <p>
 * Data structure: Same as quick-union, but maintain extra array size[i] to
 * count number of objects in the tree rooted at i.
 * <p>
 * Find. Identical to quick-union.
 * <p>
 * Union. Modify quick-union to
 * - Link root of smaller tree to root of largest tree.
 * - update the size[] array.
 * <p>
 * Weighted quick-union analysis
 * Running time.
 * - Find: takes time proportional to depth of p and q.
 * - Union: takes constant time, given roots.
 * <p>
 * Proposition: Depth of any node x is at most log N
 * 2
 * <p>
 * Cost Model: Number of array accesses (for read or write).
 * <p>
 * algorithm   initialize  union   find
 * quick-find  N           N       1
 * quick-union N           N*      N  <- worst case,*includes cost finding roots
 * weighted QU N           log N*  log N   * includes cost of finding roots
 * 2       2
 * Iterated Log Function
 * In the real world it is a number less than 5 because
 * 65536
 * log(base 2) 2     is 5.
 * N       lg* N
 * 1       0
 * 2       1
 * 4       2
 * 16      3
 * 65536   4
 * 65536
 * 2       5
 */
public final class WeightedQuickUnionUF {
    private static final String file = "src\\unionfind\\docs\\tinyuf.txt";
    //private static final String file = "src\\unionfind\\docs\\mediumuf.txt";
    //private static final String file = "C:\\Users\\Owner\\OneDrive\\largeUF.txt";

    private int[] parent;
    private int[] size;
    private int count;

    /**
     * Initializes empty union–find data structure with N site 0 through N-1.
     * Each site is initially in its own component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {n < 0}
     */
    private WeightedQuickUnionUF(final int n) {
        this.count = 0;
        this.parent = new int[n];
        this.size = new int[n];

        // set id of each object to itself (N array accesses)
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between 1 and N.
     */
    private int count() {
        return count++;
    }

    /**
     * Returns the component identifier for the component containing site i.
     * Takes time proportional to depth of p and q. Depth of any node i is at
     * most log N.
     *
     * Uses path compression. Just after computing the root of i, set the id of
     * each examined node to point to that root.
     *
     * @param i the integer representing one object
     * @return the component identifier for the component containing site i
     * @throws IndexOutOfBoundsException unless 0 <= i < n
     */
    private int find(int i) {
        validate(i);
        while (i != parent[i]) {
            // only one extra line of code! Keeps tree almost completely flat.
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    /**
     * Validate that p is a valid index.
     *
     * @param p the integer representing one object
     */
    private void validate(final int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not "
                    + "between 0 and " + (n - 1));
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return true if the two sites p & q are in the same component; false
     *         otherwise
     * @throws IndexOutOfBoundsException unless both 0 <= p < n and 0 <= q < n
     */
    private boolean connected(final int p, final int q) {

        return find(p) == find(q);
    }

    /**
     * Merges the component containing site p with the the component containing
     * site q. Takes constant time, given roots.
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

        count();
        // make smaller root point to larget one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    /**
     * Reads in a sequence of pairs of integers (between 0 and n-1) from
     * standard input, where each integer represents some object; if the
     * sites are in different components, merge the two component and print the
     * pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        try {
            Scanner in = new Scanner(new FileReader(file));
            long startTime, stopTime, duration;

            // Get the number of objects to be processed
            int n = in.nextInt();

            WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);

            startTime = System.currentTimeMillis();
            while (in.hasNextInt()) {
                int p = in.nextInt();
                int q = in.nextInt();
                if (!uf.connected(p, q)) {
                    uf.union(p, q);
                    System.out.println(p + " " + q);
                }
            }
            stopTime = System.currentTimeMillis();
            duration = stopTime - startTime;
            System.out.println(uf.count() + " components");
            System.out.println("Union Find took " + duration / 1000 + "."
                    + duration % 1000 + " seconds.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
