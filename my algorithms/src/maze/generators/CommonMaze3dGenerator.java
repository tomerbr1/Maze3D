package maze.generators;

/**
 * This class includes the common methods of every maze 3d generator
 * @author Tomer Brami & Yotam Levy
 *
 */
public abstract class CommonMaze3dGenerator implements Maze3dGenerator {
	
	int x, y, z;
	/**
	 * The generate method is an abstract method used to generate a maze by a maze generator algorithm
	 */
	@Override
	public abstract Maze3d generate(int rows, int cols, int depth);
	
	/**
	 * this method used to measure how long it takes generate a maze
	 */
	@Override
	public String measureAlgorithmTime(int rows, int cols, int depth){
		long begin = System.currentTimeMillis();
		generate(rows, cols, depth);
		long end = System.currentTimeMillis();		
		return("Maze generating time in milliseconds = "+(end-begin));
	}

}
