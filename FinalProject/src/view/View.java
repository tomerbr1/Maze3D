package view;

import presenter.Properties;

/**
 * Defines the View interface derived from the MVP architectural pattern.
 * The view is a passive interface that displays data (the model) and routes user commands (events) to the presenter to act upon that data.
 * @author Yotam Levy & Tomer Brami
 *
 */
public interface View {
	
	/**
	 * Starting the user interface by a basic thread.
	 */
	void start();

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
	 * Reference to the properties object, in order to display the chosen algorithms easily.
	 * @param prop
	 */
	void setProperties(Properties prop);
}
