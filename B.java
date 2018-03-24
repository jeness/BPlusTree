import java.util.ArrayList;

import javafx.util.Pair;

public interface B {
	public String get(Float key);   //search key
    public void insertOrUpdate(Float key, String value); //insert or update (key,value) pair
//    public String search(Float key1, Float key2);//range search
//	public Node searchRangeHelper(Float key, Node root);
//	Node searchRangeHelper(Float key);
	public String search(Float key1, Float key2);//range search
}
