package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the DisplayCrossSectionByX command.
 * @author Yotam Levy & Tomer Brami
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

		if (args == null || (args[0].contains("[a-zA-Z]+") == true || args.length < 2))
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
