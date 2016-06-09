package controller.commands;

import algorithms.search.Solution;
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

		if (args == null)
			view.displayMessage("(Controller\\DisplaySolution Cmd) Missing Parameters.\n");
		else
		{
			String solutionName = args[0];
			Solution solution = model.getSolution(solutionName);

			if (solution != null)
				view.displaySolution(solution);
			else
				view.displayMessage("(Controller\\DisplaySolution Cmd) " + solutionName + " solution not found.\n");
		}
	}
}
