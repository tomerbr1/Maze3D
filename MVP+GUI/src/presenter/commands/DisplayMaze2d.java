package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the DisplayMessage command.
 * @author Tomer
 *
 */
public class DisplayMaze2d implements Command {

	private View view;
	private Model model;
	
	public DisplayMaze2d(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		view.displayMaze2d(model.getMaze2d());
	}
}
