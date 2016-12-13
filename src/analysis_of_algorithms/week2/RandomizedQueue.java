package analysis_of_algorithms.week2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import queues.QueueStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static edu.princeton.cs.algs4.StdRandom.shuffle;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen
 * uniformly at random from items in the data structure.
 * <p/>
 * Requirement: Your randomized queue implementation must support each randomized queue operation
 * (besides creating an iterator) in constant amortized time and use space proportional
 * to the number of items currently in the queue. That is, any sequence of M randomized
 * queue operations (starting from an empty queue) should take at most cM steps in the
 * worst case, for some constant c. Additionally, your iterator implementation must
 * support construction in time linear in the number of items and it must support the
 * operations next() and hasNext() in constant worst-case time; you may use a linear
 * amount of extra memory per iterator. The order of two or more iterators to the same
 * randomized queue should be mutually independent; each iterator must maintain its own
 * random order.
 *
 * @param <T>
 */
public class RandomizedQueue<T> implements Iterable<T> {

    private T[] queue;      // queue elements
    private int length;          // number of elements in the queue


    /**
     * Constructor for randomized queue that initializes an empty queue
     */
    RandomizedQueue() {

        queue = (T[]) new Object[2];
        length = 0;
    }

    /**
     * Returns true or false if queue is empty.
     * Average time complexity: O(1)
     *
     * @return true if queue is empty; false otherwise
     */
    boolean isEmpty() {

        return length == 0;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {

        return length;
    }

    /**
     * Resize the stack.
     */
    private void resize(int capacity) {

        assert capacity >= length;

        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < length; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    void enqueue(T item) {

        if (item == null) {
            throw new NullPointerException("Item is null");
        }
        if (length == queue.length)
            resize(queue.length * 2);
        queue[length++] = item;
    }

    /**
     * Removes and returns a random item
     *
     * @return the random item that was removed
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public T dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int random = StdRandom.uniform(length);
        T item = queue[random];
        if (random != length - 1) {
            queue[random] = queue[length - 1];
        }

        // Loitering is holding a reference to an object when it is no longer needed.
        queue[length - 1 ] = null;  // to avoid loitering.
        length--;
        if (length > 0 && length == queue.length / 4)
            resize(queue.length / 2);

        return item;
    }

    /**
     * Return but do not delete a random item.
     *
     * @return the random item
     */
    public T sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        int random = StdRandom.uniform(length);
        T item = queue[random];

        return item;
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<T> {

        private int i = 0;
        T[] array = (T[]) new Object[length];

        private ArrayIterator() {
            for (int i = 0; i < length; i++) {
                array[i] = queue[i];
            }
            shuffle(array);
        }

        public boolean hasNext() {
            return i <  length - 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return array[i++];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();

        randomQueue.enqueue(1);
        randomQueue.enqueue(2);
        randomQueue.enqueue(3);
        randomQueue.enqueue(4);
        randomQueue.enqueue(5);
        randomQueue.enqueue(6);
        randomQueue.enqueue(7);
        randomQueue.enqueue(8);
        randomQueue.enqueue(9);
        randomQueue.dequeue();
        randomQueue.dequeue();

        StdOut.println("Output: ");
        for (Integer x : randomQueue) {
            StdOut.print(x + " ");
        }
    }

}
