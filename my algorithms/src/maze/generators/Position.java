package maze.generators;

import java.util.Random;

/**
 * this class intended to represent a 3d position
 * @author Tomer Brami & Yotam Levy
 *
 */
public class Position {
	private int x;
	private int y;
	private int z;
	private static Random rand = new Random();
	
	/**
	 * a CTor to the class, used to set a 3d position
	 * @param x rows index
	 * @param y columns index
	 * @param z depth index
	 */
	public Position(int x, int y, int z) {
		this.x = x; 
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Default CTor, initializing position to {0,0,0}.
	 */
	public Position() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	//Setters & Getters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Updates all the position's coordinates at once.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void updateCell(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns a random position inside the current maze instance.
	 * @param maze a maze instance
	 * @return
	 */	
	public static Position getRandomPosition(Maze3d maze) {
		return new Position(rand.nextInt(maze.getRows()), rand.nextInt(maze.getColumns()),
				rand.nextInt(maze.getDepth()));
	}
	
	/** this method overrides the object's toString in order to represent a position as string. */
	@Override
	public String toString(){
		return "{"+x+","+y+","+z+"}";
	}
	
	/** this method overrides the object's equals in order to compare two positions properly.
	 * @param other another Position
	 */
	@Override
	public boolean equals(Object other){
		Position pos = (Position)other;
		
		if (this.toString().compareTo(pos.toString()) != 0)
			return false;
		return true;
	}
}
