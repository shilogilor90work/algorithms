import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Arrays;


class Node {
  String Var;
  String [] Values;
  List<String> Parents;
  Hashtable<String, Double> CPT;
  Node(String Var, String[] Values, List<String> Parents, Hashtable<String, Double> CPT){
    this.Var = Var;
    this.Values = Values;
    this.Parents = Parents;
    this.CPT = CPT;
  }
  List<String> get_parents() {
    return Parents;
  }
  String [] get_value_options(){
    return Values;
  }
  double get_value(String value){
    return CPT.get(value);
  }
  public String get_name(){
    return Var;
  }
  public double get_prob(Hashtable<String, String> name_value){
    String key_of_CPT = "";
    for (String parent : Parents) {
      if(parent != null && !parent.isEmpty()){
        key_of_CPT = key_of_CPT + name_value.get(parent) + ",";
      }
    }
    key_of_CPT = key_of_CPT + name_value.get(Var);
    return CPT.get(key_of_CPT);
  }
  public String toString()
  {
    String a = "\nname:" + Var + " , values:" + Arrays.toString(Values) + " , Parents: ";
    for (String node : Parents) {
         a = a + node;
    }
    a = a + " , CPT: ";
    Set<String> keys = CPT.keySet();

    //Obtaining iterator over set entries
    Iterator<String> itr = keys.iterator();

    //Displaying Key and value pairs
    while (itr.hasNext()) {
       // Getting Key
       String str = itr.next();

       /* public V get(Object key): Returns the value to which
        * the specified key is mapped, or null if this map
        * contains no mapping for the key.
        */
       a = a + " Key: "+str+" & Value: "+CPT.get(str);
    }
    return a;
  }

}
