package Bag;

/**
 * BagArray: unordered collection of objects of the same type
 * Implemented using a fixed size array. When adding more elements than fit in the bag, the program exist.
 * Solutions:
 * - Use a dynamically allocated array.
 * - When its capacity is reached, allocate a new, bigger array.
 * Can contain multiple values of the same type.
 * <p>
 * <p>
 * Advantages of array over linked list:
 * 1. Arrays allow random access to element: array[i] while linked lists only allow sequential access to elements
 * 2. Arrays do no require extra storage for 'links'. Linked lists are impractical for lists of characters or
 * booleans (pointer value is bigger than data value).
 * <p>
 * Operations:
 * Traversing
 * Searching
 * Sorting
 * Insertion
 * Deletion
 * Merging
 * <p>
 * Average Time Complexity
 * Indexing:    O(1)
 * Search:      O(n)
 * Insertion:   O(n)
 * Deletion:    O(n)
 * <p>
 * constructor:        creates an empty list
 * isEmpty():          is the bag empty
 * size()              returns the number of values
 * add(value):         add a value to the bag
 * remove(value:       removes an value from the bag (if it exists)
 * occurences(value):  how many times is value in the bag
 */
public class BagArray {
    private static int INCREMENT = 20;
    private int[] data;
    private int count;

    BagArray() {
        count = 0;
        data = new int[INCREMENT];
    }

    boolean isEmpty() {
        return (count == 0);
    }

    int size() {
        return count;
    }

    void add(int value) {
        assert (count < INCREMENT);
        data[count] = value;
        count++;
    }

    void remove(int value) {
        int index = -1;
        for (int i = 0; i < count && index == -1; i++) {
            if (data[i] == value)
                index = i;
            if (index != -1) {
                data[index] = data[count - 1];
                count--;
            }
        }
    }

    int occurrences(int value) {
        int occurrences = 0;
        for (int i = 0; i < count; i++) {
            if (data[i] == value)
                occurrences++;
        }
        return occurrences;
    }

    public static void main(String[] args) {
        BagArray b = new BagArray();
        System.out.println("Is bag empty? " + b.isEmpty());
        b.add(4);
        b.add(8);
        b.add(4);

        System.out.println("size " + b.size());
        System.out.println("how many 4's? " + b.occurrences(4));
        b.remove(4);
        System.out.println("removed a 4");
        System.out.println("size " + b.size());
        System.out.println("how many 4's? " + b.occurrences(4));
    }
}

