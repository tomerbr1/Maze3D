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
 * @author Tomer
 *
 */
public abstract class CommonModel extends Observable implements Model {
	
	/** @param presenter the presenter instance the model is communicate with. */
	protected Presenter presenter;
	 
	/** @param mazes a map used to store all the Maze3d instances. */
	protected HashMap<String, Maze3d> mazes;
	
	/** @param solutions a map used to store the solutions for the Maze3d instances. */
	protected HashMap<String, Solution> solutions;
	
	/** @param message a string message by the model, used to be received by the presenter as an observer. */
	protected String message;
	
	/** @param threads a list used to store all the working threads ids. */
	protected ArrayList<Thread> threads = new ArrayList<Thread>();
	
	protected ExecutorService threadPool;
	
	/**
	 * CTor to Initiate the common variables that every model has to initiate.
	 */
	public CommonModel() {
		mazes = new HashMap<String, Maze3d>();
		solutions = new HashMap<String, Solution>();
		threads = new ArrayList<Thread>();
		threadPool = Executors.newCachedThreadPool();
	}
	
	public String getMessage(){
		return message;
	}
}
