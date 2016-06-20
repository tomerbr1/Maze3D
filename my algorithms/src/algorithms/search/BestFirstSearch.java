package algorithms.search;
/*
 * Advantages of each method:
 * -------------------------
 * 1) Best First Search - In case of a domain containing different prices for moving between
 * 						  states (like the parking problem from Eli's presentation),
 * 						  the Best First Search will find the lowest price path from 
 * 						  the start state to the goal state.
 * 
 * 2) Breadth First Search - Breadth using less code than Best, as it doesn't need to compare
 * 							 the prices of each nodes. It simply returns the shortest path,
 * 							 with less states evaluated.
 * 
 * Why i chose this implementation method of Best First Search:
 * -----------------------------------------------------------
 * Because for my view, it is the most efficient way method - There's no double coding,
 * only a flag telling to the solution algorithm from the Breadth Algorithm to use the
 * price comparing lines.
 */

/**
 * This Class extends the Breath First Search algorithm and make it a Best First Search.
 * @author Tomer Brami & Yotam Levy
 *
 */
public class BestFirstSearch extends BFS implements Searcher {

	/**
	 * the costFlag means that the Best First Search will use the costs of each action to move.
	 */
	protected boolean costFlag(){
		return true;
	}
	
}
