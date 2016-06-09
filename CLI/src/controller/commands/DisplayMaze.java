package controller.commands;

import maze.generators.Maze3d;
import model.Model;
import view.View;

/**
 * Defines the DisplayMaze command.
 * @author Tomer
 *
 */
public class DisplayMaze implements Command {

	private View view;
	private Model model;

	public DisplayMaze(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args == null)
			view.displayMessage("(Controller\\DisplayMaze Cmd) Missing Parameters.\n");
		else
		{
			String name = args[0];
			Maze3d maze = model.getMaze(name);

			if (maze != null)
				view.displayMaze(maze);
			else
				view.displayMessage("(Controller\\DisplayMaze Cmd) " + name + " Maze not found.\n");
		}
	}
}
