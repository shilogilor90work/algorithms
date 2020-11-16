import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

/**
 * class Ex1 for search algorithm task
 * @author Shilo Gilor
 */

public class Ex1 {
  static Hashtable<String, Node> Nodes_list = new Hashtable<String, Node>();
  static int plus = 0;
  static int times = 0;
  /**
   * deeply clones a Map by cloning all the values.
   */
  static public Hashtable<String,String> deepCopy(Hashtable<String, String> original) {
      Hashtable<String, String> copy = new Hashtable<String, String>(original.size());
      Set<String> keys = original.keySet();

      //Obtaining iterator over set entries
      Iterator<String> itr = keys.iterator();

      //Displaying Key and value pairs
      while (itr.hasNext()) {
         // Getting Key
         String name = itr.next();
         copy.put(name, original.get(name));
      }
      return copy;
  }
  static public Hashtable<String,Node> deepCopy_node(Hashtable<String, Node> original) {
      Hashtable<String, Node> copy = new Hashtable<String, Node>(original.size());
      Set<String> keys = original.keySet();

      //Obtaining iterator over set entries
      Iterator<String> itr = keys.iterator();

      //Displaying Key and value pairs
      while (itr.hasNext()) {
         // Getting Key
         String name = itr.next();
         copy.put(name, original.get(name));
      }
      return copy;
  }
  static public double algorithm_1(Hashtable<String, Node> Nodes_list, String query){
    String prob_search = query.substring(query.indexOf("P(")+2,query.indexOf("|"));
    String conditions = query.substring(query.indexOf("|")+1,query.indexOf("),"));
    Hashtable<String, String> base_options = new Hashtable<String, String>();
    Hashtable<String, Node> nodes_left_copy = deepCopy_node(Nodes_list);
    String[] conditions_facts = conditions.split(",");

    for (int i = 0; i<conditions_facts.length; i++){
      String [] name_value = conditions_facts[i].split("=");
      base_options.put(name_value[0], name_value[1]);
      nodes_left_copy.remove(name_value[0]);
    }

    double this_case_true = 0.0;
    double this_case_false = 0.0;
    String [] this_prob_value = prob_search.split("=");
    String [] prob_values_options = Nodes_list.get(this_prob_value[0]).get_value_options();
    boolean flag = false;
    List<String> this_node_parents = Nodes_list.get(this_prob_value[0]).get_parents();
    Set<String> keys = base_options.keySet();
    if (this_node_parents.size() == base_options.size()) {
      for (String node_name: this_node_parents){
        if (!keys.contains(node_name)) {
          flag = false;
          break;
        } else {
          flag = true;
        }
      }
    }
    if (flag) {
      base_options.put(this_prob_value[0], this_prob_value[1]);
      return Nodes_list.get(this_prob_value[0]).get_prob(base_options);
    }

    for (int i = 0; i<prob_values_options.length; i++){
      base_options.put(this_prob_value[0], prob_values_options[i]);
      nodes_left_copy.remove(this_prob_value[0]);
      double given_fact = get_all_permutations_chance(base_options, nodes_left_copy, 0.0);
      if (this_prob_value[1].equals(prob_values_options[i])){
        if (this_case_true==0){
          this_case_true = given_fact;
        } else {
          plus ++;
          this_case_true = this_case_true + given_fact;
        }
      } else {
        if (this_case_false==0) {
          this_case_false = given_fact;
        } else {
          plus ++;
          this_case_false = this_case_false + given_fact;
        }
      }
    }
    plus ++;
    return this_case_true/(this_case_false+this_case_true);

  }
  static public double get_all_permutations_chance(Hashtable<String, String> base_options , Hashtable<String, Node> nodes_left_copy, double sum_chance){
    ArrayList<Hashtable<String, String>> all_permutations = new ArrayList<Hashtable<String, String>>();
    all_permutations = get_all_permutations_recursive(all_permutations, base_options, new ArrayList<Node>(deepCopy_node(nodes_left_copy).values()));
    for (Hashtable<String, String> single_permutation : all_permutations) {
      Set<String> keys = Nodes_list.keySet();

      //Obtaining iterator over set entries
      Iterator<String> itr = keys.iterator();

      //Displaying Key and value pairs
      double mul_chance = 1;
      while (itr.hasNext()) {
         // Getting Key
         String name = itr.next();
         if (mul_chance==1){
           mul_chance=Nodes_list.get(name).get_prob(single_permutation);
         } else {
           times ++;
           mul_chance = mul_chance * Nodes_list.get(name).get_prob(single_permutation);
         }
      }
      if (sum_chance==0){
        sum_chance = mul_chance;
      }
      else {
        plus++;
        sum_chance = sum_chance + mul_chance;
      }
    }
    return sum_chance;

  }
  static public ArrayList<Hashtable<String, String>> get_all_permutations_recursive(ArrayList<Hashtable<String, String>> all_permutations, Hashtable<String, String> base, ArrayList<Node> nodes_left_copy){
    if (nodes_left_copy.isEmpty()){
      all_permutations.add(base);
      return all_permutations;
    } else {
      Node this_node = nodes_left_copy.remove(0);
      String node_name = this_node.get_name();
      String node_value_options[] = this_node.get_value_options();
      for (int i = 0; i<node_value_options.length; i++){
        Hashtable<String, String> new_base = deepCopy(base);
        new_base.put(node_name, node_value_options[i]);
        all_permutations = get_all_permutations_recursive(all_permutations, new_base, new ArrayList<Node>(nodes_left_copy));
      }
    }
    return all_permutations;
  }

/**
 * Main
 * @param args main args
 */
  public static void main(String[] args)
  {

    String message = "";
    String line;
    // List<Node> Parents = new ArrayList<Node>();
    try {
      // read file
        Scanner myReader = new Scanner(new File("input.txt"));
        // skip Network line
        myReader.nextLine();
        // skip Variables line
        myReader.nextLine();
        while (myReader.hasNextLine()){
          String next_line = myReader.nextLine();
          if (next_line.startsWith("Var")){
            List<String> Parents = new ArrayList<String>();
            Hashtable<String, Double> CPT = new Hashtable<String, Double>();
            String node_name = next_line.replace("Var ","");
            String Values[] = myReader.nextLine().replace("Values: ","").split(",");
            String Parents_string = myReader.nextLine().replace("Parents: ","");
            if (!Parents_string.equals("none")){
              String[] myParents = Parents_string.split(",");
              for (String s: myParents) {
                  Parents.add(s);
              }
            }
            // skip CPT:
            myReader.nextLine();
            line = myReader.nextLine();
            while (!"".equals(line)){
              String starter = line.substring(0,line.indexOf("="));
              String values[] = line.split("=");
              // start CPT
              double sum_prob = 0.0;
              for (int i = 1; i<values.length; i++){
                String [] name_value = values[i].split(",");
                CPT.put(starter+name_value[0],Double.parseDouble(name_value[1]));
                sum_prob = sum_prob + Double.parseDouble(name_value[1]);
              }
              CPT.put(starter + Values[Values.length-1], (double)Math.round((1-sum_prob) * 100000d) / 100000d);
              line = myReader.nextLine();
            }
            Nodes_list.put(node_name, new Node(node_name, Values, Parents, CPT));
          } else if (next_line.startsWith("Queries")){
            while (myReader.hasNext())
            {
              plus = 0;
              times = 0;
              line = myReader.nextLine();
              if (line.endsWith("1")){
                System.out.println(algorithm_1(Nodes_list, line) + "," + plus + "," + times);
              }
              // need to anylise query
              System.out.println(line);
            }
          }
        }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }




















    //
    //
    // Game start_game = new Game(data);
    // long startTime = System.currentTimeMillis();
    // long endTime = System.currentTimeMillis();
    // long totalTime = endTime - startTime;
    // int moveable_tiles = data.m_size*data.n_size - 1 - data.black_info.length;
    // Algorithm algo = new Algorithm(data.algo, start_game, data.open_info);
    // // check if all black tiles are in thier spot
    // if (pre_black_check_failure(data))
    // {
    //   message = "no path\nNum: 1";
    // }
    // else
    // {
    //   startTime = System.currentTimeMillis();
    //   solution = algo.run(moveable_tiles);
    //   endTime = System.currentTimeMillis();
    //   totalTime = endTime - startTime;
    //   if (solution == null) {
    //     message = "no path\nNum: " + algo.node_counter + "\n";
    //   } else
    //   {
    //     message = solution + "\nNum: " + algo.node_counter + "\nCost: " + solution.cost + "\n";
    //   }
    // }
    // if (data.time_info)
    // {
    //   message += totalTime/1000.0 + " seconds";
    // }
    // try {
    //   FileWriter myWriter = new FileWriter("output.txt");
    //    myWriter.write(message);
    //    myWriter.close();
    //  } catch (IOException e) {
    //    System.out.println("An error occurred.");
    //    e.printStackTrace();
    //  }
    // System.out.println(message);
 }
