/**
 * An object of type TreeNode represents one node in a binary tree of strings.
 */
class Node {
    int data;      // The data in this node.
    Node left;    // Pointer to left subtree.
    Node right;   // Pointer to right subtree.
  
    // Constructor.  Make a node containing the specified string.
    // Note that left and right pointers are initially null.
    public Node(int str) {
        data = str;
    }
}  

