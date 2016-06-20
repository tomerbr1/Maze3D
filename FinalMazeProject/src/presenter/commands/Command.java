package presenter.commands;

/**
 * Defines what a command can do.
 * @author Yotam Levy & Tomer Brami
 *
 */
public interface Command {
	public void doCommand(String[] args);
}
