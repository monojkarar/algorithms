package list;

import java.util.ArrayList;

/**
 *  ListArray: ordered set of objects of the same type.
 *  Can contain multiple values of the same type.
 *  Values are stored consecutively in memory
 *
 *  Operations:
 *  Traversing
 *  Searching
 *  Sorting
 *  Insertion
 *  Deletion
 *  Merging
 *  
 *  Average Time Complexity
 *  Indexing:    O(1)
 *  Search:      O(n)
 *  Insertion:   O(n)
 *  Deletion:    O(n)
 *
 *  constructor: creates an empty list
 *  get(i):      returns the element at position i
 *  set(i,v):    change the element at position i to v
 *  isEmpty():   is the list empty
 *  size():      returns the number of elements
 *  doesArrayContainThisValue()
 *  add(x, i):   inserts an element x at position i
 *  remove(i):   removes the element at position i
 */

public class ListDynamicArray {

    private ArrayList theArray; // Creates an array with 50 indexes

    // Constructor: create an empty list
    private ListDynamicArray(int size) {
        this.theArray = new ArrayList(size);
        generateRandomArray(size);
    }

    // Fills the List with random values
    private void generateRandomArray(int size){
        for(int i = 0; i < size; i++)
            theArray.add((int)(Math.random()*9)+10);
    }

    // Used to slow down calculations
    private void pauseAndUpdate(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Prints the ListArray on the screen in a grid
    private void printArray(){

        System.out.println("----------");
        for(int i = 0; i < theArray.size(); i++){
            System.out.print("| " + i + " | ");
            System.out.println(theArray.get(i).toString() + " |");
            System.out.println("----------");
        }
    }

    // Gets value at provided index
    // Average time complexity for indexing: O(1)
    private Object get(int index){
        if(index < theArray.size()) return theArray.get(index);
        return 0;
    }

    // Sets value at provided index
    // Average time complexity for indexing: O(1)
    private Object set(int index, int value){
        if(index < theArray.size())
            theArray.set(index, value);

        return theArray.get(index);
    }

    // Returns true or false if array is empty
    // Average time complexity: O(1)
    private boolean isEmpty() {
        return (theArray.size() == 0);
    }

    // Returns true or false if a value is in the ListArray
    // Average time complexity for search: O(n)
    private boolean doesArrayContainThisValue(int searchValue){

        boolean valueInArray = false;
        if(theArray.contains(searchValue))
            valueInArray = true;

        return valueInArray;
    }

    // Delete ListArray row for the index and move elements up
    // Average time complexity for delete: O(n)
    private void delete(int index){
        if(index < theArray.size()){
            // Overwrite the value for the supplied index and then keep overwriting every index that follows until you
            // get to the last index in the array

            for(int i = index; i < (theArray.size() - 1); i++){
                pauseAndUpdate();
                theArray.set(i,theArray.get(i+1));
            }
        }
    }

    // Insert: move elements up and insert element at index
    // Average time complexity for insert: O(n)
    private void insert(Object value){
        if(theArray.size() < 50){
            pauseAndUpdate();
            theArray.add(value);
        }
    }

    public static void main(String[] args){
		
		ListDynamicArray newArray = new ListDynamicArray(10);
        newArray.printArray();
        System.out.println(newArray.isEmpty());
		
		System.out.println(newArray.get(9));
        System.out.println(newArray.set(0, 10));
		
		System.out.println(newArray.doesArrayContainThisValue(11));
		
		newArray.delete(3);
        newArray.printArray();
		
		newArray.insert(100);
		newArray.printArray();
    }
}