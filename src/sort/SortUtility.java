package sort;

/**
 * The type Sort utility.
 */
public class SortUtility {

    /** A size. */
    private static final int SIZE = 51;
    /**
     * Generate random array.
     *
     * @param theArray  the the array
     * @param arraySize the array size
     */
// Fills the Array with random values
    static  void generateRandomArray(final Comparable[] theArray, final int
            arraySize) {

        for (int i = 0; i < arraySize; i++) {
            theArray[i] = (int) (Math.random() * 90) + 10;
        }
    }

    /**
     * Print array.
     *
     * @param theArray  the the array
     * @param arraySize the array size
     */
// Prints the Array on the screen in a grid
    public static void printArray(final int[] theArray, final int arraySize) {

        System.out.println("----------");
        for (int i = 0; i < arraySize; i++) {
            System.out.print("| " + i + " | ");
            System.out.println(theArray[i] + " |");
            System.out.println("----------");
        }
    }

    /**
     * Print horizontal array.
     *
     * @param theArray  the the array
     * @param arraySize the array size
     * @param i         the
     * @param j         the j
     */
    static void printHorizontalArray(final Comparable[] theArray, final int
            arraySize, final int i, final int j) {

        for (int n = 0; n < 2 * SIZE; n++) {
            System.out.print("-");
        }
        System.out.println();
        for (int n = 0; n < arraySize; n++) {
            if (n < 10) {
                System.out.print("|  " + n + " ");
            } else {
                System.out.print("| " + n + " ");
            }
        }
        System.out.println("|");
        for (int n = 0; n < 2 * SIZE; n++) {
            System.out.print("-");
        }
        System.out.println();
        for (int n = 0; n < arraySize; n++) {
            System.out.print("| " + theArray[n] + " ");
        }
        System.out.println("|");
        for (int n = 0; n < 2 * SIZE; n++) {
            System.out.print("-");
        }
        System.out.println();

        // END OF FIRST PART
        // ADDED FOR BUBBLE SORT
        if (j != -1) {
            // ADD THE +2 TO FIX SPACING
            for (int k = 0; k < ((j * 5) + 2); k++) {
                System.out.print(" ");
            }
            System.out.print(j);
        }

        // THEN ADD THIS CODE
        if (i != -1) {
            // ADD THE -1 TO FIX SPACING
            for (int l = 0; l < (5 * (i - j) - 1); l++) {
                System.out.print(" ");
            }
            System.out.print(i);
        }
        System.out.println();
    }

    /**
     * Print horz array.
     *
     * @param theArray  the the array
     * @param arraySize the array size
     * @param i         the
     * @param j         the j
     * @param h         the h
     */
    static void printHorzArray(final int[] theArray, final int arraySize,
                               int i, final int j, final int h) {
        if (i == j) {
            i = i - h;
        }
        for (int n = 0; n < SIZE; n++) {
            System.out.print("-");
        }
        System.out.println();

        for (int n = 0; n < arraySize; n++) {
            System.out.print("|  " + n + " ");
        }
        System.out.println("|");

        for (int n = 0; n < SIZE; n++) {
            System.out.print("-");
        }
        System.out.println();

        for (int n = 0; n < arraySize; n++) {
            System.out.print("| " + theArray[n] + " ");
        }
        System.out.println("|");

        for (int n = 0; n < SIZE; n++) {
            System.out.print("-");
        }
        System.out.println();

        if (i != -1) {
            // Number of spaces to put before the F
            int spacesBeforeFront = 5 * i + 4;

            for (int k = 0; k < spacesBeforeFront; k++) {
                System.out.print(" ");
            }
            System.out.print("I");

            // Number of spaces to put before the R
            int spacesBeforeRear = (5 * j + 1 - 1) - spacesBeforeFront;

            for (int l = 0; l < spacesBeforeRear; l++) {
                System.out.print(" ");
            }
            System.out.print("O");
            System.out.println("\n");
        }
    }
}
