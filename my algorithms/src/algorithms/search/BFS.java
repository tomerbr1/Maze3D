package algorithms.search;

import java.util.HashMap;
import java.util.Map.Entry;

import domains.Action;
import domains.Searchable;
import domains.State;

import java.util.PriorityQueue;

/**
 * This class implement the breath first search (BFS) as a search algorithm
 * @author Tomer Brami & Yotam Levy
 *
 */
public class BFS extends CommonSearcher implements Searcher {

	protected PriorityQueue<State> openList;
	protected PriorityQueue<State> closedList;
	
	/**
	 * The CTor of the breath first search creates a new open & close priority queue lists
	 */
	public BFS() {
		openList = new PriorityQueue<State>();
		closedList = new PriorityQueue<State>();
	}
	
	/**
	 * The algorithm implementation to the solution method
	 * @param Searchable a searchable problem  that the bfs needs to solve
	 */
	@Override
	public Solution search(Searchable s) {
		
		openList.add(s.getStartState());
		
		while (!openList.isEmpty()) {
			State state = openList.poll();
			this.setEvaluatedNodes(getEvaluatedNodes()+1);
			closedList.add(state);
			
			if (state.equals(s.getGoalState())) 
				return backtrace(state);
			
			HashMap<Action, State> actions = s.getAllPossibleActions(state);
			for (Entry<Action, State> entry: actions.entrySet()) {
				Action action = entry.getKey();
				State successor = entry.getValue();
				
				if (!openList.contains(successor) && !closedList.contains(successor)) {
					successor.setCameFrom(state);
					openList.add(successor);
					this.setEvaluatedNodes(getEvaluatedNodes()+1);
				}
				else if (costFlag() == true) {
					if (state.getCost() + action.getCost() < successor.getCost())
					{					
						successor.setCameFrom(state);
						successor.setCost(state.getCost() + action.getCost());

						// update priority in queue by removing and adding the state
						openList.remove(successor);
						openList.add(successor);	
						this.setEvaluatedNodes(getEvaluatedNodes()+1);
					}
				}
			}
		}
		return null; // won't reach this line never
	}
	
	/**
	 * a boolean method used in the solution method to decide if include action costs
	 * (best first search) or without action costs (breath first search)
	 * @return boolean answer
	 */
	protected boolean costFlag(){
		return false;
	}
}
