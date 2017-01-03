package hashtable;

import java.util.Scanner;

/**
 *
 */
public class HashTable3 {
    /** The array. */
    private WordList[] theArray;
    /** The array size. */
    private int arraySize;
    /** The elements. */
    private String[][] elementsToAdd = {
            {"ace", "Very good"},
            {"act", "Take action"},
            {"add", "Join (something) to something else"},
            {"age", "Grow old"},
            {"ago", "Before the present"},
            {"aid", "Help, assist, or support"},
            {"aim", "Point or direct"},
            {"air", "Invisible gaseous substance"},
            {"all", "Used to refer to the whole quantity"},
            {"amp", "Unit of measure for strength of an electrical current"},
            {"and", "Used to connect words"},
            {"ant", "A small insect"},
            {"any", "Used to refer to one or some of a thing"},
            {"ape", "A large primate"},
            {"apt", "Appropriate or suitable in the circumstances"},
            {"arc", "A part of the circumference of a curve"},
            {"are", "Unit of measure, equal to 100 square meters"},
            {"ark", "The ship built by Noah"},
            {"arm", "Two upper limbs of the human body"},
            {"art", "Expression or application of human creative skill"},
            {"ash", "Powdery residue left after the burning"},
            {"ask", "Say something in order to obtain information"},
            {"asp", "Small southern European viper"},
            {"ass", "Hoofed mammal"},
            {"ate", "To put (food) into the mouth and swallow it"},
            {"atm", "Unit of pressure"},
            {"awe", "A feeling of reverential respect"},
            {"axe", "Edge tool with a heavy bladed head"},
            {"aye", "An affirmative answer"} };

    /**
     * String hash function.
     * @param wordToHash the word to hash
     * @return the hash key value
     */
    private int stringHashFunction(final String wordToHash) {

        int hashKeyValue = 0;

        for (int i = 0; i < wordToHash.length(); i++) {

            // 'a' has the character code of 97 so subtract to make our letters
            // start at 1
            int charCode = wordToHash.charAt(i) - 96;
            int hKVTemp = hashKeyValue;

            // Calculate the hash key using the 26 letters plus blank
            hashKeyValue = (hashKeyValue * 27 + charCode) % arraySize;
            System.out.println("Hash Key Value " + hKVTemp
                    + " * 27 + Character Code " + charCode + " % arraySize "
                    + arraySize + " = " + hashKeyValue);
        }

        System.out.println();
        return hashKeyValue;
    }

    /**
     * The Constructor.
     * @param size the size
     */
    HashTable3(final int size) {

        arraySize = size;
        theArray = new WordList[size];

        // Fill the array with WordLists
        for (int i = 0; i < arraySize; i++) {
            theArray[i] = new WordList();
        }

        addTheArray(elementsToAdd);
    }

    /**
     * Insert a value.
     * @param newWord the word to insert
     */
    private void insert(final Word newWord) {

        String wordToHash = newWord.theWord;

        // Calculate the hashkey for the hash_table.Word
        int hashKey = stringHashFunction(wordToHash);

        // Add the new word to the array and set the key for the word
        theArray[hashKey].insert(newWord, hashKey);
    }

    /**
     * Find the item using the hash function.
     * @param wordToFind the item to find
     * @return the item
     */
    public Word find(final String wordToFind) {
        // Calculate the hash key for the word
        int hashKey = stringHashFunction(wordToFind);

        // NEW
        Word theWord = theArray[hashKey].find(hashKey, wordToFind);
        return theWord;
    }

    /**
     * Add elements to the hash table.
     * @param elementsToAdd elements to add
     */
    private void addTheArray(final String[][] elementsToAdd) {

        for (int i = 0; i < elementsToAdd.length; i++) {
            String word = elementsToAdd[i][0];
            String definition = elementsToAdd[i][1];

            // Create the hash_table.Word with the word name and definition
            Word newWord = new Word(word, definition);

            // Add the hash_table.Word to theArray
            insert(newWord);
        }
    }

    /**
     * Display the hash table.
     */
    void displayTheArray() {
        for (int i = 0; i < arraySize; i++) {
            System.out.println("theArray Index " + i);
            theArray[i].displayWordList();
        }
    }
}

/**
 * The Word class.
 */
class Word {
    /** The word. */
    String theWord;
    /** The definition. */
    private String definition;
    /** The key. */
    int key;
    /** Reference to next hash_table.Word made in the hash_table.WordList. */
    Word next;

    /**
     * The Constructor.
     * @param newWord the word
     * @param newDefinition the definition
     */
    protected Word(final String newWord, final String newDefinition) {

        this.theWord = newWord;
        this.definition = newDefinition;
    }

    /**
     * Return as a string.
     * @return a string
     */
    public String toString() {
        return theWord + " : " + definition;
    }
}

/**
 *  WorldList class.
 */
class WordList {
    /** Reference to first Link in list. The last Link added to the
     * LinkedList.
     */
    private Word firstWord = null;

    /**
     * Insert an item using its hash keyy.
     * @param newWord the item to insert
     * @param hashKey the hash key
     */
    protected void insert(final Word newWord, final int hashKey) {

        Word previous = null;
        Word current = firstWord;
        newWord.key = hashKey;

        while (current != null && newWord.key > current.key) {
            previous = current;
            current = current.next;
        }

        if (previous == null) {
            firstWord = newWord;
        } else {
            previous.next = newWord;
        }
        newWord.next = current;
    }

    /**
     * Display the items in the list.
     */
    void displayWordList() {

        Word current = firstWord;

        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    /**
     * Find an item in the hash table.
     * @param hashKey the hash key
     * @param wordToFind the item to find
     * @return the item.
     */
    protected Word find(final int hashKey, final String wordToFind) {

        Word current = firstWord;

        // LinearSearch.LinearSearch for key, but stop searching if the
        // hashKey < what we are searching for Because the list is sorted
        // this allows us to avoid searching the whole list

        while (current != null && current.key <= hashKey) {

            // NEW
            if (current.theWord.equals(wordToFind)) {
                return current;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Unit tests the HastTable3 data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        Scanner input = new Scanner(System.in);

        // Make a 11 item array that will hold words and definitions
        HashTable3 wordHashTable = new HashTable3(11);
        String wordLookUp = "a";

        // Keep retrieve requests until x is entered
        while (!wordLookUp.equalsIgnoreCase("x")) {

            System.out.println(": ");
            wordLookUp = input.nextLine();

            // Look for the word requested and print it out to screen
            System.out.println(wordHashTable.find(wordLookUp));
        }

        // Display every item in array with the index they are associated with
        wordHashTable.displayTheArray();
    }
}
