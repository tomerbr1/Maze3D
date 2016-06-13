package algorithms.search;

import java.util.HashMap;
import java.util.HashSet;

import domains.Action;
import domains.Searchable;
import domains.State;

/** 
 * This class implement the depth search as a search algorithm  
 * @author Tomer
 *
 */
public class DFS extends CommonSearcher {
	private HashSet<State> visitedStates = new HashSet<State>();
	private Solution solution;
	
	/**
	 * This method finds a solution to a searchable problem
	 * @param s the problem
	 * @return solution
	 */
	@Override	
	public Solution search(Searchable s) {
		try {
			dfs(s, s.getStartState());
		} catch (NullPointerException e) {
			return null;
		}
		return solution;
	}
	
	/**
	 * The dfs search algorithm
	 * @param s a searchable problem
	 * @param state a state to be given to the algorithm
	 */
	private void dfs(Searchable s, State state) {
		if (state.equals(s.getGoalState())) {
			solution = backtrace(state);
			return;
		}
		
		visitedStates.add(state);
		this.setEvaluatedNodes(getEvaluatedNodes()+1);
		
		HashMap<Action,State> actions = s.getAllPossibleActions(state);
		for(State neighbor: actions.values())
		{
			if (!visitedStates.contains(neighbor)) {
				neighbor.setCameFrom(state);
				dfs(s, neighbor);					
			} 			
		}
		return;
	}

}
