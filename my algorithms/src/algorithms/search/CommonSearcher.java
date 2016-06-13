package algorithms.search;

import java.util.ArrayList;

import domains.State;

/**
 * The CommonSearcher class includes the common implemented method of all the search algorithms
 * @author Tomer
 *
 */
public abstract class CommonSearcher implements Searcher {

	/**
	 * @param evaluatedNodes used to evaluate how many nodes were created duraing a search
	 */
	
	int evaluatedNodes = 0;
	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}
	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}
	/**
	 * The backtrace method used to create a back trace list from a state
	 * @param state the state we'll generate it's back trace
	 * @return a solution of a back trace list
	 */
	protected Solution backtrace(State state) {
		State s = state;
		ArrayList<State> states = new ArrayList<State>();
		while (s != null) {
			states.add(0, s);	
			s = s.getCameFrom();
		}
		Solution solution = new Solution();
		solution.setStates(states);
		return solution;
	}
	
}
