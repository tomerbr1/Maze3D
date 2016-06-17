package presenter.commands;

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
			view.display("(Presenter\\DisplayMaze Cmd) Missing Parameters.\n");
		else
		{
			String name = args[0];
			model.displayMaze(name);
		}
	}
}
