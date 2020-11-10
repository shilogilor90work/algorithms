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
/**
 * class Ex1 for search algorithm task
 * @author Shilo Gilor
 */
public class Ex1 {


/**
 * Main
 * @param args main args
 */
  public static void main(String[] args)
  {

    String message = "";
    String line;
    Hashtable<String, Node> Nodes_list = new Hashtable<String, Node>();
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
            List<Node> Parents = new ArrayList<Node>();
            Hashtable<String, Double> CPT = new Hashtable<String, Double>();
            String node_name = next_line.replace("Var ","");
            String Values[] = myReader.nextLine().replace("Values ","").split(",");
            String Parents_string = myReader.nextLine().replace("Parents ","");
            if (Parents_string.equals("none")){
              String[] myParents = Parents_string.split(",");
              for (String s: myParents) {
                  Parents.add(Nodes_list.get(s));
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
              CPT.put(starter+Values[Values.length-1],1-sum_prob);
              line = myReader.nextLine();
            }
            Node temp = new Node(node_name, Arrays.toString(Values), Parents, CPT);
            Nodes_list.put(node_name, temp);
            System.out.println(temp);
          } else if (next_line.startsWith("Queries")){
            while (myReader.hasNext())
            {
              line = myReader.nextLine();
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
