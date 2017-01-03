package hashtable;

import java.util.Arrays;

/**
 *  If we think of a Hash Table as an array then a hash function is used to
 *  generate a unique key for every item in the array. The position the item
 *  goes in is known as the slot. Hashing doesn't work very well in situations
 *  in which duplicate data is stored. Also it isn't good for searching for
 *  anything except a specific key. However a Hash Table is a data structure
 *  that offers fast insertion and searching capabilities.
 *
 *  PUBLIC OPERATIONS
 *  int hashFunction(x)         : find the index into the hash table using x
 *  void insert(x)              : Insert x
 *  void remove(x)              : Remove x
 *  HashEntry find(x)           : Return item and it's index that matches x
 *  void displayTheStack()
 */
public final class HashTableLinearProbing {
    /** Hash table. */
    private HashEntry[] table;
    /** Hash table size. */
    private int tableSize;
    /** Number of occupied spaces. */
    private static int occupied;

    /**
     * HashEntry class.
     */
    private class HashEntry {
        /** The key. */
        private int key;
        /** The value. */
        private String value;

        /**
         * Constructor.
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
         * @return the value
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
    private HashTableLinearProbing(final int size) {

        occupied = 0;
        tableSize = size;
        table = new HashEntry[tableSize];
        Arrays.fill(table, null);
    }

    /**
     * The hash function.
     * @param value the value to hash
     * @return the hash
     */
    private int hashFunction(final String value) {
        // Create an index to store the value in by taking the modulus
        int hash = Math.abs(Integer.parseInt(value)) % this.tableSize;

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
     */
    private int findPos(final String value) {

            int currentPos = hashFunction(value);

            // Cycle through the array until we find an empty slot.
            while (table[currentPos] != null
                    && !table[currentPos].getValue().equals(value)) {

                ++currentPos;
                System.out.println("Collision Try " + currentPos + " Instead");
                occupied++;

                // If we get to the end of the array go back to index 0
                currentPos %= tableSize;
            }

            return currentPos;
    }

    /**
     * Insert a value into the hash function.
     * @param value the value
     */
    private void insert(final String value) {
        int currentPos = findPos(value);

        table[currentPos] = new HashEntry(currentPos, value);
    }

    /**
     * Returns the value stored in the Hash Table.
     *
     * @param value the value to find
     */
    private int find(final String value) {

        int currentPos = findPos(value);

        return table[currentPos].getKey();
    }

    /**
     * Display the hash table.
     */
    private void displayTheStack() {

        int increment = 0;

        for (int m = 0; m < 4; m++) {

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

                if (table[n] == null) {
                    System.out.print("|      ");
                } else {
                    System.out.print(String.format("| %3s " + " ",
                            table[n].getValue()));
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
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        final int TABLE_SIZE = 43;

        HashTableLinearProbing table = new HashTableLinearProbing(TABLE_SIZE);

        // This contains exactly 30 items to show how collisions will work

        String[] valuesArray = {"100", "510", "170", "214", "268", "398",
                "235", "802", "900", "723", "699", "1", "16", "999", "890",
                "725", "998", "978", "988", "990", "989", "984", "320", "321",
                "400", "415", "450", "50", "660", "624" };

        for (String value : valuesArray) {
            table.insert(value);
        }
        // Locate the value 660 in the Hash Table
        table.displayTheStack();
        System.out.println("The number of collisions is : "
                + HashTableLinearProbing.occupied + "\n");
        System.out.println("The value 660 is at location: "
                + table.find("660"));

    }
}


