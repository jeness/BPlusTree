

import java.util.ArrayList; 
import java.util.List; 
import javafx.util.Pair;
public class Node {
	/** leaf node */ 
    protected boolean isLeaf; 
     
    /** root node*/ 
    protected boolean isRoot; 
 
    /** parent node */ 
    protected Node parent; 
     
    /** leaf node's previous node*/ 
    protected Node previous; 
     
    /** leaf node's next node*/ 
    protected Node next;   
     
    /** key of node */ 
    protected List<Pair<Float, ArrayList<String>>> pairs; 
     
    /** children node */ 
    protected List<Node> children; 
     
    public Node(boolean isLeaf) { 
        this.isLeaf = isLeaf; 
        pairs = new ArrayList<Pair<Float, ArrayList<String>>>(); 
         
        if (!isLeaf) { 
            children = new ArrayList<Node>(); 
        } 
    } 
 
    public Node(boolean isLeaf, boolean isRoot) { 
        this(isLeaf); 
        this.isRoot = isRoot; 
    } 
    
    public Node searchRangeHelper(Float key1, Node node) {
    	if(node.isLeaf) {
    		return node;
    	}
    	else {
    		Node indexNode = node; 
    		//if key<=leftmost key, search from num 0 node
	    	if (key1.compareTo(indexNode.pairs.get(0).getKey()) < 0) {   
	            return searchRangeHelper(key1,indexNode.children.get(0)); 
	       }
            //if key >=rightmost key, search from last node
	    	else if (key1.compareTo(indexNode.pairs.get(indexNode.pairs.size()-1).getKey()) >= 0) { 
	           return searchRangeHelper(key1,indexNode.children.get(indexNode.children.size()-1)); 
	       }else { 
	    	  int i =0;
	    	  for (i=0; i < indexNode.pairs.size()-1;i++) {
	    		  if(key1.compareTo(indexNode.pairs.get(i).getKey())>=0 &&key1.compareTo(indexNode.pairs.get(i+1).getKey())<0) {
	    			  break;
	    		  }
	    	  }
	    	  return searchRangeHelper(key1,indexNode.children.get(0));
	       }
    	}	
	}
    
    public String search(Float key1, Float key2, BplusTree tree) {
       String nullstring="Null";
       if(key1 == null ||key2 == null) {
		return nullstring;
       }
       if(key1.compareTo(key2)>0) {
        return nullstring;
       }
      
      //if not leaf node
//       if(!isLeaf) {
    	 Node leaf = searchRangeHelper(key1,tree.getRoot());  
//       }
    	 if (leaf == null) {
    		 return nullstring;
    	 }
       
       // then use the sibling pointer to find all key-value paris
       List<Pair<Float, String>> result = new ArrayList<>();

       do {
           for(int i=0; i<leaf.pairs.size(); i++) {
               Float cmpKey = leaf.pairs.get(i).getKey();
               if(key1.compareTo(cmpKey) <= 0 && key2.compareTo(cmpKey) >= 0) {
                   Float key = leaf.pairs.get(i).getKey();
                   List<String> valueList = leaf.pairs.get(i).getValue();
                   for (String val : valueList) {
                       Pair<Float, String> pair = new Pair<>(key, val);
                       result.add(pair);
                   }
               }
           }
//           leaf = leaf.nextSibling;
           leaf = leaf.getNext();

       } while (leaf != null && leaf.pairs.get(0).getKey() <= key2);
       String resultString = "";
       for (Pair<Float, String> pair : result) {
           resultString += "(" + pair.getKey() + "," + pair.getValue() + "),";
       }
       resultString = resultString.trim();
       if(resultString.length()==0) {
    	   resultString = nullstring;
       }
       else{
    	   resultString = resultString.substring(0, resultString.length() - 1);}
//       System.out.println(resultString);
       return resultString;
       
       

//		return nullstring;
	} 
     
    

