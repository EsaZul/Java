package assignment3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Vertex<T> {
    private T label;
    private Map<T, Integer> edges; // T is a vertex, Integer is the weight

    public Vertex (T label, Map<T, Integer> edges) {
        this.label = label;
        this.edges = edges;
    }

    public T getName() {
        return this.label;
    }
    public T setName(T label) {
        return this.label = label;
    }
    public Map<T, Integer> getEdge(){
        return this.edges;
    }

    public Map<T, Integer> setEdge(Map<T, Integer> edges){
        return this.edges = edges;
    }

    public static boolean isInclude(String s1, String s2, ArrayList<Vertex> poem, Map edges, Vertex v){
        for (int i = 0; i < poem.size(); i++) {                                                         //iterates through all arraylist
            if (s1.equals(poem.get(i).getName())) {                                                     //case insensitive
                for (int j = 0; j < poem.get(i).edges.size(); j++) {                                    //checks all edges present
                    if (edges.equals(poem.get(i).getEdge())) {                                          //if name and edge are same, then its repeated
                        int z = (int) edges.get(s2);
                        z++;
                        poem.get(i).edges.put(s2, z);                                                   //updates weight
                        return true;
                    }
                }
                poem.get(i).edges.put(s2, 1);                                                           //if name is same but not edge, then new path
                return true;
            }
        }
        return false;                                                   //no pair alike
    }
}
