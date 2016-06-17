package view;

import java.io.BufferedReader;
import java.io.Writer;
import java.util.Observable;

import presenter.Presenter;

/**
 * Implements what every view needs to do.
 * @author Tomer
 *
 */
public abstract class CommonView extends Observable implements View {

	protected BufferedReader in;
	protected Writer out;
	protected Presenter presenter;
	
	public abstract void start();
	public abstract void display(String message);
	public abstract void display(String[] messages);
	
	/**
	 * CTOR to initiate what every view has to.
	 * @param in how to read text from the client
	 * @param out how to print out formatted representations of objects
	 */
	public CommonView(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void setPresenter(Presenter presenter){
		this.presenter = presenter;
	}
}
