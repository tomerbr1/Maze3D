package algorithms.search;

import java.util.ArrayList;

import domains.State;

/**
 * The solution class generate the solution to a searchable problem as a states array 
 * @author Tomer
 *
 */
public class Solution {
	private ArrayList<State> states;

	/**
	 * this method returns the states array that leads the solution.
	 * @return states array
	 */
	public ArrayList<State> getStates() {
		return states;
	}

	/**
	 * this method set an array of states as the array that lead to the solution
	 * @param states an array of states
	 */
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	
	/**
	 * this method overrides the Object's toString method in order to print the solution array.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (State s: states) {
			sb.append(s).append("\n");
		}
		return sb.toString();
	}

}
