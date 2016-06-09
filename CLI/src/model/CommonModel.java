package model;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.search.Solution;
import controller.Controller;
import maze.generators.Maze3d;

/**
 * Implements what every model needs to do.
 * @author Tomer
 *
 */
public abstract class CommonModel implements Model {
	
	/** @param controller the controller instance the model is communicate with. */
	protected Controller controller;
	 
	/** @param mazes the Maze3d HashMap, used to store all the Maze3d instances. */
	protected HashMap<String, Maze3d> mazes;
	
	/** @param solutions Solution HashMap, used to store the solutions for the mazes. */
	protected HashMap<String, Solution> solutions;
	protected ExecutorService threadPool;
	
	/**
	 * CTor - Inititates what every model must initiate.
	 */
	public CommonModel() {
		mazes = new HashMap<String, Maze3d>();
		solutions = new HashMap<String, Solution>();
		threadPool = Executors.newCachedThreadPool();
	}

	public Controller getController() {
		return controller;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public HashMap<String, Maze3d> getMazes() {
		return mazes;
	}
	
	public void setMazes(HashMap<String, Maze3d> mazes){
		this.mazes = mazes;
	}
	
	public Maze3d getMaze(String name){
			return mazes.get(name);
	}
	
	public Solution getSolution(String solution){
		return solutions.get(solution);
	}

}
