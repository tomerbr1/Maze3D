package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.search.Solution;
import maze.generators.Maze3d;
import presenter.Presenter;

/**
 * Implements what every model needs to do.
 * @author Yotam Levy and Tomer Brami
 *
 */
public abstract class CommonModel extends Observable implements Model {
	
	/** @param presenter the presenter instance the model is communicate with. */
	protected Presenter presenter;
	 
	/** @param mazes a map used to store all the Maze3d instances. */
	protected HashMap<String, Maze3d> mazes;
	
	/** @param solutions a map used to store the solutions for the Maze3d instances.
	 * This map is used to identify if a solution is already exists to a maze.
	 * If it does, the existing solution will be used instead of solving it once again.
	 */
	protected HashMap<String, Solution> solutions;
	
	/** @param message a string message by the model, used to be received by the presenter as an observer. */
	protected String message;
	
	/** @param mazeToSol a map used to bind between a Maze3D to its solution.
	 * This map is used to be loaded when the game starts by decompressing itself from a gzip file,
	 * and automatically saved when game exits be compressing it to a gzip file.*/
	protected HashMap<Maze3d, Solution> mazeToSol;
	
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	
	protected ExecutorService threadPool;
	
	private Solution solution;
	
	/**
	 * CTor to Initiate the common variables that every model has to initiate.
	 */
	public CommonModel() {
		mazes = new HashMap<String, Maze3d>();
		solutions = new HashMap<String, Solution>();
		mazeToSol = new HashMap<Maze3d, Solution>();
		threadPool = Executors.newCachedThreadPool();
	}
}
