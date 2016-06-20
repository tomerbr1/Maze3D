package presenter.commands;

import model.Model;
import view.View;

/**
 * Defines the FileSize command.
 * @author Tomer
 *
 */
public class FileSize implements Command {

	private View view;
	private Model model;
	
	public FileSize(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void doCommand(String[] args) {

		if (args != null)
		{
			String fileName = args[0];
			model.fileSize(fileName);
		}
		else
		{
			view.display("(Presenter\\FileSize Cmd) Missing Parameters.\n");
			return;
		}
	}
}
