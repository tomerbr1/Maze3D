package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the exit command.
 * @author Tomer
 *
 */
public class Exit implements Command {

	private View view;
	private Model model;
	
	public Exit(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		view.exit();
		model.exit();
	}
}
