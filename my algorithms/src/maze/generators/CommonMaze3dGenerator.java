package maze.generators;

/**
 * This class includes the common methods of every maze 3d generator
 * @author Tomer
 *
 */
public abstract class CommonMaze3dGenerator implements Maze3dGenerator {
	
	/**
	 * The generate method is an abstract method used to generate a maze by a maze generator algorithm
	 */
	@Override
	public abstract Maze3d generate(int cols, int rows, int depth);
	
	/**
	 * this method used to measure how long it takes generate a maze
	 */
	@Override
	public String measureAlgorithmTime(int cols, int rows, int depth){
		long begin = System.currentTimeMillis();
		generate(rows, cols, depth);
		long end = System.currentTimeMillis();		
		return("Maze generating time in milliseconds = "+(end-begin));
	}

}