	public String get(Float key) { 
         StringBuffer duplicateValue = new StringBuffer();
        //if it is a leaf node
        if (isLeaf) { 
        	for(int i = 0;i < pairs.size(); i++) {
        		Float key1 = pairs.get(i).getKey();
        		ArrayList<String> value1 = pairs.get(i).getValue();
        		if(key1.equals(key)) {
        		for(int j=0;j<value1.size();j++) {
        			if(j==0) {
        				duplicateValue.append(value1.get(j));
        			}
        			else {
        				duplicateValue.append(", "+value1.get(j));
        			}
        		}
					String outputValue = duplicateValue.toString();
					if(outputValue!=null) {
		        			return outputValue;
		           		}
        		}
        	}
             
        	//if it is not a leaf node 
        }else { 
             //if key<=leftmost key, search from num 0 node
              if (key.compareTo(pairs.get(0).getKey()) <= 0) { 
                return children.get(0).get(key); }
            // if key>=rightmost key, search from last node
            if (key.compareTo(pairs.get(pairs.size()-1).getKey()) >= 0) { 
                return children.get(children.size()-1).get(key); 
            // or, search from the larger key before key
            }else { 
            	int low = 0, high = pairs.size() - 1, mid= 0;  
                int comp ;  
                    while (low <= high) {  
                        mid = (low + high) / 2;  
                        comp = pairs.get(mid).getKey().compareTo(key);  
                        if (comp == 0) {  
                            return children.get(mid+1).get(key);   
                        } else if (comp < 0) {  
                            low = mid + 1;  
                        } else {  
                            high = mid - 1;  
                        }  
                    }  
                    return children.get(low).get(key);  
            } 
        } 
        String nullstring="Null";
		return nullstring;
    } 
     
