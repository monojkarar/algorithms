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
 *  insertFirstLink():  insert new link in the first position in the linked list
 *  deleteFirstLink():  remove the link in the first position in the linked list
 *  display():          display the linked list
 *  find(value):        find value in the linked list (if it exists)
 *  removeLink(value):  remove value in the linked list (if it exists)
 *
 * Advantages of linked lists over arrays
 * 1. A linked list can grow or shrink in size.
 * 2. The programmer doesn't need to predict how many values could be in teh list.
 * 3. The programmer doesn't need to resize and copy the list when it reaches a certain capacity.
 * 4. When a value is inserted into or deleted from a linked list, none of the other nodes have to be moved.
 */

public class LinkList{

    private class Link {

        Object bookName;
        int millionsSold;

        // Reference to next link made in the data_structure. Self-referential data structure.
        // Define a reference for head of the list. It must be initialized to NULL to signify the end of the list.
        Link next = null;

        Link(Object bookName, int millionsSold){
            this.bookName = bookName;
            this.millionsSold = millionsSold;
        }

        public String toString(){return bookName.toString(); }
        public void display(){
            System.out.println(bookName + ": " + millionsSold + ",000,000 Sold");
        }
    }

    private Link firstLink;    // Reference to first Link in list
    private LinkList(){ firstLink = null; }

    // Returns true or false if linked list is empty
    // Average time complexity: O(1)
    private boolean isEmpty(){ return(firstLink == null); }

    //  Add a new link to the first position in the linked list
    //  - Create a new link and assign its Next the reference to the previous Link created
    //  - The linked lists first link is assigned a reference to the newest Link added.
    // Average time complexity: O(1)
    private void insertFirstLink(Object bookName, int millionsSold){

        Link newLink = new Link(bookName, millionsSold);
        // Connect the firstLink field to the new Link
        newLink.next = firstLink;
        firstLink = newLink;
    }

    //  Remove the link in the first position in the linked list
    //  Average time complexity: O(1
    private Link deleteFirstLink(){
        Link linkReference = firstLink;

        if(!isEmpty())
            firstLink = firstLink.next; // Removes the Link from the List
        else
            System.out.println("Empty LinkedList");

        return linkReference;
    }

    // Display the linked list
    // Average time complexity for traversing list: O(n)
    //
    // Start at the reference stored in firstLink and keep getting the references stored in next for every Link until
    // next returns null
    private void display(){

        Link theLink = firstLink;

        while(theLink != null){
            System.out.println(theLink.toString());
            theLink = theLink.next;
        }
        System.out.println();
    }

    // Find a value in the linked list (if it exists)
    // Average time complexity for traversing list: O(n)
    public Link find(Object bookName){
        Link theLink = firstLink;

        if(!isEmpty()){
            while(!theLink.bookName.equals(bookName)){
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
    private Link removeLink(Object bookName){

        Link currentLink = firstLink;
        Link previousLink = firstLink;

        // Keep searching as long as a match isn't made
        while(!currentLink.bookName.equals(bookName)){

            // Check if at the last Link in the LinkedList
            if(currentLink.next == null){
                // bookName not found so leave the method
                return null;
            } else {
                // We checked here so let's look in the next Link on the list
                previousLink = currentLink;
                currentLink = currentLink.next;
            }
        }

        if(currentLink == firstLink){
            // If here that means there was a match in the reference stored in firstLink in the LinkedList so just
            // assign next to firstLink
            firstLink = firstLink.next;
        } else {
            // If here there was a match in a Link other than the firstLink. Assign the value of next for the Link you
            // want to delete to the Link that's next previously pointed to the reference to remove
            System.out.println("FOUND A MATCH");
            System.out.println("currentLink: " + currentLink);
            System.out.println("firstLink: " + firstLink);

            previousLink.next = currentLink.next;
        }
        return currentLink;
    }

    public static void main(String[] args) {

        LinkList theLinkedList = new LinkList();

        // Insert Link and add a reference to the book Link added just prior to the field next
        theLinkedList.insertFirstLink("Don Quixote", 500);
        theLinkedList.insertFirstLink("A Tale of Two Cities", 200);
        theLinkedList.insertFirstLink("The Lord of the Rings", 150);
        theLinkedList.insertFirstLink("Harry Potter and the Sorcerer's Stone", 107);

        theLinkedList.display();
        System.out.println("Value of first in LinkedList " + theLinkedList.firstLink + "\n");

        // Removes the first link entered
        theLinkedList.deleteFirstLink();
        theLinkedList.display();

        // Find a certain book
        System.out.println(theLinkedList.find("The Lord of the Rings").bookName + " Was Found");

        // Remove a certain book from the list
        theLinkedList.removeLink("A Tale of Two Cities");
        System.out.println("\nA Tale of Two Cities Removed\n");
        theLinkedList.display();
    }
}