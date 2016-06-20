package algorithms.demo;

import java.util.HashMap;

import domains.Action;
import domains.Searchable;
import domains.State;
import maze.domain.MazeState;
import maze.generators.Direction;
import maze.generators.Maze3d;
import maze.generators.Position;

/**
 * This class implements a searchable problem and adapt the Maze methods on it,
 * by the Adapter Pattern.
 * @author Tomer Brami & Yotam Levy
 *
 */
public class MazeAdapter implements Searchable {
	
	private Maze3d maze;
	private static final int MOVEMENT_COST = 1;

	/**
	 * The CTor method is used only to adapt a created maze.
	 * @param maze the created maze that used by the Adapter pattern.
	 */
	public MazeAdapter(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * This method implement the problem's start position by the maze's one and return it.
	 */
	@Override
	public State getStartState() {
		MazeState startState = new MazeState(maze.getStartPosition());
		return startState;
	}

	/**
	 * This method implement the problem's goal position by the maze's one and return it.
	 */
	@Override
	public State getGoalState() {
		MazeState goalState = new MazeState(maze.getGoalPosition());
		return goalState;
	}

	/**
	 * This method implement the problem's Hashmap of all possible actions from each state
	 * by using the maze's possible moves.
	 */
	@Override
	public HashMap<Action, State> getAllPossibleActions(State s) {
		MazeState mazeState = (MazeState)s;
		Position pos = mazeState.getCurrPlayerPosition();
		Direction[] directions = maze.getPossibleDirections(pos);
		
		HashMap<Action, State> actions = new HashMap<Action, State>();
		for (Direction d: directions) {
			Action action = new Action(d.toString(), MOVEMENT_COST);
			MazeState newState = new MazeState(getNextPosition(pos, d));
			
			actions.put(action,  newState);
		}
		return actions;
	}
	
	/**
	 * The method get the current position and a direction, and returns
	 * a new position which is a neighbor of the current position,
	 * and based on the given direction.
	 * @param currPos the current position.
	 * @param dir a given direction
	 * @return a new position which is a neighbor of the current position, and based on the given direction.
	 */
	private Position getNextPosition(Position currPos, Direction dir) {
		switch (dir) {
		case UP:
			return new Position(currPos.getX() + 1, currPos.getY(), currPos.getZ());			
		case DOWN:
			return new Position(currPos.getX() - 1, currPos.getY(), currPos.getZ());	
		case RIGHT:
			return new Position(currPos.getX(), currPos.getY() + 1, currPos.getZ());	
		case LEFT:
			return new Position(currPos.getX(), currPos.getY() - 1, currPos.getZ());
		case FORWARD:
			return new Position(currPos.getX(), currPos.getY(), currPos.getZ() + 1);	
		case BACKWARD:
			return new Position(currPos.getX(), currPos.getY(), currPos.getZ() - 1);	
		}
		return null;
	}


}
