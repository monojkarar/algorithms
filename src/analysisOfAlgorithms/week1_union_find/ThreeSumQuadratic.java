package analysisOfAlgorithms.week1_union_find;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions - Analysis of Algorithms
 *
 * Question 1: 3-SUM in quadratic time
 *
 * Design an algorithm for the 3-SUM problem that takes time proportional to N^2
 * in the worst case. You may assume that you can sort the N integers in time
 * proportional to N^2 or better.
 *
 * Hint: given an integer x and a sorted array a[] of n distinct integers,
 * design a linear-time algorithm to determine
 * if there exists two distinct indices i and j such that a[i]+a[j]==x.
 */
public final class ThreeSumQuadratic {

    /** Constructor. */
    private ThreeSumQuadratic() { }
    /**
     * findThreeSum.
     * @param a the array
     */
    private static void findThreeSum(final ArrayList<Integer> a) {

        // Compute all 3-sum combinations
        for (int i = 0; i < a.size() - 2; ++i) {
            int j = i + 1;
            int k = a.size() - 1;

            while (j < k) {
                int sum = a.get(i) + a.get(j) + a.get(k);
                if (sum == 0) {
                    System.out.println(a.get(i) + " " + a.get(j) + " "
                            + a.get(k));
                }
                if (sum >= 0) {
                    --k;
                } else {
                    ++j;
                }
            }
        }
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        //initialize array
        ArrayList<Integer> a = new ArrayList<>();

        try {
            Scanner in = new Scanner(new FileReader(args[0]));
            while (in.hasNextInt()) {
                a.add(in.nextInt());
            }

            Collections.sort(a);
            findThreeSum(a);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
