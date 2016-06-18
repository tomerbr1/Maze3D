package maze.generators;
import java.util.*;

/**
 * a simple maze 3d generator algorithm class
 * @author Tomer
 *
 */
public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	/**
	 * @param maze a 3d int maze
	 * @param rand a random generator
	 */
	private Maze3d maze;
	private Random rand = new Random();
	
	/**
	 * the main method implemented to generate a maze by this generator
	 */
	@Override
	public Maze3d generate(int cols, int rows, int depth) {
		maze = new Maze3d(cols, rows, depth);
		
		initMaze();
		
		maze.setStartPosition(choosePosition());
		maze.setGoalPosition(choosePosition());	
		
		//Create path between the start/goal positions
		createPath();
		
		return maze;
	}
	
	/**
	 * this method initializing maze by set all it's cells as wall
	 */
	private void initMaze(){
		int[][][] m = maze.getMaze();
		for (int z = 0; z < maze.getDepth(); z++) {
			for (int y = 0; y < maze.getRows(); y++) {
				for (int x = 0; x < maze.getColumns(); x++)
					m[x][y][z] = Maze3d.WALL;
			}
		}
	}

	/**
	 * a method intended to return a random position inside the maze
	 * @return a random position
	 */
	private Position choosePosition() {		
		int x = rand.nextInt(maze.getColumns());
		int y = rand.nextInt(maze.getRows());
		int z = rand.nextInt(maze.getDepth());
		
		return (new Position(x, y, z));
	}
	
	/**
	 * a simple method used to create a path from the maze start positionto the goal position
	 */
	private void createPath(){
		int[][][]m = maze.getMaze();
		int x = maze.getStartPosition().getX();
		int y = maze.getStartPosition().getY();
		int z = maze.getStartPosition().getZ();
		
		if (x < maze.getGoalPosition().getX()){
			while (x < maze.getGoalPosition().getX()){
				m[x][y][z] = Maze3d.FREE;
				x++; }
		}
		else if (x > maze.getGoalPosition().getX()){
			while (x > maze.getGoalPosition().getX()){
				m[x][y][z] = Maze3d.FREE;
				x--; }	
		}
		else if (x == maze.getGoalPosition().getX())
			m[x][y][z] = Maze3d.FREE;
		
		if (y < maze.getGoalPosition().getY()){
			while (y < maze.getGoalPosition().getY()){
				m[x][y][z] = Maze3d.FREE;
				y++; }
		}
		else if (y > maze.getGoalPosition().getY()){
			while (y > maze.getGoalPosition().getY()){
				m[x][y][z] = Maze3d.FREE;
				y--; }	
		}
		else if (y == maze.getGoalPosition().getY())
			m[x][y][z] = Maze3d.FREE;
		
		if (z < maze.getGoalPosition().getZ()){
			while (z < maze.getGoalPosition().getZ()){
				m[x][y][z] = Maze3d.FREE;
				z++; }
		}
		else if (z > maze.getGoalPosition().getZ()){
			while (z > maze.getGoalPosition().getZ()){
				m[x][y][z] = Maze3d.FREE;
				z--; }
		}
		else if (z == maze.getGoalPosition().getZ())
			m[x][y][z] = Maze3d.FREE;
	}
}