    public void insertOrUpdate(Float key,ArrayList<String> values, String value, BplusTree tree){
        //if it is a leaf node
        if (isLeaf){ 
            // no need for split, just insert or update
            	if (contains(key) && pairs.size() <= tree.getOrder()){            		
                Update(key, values, value); 
                if (parent != null) { 
                    //update parent node 
                    parent.updateInsert(tree); 
                } 
                if(tree.getHeight() == 0){  
                    tree.setHeight(1);  
                }  
            return ; 
            }
            	else if (!(contains(key)) && pairs.size() < tree.getOrder()){ 
                insert(key, values, value); 
                if (parent != null) { 
                    //update parent node 
                    parent.updateInsert(tree); 
                } 
                if(tree.getHeight() == 0){  
                    tree.setHeight(1);  
                }  
            return ; 
            }
            //need split
               else {
                //split to left node and right node
                Node left = new Node(true); 
                Node right = new Node(true); 
                //set link
                if (previous != null){ 
                    previous.setNext(left); 
                    left.setPrevious(previous); 
                } 
                if (next != null) { 
                    next.setPrevious(right); 
                    right.setNext(next); 
                } 
                if (previous == null){ 
                    tree.setHead(left); 
                } 
 
                left.setNext(right); 
                right.setPrevious(left); 
                previous = null; 
                next = null; 
                 
              //copy keys from original node to new split node
                values.add(value);
                copy2Nodes(key, values, left, right, tree);  

                //if it is not a root node 
                if (parent != null) {  
                    int index = parent.getChildren().indexOf(this); 
                    parent.getChildren().remove(this); 
                    left.setParent(parent); 
                    right.setParent(parent); 
                    parent.getChildren().add(index,left); 
                    parent.getChildren().add(index + 1, right); 
                    parent.pairs.add(index,right.pairs.get(0));  
                    setEntries(null); 
                    setChildren(null); 
                     
                    //parent node insert or update key
                    parent.updateInsert(tree); 
                    setParent(null);                       
                }else { //if it is  a root node 
                    isRoot = false; 
                    Node parent = new Node(false, true); 
                    tree.setRoot(parent); 
                    left.setParent(parent); 
                    right.setParent(parent); 
                    parent.getChildren().add(left); 
                    parent.getChildren().add(right); 
                    parent.pairs.add(right.pairs.get(0));  
                    setEntries(null); 
                    setChildren(null); 
                     
                    //root node insert or update key 
                    parent.updateInsert(tree); 
                } 
                 return;
 
               }             
        //if it is not a leaf node
        }else { 
            //if key<=leftmost key, search from num 0 node
            if (key.compareTo(pairs.get(0).getKey()) <= 0) {
                children.get(0).insertOrUpdate(key, values,value, tree);
                } 
            //if key >=rightmost key, search from last node
            else if (key.compareTo(pairs.get(pairs.size()-1).getKey()) >= 0) { 
                children.get(children.size()-1).insertOrUpdate(key, values, value, tree); 
            //or, search from the node larger than key
            }else { 
            	int low = 0, high = pairs.size() - 1, mid= 0;  
                int comp ;  
                    while (low <= high) {  
                        mid = (low + high) / 2;  
                        comp = pairs.get(mid).getKey().compareTo(key);  
                        if (comp == 0) {  
                            children.get(mid+1).insertOrUpdate(key, values,value, tree);  
                            break;  
                        } else if (comp < 0) {  
                            low = mid + 1;  
                        } else {  
                            high = mid - 1;  
                        }  
                    }  
                    if(low>high){  
                        children.get(low).insertOrUpdate(key,values, value, tree);  
                    }  
            } 
         
    }
}
    private void insert(Float key, ArrayList<String> values, String value) {
    	Pair<Float, ArrayList<String>> pair = new Pair<Float, ArrayList<String>>(key, values); 
        //if size of pair = 0, insert directly
        if (pairs.size() == 0) {
        	values.add(value);
            pairs.add(pair); 
            return; 
        } 
        
        for (int i = 0; i < pairs.size(); i++) {           
           if (pairs.get(i).getKey().compareTo(key) > 0){ 
                //insert to index 0
                if (i == 0) { 
                	values.add(value);
                    pairs.add(0, pair); 
                    return; 
                //insert to index i
                }else {
                	values.add(value);
                    pairs.add(i, pair); 
                    return; 
                } 
            } 
        } 
        //insert to the last
        values.add(value);
        pairs.add(pairs.size(), pair); 
 
           
	}

	private void Update(Float key, ArrayList<String> values, String value) {
		Pair<Float, ArrayList<String>> pair = new Pair<Float, ArrayList<String>>(key, values); 
		 for (int i = 0; i < pairs.size(); i++) { 
			 // if the key is exist already, add to the key's value list
	            if (pairs.get(i).getKey().compareTo(key) == 0) { 
	            	pairs.get(i).getValue().add(value);
	                return; 
		 }
		 }
	}

	private void copy2Nodes(Float key, ArrayList<String> values, Node left,  
            Node right,BplusTree tree) {  
        // key size left node
        int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;   
        boolean b = false;//false: new pair is not inserted, true: new pair is inserted 
        for (int i = 0; i < pairs.size(); i++) {  
            if(leftSize !=0){  
                leftSize --;  
                if(!b&&pairs.get(i).getKey().compareTo(key) > 0){  
                    left.pairs.add(new Pair<Float,ArrayList<String>>(key, values));  
                    b = true;  
                    i--;  
                }else {  
                    left.pairs.add(pairs.get(i));  
                }  
            }else {  
                if(!b&&pairs.get(i).getKey().compareTo(key) > 0){  
                    right.pairs.add(new Pair<Float, ArrayList<String>>(key, values));  
                    b = true;  
                    i--;  
                }else {  
                    right.pairs.add(pairs.get(i));  
                }  
            }  
        }  
        if(!b){  
            right.pairs.add(new Pair<Float, ArrayList<String>>(key, values));  
        }  
    }   
     
