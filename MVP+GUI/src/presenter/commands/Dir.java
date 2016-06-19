package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the Dir command.
 * @author Tomer
 *
 */
public class Dir implements Command {

	private View view;
	private Model model;
	
	public Dir(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void doCommand(String[] args) {
		
		if (args == null || args.length != 1)
		{
			view.display("(Presenter\\Dir Cmd) Missing Parameters.\n");
			return;
		}
		else
		{
			String path = args[0];
			model.dirPath(path);
		}
	}
}
