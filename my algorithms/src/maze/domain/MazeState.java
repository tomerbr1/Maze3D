package maze.domain;

import domains.State;
import maze.generators.Position;

/**
 * This Class used for the adapter pattern, as class adapter for a maze state to the state class.
 * @author Tomer
 *
 */
public class MazeState extends State {
	private Position currPlayerPosition;

	/**
	 * a getter method
	 * @return the player's current position
	 */
	public Position getCurrPlayerPosition() {
		return currPlayerPosition;
	}

	/**
	 * a setter method
	 * @param currPlayerPosition a specific position as the current state
	 */
	public void setCurrPlayerPosition(Position currPlayerPosition) {
		this.currPlayerPosition = currPlayerPosition;
	}
	
	/**
	 * The CTor of the MazeState class adapter
	 * @param pos a specific position
	 */
	public MazeState(Position pos) {
		this.currPlayerPosition = pos;
		this.setDescription(pos.toString());
	}

	

}
