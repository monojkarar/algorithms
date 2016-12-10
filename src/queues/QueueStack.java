package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
 * - How do we define isFull and isEmpty?  use a counter variable to keep track of the total number of items in the
 * QueueLinkedList.
 * <p>
 * These operations take constant time: O(1)
 * <p>
 * - makeEmpty: removes all the elements.  This is allowed to take longer than constant time.
 * <p>
 * QUEUE APPLICATIONS
 * <p>
 * The best examples of applications of queues involve managing multiple processes.
 * For example, imagine the print QueueLinkedList for a computer lab. Any computer can add a new print job to the
 * QueueLinkedList (enqueue). The printer performs the dequeue operation and starts printing that job. While it is
 * printing, more jobs are added to the Q. When the printer finishes, it pulls the next job from the Q, continuing
 * until the Q is empty
 * <p>
 * QUEUE ILLUSTRATED
 * <p>
 * q.enqueue(2)		q.enqueue(3)	q.enqueue(5)	    q.dequeue()		    q.dequeue()		q.enqueue(10)
 * item = 2			item = 3
 * front/rear	2	0	front	2	0	front	2	0			x	0				x	0				x	0
 * 1	rear	3	1			3	1	front	3	1				x	1				x	1
 * 2		        2	rear	5	2	rear	5	2	front/rear	5	2		front	5	2
 * 3		        3				3				3					3		rear	10	3
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
 * IMPLEMENTING A QUEUE CLASS
 * - Queues can be implemented using arrays or linked lists and may be implemented using templates.
 * - When an item is dequeued, both front and rear indices move in the array
 * <p>
 * PROBLEM: END OF THE ARRAY
 * - When front and rear indices move in the array rear hits end of array quickly
 * - Solution: wrap index around to front of array
 * - To 'wrap' the rear index back to the front of the array,      the following code is equivalent, but shorter
 * increment rear during enqueue:                                (assuming 0 <= rear < queueSize):
 * <p>
 * if (rear == queueSize - 1) rear = 0;                          rear = (rear + 1) % queueSize;
 * else rear = rear+1;
 * <p>
 * - Do the same for advancing the front index.
 * ----------------------------------------------------------------------------------------------------------------------
 * When is it full? (rear+1)%queueSize == front
 * <p>
 * q.enqueue(2)		q.enqueue(3)	q.enqueue(5)	q.enqueue(10)		q.enqueue(15)
 * front/rear	2	0	front	2	0	front	2	0	front	2	0		front	2	0
 * 1	rear	3	1			3	1		    3	1				3	1
 * 2		        2	rear	5	2	    	5	2	    	    5	2
 * 3		        3				3	rear   10	3			   10 	3
 * 4               4               4               4       rear   15   4
 * q.enqueue(2);
 * q.enqueue(3);
 * q.enqueue(5);
 * q.enqueue(10);
 * q.enqueue(15);
 * ----------------------------------------------------------------------------------------------------------------------
 * - When is it empty? (rear+1)%queueSize == front
 * <p>
 * q.dequeue()		    q.dequeue()	    q.dequeue()	                q.dequeue()		    q.dequeue(15)
 * x	0		    x	0	     	x	0		        x	0		front	x	0
 * front       3   1		    x	1			x	1		        x	1				x	1
 * 5   2	front	5   2		    x	2	    	    x	2	    	    x	2
 * 10   3		   10   3	front  10	3	            x	3			    x 	3
 * rear       15   4   rear   15   4   rear   15   4   front/rear  15  4       rear    x   4
 * <p>
 * STACK ARRAY IMPLEMENTATION
 * <p>
 * - Stack Array is very fast (O(1)).
 * - Array may be faster than linked list (no dynamic allocation)
 * - must anticipate maximum size
 * - wasted space: entire array is allocated, even if using small portion
 * <p>
 * SUMMARY:
 * - array implementation is probably better for small objects.
 * - linked list is probably better for large objects if space is scarce or copying is expensive (resizing)
 */
public class QueueStack<T> {

    private T[] queue;          // queue elements
    private int n;              // number of elements in the queue
    private int first;           // index of first element of queue
    private int last;           // index of last element of queue

    /**
     * Constructor for queue that initializes an empty queue
     * Average time complexity: O(1)
     */
    private QueueStack() {

        queue = (T[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    /**
     * Returns true or false if queue is empty.
     * Average time complexity: O(1)
     *
     * @return true if queue is empty; false otherwise
     */
    private boolean isEmpty() {

        return n == 0;
    }

    /**
     * Returns true or false if queue is full.
     * Average time complexity: O(1)
     *
     * @return true if queue is full; false otherwise
     */
    private boolean isFull() {

        return n == queue.length;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return n;
    }

    /**
     * Removes all items off the stack.
     * Inserting first N items takes time proportional to N (not N^2)
     */
    private void resize(int capacity) {
        assert capacity >= n;

        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = queue[(first + i) % queue.length];
        }
        queue = copy;
        first = 0;
        last = n;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    private void enqueue(T item) {

        // double size of array if necessary and recopy to front of array
        if (isFull()) {
            resize(2 * queue.length);
        }
        queue[last++] = item;   // add item
        if (last == queue.length) {
            last = 0;
        }
        n++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    private T dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        T item = queue[first];
        queue[first] = null;     // to avoid loitering
        n--;
        first++;
        if (first == queue.length) {
            first = 0;           // wrap-around
        }
        if (n > 0 && n == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return queue[first];
    }

    /**
     * Display the queue
     * Average time complexity for traversing list: O(n).
     */
    private void display() {

        for (int i = 0; i < n; i++) {
            System.out.println(queue[i].toString());
        }
        System.out.println();
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

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = queue[(i + first) % queue.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        QueueStack<Integer> q = new QueueStack<>();

        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(5);
        q.display();
        q.dequeue();    // remove 2
        q.dequeue();    // remove 3
        q.enqueue(10);
        q.display();
        System.out.println(q.peek().toString());
    }
}
