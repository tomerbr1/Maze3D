package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the DisplayCrossSectionByX command.
 * @author Tomer
 *
 */
public class DisplayCrossSectionByX implements Command {

	private View view;
	private Model model;

	public DisplayCrossSectionByX(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args == null || (args[0].contains("[a-zA-Z]+") == false && args[0].length() > 2 || args[1] == null))
		{
			view.display("(Presenter\\DisplayCrossSectionByX Cmd) Missing Parameters.\n");
			return;
		}
		else
		{
			String index = args[0];
			String name = args[1];

			model.displayCrossSectionByX(index, name);
		}
	}
}
