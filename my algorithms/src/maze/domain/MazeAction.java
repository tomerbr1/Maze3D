package maze.domain;

import domains.Action;
import maze.generators.Direction;

/**
 * This Class used for the adapter pattern, as class adapter for a maze action to the action class.
 * @author Tomer
 *
 */
public class MazeAction extends Action {
	
	public static final double mazeMovementCost = 1;
	private Direction move;
	
	/**
	 * The CTor method to the Maze's action
	 * @param move a move from a state used to be checked by the action class
	 */
	public MazeAction(Direction move) {
		super(move.toString(), mazeMovementCost);
	}

}
