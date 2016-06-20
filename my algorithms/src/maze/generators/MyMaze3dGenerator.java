package maze.generators;
import java.util.ArrayList;
import java.util.Random;

/**
 * a 3d maze generator class
 * @author Tomer Brami
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
	public Maze3d generate(int rows, int cols, int depth) {
		if (rows >= 1 && cols >= 1 && depth >= 1)
		{
		maze = new Maze3d(rows, cols, depth);
		
		maze.fillWithWalls();
		
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
	 * a method intended to return a random position inside the maze
	 * @return a random position
	 */
	private Position choosePosition() {		
		int x = rand.nextInt(maze.getRows());
		while (x % 2 == 1)
			x = rand.nextInt(maze.getRows());
		
		int y = rand.nextInt(maze.getColumns());
		while (y % 2 == 1)
			y = rand.nextInt(maze.getColumns());
		
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
		int[][][]m = maze.getMaze();
		
		// Check up neighbor
		if (pos.getX() + 2 < maze.getRows() && m[pos.getX()+2][pos.getY()][pos.getZ()] == Maze3d.WALL)
			dirs.add(Direction.UP);
		
		// Check down neighbor
		if (pos.getX() - 2 >= 0 && m[pos.getX()-2][pos.getY()][pos.getZ()] == Maze3d.WALL)
			dirs.add(Direction.DOWN);

		// Check right neighbor
		if (pos.getY() + 2 < maze.getColumns() && m[pos.getX()][pos.getY() + 2][pos.getZ()] == Maze3d.WALL)
			dirs.add(Direction.RIGHT);

		// Check left neighbor
		if (pos.getY() - 2 >= 0 && m[pos.getX()][pos.getY() - 2][pos.getZ()] == Maze3d.WALL)
			dirs.add(Direction.LEFT);

		// Check forward neighbor
		if (pos.getZ() + 2 < maze.getDepth() && m[pos.getX()][pos.getY() ][pos.getZ()+2] == Maze3d.WALL)
			dirs.add(Direction.FORWARD);
				
		// Check backward neighbor
		if (pos.getZ() - 2 >= 0 && m[pos.getX()][pos.getY()][pos.getZ()-2] == Maze3d.WALL)
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
				m[pos.getX()+1][pos.getY()][pos.getZ()] = Maze3d.FREE;	
				m[pos.getX()+2][pos.getY()][pos.getZ()] = Maze3d.FREE;	
				DFS(new Position(pos.getX()+2, pos.getY(),pos.getZ()));
				break;
			case DOWN:
				m[pos.getX()-1][pos.getY()][pos.getZ()] = Maze3d.FREE;	
				m[pos.getX()-2][pos.getY()][pos.getZ()] = Maze3d.FREE;	
				DFS(new Position(pos.getX()-2, pos.getY(),pos.getZ()));
				break;
			case RIGHT:
				m[pos.getX()][pos.getY()+1][pos.getZ()] = Maze3d.FREE;		
				m[pos.getX()][pos.getY()+2][pos.getZ()] = Maze3d.FREE;	
				DFS(new Position(pos.getX(), pos.getY()+2,pos.getZ()));
				break;	
			case LEFT:
				m[pos.getX()][pos.getY()-1][pos.getZ()] = Maze3d.FREE;	
				m[pos.getX()][pos.getY()-2][pos.getZ()] = Maze3d.FREE;	
				DFS(new Position(pos.getX(), pos.getY()-2,pos.getZ()));
				break;	
			case BACKWARD:
				m[pos.getX()][pos.getY()][pos.getZ()-1] = Maze3d.FREE;		
				m[pos.getX()][pos.getY()][pos.getZ()-2] = Maze3d.FREE;	
				DFS(new Position(pos.getX(), pos.getY(),pos.getZ()-2));
				break;
			case FORWARD:
				m[pos.getX()][pos.getY()][pos.getZ()+1] = Maze3d.FREE;	
				m[pos.getX()][pos.getY()][pos.getZ()+2] = Maze3d.FREE;	
				DFS(new Position(pos.getX(), pos.getY(),pos.getZ()+2));
				break;
			}
		}
	}	

}