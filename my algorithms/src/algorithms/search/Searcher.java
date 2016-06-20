package algorithms.search;

import domains.Searchable;

/**
 * The interface to be implemented by search algorithms
 * @author Tomer Brami & Yotam Levy
 *
 */
public interface Searcher {
	
	/**
	 * The search method
	 * @param s a searchable problem
	 * @return a solution to the problem
	 */
    public Solution search(Searchable s);
}

