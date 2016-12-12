package queues;

import java.util.Iterator;

/**
 * Queue: a data structure that holds a collection of elements of the same type.
 * - The elements are accessed according to FIFO order: first in, first out
 * - No random access to other elements
 * <p>
 * OPERATIONS
 * - enqueue: add a value onto the rear of the QueueLinkedList (the end of the line)
 * - make sure it is not full first.
 * - dequeue: remove a value from the front of the QueueLinkedList(the front of the line) Next!
 * make sure it is not empty first.
 * - isFull: true if the QueueLinkedList is currently full.
 * - isEmpty: true if the QueueLinkedList currently contains no elements.
 * <p>
 * These operations take constant time: O(1)
 * <p>
 * - makeEmpty: removes all the elements.  This is allowed to take longer than constant time.
 * <p>
 * QUEUE ILLUSTRATED
 * <p>
 * q.enqueue(2)		q.enqueue(3)	q.enqueue(5)	q.dequeue(item)		q.dequeue(time)		q.enqueue(10)
 * item = 2			item = 3
 * front/rear	2	0	front	2	0	front	2	0			x	0				x	0				x	0
 * 1	rear	3	1			3	1	front	3	1				x	1				x	1
 * 2			    2	rear	5	2	rear	5	2	front/rear	5	2		front	5	2
 * 3			    3				3				3					3		rear	10	3
 * <p>
 * Note: front and rear are variables used by the implementation to carry out the operations
 * <p>
 * q.enqueue(2);
 * q.enqueue(3);
 * q.enqueue(5);
 * q.dequeue();	// remove 2
 * q.dequeue();	// remove 3
 * q.enqueue(10);
 * <p>
 * QUEUE APPLICATIONS
 * <p>
 * The best examples of applications of queues involve managing multiple processes.
 * For example, imagine the print QueueLinkedList for a computer lab. Any computer can add a new print job to the
 * QueueLinkedList (enqueue). The printer performs the dequeue operation and starts printing that job. While it is
 * printing, more jobs are added to the Q. When the printer finishes, it pulls the next job from the Q, continuing
 * until the Q is empty
 * <p>
 * IMPLEMENTING A QUEUE CLASS
 * - Just like stacks, queues can be implemented using arrays or linked lists and may be implemented using templates.
 * - When an item was dequeued, both front and rear indices move in the array
 * <p>
 * LINKED LIST IMPLEMENTATION
 * <p>
 * - Linked List is very fast (O(1)).
 * - Array may be faster than linked list (no dynamic allocation)
 * - code is actually simpler than array with resizing, especially for queues.
 * - space used by elements is always proportional to number of elements (only wasted space is for the pointers)
 * <p>
 * SUMMARY:
 * - array implementation is probably better for small objects.
 * - linked list is probably better for large objects if space is scarce or copying is expensive (resizing)
 */

public class QueueLinkedList<T> implements Iterable<T> {

    private class Node {
        T item;
        Node next = null;

        /**
         * Constructor for the Node class.
         */
        Node(T value) {
            this.item = value;
        }

        public String toString() {

            return this.item.toString();
        }
    }

    private Node first, last;      // Reference to first and last link in list

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

    // insert a new item onto queue
    private void enqueue(T item) {

        // save a link to the last node
        Node oldlast = last;

        // create a new node for the end
        last = new Node(item);
        last.item = item;
        last.next = null;

        // Link the new node to the end of the list.
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
    }

    // remove and return the item
    private T dequeue() {

        T item = first.item;

        first = first.next;

        if (isEmpty())
            last = null;

        return item;
    }

    /**
     * Display the queue
     * Average time complexity for traversing list: O(n).
     * Start at the reference stored in firstLink and keep getting the references stored in next for every Link until
     * next returns null
     */
    private void display() {

        QueueLinkedList.Node theLink = first;

        while (theLink != null) {
            System.out.println(theLink.toString());
            theLink = theLink.next;
        }
        System.out.println();
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<T> iterator() { return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<T> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {

            T item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {

        QueueLinkedList<Integer> q = new QueueLinkedList<>();

        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(5);
        q.display();
        q.dequeue();    // remove 2
        q.dequeue();    // remove 3
        q.enqueue(10);
        q.display();
    }
}
