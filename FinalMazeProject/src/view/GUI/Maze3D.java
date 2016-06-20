package view.GUI;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import algorithms.search.Solution;
import maze.generators.Maze3d;
import maze.generators.Position;

public class Maze3D extends MazeDisplayer {
	
	//Maze variables
	private Position character = new Position(0, 2, 0); //Create a 3D Game Character with default position, according to the default maze data.
	public Position goal = new Position();
	private Maze3d currentMaze;
	
	public static final int[] UP = { 0, -1, 0 };
	public static final int[] DOWN = { 0, 1, 0 };
	public static final int[] RIGHT = { 0, 0, 1 };
	public static final int[] LEFT = { 0, 0 , -1 };
	public static final int[] UPWARDS = { 1, 0, 0 };
	public static final int[] DOWNWARDS = { -1, 0, 0 };
	
	private Timer timer;
	private TimerTask task;
	//------ INITIATE MAZE METHODS ------
	
	/**
	 * CTor to paint and initiate a Maze3D instance
	 * @param parent
	 * @param style
	 */
	Maze3D(Composite parent, int style) {
		super(parent, style);
		initalizeWindow(parent, style);
	}
	
	public void initalizeWindow(Composite parent, int style){
		//define common color for later reuse
		final Color white = new Color(null, 255, 255, 255);
		setBackground(white);
	
		//print the 3d maze here - it will redraw the maze paint every time we resize the maze window.
		addPaintListener(new PaintListener() {
	
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));
	
				int width = getSize().x;
				int height= getSize().y;
	
				int mx = width/2;
	
				double w=(double)width/mazeData[0].length;
				double h=(double)height/mazeData.length;
	
				for(int i=0;i<mazeData.length;i++){
					double w0=0.7*w +0.3*w*i/mazeData.length;
					double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
					double start=mx-w0*mazeData[i].length/2;
					double start1=mx-w1*mazeData[i].length/2;
					for(int j=0;j<mazeData[i].length;j++){
						double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
						double cheight=h/2;
						if(mazeData[i][j]!=0)
							paintCube(dpoints, cheight,e);
	
						if(i==character.getY() && j==character.getZ()){
							e.gc.setBackground(new Color(null,200,0,0));
							e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
							e.gc.setBackground(new Color(null,255,0,0));
							e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
							e.gc.setBackground(new Color(null,0,0,0));				        	  
						}
					}
				}
			}
		});
	}

	//This method paints a 3D Maze Cube Board.
	private void paintCube(double[] p,double h,PaintEvent e){
		int[] f=new int[p.length];
		for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
	
		e.gc.drawPolygon(f);
	
		int[] r=f.clone();
		for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
	
	
		int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
		e.gc.drawPolygon(b);
		int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
		e.gc.drawPolygon(fr);
	
		e.gc.fillPolygon(r);
	}
	
	/**
	 * Updates the shell and redraw paintings like the Maze3D and the Character
	 * (in contrast to the native update() method, that doesn't redraw paintings).
	 */
	private void updateWindow() {
		getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				redraw();
				getShell().update();
				getDisplay().update();
			}
		});
	}	
	
	/**
	 * Method to recieve audio file and play it.
	 */
	public void makeSound(String soundFile)
	{
		File audio = new File(soundFile);
		try 
		{
			Clip c = AudioSystem.getClip();
			c.open(AudioSystem.getAudioInputStream(audio));
			c.start();
		} 
		catch (LineUnavailableException e) 
		{
			
			e.printStackTrace();
		} catch (IOException e) 
		{
			
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		
	}
	//------ SETTERS & GETTERS METHODS ------
	@Override
	public int[][] getMazeData() {
		return mazeData;
	}
	
	public void setMazeData(int[][] mazeData){
		this.mazeData = mazeData;
	}

	@Override
	public Maze3d getCurrentMaze() {
		return currentMaze;
	}

	@Override
	public void setCurrentMaze(Maze3d maze){
		this.currentMaze = maze;
		this.goal = maze.getGoalPosition();
		this.character = currentMaze.getStartPosition();
		this.mazeData = currentMaze.getCrossSectionByX(this.character.getX());
		updateWindow();
	}
	
	@Override
	public Position getCharacterPosition() {
		return character;
	}

	@Override
	public void setCharacterPosition(int x, int y, int z) {
		mazeData = currentMaze.getCrossSectionByX(x);
		moveCharacter(x, y, z);
	}
	
	//------ SOLVING METHODS ------
	/**
	 * This method will take the character to the goal position step by step.
	 */
	@Override
	public void solveTheMaze(Solution solution) {

		Collections.reverse(solution.getStates());
		timer = new Timer();
		task = new TimerTask() {
			int i = solution.getStates().size() -1;
			
			@Override
			public void run() {

				if(i >= 0){
//					System.out.println(solution.getStates().get(i));
//					System.out.println(Integer.parseInt(solution.getStates().get(i).toString().substring(1,2)));
//					System.out.println(Integer.parseInt(solution.getStates().get(i).toString().substring(3,4)));
//					System.out.println(Integer.parseInt(solution.getStates().get(i).toString().substring(5,6)));
					setCharacterPosition(Integer.parseInt(solution.getStates().get(i).toString().substring(1,2)),
							Integer.parseInt(solution.getStates().get(i).toString().substring(3,4)),
							Integer.parseInt(solution.getStates().get(i).toString().substring(5,6)));
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i--;
				}
				else{
					timer.cancel();
					timer.purge();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 100);
		timer.purge();
	}
	
	public Position getGoal() {
		return goal;
	}
	
	//------ CHARACTER MOVEMENTS METHODS ------
	public void move(int[] direction) {
	    Position pos = new Position(this.character.getX() + direction[0],
	                                this.character.getY() + direction[1],
	                                this.character.getZ() + direction[2]);
	    if (checkViableMove(pos)) {
	    	
	      this.character = pos;
	      if (direction[0] != 0)
	    	  mazeData = this.currentMaze.getCrossSectionByX(pos.getX());
	      makeSound("resources/cartoon015.wav");
	      updateWindow();
	    }
	  }
	
	private boolean checkViableMove(Position pos) {
		if (checkBounds(pos.getX(), this.currentMaze.getRows()) &&
        checkBounds(pos.getY(), this.currentMaze.getColumns()) &&
        checkBounds(pos.getZ(),this.currentMaze.getDepth()))
		{
			Position tempPosition = new Position(pos.getX(), pos.getY(), pos.getZ());
			return (currentMaze.getPositionValue(tempPosition) == 0);
		}
			return false;
	  }
	
	private boolean checkBounds(int param, int bound) {
	    return param >= 0 && param < bound;
	  }
	
	public void moveCharacter(int x, int y, int z){
		Position pos = new Position(x, y, z);
		if((x >= 0 && x < currentMaze.getMaze().length) && (y >= 0 && y < currentMaze.getMaze()[1].length) && (z >= 0 && z < currentMaze.getMaze()[0][1].length)){
			if  (currentMaze.getPositionValueInts(pos.getX(),pos.getY(),pos.getZ()) == 0){
				character.setX(x);
				character.setY(y);
				character.setZ(z);
				getDisplay().syncExec(new Runnable() {	
					@Override
					public void run() {
						redraw();
						getShell().update();
						getDisplay().update();
						
					}
				});
			}	
		}
	}
}
