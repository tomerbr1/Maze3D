package algorithms.search;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithms.demo.MazeAdapter;
import maze.generators.Maze3d;
import maze.generators.MyMaze3dGenerator;

public class DFSTest {
	private MyMaze3dGenerator gen;
	private Maze3d maze;
	private MazeAdapter adapter;
	private DFS dfs;
	private Solution solution;
	
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.) 
     */ 
	@Before
	public void setUp() throws Exception {
		gen = new MyMaze3dGenerator();
		adapter = new MazeAdapter(maze);
		dfs = new DFS();
	}

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.) 
     */
	@After
	public void tearDown() throws Exception {
	}

	//Case 1 - regular searchable Maze3D to be solved.
	@Test
	public void testSearchRegularMazeCase() {
		this.maze = gen.generate(7, 7, 7);
		this.adapter = new MazeAdapter(maze);
		this.solution = dfs.search(adapter);
	}

	//Case 2 - Insert Null as the MazeAdapter to be solved.
	@Test
	public void testSearchNullCase() {
		this.maze = gen.generate(7, 7, 7);
		this.adapter = new MazeAdapter(maze);
		this.solution = dfs.search(null);
		assertEquals(null, solution);
	}
	
	//Case 3 - Insert Out of bounds coordinate maze to be solved.
	@Test
	public void testSearchIntergersOutOfBoundsCase() {
		this.maze = gen.generate(-4, -3, -2);
		this.adapter = new MazeAdapter(maze);
		this.solution = dfs.search(adapter);
		assertEquals(null, solution);
	}

}
