interface BinTree{
    
    public void insert(int newItem);
    
    public void remove(int item);
    
    public Node getMin();
    
    public void removeMin();
    
    public Node getMax();
    
    public void removeMax();
       
    public boolean contains( int item );
    
    public String printInOrder();
    
    public String printPreOrder();
    
    public String printPostOrder();
    
    public int count();
    
    public void clear();
    
    public boolean isBalanced();
    
    public void rebalanceTree();
    
    public void mirror();    
}