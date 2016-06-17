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
	 * Starting the user interface by a basic thread.
	 */
	void start();
	
	/**
	 * Display all the files and folders in the entered path.
	 * @param path
	 */
	public void dirPath(String path);

	/**
	 * Display the commands list for CLI client.
	 */
	public void displayHelp();

	/**
	 * Displaying a string to the output.
	 * @param string
	 */
	void display(String string);
	
	/**
	 * Displaying strings to the output.
	 * @param strings
	 */
	void display(String[] strings);

	/**
	 * Display a file sizes in bytes.
	 * @param fileName
	 */
	public void fileSize(String fileName);
	
	/**
	 * Setting the presenter to the view interface.
	 * @param presenter
	 */
	void setPresenter(Presenter presenter);
	
	/**
	 * Display all the files and folders in the given path.
	 * @param path
	 */

}
