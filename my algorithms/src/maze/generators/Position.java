package maze.generators;

/**
 * this class intended to represent a 3d position
 * @author Tomer
 *
 */
public class Position {
	private int x;
	private int y;
	private int z;
	
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
