package presenter.commands;

import view.View;
import model.Model;
import presenter.Properties;

/**
 * Defines the GenerateMaze command.
 * @author Tomer
 *
 */
public class GenerateMaze implements Command {

	private Model model;
	private View view;
	private Properties prop;

	
	public GenerateMaze(View view, Model model, Properties prop) {
		this.model = model;
		this.view = view;
		this.prop = prop;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null && args.length == 4) 
		{
			try 
			{
				String name = args[0];
				int cols = Integer.parseInt(args[1]);
				int rows = Integer.parseInt(args[2]);
				int depth = Integer.parseInt(args[3]);
				model.generateMaze(name, cols, rows, depth, prop.getGenerateAlgorithm());
			} 
			catch (NumberFormatException e) {
				view.display("(Presenter\\GenerateMaze Cmd) Invalid numbers entered.\n");
				return;
			}
		}
		else
		{
			view.display("(Presenter\\GenerateMaze Cmd) Missing Parameters.\n");
			return;
		}
	}
}
