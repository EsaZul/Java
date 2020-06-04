import java.util.ArrayList;

public class Heap {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minCost
     * Time Complexity - O(n)
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        // TODO: implement this method
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        // TODO: implement this method
    }

    /**
     * findMin()
     *
     * @return the minimum element of the heap. Must run in constant time.
     */
    public City findMin() {
        // TODO: implement this method
        return null;
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        // TODO: implement this method
        return null;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
    }

    /**
     * changeKey(City c, int newCost)
     * Updates and rebalances a heap for City c.
     * Time Complexity - O(log(n))
     *
     * @param c       - the city in the heap that needs to be updated.
     * @param newCost - the new cost of city c in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(City c, int newCost) {
        // TODO: implement this method
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getCityName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
