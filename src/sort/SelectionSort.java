package sort;

import static sort.SortUtility.generateRandomArray;
import static sort.SortUtility.printHorizontalArray;

/**
 * 	Selection Sort is normally the best of the elementary sorts.
 *	There is a pass for each position (0..size-1)
 *	On each pass, the smallest (minimum) element in the rest of the list is exchanged (swapped) with element at the
 *	current position. The first part of the list (already processed) is always sorted. Each pass increases the size
 *	of the sorted portion
 *
 * 	Runtime analysis
 * 	Best    Average     Worst
 * 	O(n*n)  O(n*n)      O(n*n)
 *
 *	Array Position	0 	1	2	3	4
 *	Initial State	36	24	10	6	12
 *	After 1st pass	6 	24	10	36	12	6 and 36 are swapped
 *	After 2nd pass	6 	10 	24	36	12	10 and 24 are swapped
 *	After 3rd pass	6	10	12	36	24	12 and 24 are swapped
 *	After 4th pass	6 	10	12	24	36	24 and 36 are swapped
 *
 *	N is the number of elements in the list
 *	Outer loop executes N-1 times
 *	Inner loop executes N-1, then N-2, then N-3, ... then once.
 *	Total number of comparisons (in inner loop) is the sum of 1 to N-1 is N(N + 1) /2
 *	Efficiency of algorith is O(N2)
 */

public class SelectionSort {

    private int[] theArray = new int[50];
    private int arraySize = 10;

    private void swapValues(int indexOne, int indexTwo){

        int temp = theArray[indexOne];
        theArray[indexOne] = theArray[indexTwo];
        theArray[indexTwo] = temp;
    }

    /** On each pass, the smallest (minimum) element in the rest of the list is exchanged (swapped) with element at the
     *	current position. and then repeats searching through the entire array each time */

    private void selectionSort(){

        for(int x=0; x < arraySize; x++){
            int minimum = x;

            for(int y=x; y < arraySize; y++){
                // To change direction of sort just change this from > to <
                if( theArray[y] < theArray[minimum])
                    minimum = y;
            }

            swapValues(x, minimum);
            printHorizontalArray(theArray, arraySize, x, -1);
        }
    }

    public static void main(String[] args){

        SelectionSort newArray = new SelectionSort();
        generateRandomArray(newArray.theArray, newArray.arraySize);
        printHorizontalArray(newArray.theArray, newArray.arraySize, -1,-1);
        newArray.selectionSort();
    }
}