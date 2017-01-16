package analysisOfAlgorithms.week1_union_find;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation
 * A model for many physical systems:
 * - N-by-N grid of sites
 * - Each site is open with probability p (or blocked with probability 1-p).
 * - System percolates iff top and bottom are connected by open sites.
 *
 * This application models a percolation system using an n-by-n grid of
 * sites. Each site is either open or blocked. A full site is an open
 * site that can be connected to an open site in the top row via a chain
 * of neighboring (left, right, up, down) open sites. We say the system
 * percolates if there is a full site in the bottom row. In other words,
 * a system percolates if we fill all open sites connected to the top
 * row and that process fills some open site on the bottom row.
 *
 * Input:
 * - Size of grid N
 * - Two integer arguments that is used to calculate the site index
 * A model for many physical systems:
 * - N-by-N grid of sites
 * - each site is open with probability p (or locked with probability 1-p)
 * - System percolates iff top and bottom are connected by open sites.
 *
 * model               system      vacant site occupied site   percolates
 * ------------------------------------------------------------------------
 * electricity         material    conductor   insulated       conducts
 * fluid flow          material    empty       blocked         porous
 * social interaction  population  person      empty           communicates
 *
 *  Percolation phase transition
 *  When N is large, theory guarantees a sharp threshhold p*.
 *  - p > p*: almost certainly percolates.
 *  - p < p*: almost certainly does not percolate
 *
 * Question: What is percolation threshold p*?
 * Answer: About 0.592746 for large square lattices
 * (constant only known via simulation).
 *
 * Monte Carlo simulation
 * - initialize N-by-N whole grid to be blocked.
 * - Declare random sites open until top connected to bottom.
 * - Vacancy percentage estimates p*.
 *
 * Dynamic connectivity solution to estimate percolation threshold.
 *
 * Question: How to check whether an N-by-N system percolates?2
 * - Create an object for each site and and name them 0 to N - 1.
 * - Sites are in same component if connected by open sites.
 * - Percolates iff any site on bottom row is connected to site on top row.
 *
 * Dynamic connectivity solution to estimate percolation threshhold
 * Clever trick. Introduce 2 virtual sites (and connections to top and bottom).
 * - Percolates iff virtual top is connected to virtual bottom site.
 *
 * Question: How to model opening a new site?
 * Connect newly opened site to all of its adjacent open sites (up to 4
 * calls to union()).
 */
public class Percolation {
    /** Number of rows, columns in site grid. */
    private int n;
    /** Array for open sites. */
    private boolean[] openSites;
    /** Auxiliary grid to help avoid backwash. */
    private WeightedQuickUnionUF grid, auxGrid;

    /**
     * Initializes an empty grid with all sites initially blocked.
     *
     * @param n the size of the grid
     * @throws IllegalArgumentException if {n < 0}
     */
    public Percolation(final int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("number of sites " + n + " is "
                    + "less than or equal to 0");
        }
        this.n = n;
        int siteCount = this.n * this.n;

        // Create n-by-n grid, with all sites blocked. Index 0 and N^2 + 1
        // are reserved for virtual top and bottom sites.
        grid = new WeightedQuickUnionUF(siteCount + 2);
        auxGrid = new WeightedQuickUnionUF(siteCount + 2);
        openSites = new boolean[siteCount + 2];

        // initialize all sites to be false(blocked)
        for (int i = 1; i <= siteCount; i++) {
            openSites[i] = false;
        }
        //initialize virtual top and bottom site with open state
        openSites[0] = true;
        openSites[siteCount + 1] = true;
    }

    /**
     * Get the number of sitees.
     * @return the number of sites
     */
    private int getn() {
        return n;
    }

    /**
     * Return array index of given row and column.
     *
     * @param row number of the site
     * @param col number of the site
     * @return array index of given row and column
     */
    private int getIndex(final int row, final int col) {

        if (row <= 0 || row > this.getn()) {
            throw new IndexOutOfBoundsException("Row out of bound");
        }
        if (col <= 0 || col > this.getn()) {
            throw new IndexOutOfBoundsException("Column out of bound");
        }
        return (row - 1) * this.getn() + col;
    }

    /**
     * Is site(row, col) open?
     *
     * @param row the row
     * @param col the col
     * @return true if the site is open; false otherwise.
     */
    public boolean isOpen(final int row, final int col) {
        int index = getIndex(row, col);
        return openSites[index];
    }

    /**
     * Mark site (row, col) as true(open) if it is not open already. Connect
     * newly opened site to all if its adjacent open sites. (up to 4 calls to
     * union).
     *
     * @param row the row
     * @param col the col
     */
    public void open(final int row, final int col) {

        int index = getIndex(row, col);
        openSites[index] = true;

        // Connect top, bottom, left and right sites of current index if open.
        if (row != 1 && isOpen(row - 1, col)) {      // Check left site
            grid.union(index, getIndex(row - 1, col));
            auxGrid.union(index, getIndex(row - 1, col));
        }
        if (row != getn() && isOpen(row + 1, col)) {  // Check right site
            grid.union(index, getIndex(row + 1, col));
            auxGrid.union(index, getIndex(row + 1, col));
        }
        if (col != 1 && isOpen(row, col - 1)) {        // Check top site
            grid.union(index, getIndex(row, col - 1));
            auxGrid.union(index, getIndex(row, col - 1));
        }
        if (col != getn() && isOpen(row, col + 1)) {  // Check bottom site
            grid.union(index, getIndex(row, col + 1));
            auxGrid.union(index, getIndex(row, col + 1));
        }

        // If site in top row connect to virtual top
        if (row == 1) {
            grid.union(0, index);
            auxGrid.union(0, index);
        }
        // site on last row to virtual bottom site. Don't connect to grid to
        // solve backwash issue.
        if (row == getn()) {
            grid.union(openSites.length - 1, index);
        }
    }

    /**
     * Is site(row, col) connected to the virtual top top (is it full)?
     *
     * @param row the row
     * @param col the col
     * @return true if the site is full; false otherwise.
     */
    public boolean isFull(final int row, final int col) {
        int index = getIndex(row, col);
        return auxGrid.connected(0, index);
    }

    /**
     * Is any open site on bottom row of grid connected to open site on top row
     * by open sites(percolates)? Check whether an N-by-N system percolates
     * by checking if sites are in same connected by open sites.
     * <p>
     * Introduce a virtual top site and virtual bottom site (and connections to
     * top and bottom).
     * - Percolates iff virtual top site is connected to virtual bottom site.
     * - Efficient algorithm: only 1 call to connected().
     * <p>
     * +           <-Virtual top
     * /  /  |  \   \
     * +--+   *   +   *
     * |
     * *  *   *   +   *
     * |
     * *  *   + --+   *
     * |
     * +  *   +   *   *
     * |      |
     * +--+   *   *   *
     * \  \  |  /   /
     * +           <- Virtual bottom
     *
     * @return true if system percolates; false otherwise.
     */
    public boolean percolates() {
        // Check if virtual top and virtual bottom sites are connected.
        return grid.connected(0, openSites.length - 1);
    }
}
