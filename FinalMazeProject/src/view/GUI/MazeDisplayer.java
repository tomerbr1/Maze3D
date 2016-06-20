package view.GUI;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.search.Solution;
import maze.generators.Maze3d;
import maze.generators.Position;
import view.View;

/**
 * This class includes all the common methods for displaying a GUI Maze.
 * 
 * This class extends Canvas class, thus it allows us to converting a Maze instance
 * into a widget, as the Canvas class provides us a surface for drawing arbitrary graphics. 
 * In other words, now we can use all the widget methods, for every Maze instance.
 * @author Yotam Levy & Tomer Brami
 *
 */	
public abstract class MazeDisplayer extends Canvas {

	private View view;
	
	//Default stub maze for check
	protected int[][] mazeData={
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	};
	
	//CTor
	MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	// Common Setters & Getters
	
	public abstract int[][] getMazeData();

	public abstract void setMazeData(int[][] mazeData);
	
	public abstract Maze3d getCurrentMaze();
	
	/**
	 * Set the current Maze.
	 * @param maze
	 */
	public abstract void setCurrentMaze(Maze3d maze);
	
	public abstract void setCharacterPosition(int col, int row, int depth);
	
	/**
	 * Returns the current position of the Character
	 * @return
	 */
	public abstract Position getCharacterPosition();
	
	// Character movements methods
	
	public abstract void moveCharacter(int col, int row, int depth);
	
	public abstract void move(int[] direction);
	
	/** Solving the Maze methods */
	
	public abstract void solveTheMaze(Solution solution);
	
}
