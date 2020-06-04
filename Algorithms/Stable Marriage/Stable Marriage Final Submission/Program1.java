/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the Stable Marriage problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching marriage) {
    	ArrayList<Integer> test = new ArrayList<>(marriage.getEmployeeMatching());
    	for(int i = 0; i < test.size(); i++) {
    		int s = i;
    		int h = test.get(s);
    		if(h != -1) {
    			for (int j = 0; j < test.size(); j++) {
    				int s1 = j;
    				int h1 = test.get(j);
    				if (s != s1) {
    					if (h1 == -1) {
    						ArrayList<Integer> hPref = marriage.getLocationPreference().get(h);
    						int x = hPref.indexOf(s);
    						int y = hPref.indexOf(s1);
    						if (x > y) {
    							return false;
    				    	}
    						
    					}
    					else{
    						ArrayList<Integer> hPref = marriage.getLocationPreference().get(h);
    						ArrayList<Integer> s1Pref = marriage.getEmployeePreference().get(s1);
    						int x = hPref.indexOf(s);
    						int y = hPref.indexOf(s1);
    						if (x > y) {
    							int a = s1Pref.indexOf(h);
    							int b = s1Pref.indexOf(h1);
    							if (a < b) {
    								return false;
    							}
    						}
    					}
    				}
    			}
    		}
    		
    	}
        return true;
    }
    
   

    /**
     * Determines a employee optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_employeeoptimal(Matching marriage) {
        
    	//Initializes queue & size of location and employees
    	Queue<Integer> q = new LinkedList<>();
    	ArrayList<Integer> employee_matching = new ArrayList<>();
    	ArrayList<Integer> count = new ArrayList<>();
    	ArrayList<Integer> slots = new ArrayList<>(marriage.getLocationSlots());
    	int m = marriage.getEmployeeCount();
    	int n = marriage.getLocationCount();
    	
    	for(int i = 0; i < slots.size(); i++) {
    		if (slots.get(i) == 0) {
    			slots.set(i, -1);
    		}
    	}
    	
    	for(int i = 0; i < m; i++) {
    		q.add(i);
    		employee_matching.add(i,-1);
    		count.add(i,0);
    	}
    	
    	while (!q.isEmpty()) {
   
    		m = marriage.getEmployeeCount();
        	n = marriage.getLocationCount();
    		int employee = q.remove();
    		ArrayList<Integer> empPref = marriage.getEmployeePreference().get(employee);
    		int rank = count.get(employee);
    		if (rank < n) {
    			int store = empPref.get(rank);
    			ArrayList<Integer> locPref = marriage.getLocationPreference().get(store);
 
   
    		int flag = 0;
			while(flag == 0) {
				rank = count.get(employee);
	    		store = empPref.get(rank);
	    		locPref = marriage.getLocationPreference().get(store);
    		
	    		if(free(employee_matching, employee) & (slots.get(store) > 0)) {
	    			employee_matching.set(employee, store);
	    			slots.set(store, slots.get(store) - 1);
	    			flag++;
	    		}
	    		else {
    	    		//int employee1 = employee_matching.indexOf(store);
    	    		Iterator<Integer> iter = employee_matching.iterator();
    	    		int index = 0;
    	    		while(iter.hasNext()) {
    	    			int next = iter.next();
    	    			if(flag == 0) {
    	    				if((next == store) & (slots.get(store) > -1) & (index != employee) & (!locationPrefersE1overE(locPref, employee, index, m))) {
    	    					flag++;
    	    					employee_matching.set(employee, store);
    	    					employee_matching.set(index, -1);
    	    					//slots.set(store, slots.get(store) + 1);
    	    					count.set(index, count.get(index)+1);
    	    					if(rank < marriage.getLocationCount()) {
    	    						q.add(index);
    	    					}	
    	    				}
    	    			}
    	    			index++;
    	    		}
    	    		if (flag == 0) {
    	    			count.set(employee, rank+1);
    	    		}
    	    		
    			}
	    		if (count.get(employee) >= marriage.getLocationCount()) {
	    			flag++;
	    		}
			}
    		}
    	}
    	marriage.setEmployeeMatching(employee_matching);
    	isStableMatching(marriage);
		return marriage;
    }

    /**
     * Determines a location optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_locationoptimal(Matching marriage) {
    	Queue<Integer> q = new LinkedList<>();
    	ArrayList<Integer> employee_matching = new ArrayList<>();
    	ArrayList<Integer> count = new ArrayList<>();
    	ArrayList<Integer> slots = new ArrayList<>(marriage.getLocationSlots());
    	ArrayList<Integer> locPref;
    	int m = marriage.getEmployeeCount();
    	int n = marriage.getLocationCount();
    	
    	
    	for(int i = 0; i < slots.size(); i++) {
    		if (slots.get(i) == 0) {
    			slots.set(i, -1);
    		}
    	}
    	
    	for(int i = 0; i < m; i++) {
    		employee_matching.add(i,-1);
    	}
    	for(int i = 0; i < n; i++) {
    		q.add(i);
    		count.add(i,0);
    	}
    	
    	while (!q.isEmpty()) {
    		m = marriage.getEmployeeCount();
        	n = marriage.getLocationCount();
    		int location = q.remove();
    		locPref = marriage.getLocationPreference().get(location);
   
    			int rank = count.get(location);
        		int employee = locPref.get(rank);
    			
    			if(free(employee_matching, employee) & (slots.get(location) > 0)) {
    			
    				employee_matching.set(employee, location);
    				slots.set(location, slots.get(location) - 1);
    				count.set(location, rank+1);
    				if(slots.get(location) > 0) {
    					q.add(location);
    				}
    			}
    			else {
    				Iterator<Integer> iter = employee_matching.iterator();
    				int index = 0;
    				while(iter.hasNext()) {
    					int next = iter.next();
    					if((slots.get(location) == 0) & (next == location) & (index != employee) & (!employeePrefersL1overL(marriage.getEmployeePreference().get(employee), location, next, n))) {
    						employee_matching.set(employee, location);
    						employee_matching.set(index, -1);
    						count.set(location, rank+1);
    						if(rank < m) {
    							q.add(next);
    						}
    					}
    					index++;
    				}
    			}
    	}
    	
    		marriage.setEmployeeMatching(employee_matching);
    		return marriage;
    }
    
    
    
    
    
    static boolean employeePrefersL1overL(ArrayList<Integer> list, int loc, int loc1, int totalLocations) {
    	for (int i = 0; i < totalLocations; i++) {
    		if (list.get(i) == loc1) {
    			return true;
    		}
    		if (list.get(i) == loc) {
    			return false;
    		}
    	}
    	return false;
    }
    	
    
    static boolean locationPrefersE1overE(ArrayList<Integer> list, int employee, int employee1, int totalEmployee) {
		
    	for (int i = 0; i < totalEmployee; i++) {
    		if (list.get(i) == employee1) {
    			return true;
    		}
    		if (list.get(i) == employee) {
    			return false;
    		}
    	}
    	return false;
    }
    
    static boolean free(ArrayList<Integer> employee_matching, int employee) {
    	if (employee_matching.get(employee) == -1) {
    		return true;
    	}
    	return false;
    }
    
}
