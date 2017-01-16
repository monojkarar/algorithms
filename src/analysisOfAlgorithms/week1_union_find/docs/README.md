algs-percolation
----------------
Week 1 Programming assignment of Course "Algorithms, Part 1" from Princeton 
University in Coursera.

###### Assignment details:
> http://coursera.cs.princeton.edu/algs4/assignments/percolation.html

Summary
---------
Percolation is an abstract process where a collection of initially isolated 
nodes gradually becomes connected by addition of random links. This program 
uses weighted quick-union data type to model a percolation system and estimates 
the threshold fraction of open sites in order for a system (initially with all 
sites closed) to be percolated using Monte Carlo simulation.

Percolation
------------

To model a percolation system, Percolation data type is created using 
following API:

      public class Percolation {
         public Percolation(int N)              // create N-by-N grid, with all sites blocked
         public void open(int i, int j)         // open site if it is not already
         public boolean isOpen(int i, int j)    // is site (row i, column j) open?
         public boolean isFull(int i, int j)    // is site (row i, column j) full?
         public boolean percolates()            // does the system percolate?
      }

PercolationStats
-------------------

The PercolationStats uses Monto Carlo simulation to estimate percolation
threshold:

      public class PercolationStats {
         public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
         public double mean()                     // sample mean of percolation threshold

         public double stddev()                   // sample standard deviation of percolation threshold
         public double confidenceLo()             // returns lower bound of the 95% confidence interval
         public double confidenceHi()             // returns upper bound of the 95% confidence interval
         public static void main(String[] args)   // test client, described below
      }

Test client: PercolationVisualizer
-----------------------
PercolationVisualizer visualizes Percolation and the directory of sample input 
files can be found at:
> http://coursera.cs.princeton.edu/algs4/testing/percolation/

The file backwash.txt is used for backwash test. To use it, just run

> $ java PercolationVisualizer backwash.txt

Don't modify anything in backwash.txt and PercolationVisualizer.java. As the 
latter will read data from backwash.txt and use the API in Percolation.java 
file to test class Percolation.