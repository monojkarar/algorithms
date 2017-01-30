package trees;

/**
 * Random data normally isn't unbalanced.  Ordered data tends to create unbalanced trees.
 *
 * You can search, insert, and delete items quickly in a tree
 * Ordered Arrays are bad at insertions and deletions.
 * Finding items in a Linked List is slow.
 * Time needed to perform an operation on a tree is O(log N)
 * On average a tree is more efficient if you need to perform many different types of operations.
 */

public class LinkedBinaryTree<T extends Comparable<T>> implements BinaryTree<T> {

    private Node<T> root;

    /**
     * Creates an empty binary tree.
     */
    LinkedBinaryTree() { this.root = null; }

    /**
     * Creates a binary tree with the specified element as its root.
     */
    LinkedBinaryTree(T element) { root = new Node(element); }

    public Node<T> getRoot() { return root; }

    public void setRoot(Node<T> root) { this.root = root;}

    /**
    * returns the element stored in the root of the treee. Throws and EmptyCollectionException if the tree is empty.
    */
    @Override
    public T getRootElement() {

        if (root == null)
            //throw new EmptyCollectionException("Get root operation failed. The tree is empty");
            System.out.println("Get root operation failed. The tree is empty");

        return root.getData();
    }

    @Override
    public boolean contains(T target) {

        boolean found = false;

        if (find(target) != null)
            found = true;

        return found;
    }

    /**
     *  Returns the element in this binary tree that matches the specified target. Throws an ElementNotFoundException
     *  if the target is not found.
     */
    @Override
    public T find(T target) {

        Node<T> node = null;

        if (root != null)
            node = root.find(target);

        if (node == null)
            System.out.println("Find operation failed. No such element in tree");

        return node.getData();
    }

    /**
     *  Returns true if the binary tree is empty and false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     *  Returns the number of elements in this binary tree
     */
    public int size() {
        return root.size(root);
    }

    /**
     *  Populates and returns an iterator containing the elements in this binary tree using preorder traversal.
     */
    public ArrayList<T> preorder() {

        ArrayList<T> iter = new ArrayList<T>();

        if (root != null) root.preorder(iter);

        return iter;
    }

    /**
     *  Populates and returns an iterator containing the elements in this binary tree using inorder traversal.
     */
    public ArrayList<T> inorder() {

        ArrayList<T> iter = new ArrayList<T>();

        if (root != null) root.inorder(iter);

        return iter;
    }

    /**
     *  Populates and returns an iterator containing the elements in this binary tree using postorder traversal.
     */
    public ArrayList<T> postorder() {

        ArrayList<T> iter = new ArrayList<T>();

        if (root != null) root.postorder(iter);

        return iter;
    }

    /**
     * Return the minimum value in the binary search tree.
     */
    public T minValue() {

        Node<T> current = root;

        /** loop dow to tind the leftmost leaf */
        while (current.getLeftChild() != null)
            current = current.getLeftChild();

        return current.getData();
    }

    /**
     * Return the maximum value in the binary search tree.
     */
    public T maxValue() {

        Node<T> current = root;

        /** loop dow to tind the leftmost leaf */
        while (current.getRightChild() != null)
            current = current.getRightChild();

        return current.getData();
    }

    /**
     *  Deletes all the nodes in the tree.
     */
    void clearTree(Node<T> node) {
        if (node != null) {
            clearTree(node.getLeftChild());
            clearTree((node.getRightChild()));
            node = null;
        }
    }

    /**
     *  Satisfies the Iterable interface using an inorder traversal.
     */
    public Iterator<T> iterator() {

        return null;
    }

    public void insert(T value) {
        // If there is no root this becomes root
        if (isEmpty())
            root = new Node<T>(value);
        else
            insert(root, value);
    }

    private void insert(Node<T> currentNode, T value) {

        // Create a new trees.Node and initialize it
        Node<T> newNode = new Node<T>(value);

        Node<T> parentNode;            // Future parent for our new trees.Node

        while (true) {

            // root is the top parent so we start there
            parentNode = currentNode;

            // Check if the new node should go on  the left side of the parent node
            if ( value.compareTo(parentNode.getData()) < 0) {

                // Switch focus to the left child
                currentNode = currentNode.getLeftChild();

                // If the left child has no children
                if (currentNode == null) {
                    // then place the new node on the left of it
                    parentNode.setLeftChild(newNode);
                    return; // All Done
                }
            } else {

                // If we get here put the node on the right
                currentNode = currentNode.getRightChild();

                // If the right child has no children
                if (currentNode == null) {

                    // then place the new node on the right of it
                    parentNode.setRightChild(newNode);
                    return; // All Done
                }
            }
        }
    }

