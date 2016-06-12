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
import presenter.commands.DisplayMaze2d;
import presenter.commands.DisplayMessage;
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
	private HashMap<String, Command> viewCommands;
	private HashMap<String, Command> modelCommands;

	/**
	 * Initiates the Presenter with a model, view and setting the commands map.
	 * @param model The model.
	 * @param view The View.
	 */
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
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

	/** Display a given string using the View. */
	public void displayMessage(String[] strings){
		view.displayMessage(strings);
	}

	/** Displaying the given strings using the View. */
	public void displayMessage(String string){
		view.displayMessage(string);
	}

	/** Initiate the view and model commands maps. */
	public void setCommands() {
		viewCommands = new HashMap<String, Command>();
		modelCommands = new HashMap<String, Command>();

		viewCommands.put("generate_maze_3d", new GenerateMaze(view, model));
		viewCommands.put("display_maze", new DisplayMaze(view, model));
		viewCommands.put("file_size", new FileSize(view));
		viewCommands.put("maze_size", new MazeSize(view, model));
		viewCommands.put("dir", new Dir(view));
		viewCommands.put("load_maze", new LoadMaze(view, model));
		viewCommands.put("save_maze", new SaveMaze(view, model));
		viewCommands.put("solve", new SolveMaze(view, model));
		viewCommands.put("cross_section_by_x", new DisplayCrossSectionByX(view, model));
		viewCommands.put("cross_section_by_y", new DisplayCrossSectionByY(view, model));
		viewCommands.put("cross_section_by_z", new DisplayCrossSectionByZ(view, model));
		viewCommands.put("help", new DisplayHelp(view));
		viewCommands.put("exit", new Exit(view, model));
		// viewCommands.put("save_zip_map", new SaveZipMap(model, view));
		// viewCommands.put("load_zip_map", new LoadZipMap(model, view));

		modelCommands.put("display_solution", new DisplaySolution(view, model));
		modelCommands.put("display_message", new DisplayMessage(view, model));
		modelCommands.put("display_maze2d", new DisplayMaze2d(view, model));
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == model) {
			String commandName = (String)arg;
			Command command = modelCommands.get(commandName);
			command.doCommand(null);
		}
		else if (o == view) {
			String commandLine = (String)arg;
			String[] arr = commandLine.split(" ");
			String commandName = arr[0];

			String[] args = null;
			if (arr.length > 1)
			{
				args = new String[arr.length -1];
				System.arraycopy(arr, 1, args, 0, arr.length-1);
			}
			Command command = viewCommands.get(commandName);
			if (command == null){
				view.displayMessage("Command not found\n");
				return;
			}
			command.doCommand(args);
			view.displayMessage("\n");
		}	
	}
}
