package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import algorithms.demo.MazeAdapter;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import maze.generators.Maze3d;
import maze.generators.MyMaze3dGenerator;

/**
 * Defines the MyModel model.
 * @author Tomer
 *
 */
public class MyModel extends CommonModel implements Model {

	/**
	 * Initiates the MyModel model.
	 * @param controller
	 */
	public MyModel() {
		super();
	}

	@Override
	public void generateMaze(String name, int rows, int cols, int depth) {
		//Generate the Maze3d by a new Thread.
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				Maze3d maze = new MyMaze3dGenerator().generate(cols, rows, depth);
				mazes.put(name, maze);
				controller.displayMessage("Maze " + name + " is ready.\n");
			}
		});
	}

	@Override
	public void saveMaze(String name, String fileName) {

		if (!mazes.containsKey(name)) 
		{
			controller.displayMessage("Maze" + name + "does not exist");
			return;
		}
		Maze3d maze = mazes.get(name);
		try {
			//Compress and save the given Maze3d.
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			int size = maze.toByteArray().length;
			int count = 0;
			while(size>255)
			{
				size = size-255;
				count++;
			}	
			out.write(size);
			out.write(count);
			out.write(maze.toByteArray());
			out.flush();
			out.close();
			controller.displayMessage("Maze " + name + " is saved succesfully to the file: " + fileName +  "\n");
		} catch (FileNotFoundException e) {
			controller.displayMessage("File '" + fileName + "' Not Found");
			return;
		} catch (IOException e) {
			controller.displayMessage("IOEXception");
			return;
		}
	}

	@Override
	public void loadMaze(String fileName, String name) {	

		//Check if the given Maze3d name already exists and loaded.
		if (mazes.containsKey(name))
		{
			controller.displayMessage("Maze " + name + " Already exist and loaded succesfully\n");
			return;
		}
		byte[] myarry = null;
		try{

			//Decompress the Maze3d from the given file name and load it in the mazes HashMap.
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			int size = in.read();
			int count = in.read();
			if(count > 0)
			{
				count = count*255;
				size = size+count;
			}
			myarry = new byte[size];
			in.read(myarry);
			in.close();	
			mazes.put(name, new Maze3d(myarry));
			controller.displayMessage("Maze " + name + " has been loaded succesfully!\n");

		}catch(FileNotFoundException e){
			controller.displayMessage("File Not Found.\n");
			return;
		} catch (IOException e) {
			controller.displayMessage("General Input\\Output Error.\n");
			return;
		}		

	}

	@Override
	public void mazeSize(String name) {

		if (mazes.containsKey(name))
		{
			Maze3d maze = mazes.get(name);
			controller.displayMessage("The size of " + name + " maze in the memory is: " + maze.getCols()*maze.getRows()*maze.getDepth() + ".\n");
		}
		else
			controller.displayMessage("(MyModel\\mazeSize) Maze " + name + " doesn't exists.\n");	
	}

	@Override
	public void solveMaze(String name, String algorithm) {

		if (mazes.containsKey(name))
		{
			//Solve the Maze3d by a new thread.
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					Maze3d maze = mazes.get(name);

					if (maze != null)
					{
						Solution solution;
						MazeAdapter adapter = new MazeAdapter(maze);
						boolean solvedFlag = true;

						//Check which search algorithm is in the user input.
						switch(algorithm){
						case "BestFirstSearch":
							BestFirstSearch bfs = new BestFirstSearch();
							solution = bfs.search(adapter);
							solutions.put(name, solution);
							break;
						case "BreadthFirstSearch":
							BreadthFirstSearch breadth = new BreadthFirstSearch();
							solution = breadth.search(adapter);	
							solutions.put(name, solution);
							break;
						case "DFS":
							DFS dfs = new DFS();
							solution = dfs.search(adapter);
							solutions.put(name, solution);
							break;
						default:
							controller.displayMessage("(MyModel\\solveMaze) The algorithm " + algorithm + " doesn't exists.\n");
							solvedFlag = false;
							break;
						}
						if (solvedFlag)
							controller.displayMessage(name + " maze is solved by " + algorithm + " successfully!\n");;
					}
				}
			});
		}
		else
			controller.displayMessage("(MyModel\\solveMaze) Maze " + name + " not found.\n");
	}

	@Override
	public void exit() {
		try {
			threadPool.shutdown();
			if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)){
				threadPool.shutdownNow();
				controller.displayMessage("Threads forced to be terminated.\n");
			}
			else
				controller.displayMessage("Threads terminated successfully.\n");
		} catch (InterruptedException e){
			controller.displayMessage("Interrupted occurd during threads termination.\n");
			e.printStackTrace();
		}
	}



}
