package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Queue: data structure that holds a collection of elements of the same type.
 *  - The elements are accessed according to FIFO order: first in, first out
 *  - No random access to other elements
 *
 *  OPERATIONS
 *  These operations take constant time: O(1)
 *  - enqueue: add a value onto the rear of the QueueLinkedList (the end of
 *    the line)
 *  - make sure it is not full first.
 *  - dequeue: remove a value from the front of the QueueLinkedList(the front
 *    of the line)
 *  - make sure it is not empty first.
 *  - isFull: true if the QueueLinkedList is currently full.
 *  - isEmpty: true if the QueueLinkedList currently contains no elements.
 *
 *  These operations take linear time: O(N)
 *  - makeEmpty: removes all the elements.
 *
 *  QUEUE ILLUSTRATED
 *  q.enqueue(2)        q.enqueue(3)    q.enqueue(5)
 *  item = 2            item = 3
 *  front/rear  2   0   front   2   0   front   2   0
 *  1   rear    3   1   front   2   0   front   2   1
 *  2               2   rear    5   2   rear    5   2
 *  3               3               3               3
 *
 *  q.dequeue(item)     q.dequeue(time)     q.enqueue(10)
 *  item = 2            item = 3
 *              x   0               x   0           x   0
 *              x   1               x   1
 *  front/rear  5   2      front    5   2
 *              3            rear  10   3
 *
 *  Note: front and rear are variables used by the implementation to carry out
 *  the operations
 *  q.enqueue(2);
 *  q.enqueue(3);
 *  q.enqueue(5);
 *  q.dequeue();    // remove 2
 *  q.dequeue();    // remove 3
 *  q.enqueue(10);
 *
 *  QUEUE APPLICATIONS
 *  The best examples of applications of queues involve managing multiple
 *  processes. For example, imagine the print QueueLinkedList for a computer
 *  lab. Any computer can add a new print job to the QueueLinkedList (enqueue).
 *  The printer performs the dequeue operation and starts printing that job.
 *  While it is printing, more jobs are added to the Q. When the printer
 *  finishes, it pulls the next job from the Q, continuing until the Q is empty
 *
 *  IMPLEMENTING A QUEUE CLASS
 *  - Just like stacks, queues can be implemented using arrays or linked lists
 *    and may be implemented using templates.
 *  - When an item was de-queued, both front and rear indices move in the array
 *
 *  LINKED LIST IMPLEMENTATION
 *  - Linked List is very fast (O(1)).
 *  - Array may be faster than linked list (no dynamic allocation)
 *  - code is actually simpler than array with resizing, especially for queues.
 *  - space used by elements is always proportional to number of elements
 *    (only wasted space is for the pointers)
 *
 *  SUMMARY:
 *  - array implementation is probably better for small objects.
 *  - linked list is probably better for large objects if space is scarce or
 *    copying is expensive (resizing)
 *
 *  @param <T> Generic item
 */
public final class QueueLinkedList<T> implements Iterable<T> {

    /** Helper Node class. */
    private class Node {

        /** Generic item. */
        private T item;
        /** Reference to next node. */
        private Node next = null;

        /**
         * Constructor for the Node class.
         * @param newItem the item
         */
        Node(final T newItem) {

            this.item = newItem;
        }

        /**
         * Convert item to string.
         * @return the item as a string
         */
        public String toString() {

            return this.item.toString();
        }
    }

    /** references to first and last link in list. */
    private Node first, last;

    /**
     * Constructor for queue.
     * Average time complexity: O(1)
     */
    private QueueLinkedList() {

        first = null;
    }

    /**
     * Returns true or false if queue is empty.
     * Average time complexity: O(1)
     *
     * @return true if queue is empty.
     */
    private boolean isEmpty() {

        return (first == null);
    }

    /**
     * Insert a new item onto end of the queue.
     * @param item the item to insert
     */
    private void enqueue(final T item) {

        Node oldlast = last;    // save a link to the last node.
        last = new Node(item);  // create a new node for the end.
        last.item = item;
        last.next = null;       // prevent loitering.

        if (isEmpty()) {        // Link the new node to the end of the list.
            first = last;
        } else {
            oldlast.next = last;
        }
    }

    /**
     * Remove and return the item from front of the queue.
     * @return the item taken off the queue
     */
    private T dequeue() {

        T item = first.item;    // save item to return.
        first = first.next;     // delete the first node.

        if (isEmpty()) {
            last = null;
        }
        return item;            // return the item.
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO
     * order.
     * @return an iterator that iterates over the items in this queue in FIFO
     * order
     */
    public Iterator<T> iterator() {

        return new ListIterator();
    }

    /** An iterator, doesn't implement remove() since it's optional. */
    private class ListIterator implements Iterator<T> {

        /** The first node. */
        private Node current = first;

        /**
         * Does queue have another item?
         * @return true if there is another item; false otherwise
         */
        public boolean hasNext() {
            return current != null;
        }

        /** Not supported. */
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Get the next item.
         * @return the next item
         */
        public T next() {

            if (current == null) {
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Unit tests the QueueLinkedList data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        QueueLinkedList<Integer> q = new QueueLinkedList<>();

        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(5);
        for (Integer item : q) {
            System.out.println(item);
        }
        System.out.println();
        q.dequeue();    // remove 2
        q.dequeue();    // remove 3
        q.enqueue(10);
        for (Integer item : q) {
            System.out.println(item);
        }
    }
}
