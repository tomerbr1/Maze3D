package maze.generators;
import java.util.ArrayList;
import java.util.Random;

/**
 * a 3d maze generator class
 * @author Tomer
 *
 */
public class MyMaze3dGenerator extends CommonMaze3dGenerator{

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
		if (cols >= 1 && rows >= 1 && depth >= 1)
		{
		maze = new Maze3d(cols, rows, depth);
		
		initMaze();
		
		// Choose random start position			
		maze.setStartPosition(choosePosition());
		maze.setGoalPosition(choosePosition());
		
		DFS(maze.getStartPosition());				
		return maze;
		}
		else
			System.out.println("Numbers format exception!\n");
			return null;
	}
	
	/**
	 * this method initializing maze by set all it's cells as wall
	 */
	private void initMaze(){
		int[][][] m = maze.getMaze();
		for (int z = 0; z < maze.getDepth(); z++) {
			for (int y = 0; y < maze.getRows(); y++) {
				for (int x = 0; x < maze.getCols(); x++) {
					m[x][y][z] = Maze3d.WALL;
				}
			}
		}
	}

	/**
	 * a method intended to return a random position inside the maze
	 * @return a random position
	 */
	private Position choosePosition() {		
		int x = rand.nextInt(maze.getCols());
		while (x % 2 == 1)
			x = rand.nextInt(maze.getCols());
		
		int y = rand.nextInt(maze.getRows());
		while (y % 2 == 1)
			y = rand.nextInt(maze.getRows());
		
		int z = rand.nextInt(maze.getDepth());
		while (z % 2 == 1)
			z = rand.nextInt(maze.getDepth());
		
		return(new Position(x, y, z));
	}
	
	/**
	 * this method returns all the possible directions as a directions array
	 * @param pos a specific position
	 * @return a directions array
	 */
	private ArrayList<Direction> getPossibleDirections(Position pos) {
		ArrayList<Direction> dirs = new ArrayList<Direction>();
		int[][] []m = maze.getMaze();
		
		// Check up neighbor
		if (pos.x + 2 < maze.getCols() && m[pos.x+2][pos.y][pos.z] == Maze3d.WALL)
			dirs.add(Direction.UP);
		
		// Check down neighbor
		if (pos.x - 2 >= 0 && m[pos.x-2][pos.y][pos.z] == Maze3d.WALL)
			dirs.add(Direction.DOWN);

		// Check right neighbor
		if (pos.y + 2 < maze.getRows() && m[pos.x][pos.y + 2][pos.z] == Maze3d.WALL)
			dirs.add(Direction.RIGHT);

		// Check left neighbor
		if (pos.y - 2 >= 0 && m[pos.x][pos.y - 2][pos.z] == Maze3d.WALL)
			dirs.add(Direction.LEFT);

		// Check forward neighbor
		if (pos.z + 2 < maze.getDepth() && m[pos.x][pos.y ][pos.z+2] == Maze3d.WALL)
			dirs.add(Direction.FORWARD);
				
		// Check backward neighbor
		if (pos.z - 2 >= 0 && m[pos.x][pos.y][pos.z-2] == Maze3d.WALL)
			dirs.add(Direction.BACKWARD);
			
		return dirs;
	}
	
	/**
	 * a DFS algorithm used to generate a maze 
	 * @param pos a position used as the start position
	 */
	private void DFS(Position pos) {
		ArrayList<Direction> dirs = getPossibleDirections(pos);
		if (dirs.size() == 0)
			return;
		
		for (int i = 0; i < dirs.size(); i++) {
		
			// Choose random direction
			int idx = rand.nextInt(dirs.size());
			Direction dir = dirs.get(idx);
			dirs.remove(idx);
			int[][][] m = maze.getMaze();
			
			switch (dir) {
			case UP:
				m[pos.x+1][pos.y][pos.z] = Maze3d.FREE;	
				m[pos.x+2][pos.y][pos.z] = Maze3d.FREE;	
				DFS(new Position(pos.x+2, pos.y,pos.z));
				break;
			case DOWN:
				m[pos.x-1][pos.y][pos.z] = Maze3d.FREE;	
				m[pos.x-2][pos.y][pos.z] = Maze3d.FREE;	
				DFS(new Position(pos.x-2, pos.y,pos.z));
				break;
			case RIGHT:
				m[pos.x][pos.y+1][pos.z] = Maze3d.FREE;		
				m[pos.x][pos.y+2][pos.z] = Maze3d.FREE;	
				DFS(new Position(pos.x, pos.y+2,pos.z));
				break;	
			case LEFT:
				m[pos.x][pos.y-1][pos.z] = Maze3d.FREE;	
				m[pos.x][pos.y-2][pos.z] = Maze3d.FREE;	
				DFS(new Position(pos.x, pos.y-2,pos.z));
				break;	
			case BACKWARD:
				m[pos.x][pos.y][pos.z-1] = Maze3d.FREE;		
				m[pos.x][pos.y][pos.z-2] = Maze3d.FREE;	
				DFS(new Position(pos.x, pos.y,pos.z-2));
				break;
			case FORWARD:
				m[pos.x][pos.y][pos.z+1] = Maze3d.FREE;	
				m[pos.x][pos.y][pos.z+2] = Maze3d.FREE;	
				DFS(new Position(pos.x, pos.y,pos.z+2));
				break;
			}
		}
	}	

}