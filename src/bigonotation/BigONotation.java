package bigonotation;

import java.util.Arrays;

/**
 * Big O notation is a way to measure how well a computer algorithm scales as
 * the amount of data involved increases. It is not always a measure of speed
 * as you'll see below. This is a rough overview of Big O and doesn't cover
 * topics such as asymptotic analysis, which covers comparing algorithms as
 * data approaches infinity.
 *
 * What we are defining is the part of the algorithm  that has the greatest
 * effect. For example
 * 45n^3 + 20n^2 + 19 = 84 (n=1)
 * 45n^3 + 20n^2 + 19 = 459 (n=2) Does 19 matter?
 * 45n^3 + 20n^2 + 19 = 47019 (n=10)
 * Does the 20n^2 matter if 45n^3 = 45,000?
 * Has an O(n^3) Order of n-cubed
*/

public final class BigONotation {
    /** theArray. */
    private int[] theArray;
    /** arraySize. */
    private int arraySize;
    /** itemsInArray. */
    private int itemsInArray = 0;
    /** startTime. */
    private static long startTime;
    /** endTime. */
    private static long endTime;

    /**
     * @param args the arguments.
     */
    public static void main(final String[] args) {

		//0(1) Test
		 BigONotation testAlgo = new BigONotation(10);
		 testAlgo.addItemToArray(10);
		 System.out.println(Arrays.toString(testAlgo.theArray));

        BigONotation testAlgo2 = new BigONotation(10000);
        testAlgo2.generateRandomArray();

        BigONotation testAlgo3 = new BigONotation(20000);
        testAlgo3.generateRandomArray();

        BigONotation testAlgo4 = new BigONotation(3000);
        testAlgo4.generateRandomArray();

        BigONotation testAlgo5 = new BigONotation(40000);
        testAlgo5.generateRandomArray();

        // O(N) Test (linear search) : time to complete is in direct proportion
        // to the amount of data
        testAlgo2.linearSearchForValue(20);
        testAlgo3.linearSearchForValue(20);
        testAlgo4.linearSearchForValue(20);
        testAlgo5.linearSearchForValue(20);

        //O(N^2) Test (bubbleSort) : time to complete is in direct proportion
        // to the square of the amount of data
        testAlgo2.bubbleSort();
        testAlgo3.bubbleSort();
        testAlgo4.bubbleSort();


        // 0 (log N) Test (binary search): data being used is decreased roughly
        // 50% each time through the algorithm.
        testAlgo2.binarySearchForValue(20);
        testAlgo3.binarySearchForValue(20);

        // O (n log n) Test (quick sort): N because must sort every element in
        // array at ;east one time. Quicksort compares and moves values
        // without shifting so values are going to be compared only once.
        // Each comparison will reduce the possible final sorted list in half.
        // Comparisons = log n! = log n + log(n-1) + ... + log(1) = n log n
        startTime = System.currentTimeMillis();
        testAlgo2.quickSort(0, testAlgo2.itemsInArray);
        endTime = System.currentTimeMillis();
        System.out.println("Quick sort.SelectionSort Took "
                + (endTime - startTime) + " milliseconds");
    }

    /**
     * O(1) An algorithm that executes in the same time regardless of the
     * amount of data. This code executes in the same amount of time no
     * matter how big the array is
     * @param newItem the new Item
    */
    private void addItemToArray(final int newItem) {

        theArray[itemsInArray++] = newItem;
    }

    /** 0(N) An algorithm thats time to complete will grow in direct proportion
     * to the amount of data. The linear search is an example of this.
     *
     * To find all values that match what we are searching for we will have to
     * look in exactly each item in the array.
     *
     * If we just wanted to find one match the Big O is the same because it
     * describes the worst case scenario in which the whole array must be
     * searched
     * @param value the value
     */
    private void linearSearchForValue(final int value) {

        boolean valueInArray = false;
        String indexsWithValue = "";

        startTime = System.currentTimeMillis();

        for (int i = 0; i < arraySize; i++) {

            if (theArray[i] == value) {
                valueInArray = true;
                indexsWithValue += i + " ";
            }
        }

        System.out.println("Value Found: " + valueInArray);
        endTime = System.currentTimeMillis();
        System.out.println("Linear LinearSearch.LinearSearch Took "
                + (endTime - startTime) + " milliseconds");
    }

