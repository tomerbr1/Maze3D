package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the SaveMaze command.
 * @author Tomer
 *
 */
public class SaveMaze implements Command {

	private View view;
	private Model model;
	
	public SaveMaze(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {
		
		if (args != null && args.length == 2)
		{
			String name = args[0];
			String fileName = args[1];
			if (fileName.equals("null.maz")){
				view.display("Maze doesn't exists. Please generate or load one first.\n");
				return;
			}
			model.saveMaze(name, fileName);
		}
		else
		{
			view.display("(Presenter\\Save Maze Cmd) Missing Paramters.\n");
			return;
		}
	}
}
