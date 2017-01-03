package hashtable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * What we'll cover
 * Why we use primes
 * How to increase hash table size
 * How to avoid clustering
 * How double hashing works
 * How to find values in double hashed hash table
 *
 * Why we use primes:
 * arrayIndex = newElementVal % arraySize
 * We want to avoid collisions.  Collisions occur when storing similar data.
 * N values that are similar causes N times as many collisions. Using a prime
 * nuber for the array size helps minimize collisions.
 *
 * How to increase hash table size:
 * Find a prime sized array bigger than what we need.
 * Store values in current array and eliminate empty spaces
 * Increase the size of the current array
 * Using a hash function fill newly sized array with the original values.
 *
 * How to avoid clustering:
 * Clustering occurs because if there is a collision we just move to next index.
 * Each time this occurs it increases the chance it will hit this bigger target.
 * To avoid this use a double hashed hash table.
 * Change the step distance to get the distance to skip down in the array after
 * a collision occurs to a random index. We do this to avoid creating clusters.
 */
public final class HashTable2 {
    /** The array. */
    private String[] theArray;
    /** The array size. */
    private int arraySize;

    /**
     * Unit tests the HastTable3 data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        HashTable2 theFunc = new HashTable2(31);

        String[] elementsToAdd2 = {"100", "510", "170", "214", "268", "398",
                "235", "802", "900", "723", "699", "1", "16", "999", "890",
                "725", "998", "978", "988", "990", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };

        // Demonstrate how data normally follows patterns and how a non-prime
        // sized array can cause havoc
        String[] elementsToAdd3 = {"30", "60", "90", "120", "150", "180",
                "210", "240", "270", "300", "330", "360", "390", "420", "450",
                "480", "510", "540", "570", "600", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };

        theFunc.hashFunction2(elementsToAdd2, theFunc.theArray);

        // theFunc.modThirty();
        theFunc.increaseArraySize(60);
        theFunc.displayTheStack();
        theFunc.fillArrayWithNeg1();
        theFunc.doubleHashFunc(elementsToAdd2, theFunc.theArray);
        theFunc.displayTheStack();
        theFunc.findKeyDblHashed("990");
    }

    /**
     * Outputs matches that would put an item in index 0 if arraySize was 31.
     */
    public void modThirty() {

        for (int i = 1; i < 999; i++) {
            if (i % 30 == 0) {

                System.out.println(i);
            }
        }
    }

    /**
     * Is the number prime?
     * @param number the number to check
     * @return true if number is prime; false otherwise
     */
    private boolean isPrime(final int number) {

        // Eliminate the need to check versus even numbers
        if (number % 2 == 0) {
            return false;
        }
        // Check against all odd numbers
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        // If we make it here we know it is odd
        return true;
    }

