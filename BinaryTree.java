/* Liam Vukasinovic
 * April 16 2019
 * This program implements a binary tree and many methods to use with it. The
 * methods include inserting, removing, printing out the tree in many different
 * ways, getting max/min, counting the tree, and much more.
 */

import java.util.Scanner;
import java.util.*;

public class BinaryTree implements BinTree{
  
  boolean isMirrored = false;
  Node root; //Creates a node for the root
  
  // delete the smallest value in the tree
  public void removeMin(){
    remove(getMin().data);
  }
  
  // delete the largest value in the tree
  public void removeMax(){
    remove(getMax().data);
  }
  
  public Node getMin(Node node){
    if (node == null){ //Checks if node is null
      return null; //Returns null
    }else if(isMirrored){
      while(node.right != null){ //While there are still nodes left
        node = node.right; //Node is set one to the left
      } //broken
      return node; //Returns the min node
    }else{
      while(node.left != null){ //While there are still nodes left
        node = node.left; //Node is set one to the left
      }
      return node; //Returns the min node
    }
  }
  public Node getMin(){
    return getMin(root); //Gets the min node
  }
  
  // remove the first instance of node that matches the key
  public void remove(int key){
    // Find the node containing this key. 
    Node current = root;
    Node parent = null;
    boolean goLeft = false;
    if (isMirrored){
      while (current != null)
      {
        // Check if current contains the key. Otherwise, move into the
        // left or right subtree.
        if (current.data == key)
        {
          break; // This exits the loop. 
        }
        else if (current.data > key)
        {
          parent = current;
          goLeft = false; //Sets go left to false
          current = current.right;
        }
        else
        {
          parent = current;
          goLeft=true; //Sets go left to true
          current = current.left;
        }
      }
    }else{
      while (current != null)
      {
        // Check if current contains the key. Otherwise, move into the
        // left or right subtree.
        if (current.data == key)
        {
          break; // This exits the loop. 
        }
        else if (current.data > key)
        {
          parent = current;
          goLeft = true;
          current = current.left;
        }
        else
        {
          parent = current;
          goLeft=false;
          current = current.right;
        }
      }
    }
    // key was not found
    if (current == null){
      return;
    }
    
    // Case 1: current is a leaf node -- no children
    if (current.left == null && current.right == null){
      // set current's parent's reference to current to be null 
      // (make the parent no longer point to current)
      if(current == root){
        clear();
        return;
      }
      if (goLeft == true){
        parent.left = null;
      }
      else{
        parent.right = null;            
      }
    }
    // Case 2: current has only a left child
    else if (current.left !=null && current.right == null){
      // set current's parent to point to current's child
      if (goLeft == true){
        parent.left = current.left;
      }
      else{
        parent.right=current.left;
      }
      
      // Case 3: current has only a right child
    }
    else if (current.left == null && current.right !=null){
      
      if (parent!=null){
        // set current's parent to point to current's child
        if (goLeft == true ){
          parent.left = current.right;
        }
        else{
          parent.right=current.right;
        }
      }
      else{
        Node min = getMin(current.right);
        remove(min.data);
        root = min;
        root.right = current.right;          
        
      }
    }
    // Case 3: current has two children
    else{
      // get the largest node in the left subtree
      Node largest = getMax(current.left);
      //delete largest from the left subtree       
      remove(largest.data);
      // special case if parent if removing root
      if (parent == null){
        root = largest;
        root.left = current.left;
        root.right = current.right;                   
      }
      
      else if (goLeft == true){
        parent.left = largest;
        largest.left = current.left;
        largest.right = current.right;
      }
      //replace current with largest
      else{
        parent.right = largest;
        largest.left = current.left;
        largest.right = current.right;
      }            
    }
  }
  
  //Method for returning the max
  public Node getMax(){
    return getMax(root);
  }
  
  //Method for getting the max node
  public Node getMax(Node node){
    if (node == null){ 
      return null; //Checks if null and returns
    }else if(isMirrored){
      while(node.left !=null){ //Checks if left node is null
        node = node.left; //Moves node to the left
      }
      return node; //Returns node
    }else{        
      while(node.right !=null){ //Checks if right node is null
        node = node.right; //Moves node to the right
      }
      return node; //Returns node
    } 
  }
  
  //Method for returning if the tree is balanced
  public boolean isBalanced(){
    return isBalanced(root); //Returns if the tree is balanced or not
  }
  
  //Method for checking if a tree is balanced
  public boolean isBalanced(Node node){
    if (node == null){ //Checks if node is null
      return true; //Returns true
    }
    int leftHeight = height(node.left);
    int rightHeight = height(node.right); 
    if(Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(node.left) && isBalanced(node.right)){
      return true; //Returns treu
    }
    return false; //Otherwise returns false
  }
  
  //Method for returning rebalanced tree
  public void rebalanceTree(){
    rebalanceTree(root); //Rebalances the tree
  }
  
  //Method for rebalancing the tree
  public void rebalanceTree(Node node){
    ArrayList<Node> tree = treeToArray(root); //Creates an array of the nodes
    root = tree.get(tree.size()/2); //Sets the root to the middle value of the array
    root.left = balance(tree,0,tree.size()/2-1); //Balances the left side
    root.right = balance(tree,tree.size()/2+1,tree.size()-1); //Balances the right size
  }
  
