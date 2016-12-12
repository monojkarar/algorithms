package bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The BagArray claas represents an unordered collection of generic objects of the same type.  It supports iterating
 *  over items in an arbitrary way.
 *
 *  Implemented using a fixed size array. This implementation uses a resizing array.
 *
 *  Advantages of array over linked list:
 *  1. Arrays allow random access to element: array[i] while linked lists only allow sequential access to elements
 *  2. Arrays do no require extra storage for 'links'. Linked lists are impractical for lists of characters or
 *  booleans (pointer value is bigger than data value).
 *
 *  Average Time Complexity
 *  add:                constant amortized time
 *  isEmpty:            constant time
 *  size                constant time
 *  remove(value):      O(N)
 *  occurrences(value)  O(N)
 *
 *  constructor:        creates an empty list
 *  isEmpty():          is the bag empty
 *  size()              returns the number of values
 *  add(value):         add a value to the bag
 *  remove(value):      removes an value from the bag (if it exists)
 *  occurrences(value): how many times is value in the bag
 */
public class BagArray<T> implements Iterable<T>{

    private T[] data;   // bag elements
    private int count;  // number of elements in the bag

    /**
     * Constructor for bag that initializes an empty bag
     * Average time complexity: O(1)
     */
    private BagArray() {

        count = 0;
        data = (T[]) new Object[2];
    }

    /**
     * Returns true or false if bag is empty.
     * Average time complexity: O(1)
     *
     * @return true if bag is empty; false otherwise
     */
    boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns true or false if bag is full.
     * Average time complexity: O(1)
     *
     * @return true if bag is full; false otherwise
     */
    boolean isFull() {
        return (count == data.length-1);
    }

    /**
     * Returns the number of items in the bag.
     *
     * @return the number of items in the bag
     */
    private int size() {
        return count;
    }

    /**
     * Resize the bag.
     */
    private void resize(int capacity) {

        assert capacity >= count;

        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = data[i];
        }
        data = copy;
    }

    /**
     * Add an item into the bag.
     * Average time complexity: O(1)
     */
    private void add(T value) {

        if (isFull())
            resize(2 * data.length);

        data[count] = value;
        count++;
    }

    /**
     * Remove an item from the bag.
     * Average time complexity: O(N)
     */
    private void remove(T value) {

        int index = -1;
        for (int i = 0; i < count && index == -1; i++) {
            if (data[i] == value)
                index = i;
            if (index != -1) {
                data[index] = data[count - 1];
            }
        }
        count--;
    }

    /**
     * Count the number of items in the bag that equal a certain value.
     * Average time complexity: O(N)
     *
     * @return The number of items in the bag that equal a certain value.
     */
    private int occurrences(T value) {
        int occurrences = 0;
        for (int i = 0; i < count; i++) {
            if (data[i] == value)
                occurrences++;
        }
        return occurrences;
    }

    /**
     * Returns an iterator that iterates over the items in this bag in LIFO order.
     *
     * @return an iterator that iterates over the items in this bag in LIFO order
     */
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<T> {

        private int i = 0;

        public boolean hasNext() { return i < count; }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data[i++];
        }
    }

    public static void main(String[] args) {

        BagArray<Integer> bag = new BagArray<>();
        System.out.println("Is bag empty? " + bag.isEmpty());
        bag.add(4);
        bag.add(8);
        bag.add(4);

        for (Integer item : bag)
            System.out.println(item);


        System.out.println("size " + bag.size());
        System.out.println("how many 4's? " + bag.occurrences(4));
        bag.remove(4);
        System.out.println("removed a 4");
        System.out.println("size " + bag.size());
        System.out.println("how many 4's? " + bag.occurrences(4));
    }
}

