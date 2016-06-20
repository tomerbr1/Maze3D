package maze.generators;

/**
 * The interface used to generate a 3d maze by an algorithm
 * @author Tomer Brami & Yotam Levy
 *
 */
public interface Maze3dGenerator {
	Maze3d generate(int rows, int cols, int depth);
	String measureAlgorithmTime(int rows, int cols, int depth);
}
