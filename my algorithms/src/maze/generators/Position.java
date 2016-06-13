package maze.generators;

/**
 * this class intended to represent a 3d position
 * @author Tomer
 *
 */
public class Position {
	public int x;
	public int y;
	public int z;
	
	/**
	 * a CTor to the class, used to set a 3d position
	 * @param x column number
	 * @param y row number
	 * @param z depth number
	 */
	public Position(int x, int y, int z) {
		this.x = x; 
		this.y = y;
		this.z = z;
	}
	
	/**
	 * this method overrides the object's toString in order to represent a position as string
	 */
	@Override
	public String toString(){
		return "{"+x+","+y+","+z+"}";
	}
	
	/**
	 * this method overrides the object's equals in order to compare two positions
	 */
	@Override
	public boolean equals(Object other){
		Position pos = (Position)other;
		
		if (this.toString().compareTo(pos.toString()) != 0)
			return false;
		
		return true;
	}
}
