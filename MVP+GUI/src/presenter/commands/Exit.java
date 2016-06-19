package presenter.commands;

import model.Model;

/**
 * Defines the exit command.
 * @author Tomer
 *
 */
public class Exit implements Command {

	private Model model;
	
	public Exit(Model model) {
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		model.exit();
	}
}
