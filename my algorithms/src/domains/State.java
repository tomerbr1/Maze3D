package domains;

/**
 * This class intended to represent a state can be done (like moving from a place to place) in a searchable problem.
 * @author Tomer
 *
 */
public class State implements Comparable<State>{
    private String description;
    private double cost;
    private State cameFrom;

    /**
     * a getter method
     * @return the state's cost
     */
    public double getCost() {
		return cost;
	}

    /**
     * a setter method
     * @param cost a cost to the state
     */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * a getter method
	 * @return the previous state (where you arrived to the current state)
	 */
	public State getCameFrom() {
		return cameFrom;
	}

	/**
	 * a setter method
	 * @param cameFrom set the previous state (where you arrived to the current state)
	 */
	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	/**
	 * a getter method
	 * @return the state's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * a setter method
	 * @param description the state's description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * a method that overrides the Object's equals method in order to check if two states
	 * are equal upon their description.
	 */
	@Override
    public boolean equals(Object obj){ // we override Object's equals method
		State state = (State)obj;
		return this.description.equals(state.description);
    }

	/**
	 * a method that overrides the Object's compareTo method in order to compare two
	 * state's cost values and see which one costs more.
	 */
	@Override
	public int compareTo(State s) {
		return (int)(this.cost - s.cost);
	} 
	
	/**
	 * a method that overrides the Object's hashCode method in order to set the hashcode
	 * of each state in the HashMap by their description.
	 */
	@Override
	public int hashCode() {
		return description.hashCode();
	}
	
	/**
	 * a method that overrides the Object's toString method in order to print a state by it's description.
	 */
	@Override
	public String toString() {
		return description;
	}
 
}
