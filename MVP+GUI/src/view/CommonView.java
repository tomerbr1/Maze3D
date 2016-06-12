package view;

import java.io.BufferedReader;
import java.io.Writer;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
	public abstract void displayMessage(String message);
	public abstract void displayMessage(String[] messages);

	protected ExecutorService threadPool;
	
	/**
	 * CTOR to initiate what every view has to.
	 * @param in how to read text from the client
	 * @param out how to print out formatted representations of objects
	 */
	public CommonView(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;
		threadPool = Executors.newCachedThreadPool();
	}

	@Override
	public void setPresenter(Presenter presenter){
		this.presenter = presenter;
	}

	@Override
	public void exit() {
		displayMessage("Exiting...\n");
		try {
			threadPool.shutdown();
			if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)){
				threadPool.shutdownNow();
				displayMessage(this.getClass().getName() + "'s threads forced to be terminated.\n");
			}
			else
			{
				displayMessage(this.getClass().getName() + "'s threads terminated successfully.\n");
				setChanged();
			}
		} catch (InterruptedException e){
			displayMessage("Interruption occurred during threads termination.\n");
			e.printStackTrace();
		}
	}
}
