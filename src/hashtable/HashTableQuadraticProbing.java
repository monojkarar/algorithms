package hashtable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Quadratic Probing Method
 *
 *  Quadratic Probing is a collision resolution method that eliminates the
 *  primary clustering problem of linear probing by examining certain cells
 *  away from the original probe point.  Its name is derived from the use of
 *  the formula F(i) = i*i to resolve collisions.  Specifically, if the has
 *  function evaluates to H and a search in slot H is inconclusive, the try
 *  slots H + 1*1 + H + 2*2 + H + 3 * 3, ..., H + i * i (employing wraparound)
 *  in a sequence. This strategy differs from the linear probing strategy of
 *  searching H + 1, H +2, H + 3,..., H + i.
 *
 * Why we use primes:
 * arrayIndex = newElementVal % arraySize
 * We want to avoid collisions which occur when storing similar data.  N values
 * that are similar causes N times as many collisions. Using a prime number
 * for the array size helps minimize collisions because prime numbers tend t
 * remove some of the non-randomness that is occasionally introduced by the
 * hash function.
 *
 * How to increase hash table size:
 * Find a prime sized array bigger than what we need.
 * Store values in current array and eliminate empty spaces
 * Increase the size of the current array
 * Using a hash function fill newly sized array with the original values.
 */
public final class HashTableQuadraticProbing {
    /** The array. */
    private ArrayList<HashEntry> theArray;
    /** The array size. */
    private int arraySize;
    /** Number of occupied entries. */
    private static int occupied;
    /** Number of collisions. */
    private static int collisions;

    /**
     * HashEntry class.
     */
    private class HashEntry {
        /** The key. */
        private int key;
        /** The value. */
        private String value;

        /**
         * The constructor.
         * @param newKey the key
         * @param newValue the value
         */
        HashEntry(final int newKey, final String newValue) {
            this.key = newKey;
            this.value = newValue;
        }

        /**
         * Getter for key.
         * @return the key
         */
        int getKey() {
            return key;
        }

        /**
         * Getter for value.
         * @return the value.
         */
        String getValue() {
            return value;
        }
    }

    /**
     * Constructor.
     * Returns the value stored in the Hash Table
     *
     * @param size the size of the hash table
     */
    private HashTableQuadraticProbing(final int size) {

        collisions = 0;
        occupied = 0;
        arraySize = size;
        theArray = new ArrayList<>(Collections.nCopies(size, null));
    }

    /**
     *  Receives a number and returns if N is prime.
     *  The search for a prime number takes at most O(power(N, 1/2)).
     *
     * @param n approximate size of array
     * @return true if number is prime; false otherwise
     */
    private boolean isPrime(final int n) {

        // Eliminate the need to check versus even numbers
        if (n % 2 == 0) {
            return false;
        }
        // Check against all odd numbers
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        // If we make it here we know it is odd
        return true;
    }

    /**
     *  Receives a number and returns the next prime number that follows.
     *
     *  Prime numbers are easy to find. We expect to have to test only O(long N)
     *  numbers until we find a number that is prime.  We expect to have to
     *  test only O(log N) numbers until we find a number that is prime.
     *  The primality test takes O(power(N, 1/2)). So the search for a prime
     *  number takes at most O(pow(N, 1/2) log N) time.
     *
     * @param n approximate size of array
     * @return the next prime number
     */
    private int nextPrime(int n) {

        if (n % 2 == 0) {
            n++;
        }
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    /**
     * The hash function.
     * @param value the vlaue to hash
     * @return the hash
     */
    private int hashFunction(final String value) {
        // Create an index to store the value in by taking the modulus
        int hash = Math.abs(Integer.parseInt(value)) % this.arraySize;

        System.out.println("Modulus Index= " + hash + " for value " + value);
        return hash;
    }

    /**
     *  Division Method
     *
     *  Create a hashing function using the remainder of the key divided by the
     *  table size as the index for the given element. This function could be
     *  defined as follows: Hashcode(key) = Math.abs(key)%p This function will
     *  yield a result in the range from 0 to tableSize-1 so have an index
     *  that maps directly to a location in the table. The goal is to make
     *  the array big enough to avoid collisions, but not so big that we waste
     *  memory.
     *
     * @param value the value to store in the hash table
     * @return  find position
     */
    private int findPos(final String value) {

        int i = 0;
        int currentPos = hashFunction(value);

        // Cycle through the array until we find an empty slot.
        while (theArray.get(currentPos) != null
                && !theArray.get(currentPos).getValue().equals(value)) {

            currentPos += 2 * ++i - 1;
            System.out.println("Collision Try " + currentPos + " Instead");
            collisions++;

            if (currentPos >= theArray.size()) {
                currentPos -= theArray.size();
            }
        }

        return currentPos;
    }

    /**
     *  The rehash function.
     */
    private void rehash() {
        ArrayList<HashEntry> oldTable = theArray;
        collisions = 0;
        occupied = 0;

        // Create new empty hash table
        theArray = new ArrayList<>(Collections.nCopies(nextPrime(2 * oldTable.size()), null));

        // Copy table over
        for (HashEntry cell: oldTable) {
            if (cell != null) {
                insert(cell.getValue());
            }
        }

        setArraySize(theArray.size());
    }

    /**
     * Insert a value into the hash table.
     * @param value the value to insert
     */
    private void insert(final String value) {
        int currentPos = findPos(value);

        if (theArray.get(currentPos) == null
                || !theArray.get(currentPos).getValue().equals(value)) {
            theArray.set(currentPos, new HashEntry(currentPos, value));
        }

        if (++occupied > theArray.size() / 2) {
            rehash();
        }
    }

    /**
     * Returns the value stored in the Hash Table.
     * @param value the value
     * @return the value in the hsh table.
     */
    public int find(final String value) {

        int currentPos = findPos(value);

        return theArray.get(currentPos).getKey();
    }

    /**
     *  Display the stack.
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
                } else if (theArray.get(n) == null) {
                    System.out.print("|      ");
                } else {
                    System.out.print(String.format("| %3s " + " ",
                            theArray.get(n).getValue()));
                }
            }

            System.out.println("|");

            for (int n = 0; n < 71; n++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    /**
     * Get the size of the array.
     * @param arraySize the size of the array.
     */
    private void setArraySize(final int arraySize) {
        this.arraySize = arraySize;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        final int TABLE_SIZE = 31;

        HashTableQuadraticProbing table
                = new HashTableQuadraticProbing(TABLE_SIZE);

        String[] elementsToAdd1 = {"100", "510", "170", "214", "268", "398",
                "235", "802", "900", "723", "699", "1", "16", "999", "890",
                "725", "998", "978", "988", "990", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };

        // Demonstrate how data normally follows patterns and how a non-prime
        // sized array can cause havoc
        String[] elementsToAdd2 = {"30", "60", "90", "120", "150", "180",
                "210", "240", "270", "300", "330", "360", "390", "420", "450",
                "480", "510", "540", "570", "600", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };


        for (String element: elementsToAdd1) {
            table.insert(element);
        }
        table.displayTheStack();
        System.out.println("The number of occupied is : "
                + HashTableQuadraticProbing.occupied + "\n");
        System.out.println("The number of collisions is : "
                + HashTableQuadraticProbing.collisions + "\n");
        System.out.println("The value 990 is at location: "
                + table.find("990"));
    }
}