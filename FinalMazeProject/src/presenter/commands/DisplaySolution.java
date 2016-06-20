package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the DisplaySolution command.
 * @author Tomer
 *
 */
public class DisplaySolution implements Command {

	private View view;
	private Model model;

	public DisplaySolution(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null)
		{
			String solutionName = args[0];
			model.displaySolution(solutionName);
		}
		else
			view.display("(Presenter\\DisplaySolution Cmd) Missing Parameters.\n");
	}
}
