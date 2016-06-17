package view.CLI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import view.CommonView;

/**
 * Defines the MyCLIView view, a command line based interface.
 * @author Tomer
 * 
 */
public class CLI extends CommonView {

	/**
	 * CTOR to initiate the CLI View
	 * @param in - the input
	 * @param out - the output
	 */
	public CLI(BufferedReader in, Writer out) {
		super(in, out);
	}

	/**
	 * Start running the CLI in an independent thread.
	 */
	public void start(){
		//Displaying a welcome message
		displayMessage("Welcome to the 3D Maze CLI!\nType help to view available commands to use.\n\n");
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String line;
					do {
						out.write("Enter command: ");
						out.flush();
						line = in.readLine().toLowerCase();
						setChanged();
						notifyObservers(line);
						if (line.contains("generate_maze_3d") || line.contains("solve ")){
							Thread.sleep(50);
						}
					}
					while (!(line.equals("exit")));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}						
			}
		});
		thread.start();
		
	}

	@Override
	public void displayMessage(String string) {
		try {
			out.write(string);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void displayMessage(String[] strings) {
		for (String string : strings)
			try {
				out.write(string);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	/**
	 * Display a file sizes in bytes.
	 * @param fileName
	 */
	public void fileSize(String fileName) {			
		File file = new File(fileName);

		if (!file.exists() || !file.isFile()) {
			displayMessage("The file " + fileName + " doesn't exists.\n");
		}
		else
			displayMessage("The size of " + fileName + " file is " + file.length() + " bytes.\n");
	}

	/**
	 * Display all the files and folders in the entered path.
	 * @param path
	 */
	public void dirPath(String path) {

		try {
			File dir = new File(path);
			File[] files = dir.listFiles();

			if (files.length == 0)
				displayMessage("The directory is empty\n");
			else{
				for (File file : files)
					displayMessage(file.getName() + "\n");
			}
		} catch (NullPointerException e) {
			displayMessage("(MyCLIView\\dirPath) Invalid path.\n");
		}
	}

	/**
	 * Display the commands list for CLI client.
	 */
	public void displayHelp() {

		displayMessage("***********************\n");
		displayMessage("* Maze3d CLI Commands *\n");
		displayMessage("***********************\n");
		displayMessage("1) cross_section_x <cols_index> <maze_name> - Display a Crossed 2d maze for the given X index for a Maze3d instance.\n");
		displayMessage("2) cross_section_y <rows_index> <maze_name> - Same for Y index.\n");
		displayMessage("3) cross_section_z <depth_index> <maze_name> - Same for Z index.\n");
		displayMessage("4) dir <path> - Display all the files and folders in the given path.\n");
		displayMessage("5) display_maze <maze_name> - Display a 3D Maze.\n");
		displayMessage("6) display_solution <maze_name> - Display the solution for a 3D Maze.\n");
		displayMessage("7) file_size <name_of_file> - Display a file sizes in bytes.\n");
		displayMessage("8) generate_maze_3d <maze_name> <height> <width> <depth> - Generate a new 3d Maze according to the given parameters and chosen generating algorithm.\n");
		displayMessage("9) load_maze <name_of_file.txt> <maze_name> - Load a compressed Maze3d from file.\n");
		displayMessage("10) save_maze <maze_name> <name_of_file.txt> - Save and compress a Maze3d into file.\n");
		displayMessage("11) solve <maze_name> - Solve a 3D Maze using the chosen searching algorithm.\n");
		//displayMessage("\"save_zip_map\" <file_name>\n");
		//displayMessage("\"load_zip_map\" <file_name>\n");
		displayMessage("12) exit - Terminates the game.\n");	
	}
}
