package bag;

import java.util.ArrayList;

/**
 * BagArray: unordered collection of objects of the same type
 * Implemented using a dynamically allocated array.
 * Can contain multiple values of the same type.
 * <p>
 * Advantages of array over linked list:
 * 1.   Arrays allow random access to element: array[i] while linked lists only
 *      allow sequential access to elements
 * 2.   Arrays do no require extra storage for 'links'. Linked lists are
 *      impractical for lists of characters or booleans (pointer value is
 *      bigger than data value).
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
public final class BagDynamicArray {

    private ArrayList<Object> data;

    private BagDynamicArray() {
        data = new ArrayList<>();
    }

    private ArrayList<Object> getData() {
        return this.data;
    }

    public int size() {
        return this.getData().size();
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public void add(Integer value) {
        this.getData().add(value);
    }

    public void remove(int value) {
        for (int i = 0; i < this.size(); i++) {
            if ((Integer) this.getData().get(i) == value)
                this.getData().remove(i);
        }
    }

    private int occurrences(int value) {
        int occurrences = 0;
        for (int i = 0; i < this.size(); i++) {
            if ((Integer) this.getData().get(i) == value)
                occurrences++;
        }
        return occurrences;
    }

    public static void main(String[] args) {
        BagDynamicArray b = new BagDynamicArray();
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
