package controller.commands;

import model.Model;
import view.View;

/**
 * Defines the LoadMaze command.
 * @author Tomer
 *
 */
public class LoadMaze implements Command {

	private View view;
	private Model model;

	public LoadMaze(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {

		if (args != null && args.length >= 2)
		{
			String fileName = args[0];
			String name = args[1];
			model.loadMaze(fileName,name);
		}
		else
			view.displayMessage("(Controller\\LoadMaze Cmd) Missing paramters.\n");
	}
}
