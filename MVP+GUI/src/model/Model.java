package model;

import java.util.HashMap;

import algorithms.search.Solution;
import maze.generators.Maze3d;

/**
 * Defines the Model interface derived from the MVP architectural pattern.
 * The model is an interface defining the data to be displayed or otherwise acted upon in the user interface.
 * @author Tomer
 *
 */
public interface Model {

	/**
	 * a Getter method used to let the Presenter get a String message from the model,
	 * when it being notified as an Observer by a display_message string from an observable model.
	 * @return a string message
	 */
	String getMessage();

	/**
	 * Generate a new 3d Maze according to the given parameters.
	 * @param name a string used to identify the maze in the mazes HashMap.
	 * @param rows the X dimension.
	 * @param cols the Y dimension. 
	 * @param depth the Z dimension.
	 */
	void generateMaze(String name, int rows, int cols, int depth);

	/**
	 * Save and compress a Maze3d into file.
	 * @param name the Maze3d name in the mazes HashMap.
	 * @param fileName the name of the intended file.
	 */
	void saveMaze(String name, String fileName);

	/**
	 * Load a compressed Maze3d from file.
	 * @param fileName the Maze3d 
	 * @param name
	 */
	void loadMaze(String fileName, String name);

	/**
	 * GEt the memory size of a Maze3d instance and display it.
	 * @param name the Maze3d name in the mazes HashMap.
	 */
	void mazeSize(String name);

	/**
	 * Solve a Maze3d using a searching algorithm.
	 * @param name the Maze3d name in the mazes HashMap.
	 * @param algorithm the Search algorithm name.
	 */
	void solveMaze(String name, String algorithm);

	/**
	 * Generates a crossed 2d maze for the given X index by a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void generateCrossSectionByX(String index, String name);

	/**
	 * Generates a Crossed 2d maze for the given Y index by a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void generateCrossSectionByY(String index, String name);

	/**
	 * Generates a Crossed 2d maze for the given Z index by a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void generateCrossSectionByZ(String index, String name);

	/**
	 * a getter method to return a Maze3d from the mazes HashMap.
	 * @param name the Maze3d name.
	 * @return the given Maze3d.
	 */
	Maze3d getMaze(String name);

	/**
	 * a getter method to return a solution to Maze3d from the solutions HashMap.
	 * @param solution the solution to the Maze3d.
	 * @return the given solution.
	 */
	Solution getSolution(String solution);

	/**
	 * a setter method, used to set a 2d instance of a Maze3d, used for the generateCrossSection methods.
	 * @param maze2d
	 */
	public void setMaze2d(int[][] maze2d);

	/**
	 * a getter method, used to get the 2d instance of a Maze3d, used for the display's crossSection methods.
	 * @return
	 */
	public int[][] getMaze2d();

	/**
	 * a getter method, used to get the solutions map.
	 * @return the solutions map.
	 */
	public HashMap<String, Solution> getSolutions();

	/**
	 * Exiting method, used to terminate all the files and threads in order.
	*/
	void exit();
}
