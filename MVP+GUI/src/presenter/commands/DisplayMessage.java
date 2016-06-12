package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the DisplayMessage command.
 * @author Tomer
 *
 */
public class DisplayMessage implements Command {

	private View view;
	private Model model;
	
	public DisplayMessage(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		String msg = model.getMessage();
		view.displayMessage(msg);
	}
}