    /**
     * O(N^2) Time to complete will be proportional to the square of the amount
     * of data (Bubble sort.SelectionSort). Algorithms with more nested
     * iterations will result in O(N^3), O(N^4), etc. performance
     *
     * Each pass through the outer loop (0)N requires us to go through the
     * entire list again so N multiplies against itself or it is squared
    */
    private void bubbleSort() {

        startTime = System.currentTimeMillis();

        for (int i = arraySize - 1; i > 1; i--) {
            for (int j = 0; j < i; j++) {
                if (theArray[j] > theArray[j + 1]) {
                    swapValues(j, j + 1);
                }
            }
        }

        endTime = System.currentTimeMillis();
        System.out.println("Bubble sort.SelectionSort Took "
                + (endTime - startTime) + " milliseconds");
    }
    /**
     * O (log N) Occurs when the data being used is decreased by roughly 50%
     * each time through the algorithm. The Binary search is a perfect
     * example of this.
     *
     * Pretty fast because the log N increases at a dramatically slower rate as
     * N increases
     *
     * O (log N) algorithms are very efficient because increasing the amount of
     * data has little effect at some point early on because the amount of
     * data is halved on each run through
     *
     * @param value the value
    */
    private void binarySearchForValue(final int value) {

        startTime = System.currentTimeMillis();

        int lowIndex = 0;
        int highIndex = arraySize - 1;
        int timesThrough = 0;

        while (lowIndex <= highIndex) {
            int middleIndex = (highIndex + lowIndex) / 2;

            if (theArray[middleIndex] < value) {
                lowIndex = middleIndex + 1;
            } else if (theArray[middleIndex] > value) {
                highIndex = middleIndex - 1;
            } else {
                System.out.println("\nFound a Match for " + value + " at Index "
                        + middleIndex);
                lowIndex = highIndex + 1;
            }
            timesThrough++;
        }

        // This doesn't really show anything because the algorithm is so fast
        endTime = System.currentTimeMillis();
        System.out.println("Binary LinearSearch.LinearSearch Took "
                + (endTime - startTime) + " milliseconds.");
        System.out.println("Times Through: " + timesThrough);
    }

    /** O (n log n) Most sorts are at least O(N) because every element must be
     * looked at, at least once. The bubble sort is O(N^2). To figure out the
     * number of comparisons we need to make with the Quick sort.SelectionSort
     * we first know that it is comparing and moving values very efficiently
     * without shifting. That means values are compared
     * only once. So each comparison will reduce the possible final sorted lists
     * in half.
     * Comparisons = log n! (Factorial of n)
     * Comparisons = log n + log(n-1) + .... + log(1)
     * This evaluates to n log n
     * @param left the left ptr
     * @param right the right ptr
    */
    private void quickSort(final int left, final int right) {

        if (right - left <= 0) {
            return;
        } else {
            int pivot = theArray[right];
            int pivotLocation = partitionArray(left, right, pivot);
            quickSort(left, pivotLocation - 1);
            quickSort(pivotLocation + 1, right);
        }
    }

    /**
     * @param left the left
     * @param right the right
     * @param pivot the pivot
     * @return the int.
     */
    private int partitionArray(final int left, final int right, final int
            pivot) {

        int leftPointer = left - 1;
        int rightPointer = right;

        while (true) {
            while (theArray[++leftPointer] < pivot)
                ;

            while (rightPointer > 0 && theArray[--rightPointer] > pivot)
                ;
            if (leftPointer >= rightPointer) {
                break;
            } else {
                swapValues(leftPointer, rightPointer);
            }
        }
        swapValues(leftPointer, right);
        return leftPointer;
    }
    /**
     * The Constructor.
     * @param size the size
     */
    private BigONotation(final int size) {

        arraySize = size;
        theArray = new int[size];
    }

    /**
     * Generate random array.
     */
    private void generateRandomArray() {

        for (int i = 0; i < arraySize; i++) {
            theArray[i] = (int) (Math.random() * 1000) + 10;
        }
        itemsInArray = arraySize - 1;
    }

    /**
     * Swap values.
     * @param indexOne the index one
     * @param indexTwo the index two
     */
    private void swapValues(final int indexOne, final int indexTwo) {

        int temp = theArray[indexOne];
        theArray[indexOne] = theArray[indexTwo];
        theArray[indexTwo] = temp;
    }
}