    /** update index node after insert */ 
    protected void updateInsert(BplusTree tree){         
        // if number of children is larger than order number, split node
        if (children.size() > tree.getOrder()) { 
            //split to left node and right node
            Node left = new Node(false); 
            Node right = new Node(false); 
            //the key's size of left node and right node 
            int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2; 
            int rightSize = (tree.getOrder() + 1) / 2; 
            
            //copy children node to new node and update keys
            for (int i = 0; i < leftSize; i++){   
                left.children.add(children.get(i));   
                children.get(i).parent = left;   
            }   
            for (int i = 0; i < rightSize; i++){   
                right.children.add(children.get(leftSize + i));   
                children.get(leftSize + i).parent = right;   
            }   
            for (int i = 0; i < leftSize - 1; i++) {  
                    left.pairs.add(pairs.get(i));   
            }  
            for (int i = 0; i < rightSize - 1; i++) {  
                    right.pairs.add(pairs.get(leftSize + i));   
            }  
             
            //if it is not root node 
            if (parent != null) { 

                int index = parent.getChildren().indexOf(this); 
                parent.getChildren().remove(this); 
                left.setParent(parent); 
                right.setParent(parent); 
                parent.getChildren().add(index,left); 
                parent.getChildren().add(index + 1, right);
                parent.pairs.add(index,pairs.get(leftSize - 1));  
                setEntries(null); 
                setChildren(null); 
                 
                // update node key for parent node
                parent.updateInsert(tree); 
                setParent(null); 
            //if it is root node    
            }else { 
                isRoot = false; 
                Node parent = new Node(false, true); 
                tree.setRoot(parent); 
                tree.setHeight(tree.getHeight() + 1);  
                left.setParent(parent); 
                right.setParent(parent); 
                parent.getChildren().add(left); 
                parent.getChildren().add(right); 
                parent.pairs.add(pairs.get(leftSize - 1));  
                setEntries(null); 
                setChildren(null); 
                 
            } 
        } 
    } 
     
          
    /** confirm the node pair contains the key or not*/ 
    protected boolean contains(Float key) { 
        for (Pair<Float, ArrayList<String>> pair : pairs) { 
            if (pair.getKey().compareTo(key) == 0) { 
                return true; 
            } 
        } 
        return false; 
    }
     
    public Node getPrevious() { 
        return previous; 
    } 
 
    public void setPrevious(Node previous) { 
        this.previous = previous; 
    } 
 
    public Node getNext() { 
        return next; 
    } 
 
    public void setNext(Node next) { 
        this.next = next; 
    } 
 
    public boolean isLeaf() { 
        return isLeaf; 
    } 
 
    public void setLeaf(boolean isLeaf) { 
        this.isLeaf = isLeaf; 
    } 
 
    public Node getParent() { 
        return parent; 
    } 
 
    public void setParent(Node parent) { 
        this.parent = parent; 
    } 
 
    public List<Pair<Float, ArrayList<String>>> getPairs() { 
        return pairs; 
    } 
 
    public void setEntries(List<Pair<Float, ArrayList<String>>> pairs) { 
        this.pairs = pairs; 
    } 
 
    public List<Node> getChildren() { 
        return children; 
    } 
 
    public void setChildren(List<Node> children) { 
        this.children = children; 
    } 
     
    public boolean isRoot() { 
        return isRoot; 
    } 
 
    public void setRoot(boolean isRoot) { 
        this.isRoot = isRoot; 
    } 
     
    public String toString(){ 
        StringBuilder sb = new StringBuilder(); 
        sb.append("isRoot: "); 
        sb.append(isRoot); 
        sb.append(", "); 
        sb.append("isLeaf: "); 
        sb.append(isLeaf); 
        sb.append(", "); 
        sb.append("keys: "); 
        for (Pair pair : pairs){ 
            sb.append(pair.getKey()); 
            sb.append(", "); 
        } 
        sb.append(", "); 
        return sb.toString(); 
         
    }

	
}
