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
        if( start == dest){
            return 0;
        }
        Iterator<City> reset = cities.iterator();
        while(reset.hasNext()){
            City current = reset.next();
            current.resetMinCost();
        }
        start.setMinCost(0);
        minHeap = new Heap();
        minHeap.buildHeap(cities);
        boolean[] spt = new boolean[minHeap.getHeapSize()];

        
    
        while(minHeap.getHeapSize() > 0){
            City extract = minHeap.extractMin();
            spt[extract.getCityName()] = true;
            ArrayList<City> neighbors = extract.getNeighbors();
            ArrayList<Integer> weights = extract.getWeights();
            for (int i = 0; i < neighbors.size(); i++){
                City nextdoor = neighbors.get(i);
                int name = nextdoor.getCityName();
                int oldCost = nextdoor.getMinCost();
                int cost = weights.get(i);

                if (spt[name] == false){
                    int newCost = extract.getMinCost() + cost;
                    if (newCost < oldCost){
                        minHeap.changeKey(nextdoor, newCost);
                    }
                }
            }
            
        }
        return dest.getMinCost();
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
        Iterator<City> reset = cities.iterator();
        while(reset.hasNext()){
            City current = reset.next();
            current.resetMinCost();
        }
        start.setMinCost(0);
        Heap minHeap = new Heap();
        minHeap.buildHeap(cities);
        boolean[] spt = new boolean[minHeap.getHeapSize()];
        ArrayList<City> path = new ArrayList<City>();

    
        while(minHeap.getHeapSize() > 0){
            City extract = minHeap.extractMin();
            ArrayList<City> neighbors = extract.getNeighbors();
            ArrayList<Integer> weights = extract.getWeights();
            for (int i = 0; i < neighbors.size(); i++){
                City nextdoor = neighbors.get(i);
                int name = nextdoor.getCityName();
                int oldCost = nextdoor.getMinCost();
                int cost = weights.get(i);

                if (spt[name] == false){
                    int newCost = extract.getMinCost() + cost;
                    if (newCost < oldCost){
                        minHeap.changeKey(nextdoor, newCost);
                    }  
                }
            }
        }

        City next = dest;
        int distance = dest.getMinCost();
        
        if (distance == Integer.MAX_VALUE){
            //return something null path
        }
        else{
            while (distance != 0){
                ArrayList<City> neighbors = next.getNeighbors();
                ArrayList<Integer> weights = next.getWeights();

                for (int i = 0; i < neighbors.size(); i++){
                    City nextdoor = neighbors.get(i);
                    int name = nextdoor.getCityName();
                    int cost = nextdoor.getMinCost();
                    if (cost == distance - weights.get(i)){
                        distance = distance - weights.get(i);
                        path.add(0, next);
                        next = nextdoor;
                    }
                }
            }
        }

        if (path.size() == 0){
            return null;
        }
        else{
            path.add(0, start);
            return path;
        }
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
        //resets cities
        Iterator<City> reset = cities.iterator();
        while(reset.hasNext()){
            City current = reset.next();
            current.resetMinCost();
        }

        int flag = 0;
        Heap minHeap = new Heap();
        minHeap.buildHeap(cities);
        boolean mst[] = new boolean[minHeap.getHeapSize()];
        int total = 0;
        int name = 0;
        int [] key = new int[minHeap.getHeapSize()];
        boolean [] inHeap = new boolean[minHeap.getHeapSize()];
        
        for (int i =0; i < key.length; i++){
            key[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < minHeap.getHeapSize(); i++){
            mst[i] = false;
        }
        for (int i = 0; i < minHeap.getHeapSize(); i++){
            inHeap[i] = true;
        }


        while(minHeap.getHeapSize() > 0){
            //Goes through every City in the minHeap
            City extract = minHeap.extractMin();
            if (flag == 0){
                extract.setMinCost(0);
                flag++;
            }
            inHeap[extract.getCityName()] = false;
            ArrayList<City> neighbors = extract.getNeighbors();
            ArrayList<Integer> weights = extract.getWeights();
            //Keeps track of the least min weight
            for (int i = 0; i < neighbors.size(); i++){
                City nextdoor = neighbors.get(i);
                name = nextdoor.getCityName();
                int value = weights.get(i);
                if (inHeap[name]){
                    if (key[name] > value){
                        minHeap.changeKey(nextdoor, value);
                    }
                }
            }
        }
        Iterator<City> itr = cities.iterator();
        while(itr.hasNext()){
            City current = itr.next();
            total = total+ current.getMinCost();
        }
        return total;
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
