package presenter.commands;

import view.View;

/**
 * Defines the Dir command.
 * @author Tomer
 *
 */
public class Dir implements Command {

	private View view;
	
	public Dir(View view) {
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		
		if (args == null || args.length != 1)
		{
			view.displayMessage("(Presenter\\Dir Cmd) Missing Parameters.\n");
			return;
		}
		else
		{
			String path = args[0];
			view.dirPath(path);
		}
	}
}
