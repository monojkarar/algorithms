package analysisOfAlgorithms.week1_union_find;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * The type Percolate stats.
 */
public class PercolationStats {

    /** Number of rows and columns. */
    private int n;
    /** Number of trials. */
    private int trials;
    /** The threshold. */
    private double[] threshold;
    /** Open site count. */
    private int openSiteCount;

    /**
     * Perform trials independent experiments on an n-by-n grid.
     *
     * @param newN      size of an NxN grid
     * @param newTrials the number of trials
     */
    public PercolationStats(final int newN, final int newTrials) {

        if (newN <= 0 || newTrials <= 0) {
            throw new IllegalArgumentException("Size of row/column and number"
                    + "of trials must be larger than 0");
        }
        n = newN;
        trials = newTrials;
        threshold = new double[trials];
        openSiteCount = 0;

        runTrials(n, trials);
    }

    /**
     * Perform independent computational experiment for a number of trials on
     * an n-by-n grid.
     * @param n the number of sites
     * @param trials the number of trials
     */
    private void runTrials(final int n, final int trials) {
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSiteCount++;
                }
            }
            threshold[i] = (double) openSiteCount / (n * n);
            openSiteCount = 0;
        }
    }

    /**
     * Sample mean of percolation threshold.
     *
     * @return the sample mean as a double
     */
    private double mean() {

        return StdStats.mean(threshold);
    }

    /**
     * Sample standard deviation of percolation threshold.
     *
     * @return the standard deiviation as a double
     */
    private double stddev() {

        return StdStats.stddev(threshold);
    }

    /**
     * Low endpoint of 95% confidence interval.
     *
     * @return the low endpoint as a double.
     */
    private double confidenceLo() {

        return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
    }

    /**
     * High endpoint of 95% confidence interval.
     *
     * @return the high endpoint as a double
     */
    private double confidenceHi() {

        return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        PercolationStats perStats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        Stopwatch time = new Stopwatch();
        StdOut.printf("mean                     = %f\n", perStats.mean());
        StdOut.printf("stddev                   = %f\n", perStats.stddev());
        StdOut.printf("95%% confidence Interval = %f, %f\n",
                perStats.confidenceLo(), perStats.confidenceHi());
        StdOut.printf("Elapsed time             = " + time.elapsedTime());
    }
}