    /**
     *  Receives a number and returns the next prime number that follows.
     * @param minNumberToCheck the number to check if prime
     * @return the next prime number
     */
    private int getNextPrime(final int minNumberToCheck) {

        for (int i = minNumberToCheck; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    /**
     * Increase array size to a prime number.
     * @param minArraySize the array size
     */
    private void increaseArraySize(final int minArraySize) {

        // Get a prime number bigger than the array requested
        int newArraySize = getNextPrime(minArraySize);

        // Move the array into a bigger array with the larger prime size
        moveOldArray(newArraySize);
    }

    /**
     * Move old array to new array.
     * @param newArraySize the new array
     */
    private void moveOldArray(final int newArraySize) {

        // Create an array that has all values of theArray, but no empty spaces
        String[] cleanArray = removeEmptySpacesInArray(theArray);

        // Increase the size for theArray
        theArray = new String[newArraySize];
        arraySize = newArraySize;

        // Fill theArray with -1 in every space
        fillArrayWithNeg1();

        // Send the values previously in theArray into the new larger array
        hashFunction2(cleanArray, theArray);
    }

    /**
     * Will remove all empty spaces in an array.
     * @param arrayToClean the array to clean
     * @return the clean array
     */
    private String[] removeEmptySpacesInArray(final String[] arrayToClean) {

        ArrayList<String> stringList = new ArrayList<>();

        // Cycle through the array and if a space doesn't contain -1, or isn't
        // empty add it to the ArrayList

        for (String theString : arrayToClean) {
            if (!theString.equals("-1") && !theString.equals("")) {
                stringList.add(theString);
            }
        }
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * Double hash function.
     * @param stringsForArray strings for array
     * @param theArray the array
     */
    private void doubleHashFunc(final String[] stringsForArray,
                                final String[] theArray) {

        for (int n = 0; n < stringsForArray.length; n++) {
            // Store value in array index
            String newElementVal = stringsForArray[n];

            // Create an index to store the value in by taking the modulus
            int arrayIndex = Integer.parseInt(newElementVal) % arraySize;

            // Get the distance to skip down in the array after a collision
            // occurs. We are doing this rather than just going to the next
            // index to avoid creating clusters

            int stepDistance = 7 - (Integer.parseInt(newElementVal) % 7);
            System.out.println("step distance: " + stepDistance);

            // System.out.println("Modulus Index= " + arrayIndex + " for value "
            // + newElementVal);

            // Cycle through the array until we find an empty space
            while (!theArray[arrayIndex].equals("-1")) {

                arrayIndex += stepDistance;
                // System.out.println("Collision Try " + arrayIndex
                // + " Instead");

                // If we get to the end of the array go back to index 0
                arrayIndex %= arraySize;
            }

            theArray[arrayIndex] = newElementVal;
        }
    }

    /**
     * Returns the value stored in the Double Hashed Hash Table.
     * @param key the key
     * @return the value stored in hash table
     */
    private String findKeyDblHashed(final String key) {

        // Find the keys original hash key
        int arrayIndexHash = Integer.parseInt(key) % arraySize;

        // Find the keys original step distance
        int stepDistance = 5 - (Integer.parseInt(key) % 5);

        while (!theArray[arrayIndexHash].equals("-1")) {

            if (theArray[arrayIndexHash].equals(key)) {

                // Found the key so return it
                System.out.println(key + " was found in index "
                        + arrayIndexHash);
                return theArray[arrayIndexHash];
            }

            // Look in the next index
            arrayIndexHash += stepDistance;

            // If we get to the end of the array go back to index 0
            arrayIndexHash %= arraySize;
        }

        // Couldn't locate the key
        return null;
    }

    /**
     * The hashfunction2.
     * @param stringsForArray strings for array
     * @param theArray the array
     */
    private void hashFunction2(final String[] stringsForArray, final String[]
            theArray) {
        for (int n = 0; n < stringsForArray.length; n++) {

            String newElementVal = stringsForArray[n];

            // Create an index to store the value in by taking the modulus
            int arrayIndex = Integer.parseInt(newElementVal) % arraySize;

            //System.out.println("Modulus Index= " + arrayIndex + " for value "
            // + newElementVal);
            // Cycle through the array until we find an empty space
            while (!theArray[arrayIndex].equals("-1")) {

                ++arrayIndex;
                // System.out.println("Collision Try " + arrayIndex + " Instead");

                // If we get to the end of the array go back to index 0
                arrayIndex %= arraySize;
            }

            theArray[arrayIndex] = newElementVal;
        }
    }

    /**
     * Returns the value stored in the Hash Table.
     * @param key the key
     * @return value stored in the Hash Table
     */
    public String findKey(final String key) {

        // Find the keys original hash key
        int arrayIndexHash = Integer.parseInt(key) % arraySize;

        while (!theArray[arrayIndexHash].equals("-1")) {
            if (theArray[arrayIndexHash].equals(key)) {

                // Found the key so return it
                System.out.println(key + " was found in index "
                        + arrayIndexHash);

                return theArray[arrayIndexHash];
            }

            // Look in the next index
            ++arrayIndexHash;

            // If we get to the end of the array go back to index 0
            arrayIndexHash %= arraySize;
        }

        // Couldn't locate the key
        return null;
    }

    /**
     * Hash Table 2.
     * @param size the size of the table.
     */
    private HashTable2(final int size) {

        arraySize = size;
        theArray = new String[size];

        // Fill Array with -1 for each empty space
        fillArrayWithNeg1();
    }

    /**
     * Fill array with negative 1.
     */
    private void fillArrayWithNeg1() {

        Arrays.fill(theArray, "-1");
    }

    /**
     * Display the stack.
     */
    private void displayTheStack() {

        int increment = 0;
        int numberOfRows = (arraySize / 10) + 1;

        for (int m = 0; m < numberOfRows; m++) {
            increment += 10;

            for (int n = 0; n < 71; n++) {
                System.out.print("-");
            }
            System.out.println();

            for (int n = increment - 10; n < increment; n++) {
                System.out.format("| %3s " + " ", n);
            }

            System.out.println("|");
            for (int n = 0; n < 71; n++) {
                System.out.print("-");
            }
            System.out.println();

            for (int n = increment - 10; n < increment; n++) {
                if (n >= arraySize) {
                    System.out.print("|      ");
                } else if (theArray[n].equals("-1")) {
                    System.out.print("|      ");
                } else {
                    System.out.print(String.format("| %3s " + " ",
                            theArray[n]));
                }
            }

            System.out.println("|");

            for (int n = 0; n < 71; n++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }
}