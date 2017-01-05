package analysisOfAlgorithms.week2_stacks_and_queues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  A double-ended queue or deque (pronounced "deck") is a generalization of a
 *  stack and a queue that supports adding
 *  and removing items from either the front or the back of the data structure.
 *
 *  Requirements:
 *  Your randomized queue implementation must support each randomized queue
 *  operation (besides creating an iterator) in
 *  constant amortized time. That is, any sequence of m randomized queue
 *  operations (starting from an empty queue) should take at most cm steps in
 *  the worst case, for some constant c. A randomized queue containing n
 *  items must use at most 48n + 192 bytes of memory. Additionally, your
 *  iterator implementation must support operations next() and hasNext() in
 *  constant worst-case time.  Construction in linear time; you may (and will
 *  need to) use a linear amount of extra memory per iterator.
 *
 * @param <Item> the type parameter
 */
public class Deque<Item> implements Iterable<Item> {


    /** Reference to first and last link in queue. */
    private Node front, rear;
    /** number of items in the queue. */
    private int size;

    /** Inner Node class. */
    private class Node {
        /** Item stored in Node. */
        private Item item;
        /** references to previous and next Nodes. */
        private Node previous, next;

        /**
         * Constructor for the Node class.
         *
         * @param element the element
         */
        Node(final Item element) {
            this.item = element;
        }

        /**
         * Function to set link to previous Node.
         *
         * @param n the n
         */
        void setPrevious(final Node n) {
            previous = n;
        }

        /**
         * Function to get link to previous node.
         * @return the previous
         */
        Node getPrevious() {
            return previous;
        }

        /**
         * Function to set link to next Node.
         * @param n the n
         */
        void setNext(final Node n) {
            next = n;
        }
        /**
         * Function to get link to next node.
         * @return next
         */
        Node getNext() {
            return next;
        }
        /**
         * Function to get data from current Node.
         * @return item
         * */
        Item getItem() {
            return item;
        }
    }

    /** Constructor. */
    public Deque() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Returns true or false if queue is empty.
     * Average time complexity: O(1)
     *
     * @return true if queue is empty.
     */
    public boolean isEmpty() {

        return (size == 0);
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue.
     */
    public int size() {

        return size;
    }

    /**
     * Add the item to the front.
     *
     * @param item the item to add to the front.
     */
    private void addFirst(final Item item) {

        if (item == null) {
            throw new NullPointerException("Item is null");
        }
        Node node = new Node(item);

        if (isEmpty()) {
            front = node;
            rear = front;
        } else {
            node.setNext(front);
            front.setPrevious(node);
            front = node;
        }
        size++;
    }

    /**
     * Add the item to the end.
     *
     * @param item the item to add to the end.
     */
    private void addLast(final Item item) {

        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        Node node = new Node(item);
        size++;
        if (rear == null) {
            rear = node;
            front = rear;
        } else {
            node.setPrevious(rear);
            rear.setNext(node);
            rear = node;
        }
    }

    /**
     * Remove and return an item from the front.
     *
     * @return the item
     */
    private  Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException("Underflow Exception");
        }

        Item item = front.getItem();

        if (size == 1) {
            front = null;
            rear = null;
        } else {
            Node node = front;
            front = node.getNext();
            front.setPrevious(null);
            node = null;                // to avoid loitering
        }
        size--;

        return item;
    }

    /**
     * Remove and return an item from the end.
     *
     * @return the item
     */
    private Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException("Underflow Exception");
        }
        Item item = rear.getItem();
        if (size == 1) {
            front = null;
            rear = null;
        } else {
            Node node = rear;
            rear = rear.getPrevious();
            rear.setNext(null);
            node = null;
        }
        size--;

        return item;
    }

    /**
     * Returns an iterator that iterates over the items in queue in FIFO order.
     *
     * @return an iterator that iterates over the items in queue in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /** an iterator, doesn't implement remove() since it's optional. */
    private class ListIterator implements Iterator<Item> {

        /** The current Node. */
        private Node current = front;
        /**
         * Determines if there is a following Node.
         * @return boolean
         */
        public boolean hasNext() {
            return  current != null;
        }

        /** method not implemented. */
        public void remove() {
            throw new UnsupportedOperationException();
        }
        /**
         * Iterates through a list.
         * @return T
         */
        public Item next() {

             if (current == null) {

                 throw new NoSuchElementException();
             }

            Item item = current.item;
            current = current.getNext();
            return item;
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        Deque<Integer> deque = new Deque<>();

        //deque.isEmpty();
        //deque.addFirst(1);
        //deque.addFirst(2);
        //deque.removeFirst();

        //deque.addFirst(1);
        //deque.removeLast();

        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(6);
        deque.addLast(5);
        deque.addLast(4);

        StdOut.println("analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2_stacks_and_queues."
                + "Deque: ");
        for (Integer item : deque) {
            StdOut.println(item);
        }

        StdOut.println("Size of deque is " + deque.size());

        StdOut.println("Remove item at front: " + deque.removeFirst());
        StdOut.println("analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2_stacks_and_queues."
                + "Deque: ");
        for (Integer item : deque) {
            StdOut.println(item);
        }

        StdOut.println("Remove item at rear: " + deque.removeLast());
        StdOut.println("analysisOfAlgorithms.week2_stacks_and_queues.DequeAlgorithms.week2_stacks_and_queues.wee2."
                + "Deque: ");
        for (Integer item : deque) {
            StdOut.println(item);
        }
    }
}
