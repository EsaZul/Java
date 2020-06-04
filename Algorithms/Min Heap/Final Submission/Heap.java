import java.util.ArrayList;
import java.util.Iterator;

public class Heap {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    public boolean isEmpty (){
        if (minHeap.size() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    public int getHeapSize(){
        return minHeap.size();
    }


    //Heapify up corrects the heap by comparing the parent
    public void heapifyUp(int i){
        //Corrects if there are at least more than one city in the heap
        if (i > 1){
            int  j = (i/2)-1;
            //Corrects if the child minCost is gretaer than its parent
            if (minHeap.get(i-1).getMinCost() < minHeap.get(j).getMinCost()){
    		    City temp1 = minHeap.get(i-1);
    		    City temp2 = minHeap.get(j);
    		    minHeap.set(i-1, temp2);
                minHeap.set(j, temp1);
                temp2.setPositon(i-1);
                temp1.setPositon(j);
                heapifyUp(j+1);
            }
            //Handles case where the minCost is equivalent, but child city name is less than parent
            else if (minHeap.get(i-1).getMinCost() == minHeap.get(j).getMinCost()){
                if (minHeap.get(i-1).getCityName() < minHeap.get(j).getCityName()){
                    City temp1 = minHeap.get(i-1);
    		        City temp2 = minHeap.get(j);
    		        minHeap.set(i-1, temp2);
                    minHeap.set(j, temp1);
                    temp2.setPositon(i-1);
                    temp1.setPositon(j);
                    heapifyUp(j+1);
                }
            }
        }
    }


    //Corrects heap by comparing its children
    public void heapifyDown(int i){
        boolean isRightPresent = false;
        boolean isLeftPresent = false;
        int size = minHeap.size();
        int minRight = -1;
        int minLeft = -1;
        int min = -1;
        int leaf = 0;

        //City node is on the last row of the heap, so there is no parents, ends recursion
        if (2*i > size){
            //do nothing
            
        }
        
        else {
            int left = 2*i+1;
            int right = 2*i+2;
        
            //Check if child nodes are present
            if (left < size){
                minLeft = minHeap.get(left).getMinCost();
                isLeftPresent = true;
            }
            if (right < size){
                minRight = minHeap.get(right).getMinCost();
                isRightPresent = true;
            }

            //If both are present, checks both saves the minCost
            if (isLeftPresent & isRightPresent){
                if (minLeft < minRight){
                    min = minLeft;
                    leaf = left;
                }
                else if (minLeft > minRight){
                    min = minRight;
                    leaf = right;
                }
                else if (minLeft == minRight){
                    if (minHeap.get(left).getCityName() < minHeap.get(right).getCityName()){
                        min = minLeft;
                        leaf = left;
                    }
                    else {
                        min = minRight;
                        leaf = right;
                    }
                }
            }
            //If theres only a left child present
            if (!isRightPresent & isLeftPresent){
                min = minLeft;
                leaf = left;
            }
            //Case if for some reason there is only one right child
            if (!isLeftPresent & isRightPresent){
                min = minRight;
                leaf = right;
            }
            //Switches if min was changed
            if(minHeap.get(i).getMinCost() > min & min != -1){
                City temp1 = minHeap.get(i);
    		    City temp2 = minHeap.get(leaf);
    		    minHeap.set(i, temp2);
                minHeap.set(leaf, temp1);
                temp2.setPositon(i);
                temp1.setPositon(leaf);
                heapifyDown(leaf);
            }
            //switches if min is equal to node, but has a lower city name
            else if (minHeap.get(i).getMinCost() == minHeap.get(leaf).getMinCost() & min != -1){
                if (minHeap.get(i).getCityName() > minHeap.get(leaf).getCityName()){
                    City temp1 = minHeap.get(i);
    		        City temp2 = minHeap.get(leaf);
    		        minHeap.set(i, temp2);
                    minHeap.set(leaf, temp1);
                    temp2.setPositon(i);
                    temp1.setPositon(leaf);
                    heapifyDown(leaf);
                }
            }
        }
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minCost
     * Time Complexity - O(n)
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        //Uses insetNode to build a heap based on heapify up and down 
        minHeap = new ArrayList<City>();
        ArrayList<City> mod = new ArrayList<City>(cities);
        Iterator<City> itr = mod.iterator();
        while(itr.hasNext()){
           City current = itr.next();
           insertNode(current);
        }
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
    	minHeap.add(in);
        int current = minHeap.size();
        in.setPositon(current-1);
        if (current > 1){
            heapifyUp(current);
        }
    }

    /**
     * findMin()
     *
     * @return the minimum element of the heap. Must run in constant time.
     */
    public City findMin() {
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
    	City min = findMin();
    	delete(0);
        return min;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        int size = minHeap.size();
        //Removes last node and replaces it with the removed node
        if (size > 0){
            City lastNode = minHeap.get(size-1);
            minHeap.set(index, lastNode);
            minHeap.remove(size-1);
            lastNode.setPositon(0);
            if(size-1 > 0){
                heapifyDown(index);
            }
        }
        else{
            System.out.println("NO HEAP AVAILABLE");
        }
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
        int size = minHeap.size();
        int oldValue = c.getMinCost();
        //int index = minHeap.indexOf(c);
        int index = c.getPosition();
        int node = index + 1;
        int parent = (node/2)-1;
        int left = index*2+1;
        int right = index*2+2;
        minHeap.get(index).setMinCost(newCost);
        boolean isRightPresent = false;
        boolean isLeftPresent = false;
        

        //Finds parent and children of city, then fixes the heap
        if(index == 0 & size > 0){
        }
        else if(2*node > size){
            heapifyUp(node);
        }
        else {
            if (left < size){
                isLeftPresent = true;
            }
            if (right < size){
                isRightPresent = true;
            }

            if (newCost <= minHeap.get(parent).getMinCost()){
                if (newCost == minHeap.get(parent).getMinCost() && minHeap.get(index).getCityName() < minHeap.get(parent).getCityName()){
                    heapifyUp(node);
                }
            }
            if((isLeftPresent)){
                if(newCost >= minHeap.get(left).getMinCost()){
                    heapifyDown(index);
                }
            }
            if(isRightPresent){
                if(newCost >= minHeap.get(right).getMinCost()){
                    heapifyDown(index);
                }
            }
        }   
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
