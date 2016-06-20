package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeAdapter;
import algorithms.search.BFS;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import maze.generators.CommonMaze3dGenerator;
import maze.generators.Maze3d;
import maze.generators.MyMaze3dGenerator;
import maze.generators.Prim;
import maze.generators.SimpleMaze3dGenerator;

/**
 * Defines the MyModel model.
 * @author Yotam Levy & Tomer Brami
 *
 */
public class MyModel extends CommonModel implements Model {

	/** @param maze2d used to store a crossed section maze */
	private int[][] maze2d;
	private Solution solution;
	
	/**
	 * Initiates the MyModel model.
	 * @param controller
	 */
	public MyModel() {
		super();
	}

	@Override
	public void displayCrossSectionByX(String index, String name) {
		if (mazes.get(name) == null){
			setChanged();
			notifyObservers("Maze " + name + " not found.\n");
			return;
		}
		
		Maze3d maze = getMaze(name);

		try {
			//putting the last cross section in its variable
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
		if (mazes.get(name) == null){
			setChanged();
			notifyObservers("Maze " + name + " not found.\n");
			return;
		}
		
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
		if (mazes.get(name) == null){
			setChanged();
			notifyObservers("Maze " + name + " not found.\n");
			return;
		}
		
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

	public void dirPath(String path) {

		try {
			File dir = new File(path);
			File[] files = dir.listFiles();

			if (files.length == 0){
				setChanged();
				notifyObservers("The directory is empty\n");
			}
			else{
				setChanged();
				notifyObservers(files);
			}
		} catch (NullPointerException e) {
			setChanged();
			notifyObservers("(MyCLIView\\dirPath) Invalid path.\n");
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
					notifyObservers("Thank you for playing 3D Maze3D!\n\n" + this.getClass().getName() + "'s threads terminated successfully.\n");
				}
			} catch (InterruptedException e){
				setChanged();
				notifyObservers("Interruption occurred during threads termination.\n");
				e.printStackTrace();
			}
		}
	}

	public void fileSize(String fileName) {			
		File file = new File(fileName);

		if (!file.exists() || !file.isFile()) {
			setChanged();
			notifyObservers("The file " + fileName + " doesn't exists.\n");
		}
		else
			setChanged();
			notifyObservers("The size of " + fileName + " file is " + file.length() + " bytes.\n");
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
	
	public int[][] getMaze2d() {
		return maze2d;
	}

	public void setMaze2d(int[][] maze2d) {
		this.maze2d = maze2d;
	}


	@Override
	public HashMap<Maze3d, Solution> getMazeToSol() {
		return mazeToSol;
	}

	@Override
	public Solution getSolution(String name) {
		if (solutions.containsKey(name)){
			Solution solution = solutions.get(name);
			return solution;
		}
		else{
			setChanged();
			notifyObservers("Solution not found.\n");
		}
		return null;
	}

	public HashMap<String, Solution> getSolutions() {
		return solutions;
	}
	
	@Override
	public void generateMaze(String name, int rows, int cols, int depth, String generateAlgorithm) {

		//Generate the Maze3d by a submitting a new value-returning (Callable) thread to the threadPool.
		threadPool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = null;

				//generate a maze using the chosen algorithm from properies.xml
				switch(generateAlgorithm){
				case "MyMaze3dGenerator":
					maze = new MyMaze3dGenerator().generate(cols, rows, depth);
					break;
				case "SimpleMaze3dGenerator":
					maze = new SimpleMaze3dGenerator().generate(cols, rows, depth);
					break;
				case "Prim":
					maze = new Prim().generate(cols, rows, depth);
					break;
				default:
					setChanged();
					notifyObservers("an error occured whle trying to create Maze " + name + ".\nGenerating algorithm name is missing.\n");
					return null;
				}
				mazes.put(name, maze);
				setChanged();
				notifyObservers("Maze " + name + " is created succesfully by " + generateAlgorithm + "!\n");
				return null;	
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadAndDecompress() {
		File file = new File("mazeToSol.zip");
		if (!file.exists()) {
			setChanged();
			notifyObservers("File mazeToSol.zip not exists.\n");
		}
		else
		{
			try {
				GZIPInputStream unzip = new GZIPInputStream(new FileInputStream(("mazeToSol.zip")));
				ObjectInputStream in = new ObjectInputStream(unzip);
				mazeToSol = (HashMap<Maze3d, Solution>)	in.readObject();
				in.close();
				unzip.close();
				}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				setChanged();
				notifyObservers("(MyModel\\loadAndDecopress) Input\\Output Error.\n");
			}

			}

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
			notifyObservers("The size of " + name + " maze in the memory is: " + maze.getRows()*maze.getColumns()*maze.getDepth() + ".\n");
		}
		else
		{
			setChanged();
			notifyObservers("(MyModel\\mazeSize) Maze " + name + " doesn't exists.\n");
			return;
		}
	}

	@Override
	public void saveToZip() {
			//Compress to gzip and save the mazeToSol map.
			try {
				GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream("mazesToSol.zip"));
				ObjectOutputStream out = new ObjectOutputStream(zip);
				out.writeObject(mazeToSol);
				out.close();
			} catch (IOException e) {
				setChanged();
				notifyObservers("(MyModel\\compressAndSave) Input\\Output Error while saving the map.");
				e.printStackTrace();
			}
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
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
			notifyObservers("Maze " + name + " saved succesfully to the file: " + fileName +  "\n");
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
	public void solveMaze(String name, String searchAlgorithm) {

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

						//Solve the maze using the chosen search algorithm from properies.xml
						switch(searchAlgorithm){
						case "BFS":
							BFS bfs = new BFS();
							solution = bfs.search(adapter);
							solutions.put(maze.toString(), solution);
							setChanged();
							notifyObservers(name + " maze is solved by " + searchAlgorithm + " successfully!\n");
							break;
						case "DFS":
							DFS dfs = new DFS();
							solution = dfs.search(adapter);
							solutions.put(maze.toString(), solution);
							setChanged();
							notifyObservers(name + " maze is solved by " + searchAlgorithm + " successfully!\n");
							break;							
						default:
							setChanged();
							notifyObservers("an error occured whle trying to solve Maze " + name + ".\nSearching algorithm name is missing.\n");
							break;
						}
					}
					return null;
				}
			});
		}
	}
}
