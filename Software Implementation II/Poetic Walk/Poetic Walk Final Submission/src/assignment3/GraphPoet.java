/* EE422C Assignment #3 submission by
 * <Student Name>
 * <Student EID>
 */

package assignment3;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphPoet {
    private ArrayList<Vertex> poem = new ArrayList<>();

    /**
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {

        Scanner scan = new Scanner(corpus);

        String s1 = scan.next();
        String s2;


        while (scan.hasNext()) {
            s1 = s1.toLowerCase();                                                              //case insensitive
            HashMap<String, Integer> edge = new HashMap<>();
            Vertex v = new Vertex(s1, edge);
            s2 = scan.next();                                                                   //reads word per word
            edge.put(s2, 1);                                                                    //stores word pair and edge
            if (!Vertex.isInclude(s1, s2, poem, edge, v)) {                                     //creates a list of non-repeated words
                poem.add(v);
            }
            s1 = s2;                                                                            //updates next word pair
        }
        HashMap<String, Integer> edge = new HashMap<>();
        Vertex v = new Vertex(s1, edge);
        s2 = null;
        if (!Vertex.isInclude(s1, s2, poem, edge, v)) {                                         //adds last word with null as edge
            poem.add(v);
        }
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {
        Scanner scan = new Scanner(input);

        String s1 = scan.next();                                                                //Reads first word
        String s2;                                                                              //Will contain word after s1
        StringBuilder finalPoem = new StringBuilder();                                          //Location of poem
        finalPoem.append(s1);                                                                   //First word in poem
        finalPoem.append(" ");                                                                  //Seperates words
        String k;                                                                               //Holds bridge word, which is the key of s1
        int v;                                                                                  //Holds weight
        String l;                                                                               //Used to compare if bridge word connects to s2
        String s2end;

        while (scan.hasNext()) {                                                                //Reads all words in text
            s1 = s1.toLowerCase();                                                              //case insensitive
            s2end = scan.next();
            s2 = s2end.replaceAll("[^a-zA-Z0-9\\s+]", " ");
            s2 = s2.trim();
            String max = s2;                                                                    //Max will contain possible bridge word
            int most = 0;
            for (int i = 0; i < poem.size(); i++) {                                             //iterates through arraylist
                String same = (String) poem.get(i).getName();
                same = same.toLowerCase();
                if (s1.equals(poem.get(i).getName())){// | same.equals(poem.get(i).getName())) {    //finds s1 if in list, case insensitive
                    Iterator itr1 = poem.get(i).getEdge().keySet().iterator();                  //Gets keys in s1 vertex and makes it into set
                    while (itr1.hasNext()) {                                                    //goes through each key
                        k = (String) itr1.next();                                               //possible bridge word
                        v = (int) poem.get(i).getEdge().get(k);                                 //weight of said bridge word
                        for (int j = 0; j < poem.size(); j++) {                                 //iterates through arraylist again finding bridge word
                            if (k.equals(poem.get(j).getName())) {                              //confirming if bridge word leads to s2
                                Iterator itr2 = poem.get(j).getEdge().keySet().iterator();      //makes set of keys in bridge word
                                while (itr2.hasNext()) {                                        //goes through each map
                                    l = (String) itr2.next();                                   //gets possible s2
                                    if (s2.equals(l) & most < v) {                              //case condition where bridge word contains s2
                                            most = v;                                           //saves bridge word with biggest weight
                                            max = k;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!max.equals(s2)){                                                               //if bridge word was saved, gets implemented in poem
                finalPoem.append(max);
                finalPoem.append(" ");
            }
            finalPoem.append(s2end);                                                               //adds s2 regardless if bridge or not
            s1 = s2;                                                                                //updates edge comparison
            finalPoem.append(" ");
        }
        return String.valueOf(finalPoem).trim();                                                //Prints final poem
    }
}
