package view;

import algorithms.search.Solution;
import maze.generators.Maze3d;
import presenter.Presenter;

/**
 * Defines the View interface derived from the MVP architectural pattern.
 * The view is a passive interface that displays data (the model) and routes user commands (events) to the presenter to act upon that data.
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
	 * Setting the presenter to the view interface.
	 * @param presenter
	 */
	void setPresenter(Presenter presenter);
	
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
	 * Display a Maze3d to the output.
	 * @param maze the Maze3d instance.
	 */
	void displayMaze(Maze3d maze);
	
	/**
	 * Display a Crossed 2d maze for the given coordinate of a Maze3d instance.
	 * @param maze2d the crossed 2d maze
	 */
	void displayMaze2d(int[][] maze2d);
	
	/**
	 * Display the solution steps for a Maze3d instance.
	 * @param solution the solution instance from the solutions map.
	 */
	void displaySolution(Solution solution);
	
	/**
	 * Display the commands list for CLI client.
	 */
	void displayHelp();
}
