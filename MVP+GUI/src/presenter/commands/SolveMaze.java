package presenter.commands;

import model.Model;
import presenter.Properties;
import view.View;

/**
 * Defines the SolveMaze command.
 * @author Tomer
 *
 */
public class SolveMaze implements Command {

	private View view;
	private Model model;
	private Properties prop;

	public SolveMaze(View view, Model model, Properties prop) {
		this.view = view;
		this.model = model;
		this.prop = prop;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null && args.length == 1)
		{
			String name = args[0];

			model.solveMaze(name, prop.getSearchAlgorithm());
		}
		else
			view.display("(Presenter\\Solve Maze Cmd) Missing Parameters.\n");		
	}
}
