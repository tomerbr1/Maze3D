package controller.commands;

import view.View;
import model.Model;

/**
 * Defines the GenerateMaze command.
 * @author Tomer
 *
 */
public class GenerateMaze implements Command {

	private Model model;
	private View view;

	public GenerateMaze(View view, Model model) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null && args.length >= 4) 
		{
			try 
			{
				String name = args[0];
				int cols = Integer.parseInt(args[1]);
				int rows = Integer.parseInt(args[2]);
				int depth = Integer.parseInt(args[3]);
				model.generateMaze(name, cols, rows, depth);
			} 
			catch (NumberFormatException e) {
				view.displayMessage("(Controller\\GenerateMaze Cmd) Invalid numbers entered.\n");
			}
		}
		else
			view.displayMessage("(Controller\\GenerateMaze Cmd) Missing Parameters.\n");		
	}
}
