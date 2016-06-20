package presenter.commands;

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
		{
			view.display("(Presenter\\MazeSize Cmd) Missing Parameters.\n");
			return;
		}
		else
		{
			String name = args[0];
			model.mazeSize(name);
		}
	}
}
