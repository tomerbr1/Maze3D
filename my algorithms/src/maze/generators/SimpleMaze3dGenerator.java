package maze.generators;
import java.util.*;

/**
 * a simple maze 3d generator algorithm class
 * @author Tomer Brami & Yotam Levy
 *
 */
public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	Maze3d maze;
	/**
	 * the main method implemented to generate a maze by this generator
	 */
	@Override
	public Maze3d generate(int rows, int columns, int depth) {
		maze = new Maze3d(rows, columns, depth);
		Random rand = new Random();
		int x, y, z;
		
		maze.fillWithWalls();
		
		for(x=0; x < rows; x++){
			for (y = 0; y < columns; y++) {
				for(z=0; z < depth ; z++){
					maze.setValueInPosition(new Position(x, y, z), rand.nextInt(2)); // randomly initializing the values inside the maze to zero or one 
				}
			}
		}
		
		//building path,in order to provide at least one path
		x=0;
		y=0;
		z=0;
		Direction direction;
		
		maze.setStartPosition(new Position(0,0,0));
		
		while ((x<rows) && (y<columns) && (z<depth) && (x>=0) && (y>=0) && (z>=0))
		{
	
			maze.setHoleAt(x, y, z);
			
			direction=Direction.values()[rand.nextInt(Direction.values().length)];
			
			switch (direction) 
			{
			case UP:
				x++;
				break;
			case RIGHT:
				y++;
				break;
			case FORWARD:
				z++;
				break;
			default:
				x++;
				break;
			}
			
		}
		//initializing the exit of the maze
		if(x == rows)
		{
			maze.setGoalPosition(new Position(x-1, y, z));
		}
		else if(y==columns)
		{
			maze.setGoalPosition(new Position(x, y-1, z));
		}
		else if(z==depth)
		{
			maze.setGoalPosition(new Position(x, y, z-1));
		}
		
		return maze;

}
}
