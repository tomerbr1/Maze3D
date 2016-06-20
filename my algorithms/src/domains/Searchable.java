package domains;

import java.util.HashMap;

/**
 * The Searchable interface intended to be implemented by a searchable problem
 * @author Tomer Brami & Yotam Levy
 */
public interface Searchable {
	/**
	 * a generic getter method
	 * @return the start state of a searchable problem
	 */
	   State getStartState();
	   
	   /**
	    * a generic getter method
	    * @return the goal state of a searchable problem
	    */
	   State getGoalState();
	   
	   /**
	    * a HashMap used to store all the possible actions from each state
	    * @param s a state used to check all the possible action
	    * @return a HashMap 
	    */
	   HashMap<Action, State> getAllPossibleActions(State s);
}