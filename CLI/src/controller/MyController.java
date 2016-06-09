package controller;

import java.util.HashMap;

import controller.commands.Command;
import controller.commands.Dir;
import controller.commands.DisplayCrossSectionByX;
import controller.commands.DisplayCrossSectionByY;
import controller.commands.DisplayCrossSectionByZ;
import controller.commands.DisplayMaze;
import controller.commands.DisplaySolution;
import controller.commands.Exit;
import controller.commands.FileSize;
import controller.commands.GenerateMaze;
import controller.commands.LoadMaze;
import controller.commands.MazeSize;
import controller.commands.SaveMaze;
import controller.commands.SolveMaze;
import model.Model;
import view.View;

/**
 * Defines the MyController controller.
 * @author Tomer
 *
 */
public class MyController extends CommonController implements Controller {

	/**
	 * @param commands The commands map.
	 */
	private HashMap<String,Command> commands;
	
	/**
	 * Initiates a new MyController by MVC pattern.
	 * @param model The model.
	 * @param view The View.
	 */
	public MyController(Model model, View view) {
		super(model, view);
		setCommands();
	}

	@Override
	public void setCommands() {
		this.commands = new HashMap<String,Command>();
		commands.put("dir", new Dir(view));
		commands.put("display", new DisplayMaze(view, model));
		commands.put("display_cross_section_by_x", new DisplayCrossSectionByX(view, model));
		commands.put("display_cross_section_by_y", new DisplayCrossSectionByY(view, model));
		commands.put("display_cross_section_by_z", new DisplayCrossSectionByZ(view, model));
		commands.put("exit", new Exit(view, model));
		commands.put("file_size", new FileSize(view));
		commands.put("maze_size", new MazeSize(view, model));		
		commands.put("generate_maze_3d", new GenerateMaze(view, model));
		commands.put("load", new LoadMaze(view, model));
		commands.put("save", new SaveMaze(view, model));
		commands.put("solve", new SolveMaze(view, model));
		commands.put("display_solution", new DisplaySolution(view, model));
		view.setCommands(commands);	
	}
}
