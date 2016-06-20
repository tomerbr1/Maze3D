package maze.generators;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * The Maze3d class sets a generic 3d maze.
 *  
 * @author Tomer Brami & Yotam Levy
 * @version 1.0
 * @since 22-04-2016
 * 
 * @param rows (x) rows size (up & down)
 * @param columns (y) columns size (right & left)
 * @param depth (z) depth size (forward & backwards)
 * @param maze set an integers maze based on the rows/columns/depth values
 * @param startPosition defines the maze's start position
 * @param goalPosition defines the maze's goal (end) position
 * @param WALL defines what byte presents a wall in the maze
 * @param FREE defines what byte presents a free space in the maze
 */
public class Maze3d {

	private int rows; 
	private int columns;
	private int depth;
	protected int[][][] maze;
	private Position startPosition;
	private Position goalPosition;
	static final int WALL = 1;
	static final int FREE = 0;

	/** 
	 * This CTor is used to set a maze using three integers.
	 * @param rows (x) defines the maze rows size (up & down)
	 * @param columns (y) defines the maze columns size (right & left)
	 * @param depth (z) defines the maze depth (stages) size (forward & backward)
	 */
	public Maze3d(int rows, int columns, int depth) {		
		if (rows >= 1 && columns >= 1 && depth >= 1)
		{
			this.rows = rows;
			this.columns = columns;
			this.depth = depth;
			this.maze = new int[rows][columns][depth];
		}
		else
			return;
	}

