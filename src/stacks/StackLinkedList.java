package stacks;

import queues.QueueLinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Stacks are used to complete a task and are soon after discarded.
 * <p>
 * StackArray
 * 1.  StackArray is a data structure that holds a collection of elements of the same type.
 * 2.  Allows only a single item to be added or removed at a time
 * 3.  Allows access to the last item inserted, first out (LIFO)
 * 4.  Problem: No random access to other elements
 * 5.  StackArray overflow: Trying to push an item onto a full stack
 * 6. 	StackArray underflow: Trying to pop an item from an empty stack
 * <p>
 * STACK OPERATIONS
 * Running Time Analysis: These operations take constant time O(1) in the worst case.
 * Memory Analysis:
 * 16 bytes (object overhead)
 * 8 bytes (inner class overhead)
 * 8 bytes (reference to object T)
 * 8 bytes (reference to Node)
 * --------------------------------
 * 40 bytes per stack node
 * <p>
 * Remark: Analysis includes memory for the stack (but not the items themselves, which the client owns).
 * <p>
 * isEmpty: true if the stack currently contains no elements
 * isFull: true if the stack is currently full, i.e.,has no more space to hold additional elements
 * push: add a item onto the top of the stack. Make sure it is not full first.
 * pop: remove (and return) the item from the top of the stack. Make sure it is not empty first.
 * peek: Looks at the object at the top of this stack without removing it from the stack.
 * <p>
 * This operation should take linear time O(n)
 * makeEmpty: removes all the elements
 */

public class StackLinkedList<T> implements Iterable {

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

        /**
         * Display the item in the node.
         */
        void display() {

            System.out.println(this.item.toString());
        }
    }

    private Node head;    // Reference to first Link in list

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
     */
    private void push(T item) {

        Node oldFirst = head;

        // insert at end of list
        head = new Node(item);
        head.next = oldFirst;
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
     * Start at the reference stored in firstLink and keep getting the references stored in next for every Link until
     * next returns null
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
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<T> iterator() { return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<T> {

        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {

            if (current == null) {

                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            return item;
        }
    }


    public static void main(String[] args) {

        StackLinkedList<String> stack = new StackLinkedList<>();

        // Insert Link and add a reference to the book Link added just prior to the field next
        stack.push("Don Quixote");
        stack.push("A Tale of Two Cities");
        stack.push("The Lord of the Rings");
        stack.push("Harry Potter and the Sorcerer's Stone");

        for (Object item: stack)
            System.out.println(item);

        System.out.println("Value of first item in the stack is " + stack.peek() + "\n");

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