   /* public boolean remove(int key) {

        // Start at the top of the tree
        Node focusNode = root;
        Node parent = root;

        // When searching for a trees.Node this will tell us whether to search to the right or left
        boolean isItALeftChild = true;

        // While we haven't found the trees.Node keep looking
        while (focusNode.getKey() != key) {

            parent = focusNode;

            // If we should search to the left
            if (key < focusNode.getKey()) {
                isItALeftChild = true;

                // Shift the focus trees.Node to the left child
                focusNode = focusNode.getLeftChild();
            } else {

                // Greater than focus node so go to the right
                isItALeftChild = false;

                // Shift the focus trees.Node to the right child
                focusNode = focusNode.getRightChild();
            }

            // The node wasn't found
            if (focusNode == null)
                return false;
        }

        // If trees.Node doesn't have children delete it
        if (focusNode.getLeftChild() == null && focusNode.getRightChild() == null) {

            // If root delete it
            if (focusNode == root)
                root = null;
                // If it was marked as a left child of the parent delete it in its parent
            else if (isItALeftChild)
                parent.setLeftChild(null);
                // Vice versa for the right child
            else
                parent.setRightChild(null);
        }

        // If no right child
        else if (focusNode.getRightChild() == null) {

            if (focusNode == root)
                root = focusNode.getLeftChild();

                // If focus trees.Node was on the left of parent move the focus Nodes left child up to the parent node
            else if (isItALeftChild)
                parent.setLeftChild(focusNode.getLeftChild());

                // Vice versa for the right child
            else
                parent.setRightChild(focusNode.getLeftChild());
        }

        // If no left child
        else if (focusNode.getLeftChild() == null) {

            if (focusNode == root)
                root = focusNode.getRightChild();

                // If focus trees.Node was on the left of parent move the focus Nodes right child up to the parent node
            else if (isItALeftChild)
                parent.setLeftChild(focusNode.getRightChild());

                // Vice versa for the left child
            else
                parent.setRightChild(focusNode.getRightChild());
        }

        // Two children so I need to find the deleted nodes replacement
        else {

            Node replacement = getReplacementNode(focusNode);

            // If the focusNode is root replace root with the replacement
            if (focusNode == root)
                root = replacement;

                // If the deleted node was a left child make the replacement the left child
            else if (isItALeftChild)
                parent.setLeftChild(replacement);

                // Vice versa if it was a right child
            else
                parent.setRightChild(replacement);

            replacement.setLeftChild(focusNode.getLeftChild());
        }

        return true;
    } */

    /*private Node getReplacementNode(Node replacedNode) {

        Node replacementParent = replacedNode;
        Node replacement = replacedNode;

        Node focusNode = replacedNode.getRightChild();

        // While there are no more left children
        while (focusNode != null) {
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.getLeftChild();
        }

        // If the replacement isn't the right child move the replacement into the parents leftChild slot and move the
        // replaced nodes right child into the replacements rightChild
        if (replacement != replacedNode.getRightChild()) {

            replacementParent.setLeftChild(replacement.getRightChild());
            replacement.setRightChild(replacedNode.getRightChild());
        }

        return replacement;
    }*/

    public static void main(String[] args) {

        LinkedBinaryTree theTree = new LinkedBinaryTree();
        /*theTree.insert(50, "Boss");
        theTree.insert(25, "Vice President");
        theTree.insert(15, "Office Manager");
        theTree.insert(30, "Secretary");
        theTree.insert(75, "Sales Manager");
        theTree.insert(85, "Salesman 1");*/

        theTree.insert(50);
        theTree.insert(25);
        theTree.insert(15);
        theTree.insert(30);
        theTree.insert(75);
        theTree.insert(85);

        // Different ways to traverse binary trees
        System.out.print("\npreOrderTraversal: ");
        for (Object node: theTree.preorder())
            System.out.print(node + " ");

        System.out.print("\ninOrderTraversal: ");
        for (Object node: theTree.inorder())
            System.out.print(node + " ");

        System.out.print("\npostOrderTraversal: ");
        for (Object node: theTree.postorder())
            System.out.print(node + " ");

        // Find the node with key 75
        System.out.println();
        System.out.println(theTree.find(75));
        System.out.println(theTree.contains(75));
        System.out.println("\nThe minimum value in BST is: " + theTree.minValue());
        System.out.println("\nThe maximum value in BST is: " + theTree.maxValue());

        theTree.clearTree(theTree.getRoot());
    }
}