  //Another method for balancing the tree
  public Node balance(ArrayList<Node> list, int start, int end){
    if (start > end){ //Checks if start is bigger then the end
      return null; //Returns null
    }
    
    int mid = (start + end) / 2; //Gets the mid point
    Node node = list.get(mid); //Assigns a node to the middle
    
    node.left = balance(list, start, mid - 1); //Balances left
    node.right = balance(list, mid + 1, end); //Balances right
    
    return node; //Returns node 
  }
  
  //Method for traversing the tree inorder to an array list
  public ArrayList<Node> treeToArray(Node node){
    ArrayList<Node> array = new ArrayList<Node>(); //Creates array
    if (node.left != null){ //Checks if the node to the left is null
      array.addAll(treeToArray(node.left)); //Gets left side
    }
    array.add(node); //Adds node
    if (node.right != null){ //Checks if the node to the right is null
      array.addAll(treeToArray(node.right)); //Gets right size
    }
    return array; //Returns array
  }
  
  
  
  //Method for returns mirrored tree
  public void mirror(){
    mirror(root); //Swaps left and right tree
    //Sets global varible to tell if tree is mirrored or not
    if (isMirrored){
      isMirrored = false; //Sets to false
    }else{
      isMirrored = true; //Sets to true
    }
  }
  
  //Method for swapping left and right trees
  public void mirror(Node node){
    if (node == null){ //Checks if current node is null
      return;
    }
    Node temp = node.left; //Creates a temp node for mirroring
    node.left = node.right; //Swaps left and right
    node.right = temp; //Swaps right with left
    
    mirror(node.left);
    mirror(node.right);
  }
  
  //Method for returning the height
  public int height(){
    return height(root);
  }
  
  public int height(Node node) 
  {
    /* base case tree is empty */
    if (node == null)
      return 0;
    
    /* If tree is not empty then height = 1 + max of left
     height and right heights */
    return 1 + Math.max(height(node.left), height(node.right));
  }
  
  
  //Method for clearing the tree
  public void clear(){
    root = null; //Sets root to null
    if (isMirrored){
      isMirrored = false;
    }
  }
  
  //Method for inserting a node
  public void insert(int newItem){
    root = insert(root, newItem);
  }
  
  
  /* A recursive function to insert a new key in BST */
  public Node insert(Node node, int key) {
    
    /* If the tree is empty, return a new node */
    if (node == null) { //Checks if null
      node = new Node(key);
      return node; //Returns the node
    }else if(isMirrored){
      if (key < node.data)
        node.right = insert(node.right, key); //Does it for the right
      else if (key > node.data)
        node.left = insert(node.left, key); //Does it for the left
      else{
      }
    }else{
      /* Otherwise, recur down the tree */
      if (key < node.data)
        node.left = insert(node.left, key); //Does it for the left
      else if (key > node.data)
        node.right = insert(node.right, key); //Does it for the right
      else{
      }
    }
    /* return the (unchanged) node pointer */
    return node;
  }        
  
  //Returns if the tree contains an item
  public boolean contains(int item){  
    return contains(root,item);
  }
  
  //Method for checking if the tree contains an item
  public boolean contains(Node node, int item){
    if (node == null){
      return false; //Returns false if null
    }else if (node.data == item){
      return true; //Treu if current node is equal to item
    }else if (node.data > item && isMirrored == false){ //Checks if its greater then the item
      return contains(node.left,item); //Searches left sub tree
    }else if(node.data > item && isMirrored == true){
      return contains(node.right,item); //Searches right sub tree
    }else if (isMirrored){
      return contains(node.left,item);
    }else{
      return contains(node.right,item); 
    }
  }
  
  //Method for returning inorder
  public String printInOrder(){
    return printInOrder(root); //Returns in order
  }
  
  //Method for printing tree in order
  public String printInOrder(Node node) {
    String order = "";
    if (node != null) {
      // Print item in the node.
      order += printInOrder(node.left);   
      order += node.data + " ";// Print items in left subtree.
      order += printInOrder(node.right);            // Print items in the right subtree.
    }
    return order;
  }
  
  //Method for returnig tree in pre order
  public String printPreOrder(){
    return printPreOrder(root); 
  }
  
  //Method for printing tree in pre order
  public String printPreOrder(Node node){
    String tree = "";
    if (node != null) {
      tree += node.data + " ";  // Print item in the node.
      tree += printPreOrder(node.left); // Print items in left subtree.
      tree += printPreOrder(node.right); // Print items in the right subtree.
    }
    return tree; //Returns pre order
  }
  
  //Method for returnig tree in post order
  public String printPostOrder(){
    return printPostOrder(root); //Returns post order
  }
  
  //Method for printing tree in post order
  public String printPostOrder(Node node){
    String tree = "";
    if (node != null) {
      tree += printPostOrder(node.left); // Print items in left subtree.
      tree += printPostOrder(node.right); // Print items in the right subtree.
      tree += node.data + " ";  // Print item in the node.
    }
    return tree; //Returns post order
  }
  
  //Method for returning the count
  public int count(){
    return count(root); //Returns count
  }
  
  //Method for counting how many nodes are in the tree
  public int count(Node node){
    int counter = 0;
    if (node == null){
      return 0; //Returns a count of 0
    }
    return 1 + count(node.left) + count(node.right); //Returns final count
  }
}