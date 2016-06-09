package view;

import java.util.HashMap;

import algorithms.search.Solution;
import controller.Controller;
import controller.commands.Command;
import maze.generators.Maze3d;

/**
 * Defines what every View type must implement.
 * @author Tomer
 *
 */
public interface View {
	
	/**
	 * Starting the user interface.
	 */
	void start();
	
	/**
	 * Displaying a string to the output.
	 * @param string
	 */
	void displayMessage(String string);
	
	/**
	 * Displaying strings to the output.
	 * @param strings
	 */
	void displayMessage(String[] strings);
	
	/**
	 * Setting the commands map to the user interface.
	 * @param commands
	 */
	void setCommands(HashMap<String,Command> commands);
	
	/**
	 * Setting the controller to the user interface.
	 * @param controller
	 */
	void setController(Controller controller);
	
	/**
	 * Closing the user interface.
	 */
	void exit();
	
	/**
	 * Display all the files and folders in the given path.
	 * @param path
	 */
	void dirPath(String path);
	
	/**
	 * Display a file sizes in bytes.
	 * @param fileName
	 */
	void fileSize(String fileName);
	
	/**
	 * Display the memory size of a Maze3d.
	 * @param maze the Maze3d instance.
	 * @param name the given Maze3d's name.
	 */
	void mazeSize(Maze3d maze, String name);
	
	/**
	 * Display a Maze3d to the output.
	 * @param maze the Maze3d instance.
	 */
	void displayMaze(Maze3d maze);
	
	/**
	 * Display a Crossed 2d maze for the given X index for a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void displayCrossSectionByX(int index, Maze3d maze);
	
	/**
	 * Display a Crossed 2d maze for the given Y index for a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void displayCrossSectionByY(int index, Maze3d maze);
	
	/**
	 * Display a Crossed 2d maze for the given Z index for a Maze3d instance.
	 * @param index
	 * @param maze
	 */
	void displayCrossSectionByZ(int index, Maze3d maze);
	
	/**
	 * Display the solution steps for a Maze3d instance.
	 * @param solution the solution instance from the solutions map.
	 */
	void displaySolution(Solution solution);
}
