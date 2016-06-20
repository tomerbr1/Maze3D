package presenter.commands;

import maze.generators.Maze3d;
import model.Model;
import view.View;
import view.GUI.GUI;

/**
 * Defines the DisplayMaze command.
 * @author Yotam Levy & Tomer Brami
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
			view.display("(Presenter\\DisplayMaze Cmd) Missing Parameters.\n");
		else
		{
			String name = args[0];
			Maze3d maze = model.getMaze(name);

			if (maze == null)
				return; //message about it already displayed
			else
			{
				if (view instanceof GUI)
				{
					((GUI)view).setMaze3d(maze);
				}
				else
					model.displayMaze(name);
			}
		}
	}
}