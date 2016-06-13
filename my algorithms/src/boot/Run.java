package boot;

import algorithms.demo.Demo;
import maze.generators.Maze3d;
import maze.generators.Maze3dGenerator;
import maze.generators.Position;

/**
 * This class intended to run a demo of the project.
 * @author Tomer
 *
 */
public class Run {


	
	private static void testMazeGenerator(Maze3dGenerator mg){
		// prints the time it takes the algorithm to run
		System.out.println(mg.measureAlgorithmTime(10, 12, 3));
		// generate another 3d maze
		Maze3d maze=mg.generate(10, 10, 3);
		// get the maze entrance
		Position p = maze.getStartPosition();
		// print the maze
		System.out.println(maze);
		// get all the possible moves from a position
		String[] moves=maze.getPossibleMoves(p);
		// print the moves
		System.out.println("All possible moves of "+ p +":");
		for(String move : moves)
			System.out.println(move);
		System.out.println("");

		try{
			// get 2d cross sections of the 3d maze
			int[][] maze2dx=maze.getCrossSectionByX(2);
			
			maze.printCrossed(maze2dx);
			
			int[][] maze2dy=maze.getCrossSectionByY(5);
			
			maze.printCrossed(maze2dy);

			int[][] maze2dz=maze.getCrossSectionByZ(0);

			maze.printCrossed(maze2dz);
			
			// this should throw an exception!
			maze.getCrossSectionByX(-1);
			
			} catch (IndexOutOfBoundsException e){
					System.out.println("good!");
			}
	}
	
/**
 * The main method runs a demo of the project.
 * @param args not used.
 */
	public static void main(String[] args) {

		//exercise 2
		Demo demo = new Demo();
		demo.run();
//		testMazeGenerator(new SimpleMaze3dGenerator());
//		System.out.println("~~~END SIMPLE MAZE~~~");
//		testMazeGenerator(new MyMaze3dGenerator());
//		System.out.println("~~~END DFS~~~");
		//exercise 3
		demo.compressMaze();
		

	}

}
