package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import algorithms.demo.MazeAdapter;
import algorithms.search.BestFirstSearch;
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

	/** @param maze2d used to store a crossed section maze */
	private int[][] maze2d;

	/**
	 * Initiates the MyModel model.
	 * @param controller
	 */
	public MyModel() {
		super();
	}

	@Override
	public void generateMaze(String name, int rows, int cols, int depth) {

		//Generate the Maze3d by a submitting a new value-returning (Callable) thread to the threadPool.
		threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = new MyMaze3dGenerator().generate(cols, rows, depth);
				mazes.put(name, maze);
				message = "Maze " + name + " is ready.\n";
				setChanged();
				notifyObservers("display_message");
				return null;
			}
		});
	}

	@Override
	public void saveMaze(String name, String fileName) {

		//Check if the maze already exists in the mazes map
		if (!mazes.containsKey(name)) 
		{
			message = "The maze" + name + "does not exists\n";
			setChanged();
			notifyObservers("display_message");
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
			message = "Maze " + name + " is saved succesfully to the file: " + fileName +  "\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (FileNotFoundException e) {
			message = "File '" + fileName + "' Not Found\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IOException e) {
			message = "Input\\Output error.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}
	}

	@Override
	public void loadMaze(String fileName, String name) {	

		//Check if the given Maze3d name already exists and loaded.
		if (mazes.containsKey(name))
		{
			message = "Maze " + name + " Already exist and loaded succesfully\n";
			setChanged();
			notifyObservers("display_message");
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
			message = "Maze " + name + " has been loaded succesfully!\n";
			setChanged();
			notifyObservers("display_message");

		}catch(FileNotFoundException e){
			message = "File " + fileName + " Not Found.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IOException e) {
			message = "Input\\Output Error.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}		

	}

	@Override
	public void mazeSize(String name) {

		if (mazes.containsKey(name))
		{
			Maze3d maze = mazes.get(name);
			message = "The size of " + name + " maze in the memory is: " + maze.getCols()*maze.getRows()*maze.getDepth() + ".\n"; 
			setChanged();
			notifyObservers("display_message");
		}
		else
		{
			message = "(MyModel\\mazeSize) Maze " + name + " doesn't exists.\n";	
			setChanged();
			notifyObservers("display_message");
			return;
		}
	}

	@Override
	public void solveMaze(String name, String algorithm) {

		//Check if the given Maze3d name exists in the mazes map.
		if (mazes.containsKey(name))
		{
			//Solve the Maze3d by a new thread.
			threadPool.submit(new Callable<Solution>() {

				@Override
				public Solution call() {
					Maze3d maze = mazes.get(name);

					if (maze != null)
					{
						Solution solution;
						MazeAdapter adapter = new MazeAdapter(maze);

						//Check which search algorithm is in the user input.
						switch(algorithm){
						case "bfs":
							BestFirstSearch bfs = new BestFirstSearch();
							solution = bfs.search(adapter);
							solutions.put(name, solution);
							setChanged();
							notifyObservers("display_solution");
							break;
						case "dfs":
							DFS dfs = new DFS();
							solution = dfs.search(adapter);
							solutions.put(name, solution);
							setChanged();
							notifyObservers("display_solution");
							break;
						default:
							message = "The algorithm " + algorithm + " doesn't exists.\nPlease choose between \"bfs\" and \"dfs\".\n";
							setChanged();
							notifyObservers("display_message");
							break;
						}
					}
					return null;
				}
			});
		}
		else
		{
			message = "(MyModel\\solveMaze) Maze " + name + " not found.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}
	}
	@Override
	public void exit() {
		try {
			threadPool.shutdown();
			if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)){
				threadPool.shutdownNow();
				message = this.getClass().getName() + "'s threads forced to be terminated.\n";
				setChanged();
				notifyObservers("display_message");
			}
			else
			{
				message = this.getClass().getName() + "'s threads terminated successfully.\n";
				setChanged();
				notifyObservers("display_message");
			}
		} catch (InterruptedException e){
			message = "Interruption occurred during threads termination.\n";
			setChanged();
			notifyObservers("display_message");
			e.printStackTrace();
		}
	}

	@Override
	public Maze3d getMaze(String name){

		if (mazes.containsKey(name))
			return mazes.get(name);
		else
		{
			message = "Maze " + name + " doesn't exists.\n";
			setChanged();
			notifyObservers("display_message");
			return null;
		}
	}

	@Override
	public int[][] getMaze2d(){
		return maze2d;
	}

	@Override
	public void setMaze2d(int[][] maze2d){
		this.maze2d = maze2d;
	}

	@Override
	public Solution getSolution(String solution){
		return solutions.get(solution);
	}

	@Override
	public HashMap<String, Solution> getSolutions() {
		return solutions;
	}

	@Override
	public void generateCrossSectionByX(String index, String name) {
		Maze3d maze = getMaze(name);

		try {
			maze2d = maze.getCrossSectionByX(Integer.parseInt(index));
			setChanged();
			notifyObservers("display_maze2d");
			message = "Cross Section by X\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (NumberFormatException e) {
			message = "Invalid parameter.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IndexOutOfBoundsException e) {
			message = "Index is out of bound\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}		
	}

	@Override
	public void generateCrossSectionByY(String index, String name) {
		Maze3d maze = getMaze(name);

		try {
			maze2d = maze.getCrossSectionByY(Integer.parseInt(index));
			setChanged();
			notifyObservers("display_maze2d");
			message = "Cross Section by Y\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (NumberFormatException e) {
			message = "Invalid parameter.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IndexOutOfBoundsException e) {
			message = "Index is out of bound\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}	
	}

	@Override
	public void generateCrossSectionByZ(String index, String name) {
		Maze3d maze = getMaze(name);

		try {
			maze2d = maze.getCrossSectionByZ(Integer.parseInt(index));
			setChanged();
			notifyObservers("display_maze2d");
			message = "Cross Section by Z\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (NumberFormatException e) {
			message = "Invalid parameter.\n";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IndexOutOfBoundsException e) {
			message = "Index is out of bound\n";
			setChanged();
			notifyObservers("display_message");
			return;
		}
	}

}
