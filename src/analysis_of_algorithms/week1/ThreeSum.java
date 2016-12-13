package analysis_of_algorithms.week1;

import edu.princeton.cs.algs4.In;

import java.util.Arrays;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions
 * <p>
 * Question 1: 3-SUM in quadratic time
 * <p>
 * Design an algorithm for the 3-SUM problem that takes time proportional to N^2
 * in the worst case. You may assume that you can sort the N integers in time
 * proportional to N^2 or better.
 * <p>
 * The analysis_of_algorithms.ThreeSum class provides methods for counting and printing the number of triples in an
 * array of distinct integer that sum to 0(ignoring integer overflow). This implementation uses sorting and binary
 * search and time proportional to n^2 log n, where n is the number of integers.
 */
public class ThreeSum {

    // do not instantiate
    private ThreeSum() {
    }

    /**
     * returns true if the sorted array contains any duplicated integers
     *
     * @param a the array of integers
     */
    private static boolean containsDuplicates(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) return true;
        }
        return false;
    }

    /**
     * Prints to standard output the (i, j, k) with (@code i < j < k)
     * such that (@code a[i] + a[j] + a[k] == 0).
     *
     * @param a the array of integers
     * @throws IllegalArgumentException if the array contains duplicate integers.
     */
    public static void printAll(int[] a) {

        int n = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers.");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = Arrays.binarySearch(a, -a[i] + a[j]);
                if (k > j) System.out.println(a[i] + " " + a[j] + " " + a[k]);
            }
        }
    }

    /**
     * Returns the number of triples (i, j, k) with (@code i < j < k) such
     * that (@code a[i] + a[j] + a[k] == 0).
     *
     * @param a the array of integers
     * @return the number of triples (i, j, k) with (@code i < j < k)
     * such that (@code a[i] + a[j] + a[k] = 0
     */
    public static int count(int[] a) {

        int n = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = Arrays.binarySearch(a, -(a[i] - a[j]));
                if (k > j) count++;
            }
        }
        return count;
    }

    /**
     * Reads in a sequence of distinct integers from a file, specified as a command-line argument;
     * Counts the number of triples that sums to exactly zero; prints out the time to perform the computation.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        In in = new In(args[0]);
        int[] a = in.readAllInts();
        int count = count(a);
        printAll(a);
        System.out.println(count);
    }
}
