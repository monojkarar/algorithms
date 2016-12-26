package search;

import static sort.SortUtility.generateRandomArray;

/**
 * Linear Search.
 */
public final class LinearSearch {
    /** Create an array with n indexes. */
    private Comparable[] theArray;

    /**
     * The constructor.
     * @param n the size of the array.
     */
    LinearSearch(final int n) {
        generateRandomArray(this.theArray, this.theArray.length);
        theArray = new Comparable[n];
    }

    /**
     * Get the array.
     * @return the array
     */
    private Comparable[] getTheArray() {
        return theArray;
    }
    /**
     *  Prints the Array on the screen in a grid.
     */
    public  void printArray() {

        System.out.println("----------");
        for (int i = 0; i < theArray.length; i++) {
            System.out.print("| " + i + " | ");
            System.out.println(theArray[i] + " |");
            System.out.println("----------");
        }
    }
    /**
     * Linear LinearSearch.LinearSearch : Every index must be looked at.
     * @param value the value
     * @return return index of value
     */
    private String linearSearchForValue(final Comparable value) {

        boolean valueInArray = false;
        String indexsWithValue = "";
        System.out.print("The Value was Found in the Following Indexes: ");
        for (int i = 0; i < getTheArray().length; i++) {
            if (theArray[i] == value) {
                valueInArray = true;
                System.out.print(i + " ");
                indexsWithValue += i + " ";
            }
        }
        if (!valueInArray) {
            indexsWithValue = "None";
            System.out.print(indexsWithValue);
        }

        System.out.println();
        return indexsWithValue;
    }

    /**
     * Unit test for Linear Search class.
     * @param args the arguments
     */
    public static void main(final String[] args) {
        LinearSearch ls = new LinearSearch(Integer.parseInt(args[0]));
        ls.printArray();
        ls.linearSearchForValue(11);
    }
}
