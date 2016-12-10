package stacks;

/**
 * Stacks are used to complete a task and are soon after discarded.
 * <p>
 * StackArray
 * 1.  StackArray is a data structure that holds a collection of elements of the same type.
 * 2.  Allowa only a single item to be added or removed at a time
 * 3.  Allows access to the last item inserted, first out (LIFO)
 * 4.  Problem: No random access to other elements
 * 5.  StackArray overflow: Trying to push an item onto a full stack
 * 6. 	StackArray underflow: Trying to pop an item from an empty stack
 * <p>
 * STACK OPERATIONS
 * Operations: These operations should take constant time O(1)
 * <p>
 * isEmpty: true if the stack currently contains no elements
 * isFull: true if the stack is currently full, i.e.,has no more space to hold additional elements
 * push: add a item onto the top of the stack. Make sure it is not full first.
 * pop: remove (and return) the item from the top of the stack. Make sure it is not empty first.
 * peek:
 * pushMany(String multipleValues):
 * popAll():
 * popDisplayAll():
 * displayTheStack():
 * <p>
 * This oporation should take linear time O(n)
 * makeEmpty: removes all the elements
 */

import java.util.Arrays;

public class StackDynamicArray {
    private static int SIZE = 100;

    private String[] stackArray;
    private int stackSize;
    private int top;

    private StackDynamicArray(int size) {
        stackSize = SIZE;
        stackArray = new String[SIZE];
        top = -1;   // stack is empty

        // Assigns the item of -1 to every item in the array so I control what gets printed to screen
        Arrays.fill(stackArray, "-1");
    }

    private boolean isEmpty() {
        return (top == -1);
    }

    private boolean isFull() {
        return top == SIZE - 1;
    }

    private void makeEmpty() {
        top = -1;
    }

    private void push(String input) {

        if (top + 1 < stackSize) {
            top++;
            stackArray[top] = input;
        } else
            System.out.println("Sorry But the stack.StackArray is Full");

        displayTheStack();
        System.out.println("PUSH " + input + " Was Added to the stack.StackArray\n");
    }

    private String pop() {

        if (top >= 0) {
            displayTheStack();
            System.out.println("POP " + stackArray[top] + " Was Removed From the stack.StackArray\n");
            // Just like in memory an item isn't deleted, but instead is just not available
            stackArray[top] = "-1"; // Assigns -1 so the item won't appear
            return stackArray[top--];
        } else {
            displayTheStack();
            System.out.println("Sorry But the stack.StackArray is Empty");
            return "-1";
        }
    }

    private String peek() {

        displayTheStack();
        System.out.println("PEEK " + stackArray[top] + " Is at the Top of the stack.StackArray\n");
        return stackArray[top];
    }

    private void pushMany(String multipleValues) {
        String[] tempString = multipleValues.split(" ");

        for (int i = 0; i < tempString.length; i++)
            push(tempString[i]);
    }

    private void popAll() {

        for (int i = top; i >= 0; i--)
            pop();
    }

    private void popDisplayAll() {
        String theReverse = "";

        for (int i = top; i >= 0; i--)
            theReverse += stackArray[i];

        System.out.println("The Reverse: " + theReverse);
        popAll();
    }

    private void displayTheStack() {

        for (int n = 0; n < 61; n++) System.out.print("-");
        System.out.println();
        for (int n = 0; n < stackSize; n++)
            System.out.format("| %2s " + " ", n);

        System.out.println("|");
        for (int n = 0; n < 61; n++) System.out.print("-");
        System.out.println();
        for (int n = 0; n < stackSize; n++) {
            if (stackArray[n].equals("-1")) System.out.print("|     ");
            else System.out.print(String.format("| %2s " + " ", stackArray[n]));
        }
        System.out.println("|");
        for (int n = 0; n < 61; n++) System.out.print("-");
        System.out.println();
    }

    public static void main(String[] args) {
        StackDynamicArray theStack = new StackDynamicArray(10);

        theStack.push("10");
        theStack.push("17");
        theStack.push("13");

        // Look at the top item on the stack
        theStack.peek();

        // Remove values from the stack (LIFO)
        theStack.pop();
        theStack.pop();
        theStack.pop();

        // Add many to the stack
        theStack.pushMany("R E D R U M");

        // Remove all from the stack
        // theStack.popAll();

        // Remove all from the stack and print them
        theStack.popDisplayAll();
        theStack.displayTheStack();
    }
}