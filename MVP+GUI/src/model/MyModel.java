package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	public void displayCrossSectionByX(String index, String name) {
		Maze3d maze = getMaze(name);

		try {
			maze2d = maze.getCrossSectionByX(Integer.parseInt(index));
			setChanged();
			notifyObservers(maze2dToString(maze2d));
			return;
		} catch (NumberFormatException e) {
			setChanged();
			notifyObservers("Invalid parameter.\n");
			return;
		} catch (IndexOutOfBoundsException e) {
			setChanged();
			notifyObservers("Index is out of bound\n");
			return;
		}		
	}

	@Override
	public void displayCrossSectionByY(String index, String name) {
		Maze3d maze = getMaze(name);

		try {
			maze2d = maze.getCrossSectionByY(Integer.parseInt(index));
			setChanged();
			notifyObservers(maze2dToString(maze2d));
			return;
		} catch (NumberFormatException e) {
			setChanged();
			notifyObservers("Invalid parameter.\n");
			return;
		} catch (IndexOutOfBoundsException e) {
			setChanged();
			notifyObservers("Index is out of bound\n");
			return;
		}	
	}

	@Override
	public void displayCrossSectionByZ(String index, String name) {
		Maze3d maze = getMaze(name);

		try {
			maze2d = maze.getCrossSectionByZ(Integer.parseInt(index));
			setChanged();
			notifyObservers(maze2dToString(maze2d));
			return;
		} catch (NumberFormatException e) {
			setChanged();
			notifyObservers("Invalid parameter.\n");
			return;
		} catch (IndexOutOfBoundsException e) {
			setChanged();
			notifyObservers("Index is out of bound\n");
			return;
		}
	}

	@Override
	public void displayMaze(String name) {
		if (mazes.containsKey(name)){
			Maze3d maze = mazes.get(name);
			setChanged();
			notifyObservers(maze.toString());
			return;
		}
		else {
			setChanged();
			notifyObservers("Maze " + name + " doesn't exists.\n");
			return;
		}

	}

	@Override
	public void displaySolution(String name) {

		if (!mazes.containsKey(name))
		{
			setChanged();
			notifyObservers("(MyModel\\solveMaze) Maze " + name + " not found.\n");
			return;
		}
		else
		{
			String mazeToStr = mazes.get(name).toString();

			if (solutions.containsKey(mazeToStr)) {
				Solution solution = solutions.get(mazeToStr);
				setChanged();
				notifyObservers(solution.toString());
				return;
			}
			else if (!solutions.containsKey(mazeToStr)){
				setChanged();
				notifyObservers("Solution for " + name + " doesn't exists. Use solve command first.\n");
			}
		}
	}

	@Override
	public void exit() {
		if (!threadPool.isTerminated())
		{
			try {
				threadPool.shutdown();
				if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)){
					threadPool.shutdownNow();
					setChanged();
					notifyObservers(this.getClass().getName() + "'s threads forced to be terminated.\n");
				}
				else
				{
					setChanged();
					notifyObservers(this.getClass().getName() + "'s threads terminated successfully.\n");
				}
			} catch (InterruptedException e){
				setChanged();
				notifyObservers("Interruption occurred during threads termination.\n");
				e.printStackTrace();
			}
		}
	}

	@Override
	public Maze3d getMaze(String name){

		if (mazes.containsKey(name))
			return mazes.get(name);
		else
		{
			setChanged();
			notifyObservers("Maze " + name + " doesn't exists.\n");
			return null;
		}
	}

	@Override
	public void generateMaze(String name, int rows, int cols, int depth) {

		//Generate the Maze3d by a submitting a new value-returning (Callable) thread to the threadPool.
		threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = new MyMaze3dGenerator().generate(cols, rows, depth);
				mazes.put(name, maze);
				setChanged();
				notifyObservers("Maze " + name + " is ready.\n");
				return null;
			}
		});
	}

	@Override
	public void loadMaze(String fileName, String name) {	

		//Check if the given Maze3d name already exists and loaded.
		if (mazes.containsKey(name))
		{
			setChanged();
			notifyObservers("Maze " + name + " Already exist and loaded succesfully\n");
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
			setChanged();
			notifyObservers("Maze " + name + " has been loaded succesfully!\n");

		}catch(FileNotFoundException e){
			setChanged();
			notifyObservers("File " + fileName + " Not Found.\n");
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers("Input\\Output Error.\n");
			return;
		}		
	}

	/**
	 * an inner method, used to convert a crossed-sectioned 2d maze into string.
	 * @param Maze2d
	 * @return
	 */
	public String maze2dToString(int[][] Maze2d) {
		StringBuilder sb = new StringBuilder();

		for (int[] i : Maze2d){
			for (int j : i){
				sb.append(j);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public void mazeSize(String name) {

		if (mazes.containsKey(name))
		{
			Maze3d maze = mazes.get(name);
			setChanged();
			notifyObservers("The size of " + name + " maze in the memory is: " + maze.getCols()*maze.getRows()*maze.getDepth() + ".\n");
		}
		else
		{
			setChanged();
			notifyObservers("(MyModel\\mazeSize) Maze " + name + " doesn't exists.\n");
			return;
		}
	}

	@Override
	public void saveMaze(String name, String fileName) {

		//Check if the maze already exists in the mazes map
		if (!mazes.containsKey(name)) 
		{
			setChanged();
			notifyObservers("The maze" + name + "does not exists\n");
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
			setChanged();
			notifyObservers("Maze " + name + " is saved succesfully to the file: " + fileName +  "\n");
			return;
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers("File '" + fileName + "' Not Found\n");
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers("Input\\Output error.\n");
			return;
		}
	}

	@Override
	public void solveMaze(String name, String algorithm) {

		if (!mazes.containsKey(name)){
			setChanged();
			notifyObservers("(MyModel\\solveMaze) Maze " + name + " not found.\n");
			return;
		}

		String mazeToStr = mazes.get(name).toString();
		//check if the given Maze3d already solved, to optimize caching and calculating time

		if (solutions.containsKey(mazeToStr)) {
			setChanged();
			notifyObservers(name + " maze is already solved.\n");
			return;
		}
		else if (!solutions.containsKey(mazeToStr))
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
							solutions.put(maze.toString(), solution);
							setChanged();
							notifyObservers(name + " maze is solved by " + algorithm + " successfully!\n");
							break;
						case "dfs":
							DFS dfs = new DFS();
							solution = dfs.search(adapter);
							solutions.put(maze.toString(), solution);
							setChanged();
							notifyObservers(name + " maze is solved by " + algorithm + " successfully!\n");
							break;
						default:
							setChanged();
							notifyObservers("The algorithm " + algorithm + " doesn't exists.\nPlease choose between \"bfs\" and \"dfs\".\n");
							break;
						}
					}
					return null;
				}
			});
		}
	}

}