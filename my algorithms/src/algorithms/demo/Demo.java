package algorithms.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import maze.generators.Maze3d;
import maze.generators.MyMaze3dGenerator;

/**
 * This class used to generate a specific 3d maze by one of the maze generators algorithms,
 * solve it by one of the search algorithms and print the maze and it's solution.
 * @author Tomer
 */
public class Demo {
	
	/**
	 * This is the only method used in class, in order to run a program demo.
	 */
	public void run(){
		MyMaze3dGenerator gen = new MyMaze3dGenerator();
		Maze3d maze = gen.generate(7, 7, 7);
		System.out.println("My Maze 3D:\n");
		System.out.println(maze);
		MazeAdapter adapter = new MazeAdapter(maze);
		//BestFirstSearch bfs = new BestFirstSearch();
		System.out.println("Solution:\n");
		DFS dfs = new DFS();
		Solution solution = dfs.search(adapter);
		
		System.out.println(solution);
		System.out.println("Num of nodes evaluated by solution: " + dfs.getEvaluatedNodes());
		
	}
	
	public void compressMaze() {
		MyMaze3dGenerator gen = new MyMaze3dGenerator();
		Maze3d maze = gen.generate(5, 5, 3);
		System.out.println(maze.toString());
		
		// save it to a file
		try {
			OutputStream out=new MyCompressorOutputStream(new FileOutputStream("1.maz"));
			out.write(maze.toByteArray());
			out.flush();
			out.close();
			InputStream in=new MyDecompressorInputStream(new FileInputStream("1.maz"));
			byte b[]=new byte[maze.toByteArray().length];
			in.read(b);
			in.close();
			Maze3d loaded=new Maze3d(b);
			System.out.println(loaded.equals(maze));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
