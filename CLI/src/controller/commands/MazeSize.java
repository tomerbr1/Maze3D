package controller.commands;

import maze.generators.Maze3d;
import model.Model;
import view.View;

/**
 * Defines the MazeSize command.
 * @author Tomer
 *
 */
public class MazeSize implements Command {

	private View view;
	private Model model;

	public MazeSize(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args == null)
			view.displayMessage("(Controller\\MazeSize Cmd) Missing Parameters.\n");
		else
		{
			String name = args[0];
			Maze3d maze = model.getMaze(name);

			if (maze != null)
				view.mazeSize(maze, name);
			else
				view.displayMessage("(Controller\\MazeSize Cmd) " + name + " Maze not found.\n");
		}
	}
}
