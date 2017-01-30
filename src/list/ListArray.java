package list;

/**
 *  ListArray: ordered set of objects of the same type.
 *  Can contain multiple values of the same type.
 *  Values are stored consecutively in memory.
 *
 *  Advantages of array over linked list:
 *  1. Arrays allow random access to element: array[i] while linked lists only allow sequential access to elements
 *  2. Arrays do no require extra storage for 'links'. Linked lists are impractical for lists of characters or
 *     booleans (pointer value is bigger than data value).
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

public class ListArray {

    private int[] theArray;     // Creates an array with 50 indexes
    private int capacity;       // Elements in theArray

    // Constructor: create an empty list
    private ListArray(int size, int capacity) {
        this.theArray = new int[size];
        this.capacity = capacity;
    }

    private int[] getArray(){ return theArray; }
    private int size(){ return capacity; }

    // Fills the ListArray with random values
    private void generateRandomArray(){
        for(int i = 0; i < size(); i++)
            theArray[i] = (int)(Math.random()*9)+10;
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
        for(int i = 0; i < size(); i++){
            System.out.print("| " + i + " | ");
            System.out.println(theArray[i] + " |");
            System.out.println("----------");
        }
    }

    // Gets value at provided index
    // Average time complexity for indexing: O(1)
    private int get(int index){
        if(index < size()) return theArray[index];
        return 0;
    }

    // Sets value at provided index
    // Average time complexity for indexing: O(1)
    private int set(int index, int value){
        if(index < size()) theArray[index] = value;
            return theArray[index];
    }

    // Returns true or false if array is empty
    // Average time complexity: O(1)
    private boolean isEmpty() {
        if (size() != 0)
            return true;
        return false;
    }

    // Returns true or false if a value is in the ListArray
    // Average time complexity for search: O(n)
    private boolean doesArrayContainThisValue(int searchValue){

        boolean valueInArray = false;
        for(int i = 0; i < size(); i++){
            if(theArray[i] == searchValue)
                valueInArray = true;
        }
        return valueInArray;
    }

    // Delete ListArray row for the index and move elements up
    // Average time complexity for delete: O(n)
    private void delete(int index){
        if(index < size()){
            // Overwrite the value for the supplied index and then keep overwriting every index that follows until you
            // get to the last index in the array

            for(int i = index; i < (size() - 1); i++){
                pauseAndUpdate();
                theArray[i] = theArray[i+1];
            }
            capacity--;
        }
    }

    // Insert: move elements up and insert element at index
    // Average time complexity for insert: O(n)
    private void insert(int value){
        if(size() < 50){
            pauseAndUpdate();
            theArray[size()] = value;
            capacity++;
        }
    }

    public static void main(String[] args){
		
		ListArray newArray = new ListArray(50, 10);
        System.out.println(newArray.isEmpty());
		newArray.generateRandomArray();
		newArray.printArray();
		
		System.out.println(newArray.get(9));
        System.out.println(newArray.set(0, 10));
		
		System.out.println(newArray.doesArrayContainThisValue(11));
		
		newArray.delete(3);
        newArray.printArray();
		
		newArray.insert(100);
		newArray.printArray();
    }
}