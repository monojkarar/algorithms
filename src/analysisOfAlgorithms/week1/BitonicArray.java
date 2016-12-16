package analysisOfAlgorithms.week1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions
 * <p>
 * Question 2: Search in a bitonic array
 * An array is bitonic if it is comprised of an increasing sequence of integers
 * followed immediately by a decreasing sequence of integers. Write a program
 * that, given a bitonic array of N distinct integer values, determines whether
 * a given integer is in the array.
 * <p>
 * Standard version: Use ~3lgN compares in the worst case.
 * Signing bonus: Use ~2lgN compares in the worst case (and prove that no
 * algorithm can guarantee to perform fewer than ~2lgN compares in the worst case).
 * <p>
 * Number of comparisons to find the largest element, in the worst case = logn
 * Number of comparisons to find the element in the increasing part of the array = logn1
 * Number of comparisons to find the element in the decreasing part of the array = logn2
 * Where n1+n2 = n = number of elements in the array
 * Therefore, total number of comparisons = 3 log n
 * <p>
 * Hints: Standard version. First, find the maximum integer using ∼1lgn compares—this
 * divides the array into the increasing and decreasing pieces.
 * Signing bonus. Do it without finding the maximum integer.
 */
public class BitonicArray {

    /**
     * returns true if the sorted array contains any duplicated integers
     *
     * @param a the array of integers
     */
    private static boolean containsDuplicates(Integer[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i].equals(a[i - 1])) return true;
        }
        return false;
    }

    /**
     * returns true if the sorted array contains any duplicated integers
     *
     * @param array the array of integers
     * @param start the index of the first element (inclusive) to be searched
     * @param end   the index of the last element (exclusive) to be searched
     */
    private static int findMax(Integer[] array, int start, int end) {

        int middle = (start + end) / 2;
        if (start == end) {
            return middle;
        } else if (array[middle] < array[middle + 1]) {
            return findMax(array, middle + 1, end);
        } else {
            return findMax(array, start, middle);
        }
    }

    /**
     * returns index if the sorted array contains any duplicated integers
     *
     * @param array the array
     * @param start the index of the first element (inclusive) to be searched
     * @param end   the index of the last element (inclusive) to be searched
     * @param key   index of the search key, if it is contained in the bitonic array within the specified range
     */
    private static int bitonicBinarySearch(Object[] array, int start, int end, int key) {

        int middle = (start + end) / 2;
        if (array[middle].equals(key)) {
            return middle;
        }
        if (start == end) {
            return -1;
        }

        Comparable midVal = (Comparable) array[middle];
        int cmp = midVal.compareTo(key);

        if (cmp < 0) {
            return bitonicBinarySearch(array, middle + 1, end, key);
        } else if (cmp > 0) {
            return bitonicBinarySearch(array, start, middle, key);
        }

        return -1;
    }

    /**
     * returns index if the sorted array contains any duplicated integers
     *
     * @param array the array of integers
     * @param max   the maximum element in the bitonic array
     * @param key   index of the search key, if it is contained in the bitonic array within the specified range
     */
    public static int find(Integer[] array, int max, int key) {

        int k = bitonicBinarySearch(array, 0, max, key);
        // Search increasing part of the array using binary search
        if (k == -1) {
            k = bitonicBinarySearch(array, max, array.length - 1, key);
        }
        return k;
    }

    /**
     * Reads in a bitonic sequence of distinct integers from a file, specified as a command-line argument;
     * Determines whether an integer is in a bitonic array.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        //initialize array
        ArrayList<Integer> arraylist = new ArrayList<>();

        try {
            Scanner in = new Scanner(new FileReader(args[0]));
            while (in.hasNextInt()) {
                arraylist.add(in.nextInt());
            }
            Integer[] array = new Integer[arraylist.size()];
            arraylist.toArray(array);

            if (containsDuplicates(array)) throw new IllegalArgumentException("array contains duplicate integers");

            int max = findMax(array, 0, array.length - 1);
            System.out.println("Max integer " + array[max] + " is in bitonic array at index " + max);
            int key = 1;
            System.out.println("Integer " + key + " is in bitonic array at index " + find(array, max, key));
            key = 4;
            System.out.println("Integer " + key + " is in bitonic array at index " + find(array, max, key));
            key = 5;
            System.out.println("Integer " + key + " is in bitonic array at index " + find(array, max, key));
            key = 9;
            System.out.println("Integer " + key + " is in bitonic array at index " + find(array, max, key));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
