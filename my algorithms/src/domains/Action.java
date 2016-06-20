package domains;

/**
 * The action class represents every action and its cost that can be done from a state.
 * @author Tomer Brami & Yotam Levy
 *
 */
public class Action {
	String description;
	double cost;
	
	/**
	 * The CTor of the class set a description & cost for an action.
	 * @param description a string to represent the action
	 * @param cost the cost of an action
	 */
	public Action(String description, double cost){
		this.description=description;
		this.cost=cost;
	}

	// public Action() {}
	
	/**
	 * a getter method
	 * @return the action's description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * a setter method
	 * @param description the action's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * a getter method
	 * @return the action's cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * a setter method
	 * @param cost the actoin's cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * an override method to the Object's toString, in order to print an action as it's description
	 */
	@Override
	public String toString() {
		return this.description;
	}
	
	

}
