package stacks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Stacks are used to complete a task and are soon after discarded.
 *
 * Stack
 * 1.  Stack is a data structure that holds a collection of elements of
 *     the same type.
 * 2.  Allows only a single item to be added or removed at a time
 * 3.  Allows access to the last item inserted, first out (LIFO)
 * 4.  Problem: No random access to other elements
 * 5.  Stack overflow: Trying to push an item onto a full stack
 * 6.  Stack underflow: Trying to pop an item from an empty stack
 *
 * Memory Analysis: A stack with N items uses ~40 N bytes
 * 16 bytes (object overhead)
 * 8 bytes  (inner class overhead)
 * 8 bytes  (reference to object T)
 * 8 bytes  (reference to Node)
 * --------------------------------
 * 40 bytes per stack node
 *
 * Remark: Analysis includes memory for the stack (but not the items themselves,
 * which the client owns).
 *
 * STACK OPERATIONS
 * Running Time Analysis: These operations take constant time in the worst case:
 * isEmpty: true if the stack currently contains no elements
 * isFull: true if the stack is currently full, i.e.,has no more space to hold
 * additional elements
 * push: add a item onto the top of the stack. Make sure it is not full first.
 * pop: remove (and return) the item from the top of the stack. Make sure it is
 * not empty first.
 * peek: Looks at the object at the top of this stack without removing it from
 * the stack.
 *
 * This operation should take linear time O(n)
 * makeEmpty: removes all the elements
 *
 * @param <T> a generic item
 */

public final class StackLinkedList<T> implements Iterable {

    /**
     * Node helper class.
     */
    private class Node {

        /** Generic item. */
        private T item;
        /** Reference to the next node. */
        private Node next = null;

        /**
         * Constructor for the Node class.
         * @param newItem the value
         */
        Node(final T newItem) {
            this.item = newItem;
        }

        /**
         * Convert item to string.
         * @return item as a string
         */
        public String toString() {

            return this.item.toString();
        }

        /** Display the item in the node. */
        void display() {

            System.out.println(this.item.toString());
        }
    }

    /** Reference to first Link in list. */
    private Node head;

    /**
     * Constructor for stack.
     * Average time complexity: O(1)
     */
    private StackLinkedList() {

        head = null;
    }

    /**
     * Returns true or false if stack is empty.
     * Average time complexity: O(1)
     *
     * @return true if stack is empty.
     */
    public boolean isEmpty() {

        return (head == null);
    }

    /**
     * Removes all items off the stack.
     * Average time complexity: O(N)
     */
    private void makeEmpty() {

        while (!isEmpty()) {
            pop();
        }
    }

    /**
     * Push an item onto the stack.
     * Average time complexity: O(1)
     * @param item the item
     */
    private void push(final T item) {

        Node oldFirst = head;   // save a link to the list
        head = new Node(item);  // create a new node for the beginning
        head.next = oldFirst;   // set the instance variables in the new node
    }

    /**
     * Pop an item off the stack.
     * Average time complexity: O(1)
     *
     * @return The item popped of the stack
     */
    private T pop() {
        T item = head.item;
        head = head.next;
        return item;
    }

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return the object at the top of this stack
     */
    private T peek() {

        return head.item;
    }

    /**
     * Display the linked list
     * Average time complexity for traversing list: O(n).
     * Start at the reference stored in firstLink and keep getting the
     * references stored in next for every Link until next returns null
     */
    private void display() {

        Node theLink = head;

        while (theLink != null) {
            System.out.println(theLink.toString());
            theLink = theLink.next;
        }
        System.out.println();
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

    /** an iterator, doesn't implement remove() since it's optional. */
    private class ListIterator implements Iterator<T> {
        /** The current node. */
        private Node current = head;

        /**
         * Is there another item?
         * @return true if there is another item; false otherwise.
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
     * Unit tests the StackLinkedList data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        StackLinkedList<String> stack = new StackLinkedList<>();

        // Insert Link and add a reference to the book Link added just prior to
        // the field next
        stack.push("Don Quixote");
        stack.push("A Tale of Two Cities");
        stack.push("The Lord of the Rings");
        stack.push("Harry Potter and the Sorcerer's Stone");

        for (Object item: stack) {
            System.out.println(item);
        }

        // Look at the top item on the stack
        System.out.println("Peeks at top of stack is " + stack.peek() + "\n");

        // Remove value from the stack (LIFO)
        System.out.println("Pop the top of the stack: " + stack.pop());

        Scanner in = new Scanner(System.in);
        System.out.println("Enter a string (q to Quit):\n");
        while (!in.next().equals("q")) {
            String s = in.next();
            if (s.equals("q")) {
                while (!stack.isEmpty()) {
                    stack.head.display();
                }
                stack.makeEmpty();
                return;
            } else {
                stack.push(s);
            }
        }
    }
}
