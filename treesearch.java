import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class treesearch{
    public static void main(String[] args) throws IOException { 
    	// read file content from input.txt
        StringBuffer sb= new StringBuffer("");    
        File reader = new File("./"+args[0]);
//        File reader = new File("./input.txt");
        BufferedReader br = new BufferedReader(new FileReader(reader));
        File writer = new File("./output_file.txt");
        BufferedWriter wr = new BufferedWriter(new FileWriter(writer));
        String str = null;
    	int m = 0;
    	BplusTree tree = null;
    	str = br.readLine();
    	m = Integer.parseInt(str.trim()); // order number
    	tree = new BplusTree(m);
        while((str = br.readLine()) != null) {
        		if (!str.contains(",")) { //Search(key)
        			float key;
        			String str2="";
        			if(str != null && !"".equals(str)){
	        			for(int i=0;i<str.length();i++){
		        			if(str.charAt(i)==45||str.charAt(i)==46||(str.charAt(i)>=48 && str.charAt(i)<=57)){ //把负号和数字和小数点过滤出来
		        				str2+=str.charAt(i);
	        			}
        			}
	        			key = Float.parseFloat(str2.trim());
	        			System.out.println(tree.get(key));
	        			wr.write(tree.get(key));
	        			wr.newLine();
        			}
        		}
        		else if (str.startsWith("Insert")) { //Insert(key,value)
        			float key;
        			String value;
        			String str3="";
        			str3 = str.substring(str.indexOf("(")+1,str.indexOf(",")); 
        			key = Float.parseFloat(str3.trim());
        			value = str.substring(str.indexOf(",")+1,str.indexOf(")")).trim();
        			tree.insertOrUpdate(key, value); 
        		}
        		else { //Search(key1,key2)
        			float key1,key2;
        			String str4="";
        			String str5="";
        			str4 = str.substring(str.indexOf("(")+1,str.indexOf(","));
        			str5 = str.substring(str.indexOf(",")+1,str.indexOf(")"));
        			key1 = Float.parseFloat(str4.trim());
        			key2 = Float.parseFloat(str5.trim());
        			System.out.println(tree.search(key1, key2));
        			wr.write(tree.search(key1, key2));
        			wr.newLine();
       			
        		}
        			 
//        	}
              sb.append(str+"/n");
        }
        
        br.close();
        wr.close();
        

    }
    
   } 

