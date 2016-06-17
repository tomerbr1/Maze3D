package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import presenter.commands.Command;
import presenter.commands.Dir;
import presenter.commands.DisplayCrossSectionByX;
import presenter.commands.DisplayCrossSectionByY;
import presenter.commands.DisplayCrossSectionByZ;
import presenter.commands.DisplayHelp;
import presenter.commands.DisplayMaze;
import presenter.commands.DisplaySolution;
import presenter.commands.Exit;
import presenter.commands.FileSize;
import presenter.commands.GenerateMaze;
import presenter.commands.LoadMaze;
import presenter.commands.MazeSize;
import presenter.commands.SaveMaze;
import presenter.commands.SolveMaze;
import view.View;

/**
 * Defines the Presenter class derived from the MVP architectural pattern.
 * The presenter acts upon the model and the view. It retrieves data from repositories (the model), and formats it for display in the view.
 * @author Tomer
 *
 */
public class Presenter implements Observer {

	private Model model;
	private View view;
	private Properties prop;
	private HashMap<String, Command> commands;

	/**
	 * Initiates the Presenter with a model, view and setting the commands map.
	 * @param model The model.
	 * @param view The View.
	 */
	public Presenter(Model model, View view, Properties prop) {
		this.model = model;
		this.view = view;
		this.prop = prop;
		setCommands();
	}

	/** Set the Presenter's model. */
	public void setModel(Model model) {
		this.model = model;
	}

	/** Set the Presenter's view. */
	public void setView(View view) {
		this.view = view;
	}

	/** Initiate the commands maps. */
	public void setCommands() {
		commands = new HashMap<String, Command>();

		//View's commands
		commands.put("generate_maze_3d", new GenerateMaze(view, model, prop));
		commands.put("display_maze", new DisplayMaze(view, model));
		commands.put("file_size", new FileSize(view));
		commands.put("maze_size", new MazeSize(view, model));
		commands.put("dir", new Dir(view));
		commands.put("load_maze", new LoadMaze(view, model));
		commands.put("save_maze", new SaveMaze(view, model));
		commands.put("solve", new SolveMaze(view, model, prop));
		commands.put("cross_section_by_x", new DisplayCrossSectionByX(view, model));
		commands.put("cross_section_by_y", new DisplayCrossSectionByY(view, model));
		commands.put("cross_section_by_z", new DisplayCrossSectionByZ(view, model));
		commands.put("display_solution", new DisplaySolution(view, model));
		commands.put("help", new DisplayHelp(view));
		commands.put("exit", new Exit(view, model));

		//Model's commands
		commands.put("display_solution", new DisplaySolution(view, model));
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == view) {
			String commandLine = (String)arg;
			String[] arr = commandLine.split(" ");
			String commandName = arr[0];

			String[] args = null;
			if (arr.length > 1)
			{
				args = new String[arr.length -1];
				System.arraycopy(arr, 1, args, 0, arr.length-1);
			}
			Command command = commands.get(commandName);
			if (command == null){
				view.displayMessage("Command not found\n");
				return;
			}
			command.doCommand(args);
		}
		else if (o == model) {
			view.displayMessage((String)arg);
		}
	}
}
