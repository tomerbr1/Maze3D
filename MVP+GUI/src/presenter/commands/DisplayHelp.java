package presenter.commands;

import view.View;

/**
 * Defines the DisplayHelp command.
 * @author Tomer
 *
 */
public class DisplayHelp implements Command {

	private View view;

	public DisplayHelp(View view) {
		this.view = view;
	}

	@Override
	public void doCommand(String[] args) {
		view.displayHelp();
	}
}
