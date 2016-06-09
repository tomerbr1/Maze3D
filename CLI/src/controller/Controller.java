package controller;

import model.Model;
import view.View;

/**
 * Defines what every Controller type must implement.
 * @author Tomer
 *
 */
public interface Controller {
	
	/**
	 * Set the Controller's model.
	 * @param model
	 */
	public void setModel(Model model);
	
	/**
	 * Set the Controller's view.
	 * @param view
	 */
	public void setView(View view);
	
	/**
	 * Initiate the commands map.
	 */
	public void setCommands();	
	
	/**
	 * Displaying a given string using the View.
	 * @param string
	 */
	public void displayMessage(String string);
	
	/**
	 * Displaying the given strings using the View.
	 * @param strings
	 */
	public void displayMessage(String[] strings);
}
