package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the SolveMaze command.
 * @author Tomer
 *
 */
public class SolveMaze implements Command {

	private View view;
	private Model model;

	public SolveMaze(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null && args.length == 2)
		{
			String name = args[0];
			String algorithm = args[1];

			if (model.getMaze(name) != null)
				model.solveMaze(name, algorithm);
			else
			{
				view.displayMessage("(Presenter\\DisplayMaze Cmd) Maze " + name + " Maze not found.\n");
				return;
			}
		}
		else
			view.displayMessage("(Presenter\\Solve Maze Cmd) Missing Parameters.\n");		
	}
}
