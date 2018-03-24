# BPlusTree
Course Project for COP5536 - Advanced Data Structure at Fall 2017 UF
- Summary<br>
Implement a B plus tree data structure for storing 10000+ (key, value)
- Function<br>
1. `treesearch.java`<br>
The main entrance of code. Including file input and output processing.
2. `B.java`<br>
Interface for the BplusTree, list the main methods.
3. `BplusTree.java`<br> 
B Plus tree structure support for nodes. Including get and set methods.
4. `Node.java` <br>
insertOrUpdate method for the insert(key,value) function, insert (key,value) into the B+ tree
get method for the search(key) function. Search for specific value in the B+ tree
search method for the search(key1,key2) function. Search for specific value among range from key1 to key2 in the B+ tree
- Usage(In cmd)
```
1.run "make"
2.java treesearch file_name (In example here, the file_name = input.txt)
```
