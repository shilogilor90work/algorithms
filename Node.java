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


class Node {
  String Var;
  String Values;
  List<Node> Parents;
  Hashtable<String, Double> CPT;
  Node(String Var, String Values, List<Node> Parents, Hashtable<String, Double> CPT){
    this.Var = Var;
    this.Values = Values;
    this.Parents = Parents;
    this.CPT = CPT;
  }
  double get_value(String value){
    return 0.0;
  }
  public String toString()
  {
    String a = "\nname:" + Var + " , values:" + Values + " , Parents: ";
    for (Node node : Parents) {
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
