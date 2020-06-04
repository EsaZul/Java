import java.util.ArrayList;
import java.util.*;

public class Program2 {
    private ArrayList<City> cities;     //this is a list of all cities, populated by Driver class.
    private Heap minHeap;

    // feel free to add any fields you'd like, but don't delete anything that's already here

    public Program2(int numCities) {
        minHeap = new Heap();
        cities = new ArrayList<City>();
    }

    /**
     * findCheapestPathPrice(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return the minimum cost possible to get from start to dest.
     * If no path exists, return Integer.MAX_VALUE
     */
    public int findCheapestPathPrice(City start, City dest) {
        // TODO: implement this function
        return -1;
    }

    /**
     * findCheapestPath(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return an ArrayList of nodes representing a minimum-cost path on the graph from start to dest.
     * If no path exists, return null
     */
    public ArrayList<City> findCheapestPath(City start, City dest) {
        // TODO: implement this function
        return null;
    }

    /**
     * findLowestTotalCost()
     *
     * @return The sum of all edge weights in a minimum spanning tree for the given graph.
     * Assume the given graph is always connected.
     * The government wants to shut down as many tracks as possible to minimize costs.
     * However, they can't shut down a track such that the cities don't remain connected.
     * The tracks you're leaving open cost some money (aka the edge weights) to maintain. Minimize the overall cost.
     */
    public int findLowestTotalCost() {
        // TODO: implement this function
        return -1;
    }

    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (City v : cities) {
            boolean first = true;
            o += "City ";
            o += v.getCityName();
            o += " has neighbors: ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getCityName() : ", " + n.getCityName();
                first = false;
            }
            first = true;
            o += " with weights ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<City> getAllCities() {
        return cities;
    }

    //used by Driver class to populate each Node with correct neighbors and corresponding weights
    public void setEdge(City curr, City neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    //This is used by Driver.java and sets vertices to reference an ArrayList of all nodes.
    public void setAllNodesArray(ArrayList<City> x) {
        cities = x;
    }
}
