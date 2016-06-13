package maze.generators;

/**
 * The interface used to generate a 3d maze by an algorithm
 * @author Tomer
 *
 */
public interface Maze3dGenerator {
	Maze3d generate(int cols, int rows, int depth);
	String measureAlgorithmTime(int cols, int rows, int depth);
}
