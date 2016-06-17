package presenter.commands;

import view.View;

/**
 * Defines the FileSize command.
 * @author Tomer
 *
 */
public class FileSize implements Command {

	private View view;
	
	public FileSize(View view) {
		this.view = view;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null)
		{
			String fileName = args[0];
			view.fileSize(fileName);
		}
		else
		{
			view.display("(Presenter\\FileSize Cmd) Missing Parameters.\n");
			return;
		}
	}
}
