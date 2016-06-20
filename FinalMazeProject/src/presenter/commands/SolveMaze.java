package presenter.commands;

import algorithms.demo.MazeAdapter;
import algorithms.search.BFS;
import algorithms.search.Solution;
import maze.generators.Maze3d;
import model.Model;
import presenter.Properties;
import view.View;
import view.GUI.GUI;

/**
 * Defines the SolveMaze command.
 * @author Yotam Levy & Tomer Brami
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
		if (args==null||args.length !=1)
		{
			view.display("Incorrect number of args, please type a valid command.\n");
			return;
		}
		String name = args[0];
		String algorithm = prop.getSearchAlgorithm();
		Maze3d maze = model.getMaze(name);
		if (maze == null){
			view.display("Maze " + "'"+ name +"'" + " not found\n");
		}else
			if(view instanceof GUI){
				MazeAdapter mAdapter = new MazeAdapter(maze);
				BFS bfs = new BFS(); 
				Solution sol = bfs.search(mAdapter);
				((GUI) view).setSolution(sol);
			}
			else
			model.solveMaze(name, algorithm);	
	}
}
