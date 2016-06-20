package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the DisplayCrossSectionByY command.
 * @author Yotam Levy & Tomer Brami
 *
 */
public class DisplayCrossSectionByY implements Command {

	private View view;
	private Model model;

	public DisplayCrossSectionByY(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args == null || (args[0].contains("[a-zA-Z]+") == true || args.length < 2))
		{
			view.display("(Presenter\\DisplayCrossSectionByY Cmd) Missing Parameters.\n");
			return;
		}
		else
		{
			String index = args[0];
			String name = args[1];

			model.displayCrossSectionByY(index, name);
		}
	}
}
