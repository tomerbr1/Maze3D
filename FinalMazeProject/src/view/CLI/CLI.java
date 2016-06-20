package view.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Observable;
/**
 * Defines the MyCLIView view, a command line based interface.
 * @author Yotam Levy & Tomer Brami
 * 
 */
public class CLI extends Observable {

	private BufferedReader in;
	private Writer out;
	/**
	 * CTOR to initiate the CLI View
	 * @param in - the input
	 * @param out - the output
	 */
	public CLI(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;
	}

	/**
	 * Start running the CLI in an independent thread.
	 */
	public void start(){
				try {
					String line;
					do {
						out.write("Enter command: ");
						out.flush();
						line = in.readLine().toLowerCase();
						setChanged();
						notifyObservers(line);
						if (line.contains("generate_maze_3d") || line.contains("solve ")){
							Thread.sleep(50);
						}
					}
					while (!(line.equals("exit")));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}							
	}

}
