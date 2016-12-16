package analysisOfAlgorithms.week1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions - Analysis of Algorithms
 * <p>
 * Question 1: 3-SUM in quadratic time
 * <p>
 * Design an algorithm for the 3-SUM problem that takes time proportional to N^2
 * in the worst case. You may assume that you can sort the N integers in time
 * proportional to N^2 or better.
 * <p>
 * Hint: given an integer x and a sorted array a[] of n distinct integers, design a linear-time algorithm to determine
 * if there exists two distinct indices i and j such that a[i]+a[j]==x.
 */
public class ThreeSumQuadratic {

    private static void findThreeSum(ArrayList<Integer> a) {

        // Compute all 3-sum combinations
        for (int i = 0; i < a.size() - 2; ++i) {
            int j = i + 1;
            int k = a.size() - 1;

            while (j < k) {
                int sum = a.get(i) + a.get(j) + a.get(k);
                if (sum == 0) {
                    System.out.println(a.get(i) + " " + a.get(j) + " " + a.get(k));
                }
                if (sum >= 0) {
                    --k;
                } else {
                    ++j;
                }
            }
        }
    }

    public static void main(String[] args) {
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
