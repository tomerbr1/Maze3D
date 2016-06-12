package model;

import maze.generators.Maze3d;

/**
 * Defines the Model interface derived from the MVP architectural pattern.
 * The model is an interface defining the data to be displayed or otherwise acted upon in the user interface.
 * @author Tomer
 *
 */
public interface Model {

	/**
	 * Display a Maze3d to the output.
	 * @param maze the Maze3d instance.
	 */
	void displayMaze(String name);
	
	/**
	 * Display the solution steps for a Maze3d instance.
	 * @param name the solved 3D maze name.
	 */
	void displaySolution(String name);
	
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
	 * Get the memory size of a Maze3d instance and display it.
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
	void displayCrossSectionByX(String index, String name);

	/**
	 * Generates a Crossed 2d maze for the given Y index by a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void displayCrossSectionByY(String index, String name);

	/**
	 * Generates a Crossed 2d maze for the given Z index by a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void displayCrossSectionByZ(String index, String name);

	/**
	 * an inner getter method, used by the model to return a Maze3d from the mazes HashMap.
	 * @param name the Maze3d name.
	 * @return the given Maze3d.
	 */
	Maze3d getMaze(String name);

	/**
	 * Exiting method, used to terminate all the files and threads in order.
	*/
	void exit();
}
