

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
public class BplusTree implements B{
	/** root node */ 
    protected Node root; 
    /** m value, order number */ 
    protected int order; 
    /** tree height*/  
    protected int height = 0;  
	/** head node of leaf node*/ 
    protected Node head; 
     
    public Node getHead() { 
        return head; 
    } 
 
    public void setHead(Node head) { 
        this.head = head; 
    } 
 
    public Node getRoot() { 
        return root; 
    } 
 
    public void setRoot(Node root) { 
        this.root = root; 
    } 
 
    public int getOrder() { 
        return order; 
    } 
 
    public void setOrder(int order) { 
        this.order = order; 
    } 
    
    public void setHeight(int height) {  
        this.height = height;  
    }  
      
    public int getHeight() {  
        return height;  
    } 
    
    @Override
    public String search(Float key1, Float key2)
    {
    	return root.search(key1,key2,this);
    }
    @Override 
    public String get(Float key) { 
        return root.get(key); 
    } 
//    @Override 
//    public Node searchRangeHelper(Float key) { 
//        return root.searchRangeHelper(key,this); 
//    } 
    @Override 
    public void insertOrUpdate(Float key, String value) { 
    	ArrayList<String> values = new ArrayList<String>();
        root.insertOrUpdate(key, values, value, this); 
    } 
    
    public BplusTree(int order){ 
        if (order < 3) { 
            System.out.print("order must be greater than 2"); 
            System.exit(0); 
        } 
        this.order = order; 
        root = new Node(true, true); 
        head = root; 
    } 
     

}
