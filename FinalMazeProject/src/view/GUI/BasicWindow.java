package view.GUI;

import java.util.HashMap;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * a Class that contains all the basic variables that every GUI window includes.
 * The class is Observable as a part of the MVP Structure.
 * The class is Runnable as being ruuning in seperate thread.
 * @author Yotam Levy & Tomer Brami
 *
 */
public abstract class BasicWindow extends Observable implements Runnable {

	protected Display display;
	protected Shell shell;
	private HashMap<String, Listener> listenerCollection;
	
	//CTOR used to initiate the shell and display, and define the shell's dimensions and style.
	public BasicWindow(String title, int width, int height, int style) {
		this.display = new Display();
		this.shell = new Shell(display, style); //shells are fixed but can be minimized.
		shell.setSize(width, height);
		shell.setText(title);
		
		//Display the shell at the center of the monitor
	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	}
	
	//CTOR used to initiate the shell and display, and define the shell's dimensions.
	public BasicWindow(String title, int width, int height) {
		this.display = new Display();
		this.shell = new Shell(display); //shells are fixed but can be minimized.
		shell.setSize(width, height);
		shell.setText(title);
		
		//Display the shell at the center of the monitor
	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	}
	
	//CTOR used to initiate the shell and display, and define the shell's dimensions.
	public BasicWindow(String title, int width, int height, HashMap<String, Listener> listenerCollection) {
		this.display = new Display();
		this.shell = new Shell(display); //shells are fixed but can be minimized.
		this.shell.setSize(width, height);
		shell.setText(title);
		this.listenerCollection=listenerCollection;
		
		//Display the shell at the center of the monitor
	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	}
	
	/**
	 * every class that extends the BasicWindow needs to implement initWidgets,
	 * to initialize all the widgets it has and adding their
	 * event handlers.
	 */
	protected abstract void initWidgets();
	
	@Override
	public void run() {
		/**
		 * The following order is constant.
		 * First initWidget will initiate the widgets,
		 * Then we'll open the shell (window) and do the main loop
		 */
		
		initWidgets(); //initialize the shell's widgets
		shell.open(); //open the window (the shell)
		
		// main event loop
		 while(!shell.isDisposed()){ // window isn't closed

			 // 1. read events, put then in a queue.
			 // 2. dispatch the assigned listener
		  if(!display.readAndDispatch()){ // if the queue is empty
		   display.sleep();	// sleep until an event occurs 
		  }
		 }	// shell is disposed
		 display.dispose();	// dispose OS components
	}

}