	/**
	 * This CTOR is used to set a maze using bytes array it receives from an input stream
	 * @param byteArr The bytes Array from the InputStream
	 * @throws IOException 
	 */
	public Maze3d(byte[] byteArr) throws IOException {
		ByteArrayInputStream in=new ByteArrayInputStream(byteArr);
		DataInputStream dis=new DataInputStream(in);
		//creating a stream that reads primitive types easier
		
		int i = 0;
		this.rows = (int)byteArr[i++];
		this.columns = (int)byteArr[i++];
		this.depth = (int)byteArr[i++];

		this.startPosition = new Position((int)byteArr[i++], (int)byteArr[i++],(int)byteArr[i++]);
		this.goalPosition = new Position((int)byteArr[i++], (int)byteArr[i++],(int)byteArr[i++]);

		maze = new int[rows][columns][depth];

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				for (int z = 0; z < depth; z++)
					maze[x][y][z] = dis.read();
			}
		}
	}

	/**
	 * This method returns the maze's rows value.
	 * @return the maze's rows value.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * This method returns the maze's columns value.
	 * @return the maze's columns value.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * This method returns the maze's depth value.
	 * @return the maze's depth (stages) value.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * This method returns the maze itself (based on three integers).
	 * @return the maze itself (based on three integers).
	 */
	public int[][][] getMaze() {
		return maze;
	}

	/**
	 * This method returns the maze's start position.
	 * @return the maze's start position.
	 */
	public Position getStartPosition() {
		return startPosition;
	}

	/**
	 * This method sets the maze's start position.
	 * @param startPosition the maze's start position
	 */
	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * This method returns the maze's goal position.
	 * @return the maze's goal position.
	 */
	public Position getGoalPosition() {
		return goalPosition;
	}

	/**
	 * This method sets the maze's goal position.
	 * @param startPosition the maze's goal position
	 */
	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}	

	/**
	 * Filling a maze instance with walls all over.
	 */
	public void fillWithWalls()
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				for(int n=0;n<depth;n++)
				{
					maze[i][j][n]=1;
				}
			}
		}
	}

	/**
	 * Returns the value of a specific call.
	 * @return 0 if the cell holds a hole, 1 if it's holds a wall.
	 */
	public int getValueAt(int x, int y, int z) {
		return maze[x][y][z];
	}

	/**
	 * Set a specific cell as wall.
	 */
	public void setWallAt(int x, int y, int z) {
		maze[x][y][z] = WALL;
	}

	/**
	 * Set a specific cell as hole.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setHoleAt(int x, int y, int z) {
		maze[x][y][z] = FREE;
	}

	/**
	 * Set a specific cell, represented as a Position, as hole.
	 * @param pos
	 */
	public void setHoleAt(Position pos) {
		setHoleAt(pos.getX(), pos.getY(), pos.getZ());
	}

	/**
	 * Set a specific cell, represented by a Position, as hole or wall.
	 * @param p a specific position inside the maze.
	 * @param bin a binary option, 0 = Hole, 1 = Wall.
	 */
	public void setValueInPosition(Position p, int bin)
	{
		setValueAt(p.getX(), p.getY(), p.getZ(), bin);
	}

	/**
	 * Set a specific cell, represented by a Position, as hole or wall
	 * @param x cell's row
	 * @param y cell's column
	 * @param z cell's depth
	 * @param bin a binary option, 0 = Hole, 1 = Wall.
	 * @return the value the cell recieved
	 */
	public int setValueAt(int x, int y, int z, int bin) {
		return maze[x][y][z] = bin;
	}

	/**
	 * This method returns a crossed section maze by X (rows)
	 * @param x cross the maze by it's rows
	 * @return a 2d maze crossed by the original maze's rows value
	 * @throws IndexOutOfBoundsException throws exception in case the given rows index is out of bound
	 */
	public int[][] getCrossSectionByX(int x) throws IndexOutOfBoundsException{
		if ((x<0) || (x >= rows))
			throw new IndexOutOfBoundsException("Error: Column Index out of bounds.");

		int[][] maze2dX = new int[this.columns][this.depth];
		for (int y = 0; y < this.columns; y++) {
			for (int z = 0; z < this.depth; z++)
				maze2dX[y][z] = this.maze[x][y][z];
		}
		return maze2dX;
	}

	/**
	 * This method returns a crossed section maze by Y (columns)
	 * @param y cross the maze by it's columns
	 * @return a 2d maze crossed by the original maze's columns value
	 * @throws IndexOutOfBoundsException throws exception in case the given columns index is out of bound
	 */
	public int[][] getCrossSectionByY(int y) throws IndexOutOfBoundsException{
		if ((y<0) || (y >= columns))
			throw new IndexOutOfBoundsException("Error: Row Index out of bounds.");

		int[][] maze2dY = new int[rows][depth];
		for (int x = 0; x < this.rows; x++) {
			for (int z = 0; z < this.depth; z++)
				maze2dY[x][z] = this.maze[x][y][z];
		}
		return maze2dY;
	}

	/**
	 * This method returns a crossed section maze by Z (depth)
	 * @param z cross the maze by it's depth
	 * @return a 2d maze crossed by the original maze's depth value
	 * @throws IndexOutOfBoundsException throws exception in case the given depth index is out of bound
	 */
	public int[][] getCrossSectionByZ(int z) throws IndexOutOfBoundsException{
		if ((z<0) || (z >= depth))
			throw new IndexOutOfBoundsException("Error: Depth Index out of bounds.");

		int[][] maze2dZ = new int[rows][columns];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < rows; y++)
				maze2dZ[x][y] = this.maze[x][y][z];
		}
		return maze2dZ;
	}

	/**
	 * This method prints a 2d crossed maze.
	 * @param maze2d a 2d crossed maze
	 */
	public void printCrossed(int[][] maze2d){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<maze2d.length; i++){
			for (int j=0; j<maze2d[0].length; j++)
				sb.append(maze2d[i][j] + " ");
			sb.append("\n");
		}
		System.out.println(sb);
	}

	/** 
	 * This method check what directions are possible to move from a specific position, and returns them as Directions (enum) array.
	 * @param pos a position to be checked for it's possible directions to move.
	 * @return a directions array of the possible directions to move.
	 */
	public Direction[] getPossibleDirections(Position pos) {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		if (pos.getX() + 1 < rows && maze[pos.getX() + 1][pos.getY()][pos.getZ()] == FREE)
			directions.add(Direction.UP);
		if (pos.getX() - 1 >=0 && maze[pos.getX() - 1][pos.getY()][pos.getZ()] == FREE)
			directions.add(Direction.DOWN);
		if (pos.getY() + 1 < columns && maze[pos.getX()][pos.getY() + 1][pos.getZ()] == FREE)
			directions.add(Direction.RIGHT);
		if (pos.getY() - 1 >= 0 && maze[pos.getX()][pos.getY() - 1][pos.getZ()] == FREE)
			directions.add(Direction.LEFT);
		if (pos.getZ() + 1 < depth && maze[pos.getX()][pos.getY()][pos.getZ()+1] == FREE)
			directions.add(Direction.FORWARD);
		if (pos.getZ() - 1 >= 0 && maze[pos.getX()][pos.getY()][pos.getZ()-1] == FREE)
			directions.add(Direction.BACKWARD);

		Direction[] arr = new Direction[directions.size()]; 
		directions.toArray(arr);
		return arr;		
	}

	/**
	 * This method check what directions are possible to move from a specific position, and returns them as String array.
	 * @param pos a position to be checked for it's possible directions to move.
	 * @return a String array of the possible directions to move.
	 */
	public String[] getPossibleMoves(Position p) {
		ArrayList<String> moves = new ArrayList<String>();

		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		

		if ((x - 1 >= 0) && (maze[x - 1][y][z] == FREE)) 
			moves.add("Down");

		if ((x + 1 < rows) && (maze[x + 1][y][z] == FREE))
			moves.add("Up");

		if ((y - 1 >= 0) && (maze[x][y - 1][z] == FREE))
			moves.add("Left");

		if ((y + 1 < columns) && (maze[x][y + 1][z] == FREE))
			moves.add("Right");

		if ((z - 1 >= 0) && (maze[x][y][z - 1] == FREE))
			moves.add("Backward");

		if ((z + 1 < depth) && (maze[x][y][z + 1] == FREE))
			moves.add("Forward");

		String[] arr = moves.toArray(new String[moves.size()]);

		return arr;
	}

	/**
	 * This method overrides the Object's toString() method in order to print a maze correctly.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Start: " + startPosition + "\n");
		sb.append("Goal: " + goalPosition + "\n\n");

		for (int z = 0; z < depth; z++) {
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++)
					sb.append(maze[x][y][z] + " ");
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Returns the value of a specific position in the maze3d.
	 * @param pos
	 * @return 1 = Wall, 0 = Free
	 */
	public int getPositionValue(Position pos){
		return maze[pos.getX()][pos.getY()][pos.getZ()];
	}

	public int getPositionValueInts(int x, int y, int z){
		return maze[x][y][z];
	}
	
	/**
	 * returning hashCode based on the content of the byte array representing the maze
	 */
	@Override
	public int hashCode()  
	{
		return Arrays.hashCode(this.toByteArray());
	}
	
	/**
	 * This method overrides the Object's equals method in order to compare two maze objects.
	 */
	@Override
	public boolean equals(Object maze3d){

		Maze3d other = (Maze3d)maze3d;

		if ((this.getColumns() != other.getColumns()) || (this.getRows() != other.getRows()) || (this.getDepth() != other.getDepth()))
			return false;

		if (!this.getStartPosition().equals(other.getStartPosition()) || !this.getGoalPosition().equals(other.getGoalPosition()))
			return false;

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				for (int z = 0; z < this.depth; z++) {
					if (this.maze[x][y][z] != other.maze[x][y][z])
						return false;
				}
			}
		}
		return true;

	}

	/**
	 * returning all the maze3d data converted to byte array
	 * format:
	 * 4 bytes of size of x axis,4 bytes of size of y axis,4 bytes of size of z axis,
	 * all the cells of maze 3d matrix,each one as byte,
	 * the start position:3 integers represented by 4 bytes each
	 * the goal position :3 integers represented by 4 bytes each
	 * 
	 * @return byte array with the maze details
	 * @throws IOException 
	 */
	public byte[] toByteArray() {
		ArrayList<Byte> arr = new ArrayList<Byte>();
		arr.add((byte)rows);
		arr.add((byte)columns);
		arr.add((byte)depth);
		arr.add((byte)startPosition.getX());
		arr.add((byte)startPosition.getY());
		arr.add((byte)startPosition.getZ());
		arr.add((byte)goalPosition.getX());
		arr.add((byte)goalPosition.getY());
		arr.add((byte)goalPosition.getZ());

		for (int z = 0; z < depth; z++) {
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++)
					arr.add((byte)maze[x][y][z]);
			}
		}

		//Copy the array list to array of bytes
		byte[] bytes = new byte[arr.size()];
		for (int i=0;i<arr.size(); i++) {
			bytes[i] = arr.get(i);
		}

		return bytes;

	}

}