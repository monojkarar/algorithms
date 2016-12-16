package unionfind;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * The type Percolate stats.
 */
public class PercolationStats {

    private int N;
    private int trials;
    private double[] threshold;
    private int openSiteCount;

    /**
     * Perform trials independent experiments on an n-by-n grid.
     *
     * @param n      size of an NxN grid
     * @param trials the number of trials
     */
    public PercolationStats(final int n, final int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Size of row/column and number"
                    + "of trials must be larger than 0");
        }
        this.N = n;
        this.trials = trials;
        this.threshold = new double[trials];
        this.openSiteCount = 0;

        runTrials(N, trials);
    }

    /**
     * Perform independent computational experiment for a number of trials on
     * an n-by-n grid.
     */
    private void runTrials(final int N, final int trials) {
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSiteCount++;
                }
            }
            threshold[i] = (double) openSiteCount / (N * N);
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
    public static void main(String[] args) {

        PercolationStats perStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        Stopwatch time = new Stopwatch();
        StdOut.printf("mean                     = %f\n", perStats.mean());
        StdOut.printf("stddev                   = %f\n", perStats.stddev());
        StdOut.printf("95%% confidence Interval = %f, %f\n", perStats.confidenceLo(), perStats.confidenceHi());
        StdOut.printf("Elapsed time             = " + time.elapsedTime());
    }
}
