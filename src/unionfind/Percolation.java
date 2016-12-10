package unionfind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Author:        Raul Zuniga
 * Login:         rzuniga64@gmail.com
 * Written:       12/4/2016
 * <p>
 * This application models a percolation system using an n-by-n grid of
 * sites. Each site is either open or blocked. A full site is an open
 * site that can be connected to an open site in the top row via a chain
 * of neighboring (left, right, up, down) open sites. We say the system
 * percolates if there is a full site in the bottom row. In other words,
 * a system percolates if we fill all open sites connected to the top
 * row and that process fills some open site on the bottom row.
 * <p>
 * Input:
 * - Size of grid N
 * - Two integer arguments that is used to calculate the site index
 * A model for many physical systems:
 * - N-by-N grid of sites
 * - each site is open with probability p (or locked with probability 1-p)
 * - System percolates iff top and bottom are connected by open sites.
 * <p>
 * model               system      vacant site occupied site   percolates
 * ------------------------------------------------------------------------
 * electricity         material    conductor   insulated       conducts
 * fluid flow          material    empty       blocked         porous
 * social interaction  population  person      empty           communicates
 * <p>
 * Monte Carlo simulation
 * - initialize N-by-N whole grid to be blocked.
 * - Declare random sites open until top connected to bottom.
 * - Vacancy percentage estimates p*.
 * <p>
 * Dynamic connectivity solution to estimate percolation threshold.
 * <p>
 * Question: How to check whether an N-by-N system percolates?2
 * - Create an object for each site and and name them 0 to N - 1.
 * - Sites are in same component if connected by open sites.
 * - Percolates iff any site on bottom row is connected to site on top row.
 * <p>
 * Question: How to model opening a new site?
 * Connect newly opened site to all of its adjacent open sites (up to 4 calls to union()).
 * <p>
 * Question: What is percolation threshold p*?
 * Answer: About 0.592746 for large square lattices (constant only known via simulation).
 */
public class Percolation {

    private int N;                              // number of rows, columns in site grid
    private boolean[] openSites;                // array for open sites
    private WeightedQuickUnionUF grid, auxGrid; // auxiliary grid to help avoid backwash

    /**
     * Initializes an empty grid with all sites initially blocked.
     *
     * @param n the size of the grid
     * @throws IllegalArgumentException if {n < 0}
     */
    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException("number of sites " + n + " is less than or equal to 0");

        N = n;
        int siteCount = N * N;

        // Create n-by-n grid, with all sites blocked. Index 0 and N^2 + 1 are reserved for virtual top and bottom sites.
        grid = new WeightedQuickUnionUF(siteCount + 2);
        auxGrid = new WeightedQuickUnionUF(siteCount + 2);
        openSites = new boolean[siteCount + 2];

        // initialize all sites to be false(blocked)
        for (int i = 1; i <= siteCount; i++)
            openSites[i] = false;

        //initialize virtual top and bottom site with open state
        openSites[0] = openSites[siteCount + 1] = true;
    }

    public int getN() {
        return N;
    }

    /**
     * Return array index of give row and column.
     *
     * @param row number of the site
     * @param col number of the site
     */
    private int getIndex(int row, int col) {

        if (row <= 0 || row > this.getN())
            throw new IndexOutOfBoundsException("Row out of bound");
        if (col <= 0 || col > this.getN())
            throw new IndexOutOfBoundsException("Column out of bound");

        return (row - 1) * this.getN() + col;
    }

    /**
     * Is site(row, col) open?
     *
     * @param row the row
     * @param col the col
     * @return true if the site is open; false otherwise.
     */
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return openSites[index];
    }

    /**
     * Mark site (row, col) as true(open) if it is not open already. Connect newly opened site to all if its adjacent
     * open sites. (up to 4 calls to union).
     *
     * @param row the row
     * @param col the col
     */
    public void open(int row, int col) {

        int index = getIndex(row, col);
        openSites[index] = true;

        // Connect top, bottom, left and right sites of current index if they are open.
        if (row != 1 && isOpen(row - 1, col))        // Check left site
        {
            grid.union(index, getIndex(row - 1, col));
            auxGrid.union(index, getIndex(row - 1, col));
        }
        if (row != getN() && isOpen(row + 1, col))    // Check right site
        {
            grid.union(index, getIndex(row + 1, col));
            auxGrid.union(index, getIndex(row + 1, col));
        }
        if (col != 1 && isOpen(row, col - 1))         // Check top site
        {
            grid.union(index, getIndex(row, col - 1));
            auxGrid.union(index, getIndex(row, col - 1));
        }
        if (col != getN() && isOpen(row, col + 1))    // Check bottom site
        {
            grid.union(index, getIndex(row, col + 1));
            auxGrid.union(index, getIndex(row, col + 1));
        }

        // If site in top row connect to virtual top
        if (row == 1) {
            grid.union(0, index);
            auxGrid.union(0, index);
        }
        if (row == N) {   // site on last row to virtual bottom site. Don't connect to grid to solve backwash issue.
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
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        return auxGrid.connected(0, index);
    }

    /**
     * Is any open site on bottom row of grid connected to open site on top row by open sites(percolates)?
     * Check whether an N-by-N system percolates by checking if sites are in same connected by open sites.
     * <p>
     * Introduce a virtual top site and virtual bottom site (and connections to top and bottom).
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
