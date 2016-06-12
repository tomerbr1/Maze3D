package view;

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
	 * Display the commands list for CLI client.
	 */
	void displayHelp();
}
