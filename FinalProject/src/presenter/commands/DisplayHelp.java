package presenter.commands;

import view.View;

/**
 * Defines the DisplayHelp command.
 * @author Yotam Levy & Tomer Brami
 *
 */
public class DisplayHelp implements Command {

	private View view;

	public DisplayHelp(View view) {
		this.view = view;
	}

	@Override
	public void doCommand(String[] args) {
		if (view.getClass().toString().equals("class view.CLI.MyView"))
		{
			view.display("***********************\n");
			view.display("* Maze3d CLI Commands *\n");
			view.display("***********************\n");
			view.display("1) cross_section_by_x <cols_index> <maze_name> - Display a Crossed 2d maze for the given X index for a Maze3d instance.\n");
			view.display("2) cross_section_by_y <rows_index> <maze_name> - Same for Y index.\n");
			view.display("3) cross_section_by_z <depth_index> <maze_name> - Same for Z index.\n");
			view.display("4) dir <path> - Display all the files and folders in the given path.\n");
			view.display("5) display_maze <maze_name> - Display a 3D Maze.\n");
			view.display("6) display_solution <maze_name> - Display the solution for a 3D Maze.\n");
			view.display("7) file_size <name_of_file> - Display a file sizes in bytes.\n");
			view.display("8) generate_maze_3d <maze_name> <height> <width> <depth> - Generate a new 3d Maze according to the given parameters and chosen generating algorithm.\n");
			view.display("9) load_maze <name_of_file.maz> <maze_name> - Load a compressed Maze3d from file.\n");
			view.display("10) save_maze <maze_name> <name_of_file.txt> - Save and compress a Maze3d into file.\n");
			view.display("11) solve <maze_name> - Solve a 3D Maze using the chosen searching algorithm.\n");
			view.display("12) save_zip_map <file_name>\n");
			view.display("13) load_zip_map <file_name>\n");
			view.display("14) exit - Terminates the game.\n");	
		}
	}
}
