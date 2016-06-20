package view.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Observable;
import java.util.Observer;

import presenter.Presenter;
import presenter.Properties;
import view.View;

/**
 * Implements a CLI View.
 * @author Yotam Levy & Tomer Brami
 *
 */
public class MyView extends Observable implements View, Observer {

	private CLI cli;
	protected Presenter presenter;
	protected BufferedReader in;
	protected Writer out;
	
	public MyView(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;
		
		cli = new CLI(in, out);
		cli.addObserver(this);
	}

	/**
	 * Display all the files and folders in the entered path.
	 * @param path
	 */
	@Override
	public void display(String string) {
		try {
			out.write(string);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void display(String[] strings) {
		for (String string : strings)
			try {
				out.write(string);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void start() {
		//Displaying a welcome message
		display("Welcome to the 3D Maze CLI!\nType help to view available commands to use.\n\n");
		
		//Running the CLI in a different thread.
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				cli.start();
			}
		});
		thread.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			this.setChanged();
			this.notifyObservers(arg);			
		}
	}

	@Override
	public void setProperties(Properties prop) {
		//Not in use in CLI
	}

}
