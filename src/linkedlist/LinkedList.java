package linkedlist;

/**
 * A data structure representing a series of dynamically allocated nodes chained together in a sequence.
 * Each node points to another node.
 * Each nodes contains a data field (structure, object, etc) and a pointer that can point to another node.
 * A separate pointer (the head) points to the first item in the list.
 * The last element (the tail) points to nothing (NULL).
 * An empty list contains no nodes. The list head points to NULL
 *
 *  Operations:
 *  constructor:        creates an empty linked list
 *  isEmpty():          is the list empty
 *  append:             add a link to the end of the list
 *  insert:             add a node within the list
 *  delete:             delete a link
 *  display():          display the linked list
 *  delete              destroy the list
 *  find(value):        find value in the linked list (if it exists)
 *  removeLink(value):  remove value in the linked list (if it exists)
 *
 * Advantages of linked lists over arrays
 * 1. A linked list can grow or shrink in size.
 * 2. The programmer doesn't need to predict how many values could be in teh list.
 * 3. The programmer doesn't need to resize and copy the list when it reaches a certain capacity.
 * 4. When a value is inserted into or deleted from a linked list, none of the other nodes have to be moved.
 */

public class LinkedList {

    public class Link {

        // Reference to next link made in the data_structure. Self-referential data structure.
        // Define a reference for head of the list. It must be initialized to NULL to signify the end of the list.
        Object value;
        Link next = null;

        Link() {}
        Link(Object value){
            this.value = value;
        }

        public String toString(){return value.toString(); }
        public void display(){ System.out.println(value); }
        public Object getValue() { return value; }
        public Link getNext() { return next; }
    }

    // Constructor: sets up empty list
    private Link head;      // Reference to first Link in list
    public LinkedList(){ head = null; }

    public Link getFirstLink() { return head; }

    // Returns true or false if linked list is empty
    // Average time complexity: O(1)
    public boolean isEmpty(){ return(head == null); }

    /**
    *  Append add new node to end of list
    *  Algorithm:
    *  1. Create a new node and store the data in it
    *  2. If the list has no nodes
    *          Make head point to the new node
    *     Else
    *          Find teh last node in the list
    *          Make the lst node point to the new node
    */

    public void append(Object value) {
        Link newLink = new Link();
        newLink.value = value;
        newLink.next = null;

        if (head == null) {
            head = newLink;
        } else {
            Link temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newLink;
        }

    }

    /**
     *  insert:  Inserts a new node into the middle of a list.
     * 	Uses two extra pointers:
     * 	one to point to node before the insertion point
     * 	one to point to the node after the insertion point [this one is optional]
     *
     * 	Insert node in a certain position
     *
     * 	Create the new node, store the data in it
     * 	Use pointer p to traverse the list,
     * 	    until it points to: node after insertion point or  NULL
     * 	    as p is advancing, make n point to the node  before
     *  if p points to first node (p is head, n was not set)
     *      make head point to new node
     *      make new node point to p’s node
     *  else
     *      make n’s node point to new node
     *      make new node point to p’s node
     */

    public void insert(Object value) {
        Link newLink = new Link();
        newLink.value = value;

        Link p = head;
        Link n = null;
        while (p != null ) {
            n = p;
            p = p.next;
        }

        if (p == head) {
            head = newLink;
            newLink.next = p;
        } else {
            n.next = newLink;
            newLink.next = p;
        }
    }

    /**
     *  Display the linked list
     *  Average time complexity for traversing list: O(n)
     *
     * Start at the reference stored in firstLink and keep getting the references stored in next for every Link until
     * next returns null
     */

    public void display(){

        Link theLink = head;

        while(theLink != null){
            System.out.println(theLink.toString());
            theLink = theLink.next;
        }
        System.out.println();
    }

    // Find a value in the linked list (if it exists)
    // Average time complexity for traversing list: O(n)
    public Link find(Object bookName){
        Link theLink = head;

        if(!isEmpty()){
            while(!theLink.value.equals(bookName)){
                // Checks if at the end of the LinkedList
                if(theLink.next == null)
                    // Got to the end of the Links in LinkedList without finding a match
                    return null;
                else
                    // Found a matching Link in the LinkedList
                    theLink = theLink.next;
            }
        } else
            System.out.println("Empty LinkedList");

        return theLink;
    }

    // Remove link that contains a certain value in the linked list (if it exists)
    // Average time complexity for traversing list: O(n)
    public Link removeLink(Object value){

        Link currentLink = head;
        Link previousLink = head;

        // Keep searching as long as a match isn't made
        while(!currentLink.value.equals(value)){

            // Check if at the last Link in the LinkedList
            if(currentLink.next == null){
                // value not found so leave the method
                return null;
            } else {
                // We checked here so let's look in the next Link on the list
                previousLink = currentLink;
                currentLink = currentLink.next;
            }
        }

        if(currentLink == head){
            // If here that means there was a match in the reference stored in firstLink in the LinkedList so just
            // assign next to firstLink
            head = head.next;
        } else {
            // If here there was a match in a Link other than the firstLink. Assign the value of next for the Link you
            // want to delete to the Link that's next previously pointed to the reference to remove
            System.out.println("FOUND A MATCH");
            System.out.println("currentLink: " + currentLink);
            System.out.println("head: " + head);

            previousLink.next = currentLink.next;
        }
        return currentLink;
    }

    public static void main(String[] args) {

        LinkedList theLinkedList = new LinkedList();

        // Insert Link and add a reference to the book Link added just prior to the field next
        theLinkedList.insert("Don Quixote");
        theLinkedList.insert("A Tale of Two Cities");
        theLinkedList.insert("The Lord of the Rings");
        theLinkedList.insert("Harry Potter and the Sorcerer's Stone");

        theLinkedList.display();
        System.out.println("Value of first in LinkedList " + theLinkedList.head + "\n");

        // Find a certain book
        System.out.println(theLinkedList.find("The Lord of the Rings").value + " Was Found");

        // Remove a certain book from the list
        theLinkedList.removeLink("A Tale of Two Cities");
        System.out.println("\nA Tale of Two Cities Removed\n");
        theLinkedList.display();
    }
}