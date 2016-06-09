package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.search.Solution;
import controller.commands.Command;
import maze.generators.Maze3d;

public class MyView extends CommonView implements View {

	private CLI cli;
	
	public MyView(BufferedReader in, PrintWriter out) {
		super(in, out);
	}
	
	@Override
	public void displayMessage(String string) {
			if (string != null){
				out.write(string);
				out.flush();
			} else {
				out.println("Error displaying message by view.\n");
				out.flush();
			}
	}
	
	@Override
	public void displayMessage(String[] strings) {
		
			if (strings != null){	
				for (String string : strings)
				out.write(string);
				out.flush();
			} else {
				out.println("Error displaying messages by view.\n");
				out.flush();
			}
	}

	@Override
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {				
				cli.start();
			}		
		});	
		thread.start();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		cli = new CLI(in, out, commands);	
	}
	
	@Override
	public void fileSize(String fileName) {			
			File file = new File(fileName);
			
			if (!file.exists() || !file.isFile()) {
				out.write("The file " + fileName + " doesn't exists.\n");
			}
			else
				displayMessage("The size of " + fileName + " file is " + file.length() + " bytes.\n");
	}

	@Override
	public void mazeSize(Maze3d maze, String name) {
		out.write("The size of " + name + " maze in the memory is: " + maze.getCols()*maze.getRows()*maze.getDepth() + ".\n");
		out.flush();
	}
	
	@Override
	public void dirPath(String path) {
		
		try {
			File dir = new File(path);
			File[] files = dir.listFiles();
			
			if (files.length == 0)
				out.write("The directory is empty\n");
			else{
			for (File file : files)
				out.write(file.getName() + "\n");
			out.flush();
			}
		} catch (NullPointerException e) {
			out.write("(MyView\\dirPath) Invalid path.\n");
		}
	}

	@Override
	public void displayMaze(Maze3d maze) {
		out.write(maze.toString());
		out.flush();
	}

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
	public void displayCrossSectionByX(int index, Maze3d maze) {
		try {
			out.write(maze2dToString(maze.getCrossSectionByX(index)));
			out.flush();
		} catch (IndexOutOfBoundsException e) {
			out.write("(MyView\\displayCrossSectionByX) Index given is out of bound.\n");
		}
	}

	@Override
	public void displayCrossSectionByY(int index, Maze3d maze) {
		try {
			out.write(maze2dToString(maze.getCrossSectionByY(index)));
			out.flush();
		} catch (IndexOutOfBoundsException e) {
			out.write("(MyView\\displayCrossSectionByY) Index given is out of bound.\n");
		}
	}

	@Override
	public void displayCrossSectionByZ(int index, Maze3d maze) {
		try {
			out.write(maze2dToString(maze.getCrossSectionByZ(index)));
			out.flush();
		} catch (IndexOutOfBoundsException e) {
			out.write("(MyView\\displayCrossSectionByZ) Index given is out of bound.\n");
		}
	}

	@Override
	public void displaySolution(Solution solution) {
		try {
			out.write(solution.toString());
			out.flush();
		} catch (Exception e) {
			out.write("(MyView\\displaySolution) General error.\n");
			e.printStackTrace();
		}
	}
}
