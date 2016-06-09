package model;

import algorithms.search.Solution;
import maze.generators.Maze3d;

/**
 * Defines what every Model type must implement.
 * @author Tomer
 *
 */
public interface Model {
	
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
	 * Display the memory size of a Maze3d.
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
	 * a Getter method to return a Maze3d from the mazes HashMap.
	 * @param name the Maze3d name.
	 * @return the given Maze3d.
	 */
	Maze3d getMaze(String name);
	
	/**
	 * a Getter method to return a solution to Maze3d from the solutions HashMap.
	 * @param solution the solution to the Maze3d.
	 * @return the given solution.
	 */
	Solution getSolution(String solution);
	
	/**
	 * Exiting method, used to terminate all the files and threads in order.
	 */
	void exit();

}
