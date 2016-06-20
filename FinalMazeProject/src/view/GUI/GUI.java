package view.GUI;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.search.Solution;
import maze.generators.Maze3d;
import presenter.Properties;
import view.View;

/**
 * Implements a GUI View shell to the 3D Maze game.
 * The GUI Class initializing the MazeDisplay shell's buttons,
 * their listeners and the keyboard keys listeners.
 * 
 * @author Yotam Levy & Tomer Brami
 *
 */
public class GUI extends BasicWindow implements View {

	/**
	 * Timer and TimerTask are necessary for randomizing the gameCharacter movements thread.
	 * @param timer
	 * @param task  
	 */
	Timer timer;
	TimerTask task;
	MazeDisplayer maze;
	Properties prop;
	private MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
	public int[][] mazeToPaint;
	private Maze3d maze3d;
	MenuItem solve;
	String n;
	private Solution solution;
	private boolean isStartedGame;
	String MazeFileSelectedName;

	Label lblHelp;
	Label lblFloor;
	Label lblAvailablefloor;

	final Color white = new Color(null, 255, 255, 255);
	final Color cyan = new Color(null, 30, 144, 255);
	final Color black = new Color(null, 0, 0, 0);
	/**
	 * CTor, initiates a window shell according to given basic parameters.
	 * @param title Shell's title
	 * @param width Shell's width
	 * @param height Shell's height
	 */
	public GUI(String title, int width, int height) {
		super(title, width, height);
		messageBox = new MessageBox(shell,  SWT.ICON_INFORMATION| SWT.OK);
	}

	/**
	 * initWidgets will show the MazeDisplay object.
	 */
	@Override
	protected void initWidgets() {

		shell.setLayout(new GridLayout(2, false));

		shell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				exitGame();
			}
		});
		//--- MENU BAR----
		// Create the bar menu
		Menu menuBar = new Menu(shell, SWT.BAR);

		//File menu
		Menu fileMenu = new Menu(menuBar);
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);

		// Create all the items in the File dropdown menu
		MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
		newItem.setText("Generate New Maze");
		MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
		openItem.setText("Open...");
		MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
		saveItem.setText("Save");
		new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem setupItem = new MenuItem(fileMenu, SWT.NONE);
		setupItem.setText("Quick Setup");
		new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("Exit");

		//Help Menu
		Menu helpMenu = new Menu(menuBar);
		MenuItem helpItem = new MenuItem(menuBar, SWT.CASCADE);
		helpItem.setText("Help");
		helpItem.setMenu(helpMenu);

		MenuItem solveItem = new MenuItem(helpMenu, SWT.NONE);
		solveItem.setText("Solve the maze");
		MenuItem aboutItem = new MenuItem(helpMenu, SWT.NONE);
		aboutItem.setText("About the game");
		shell.setMenuBar(menuBar);
		//------ MENU BAR'S LISTENERS ------/

		newItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				generateMaze();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		openItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				loadMaze();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		exitItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				exitGame();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


		solveItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				solveMaze();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		setupItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				quickSetup();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		saveItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String command = "save_maze " + n + " " + n + ".maz" ;
				setChanged();
				notifyObservers(command);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		aboutItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				aboutGame();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		//a Composite to group all the buttons of the 3D Maze Shell in one column
		Composite buttonsComposite = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new FillLayout(SWT.NONE));

		//------ MAZE's BUTTONS & PAINT: INSTANCES -------

		Button btnGenerate = new Button(buttonsComposite, SWT.PUSH);
		btnGenerate.setText("Generate Maze");
		//generateButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button btnSolve = new Button(buttonsComposite, SWT.PUSH);
		btnSolve.setText("Solve Maze");

		Button btnLoad = new Button(buttonsComposite, SWT.PUSH);
		btnLoad.setText("Load Maze");

		Button btnSave = new Button(buttonsComposite, SWT.PUSH);
		btnSave.setText("Save Maze");

		Button btnExit = new Button(buttonsComposite, SWT.PUSH);
		btnExit.setText("Exit");

		lblHelp = new Label(buttonsComposite, SWT.NONE);
		lblHelp.setFont(new Font(null, "Segoe UI Light", 10, SWT.NORMAL));
		lblHelp.setText("TIP:\nUse the Generate or Load\nbuttons for playing.");
		
		lblFloor = new Label(buttonsComposite, SWT.BOLD);
		lblFloor.setFont(new Font(null, "Segoe UI", 10, SWT.BOLD));
		lblFloor.setText("");
		
		lblAvailablefloor = new Label(buttonsComposite, SWT.NORMAL);
		lblAvailablefloor.setFont(new Font(null, "Segoe UI", 10, SWT.NORMAL));
		lblAvailablefloor.setText("");

		//IMPORTANT: Maze paint instance injection into window		
		maze = new Maze3D(shell, SWT.BORDER); //now when md's parent is shell, we know we have an instance of md in the shell window.
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));


		//------ MAZE's BUTTONS: LISTENERS -------

		/* "Generate Maze" button
		 * ----------------------
		 * opens a form shell for generating maze input from user.
		 */
		btnGenerate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				generateMaze();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		//Exit button terminates the whole program.
		btnExit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				exitGame();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		btnSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String command = "save_maze " + n + " " + n + ".maz" ;
				setChanged();
				notifyObservers(command);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0){}
		});

		btnLoad.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				loadMaze();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0){}
		});

		btnSolve.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0){
				solveMaze();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});	
	}


	//------ SETTERS & GETTERS --------
	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	@Override
	public void display(String string) {
		messageBox.setMessage(string);
		messageBox.open();
	}

	public void display(String title, String message){
		messageBox.setText(title);
		messageBox.setMessage(message);
		messageBox.open();
	}

	@Override
	public void display(String[] strings) {
		//Not in use for GUI.
	}


	@Override
	public void start() {
		this.run();
	}

	@Override
	public void setProperties(Properties prop) {
		this.prop = prop;
	}

	private void generateMaze(){
		Shell generateMazeShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);

		generateMazeShell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				shell.setEnabled(true);
			}
		});

		//Generate Maze shell's parameters
		generateMazeShell.setBackground(cyan);
		GridLayout shellGridLayout = new GridLayout(2, false);
		shellGridLayout.marginBottom = 7;
		shellGridLayout.marginWidth = 10;
		shellGridLayout.marginHeight = 7;
		generateMazeShell.setLayout(shellGridLayout);
		generateMazeShell.setText("Generate New Maze");

		GridData labels = new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1);
		GridData texts = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

		//"New Maze" title
		Label title = new Label(generateMazeShell, SWT.NONE);
		GridData titleGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		titleGridData.widthHint = 316;
		title.setLayoutData(titleGridData);
		title.setBackground(cyan);
		title.setForeground(white);
		title.setFont(new Font(null, "Segoe UI Light", 24, SWT.NORMAL));
		title.setText("New Maze");

		//Maze's name label
		Label nameLabel = new Label(generateMazeShell, SWT.NONE);
		nameLabel.setLayoutData(labels);
		nameLabel.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		nameLabel.setForeground(white);
		nameLabel.setBackground(cyan);
		nameLabel.setText("Name:");

		Text nameText = new Text(generateMazeShell, SWT.BORDER);
		nameText.setFont(new Font(null, "Segoe UI", 18, SWT.NORMAL));
		nameText.setBackground(white);
		nameText.setLayoutData(texts);

		//Margin composite+label for better separate with the next section
		Composite cmp_Margin3 = new Composite(generateMazeShell, SWT.NONE);
		cmp_Margin3.setBackground(cyan);
		GridData gd_MarginComposite3 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_MarginComposite3.heightHint = 5;
		cmp_Margin3.setLayoutData(gd_MarginComposite3);

		//Floors input
		Label floorsLabel = new Label(generateMazeShell, SWT.NONE);
		floorsLabel.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		floorsLabel.setForeground(white);
		floorsLabel.setBackground(cyan);
		floorsLabel.setText("Floors:");
		floorsLabel.setLayoutData(labels);

		Text floorsText = new Text(generateMazeShell, SWT.BORDER);
		floorsText.setFont(new Font(null, "Segoe UI", 18, SWT.NORMAL));
		floorsText.setBackground(white);
		floorsText.setLayoutData(texts);
		floorsText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
		//Margin composite+label for better separate with the next section
		Composite cmp_Margin2 = new Composite(generateMazeShell, SWT.NONE);
		cmp_Margin2.setBackground(cyan);
		GridData gd_MarginComposite2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_MarginComposite2.heightHint = 5;
		cmp_Margin2.setLayoutData(gd_MarginComposite2);
		new Label(shell, SWT.NONE);

		//Rows input
		Label rowsLabel = new Label(generateMazeShell, SWT.NONE);
		rowsLabel.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		rowsLabel.setForeground(white);
		rowsLabel.setBackground(cyan);
		rowsLabel.setText("Rows:");
		rowsLabel.setLayoutData(labels);

		Text rowsText = new Text(generateMazeShell, SWT.BORDER);
		rowsText.setFont(new Font(null, "Segoe UI", 18, SWT.NORMAL));
		rowsText.setBackground(white);
		rowsText.setLayoutData(texts);
		rowsText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
		//Margin composite+label for better separate with the next section
		Composite cmp_Margin = new Composite(generateMazeShell, SWT.NONE);
		cmp_Margin.setBackground(cyan);
		GridData gd_MarginComposite = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_MarginComposite.heightHint = 5;
		cmp_Margin.setLayoutData(gd_MarginComposite);
		new Label(shell, SWT.NONE);

		//Columns input
		Label colsLabel = new Label(generateMazeShell, SWT.NONE);
		colsLabel.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		colsLabel.setForeground(white);
		colsLabel.setBackground(cyan);
		colsLabel.setText("Columns: ");
		colsLabel.setLayoutData(labels);

		Text colsText = new Text(generateMazeShell, SWT.BORDER);
		colsText.setFont(new Font(null, "Segoe UI", 18, SWT.NORMAL));
		colsText.setBackground(white);
		colsText.setLayoutData(texts);
		colsText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		//Margin composite+label for better separate with the next section
		Composite cmp_Margin4 = new Composite(generateMazeShell, SWT.NONE);
		cmp_Margin4.setBackground(cyan);
		GridData gd_MarginComposite4 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_MarginComposite4.heightHint = 5;
		cmp_Margin4.setLayoutData(gd_MarginComposite4);
		new Label(shell, SWT.NONE);

		Button generateButton = new Button(generateMazeShell, SWT.PUSH);
		generateButton.setText("Generate");
		generateButton.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1));

		generateButton.addSelectionListener(new SelectionListener()
		{						
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				String command = "generate_maze_3d ";
				n = nameText.getText();
				command +=  n + ' ' + floorsText.getText() + ' ' + rowsText.getText() + ' ' + colsText.getText();
				setChanged();
				notifyObservers(command);
				display("Maze " + n + " is created succesfully by " + prop.getGenerateAlgorithm() + "!");
				generateMazeShell.close();
				shell.setEnabled(true);
				lblHelp.setText("\nTIP: Use arrow keys or\nPage Up\\Page Down");
				displayMaze();
				lblFloor.setText("\nYou are at floor " + maze.getCharacterPosition().getX() + " of " + maze3d.getColumns() + ".");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		generateMazeShell.pack();
		generateMazeShell.open();

	}

	private void displayMaze(){
		if (n == null)
		{
			display("You must generate or load a maze first.");
			isStartedGame = false;
			return;
		}
		else{
			String command = "display_maze " + n;
			setChanged();
			notifyObservers(command);

			mazeToPaint = maze3d.getCrossSectionByX(maze3d.getStartPosition().getX());
			maze.setMazeData(mazeToPaint);

			maze.setCurrentMaze(maze3d);         
			//maze.setCharacterPosition(maze3d.getStartPosition().getY(), maze3d.getStartPosition().getZ());
			maze.redraw();
			isStartedGame = true;
			maze.forceFocus();


			maze.addKeyListener(new KeyListener() {

				@Override
				public void keyReleased(KeyEvent arg0) {
					lblFloor.setText("\nYou are at floor " + maze.getCharacterPosition().getX() + " of " + maze3d.getColumns() + ".");
					if (maze.getCharacterPosition().getX()+1 < maze3d.getColumns() && maze.getCharacterPosition().getX()-1 >= 0){

						if (maze3d.getPositionValueInts(maze.getCharacterPosition().getX()+1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 0 && maze3d.getPositionValueInts(maze.getCharacterPosition().getX()-1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 1)
							lblAvailablefloor.setText("You can go up");
						else if (maze3d.getPositionValueInts(maze.getCharacterPosition().getX()+1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 1 && maze3d.getPositionValueInts(maze.getCharacterPosition().getX()-1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 1)
							lblAvailablefloor.setText("");

						if (maze3d.getPositionValueInts(maze.getCharacterPosition().getX()-1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 0 && maze3d.getPositionValueInts(maze.getCharacterPosition().getX()+1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 0)
							lblAvailablefloor.setText("You can go up\\down");

						else if (maze3d.getPositionValueInts(maze.getCharacterPosition().getX()-1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 0 && maze3d.getPositionValueInts(maze.getCharacterPosition().getX()+1, maze.getCharacterPosition().getY(), maze.getCharacterPosition().getZ()) == 1)
							lblAvailablefloor.setText("You can go down");
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {

					switch (e.keyCode) {
					case SWT.ARROW_LEFT:
						maze.move(Maze3D.LEFT);
						break;
					case SWT.ARROW_RIGHT:
						maze.move(Maze3D.RIGHT);
						break;
					case SWT.ARROW_UP:
						maze.move(Maze3D.UP);
						break;
					case SWT.ARROW_DOWN:
						maze.move(Maze3D.DOWN);
						break;
					case SWT.PAGE_UP:
						maze.move(Maze3D.UPWARDS);
						break;
					case SWT.PAGE_DOWN:
						maze.move(Maze3D.DOWNWARDS);
						break;

					}
					if(maze.getCharacterPosition().equals(maze3d.getGoalPosition())){
						display("You Won!!!");
					}
				}
			});
		}
	}

	private void loadMaze(){
		FileDialog fd = new FileDialog(shell);
		fd.setText("Load Maze");
		fd.setFilterPath("files");
		String [] filterExt = {"*.maz"};
		fd.setFilterExtensions(filterExt);
		String fileSelected = fd.open();
		if (fileSelected == null) 
		{
			display("No file selected.\n");
			return;
		}

		String fileNameWithMAZ = fd.getFileName();
		String tmpToMaze[] = fileNameWithMAZ.split(".maz");
		MazeFileSelectedName = tmpToMaze[0];

		System.out.println(MazeFileSelectedName);
		System.out.println(fileNameWithMAZ);
		setChanged();
		String command = "load_maze " + fileNameWithMAZ + " " + MazeFileSelectedName;
		System.out.println(command);
		setChanged();
		notifyObservers(command);
		n = MazeFileSelectedName;
		displayMaze();
	}

	private void exitGame(){
		String command = "exit";
		setChanged();
		notifyObservers(command);
		shell.dispose();
	}

	private void solveMaze(){
		if (n == null){
			display("Please generate or load a maze first.\n");
			return;
		}
		String command = "solve" + " " + n;
		setChanged();
		notifyObservers(command);
		maze.solveTheMaze(solution);
		display("Solving by " + prop.getSearchAlgorithm());
	}

	private void quickSetup(){
		Shell quickSetupShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM | SWT.CLOSE | SWT.TITLE | SWT.MIN);

		GridLayout gl_shell = new GridLayout(3, false);
		gl_shell.marginBottom = 25;
		gl_shell.marginWidth = 0;
		gl_shell.marginHeight = 0;
		quickSetupShell.setLayout(gl_shell);
		quickSetupShell.setBackground(cyan);
		//Icon and Title
		Composite cmp_Logo = new Composite(quickSetupShell, SWT.NONE);
		GridData gl_Cmp_Logo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gl_Cmp_Logo.heightHint = 95;
		gl_Cmp_Logo.widthHint = 536;
		cmp_Logo.setLayoutData(gl_Cmp_Logo);
		cmp_Logo.setBackground(black);

		Label lbl_Img_TitleIcon = new Label(cmp_Logo, SWT.NONE);
		lbl_Img_TitleIcon.setBounds(10, 0, 90, 95);
		lbl_Img_TitleIcon.setBackground(black);
		lbl_Img_TitleIcon.setImage(new Image(display, "resources/icon.png"));

		Label lbl_Img_TitleLogo = new Label(cmp_Logo, SWT.CENTER);
		lbl_Img_TitleLogo.setImage(new Image(display, "resources/logo.png"));
		lbl_Img_TitleLogo.setBackground(black);
		lbl_Img_TitleLogo.setBounds(106, 0, 412, 95);
		new Label(quickSetupShell, SWT.NONE);

		//Quick Setup title
		Label lbl_QuickSetup = new Label(quickSetupShell, SWT.NONE);
		GridData gd_Lbl_QuickSetup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_Lbl_QuickSetup.widthHint = 216;
		lbl_QuickSetup.setLayoutData(gd_Lbl_QuickSetup);
		lbl_QuickSetup.setBackground(cyan);
		lbl_QuickSetup.setForeground(white);
		lbl_QuickSetup.setFont(new Font(null, "Segoe UI Light", 24, SWT.NORMAL));
		lbl_QuickSetup.setText("Quick Setup");

		//Add margin to the left
		Label lbl_MarginLeft = new Label(quickSetupShell, SWT.NONE);
		lbl_MarginLeft.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lbl_MarginLeft.setBackground(cyan);
		lbl_MarginLeft.setText("         ");

		//UI Section - not in use here
		new Label(quickSetupShell, SWT.NONE);

		//Margin composite+label for better separate with the next section
		Composite cmp_Margin = new Composite(quickSetupShell, SWT.NONE);
		cmp_Margin.setBackground(cyan);
		GridData gd_MarginComposite = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_MarginComposite.heightHint = 22;
		cmp_Margin.setLayoutData(gd_MarginComposite);
		new Label(quickSetupShell, SWT.NONE);

		//Maze Generator Algorithm Section
		Label lbl_Img_Generate = new Label(quickSetupShell, SWT.NONE);
		lbl_Img_Generate.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 2));
		lbl_Img_Generate.setImage(new Image(null, "resources/generate.png"));
		lbl_Img_Generate.setBackground(cyan);

		Label lbl_Generate = new Label(quickSetupShell, SWT.READ_ONLY | SWT.BOTTOM);
		lbl_Generate.setBackground(cyan);
		lbl_Generate.setForeground(white);
		GridData gd_Lbl_Generate = new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1);
		gd_Lbl_Generate.widthHint = 403;
		gd_Lbl_Generate.heightHint = 47;
		lbl_Generate.setLayoutData(gd_Lbl_Generate);
		lbl_Generate.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		lbl_Generate.setText(" Maze Generator Algorithm            ");
		new Label(quickSetupShell, SWT.NONE);

		//Generator Section - Composite with buttons for DFS and SimpleMazeGenerator
		Composite cmp_Generate = new Composite(quickSetupShell, SWT.NONE);
		GridData gd_Cmp_Generate = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_Cmp_Generate.heightHint = 29;
		cmp_Generate.setLayoutData(gd_Cmp_Generate);
		cmp_Generate.setBackground(cyan);

		Button btn_Generate_DFS = new Button(cmp_Generate, SWT.RADIO);
		btn_Generate_DFS.setSelection(true);
		btn_Generate_DFS.setBackground(cyan);
		btn_Generate_DFS.setBounds(10, 0, 16, 20);

		Button btn_Generate_Prim = new Button(cmp_Generate, SWT.RADIO);
		btn_Generate_Prim.setBackground(cyan);
		btn_Generate_Prim.setBounds(110, 0, 16, 20);

		Button btn_Generate_Simple = new Button(cmp_Generate, SWT.RADIO);
		btn_Generate_Simple.setBackground(cyan);
		btn_Generate_Simple.setBounds(225, 0, 16, 20);

		Label lbl_Generate_DFS = new Label(cmp_Generate, SWT.NONE);
		lbl_Generate_DFS.setText("DFS");
		lbl_Generate_DFS.setForeground(white);
		lbl_Generate_DFS.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_Generate_DFS.setBackground(cyan);
		lbl_Generate_DFS.setBounds(32, -3, 65, 23);

		Label lbl_Generate_Prim = new Label(cmp_Generate, SWT.NONE);
		lbl_Generate_Prim.setText("Prim");
		lbl_Generate_Prim.setForeground(white);
		lbl_Generate_Prim.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_Generate_Prim.setBackground(cyan);
		lbl_Generate_Prim.setBounds(132, -3, 65, 23);

		Label lbl_Generate_Simple = new Label(cmp_Generate, SWT.NONE);
		lbl_Generate_Simple.setText("Simple");
		lbl_Generate_Simple.setForeground(white);
		lbl_Generate_Simple.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_Generate_Simple.setBackground(cyan);
		lbl_Generate_Simple.setBounds(250, -3, 77, 26);

		new Label(quickSetupShell, SWT.NONE);
		new Label(quickSetupShell, SWT.NONE);

		//Margin composite+label for better separate with the next section
		Composite cmp_Space2 = new Composite(quickSetupShell, SWT.NONE);
		GridData gd_Cmp_Space2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_Cmp_Space2.heightHint = 22;
		cmp_Space2.setLayoutData(gd_Cmp_Space2);
		cmp_Space2.setBackground(cyan);
		new Label(quickSetupShell, SWT.NONE);

		//Maze Search Algorithm Section
		Label lbl_Img_Searcher = new Label(quickSetupShell, SWT.NONE);
		GridData gd_Lbl_Img_Searcher = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 2);
		gd_Lbl_Img_Searcher.heightHint = 70;
		lbl_Img_Searcher.setLayoutData(gd_Lbl_Img_Searcher);
		lbl_Img_Searcher.setImage(new Image(null, "resources/searcher.png"));
		lbl_Img_Searcher.setBackground(cyan);

		Label lbl_Search = new Label(quickSetupShell, SWT.READ_ONLY | SWT.BOTTOM);
		lbl_Search.setForeground(white);
		lbl_Search.setBackground(cyan);
		GridData gd_Lbl_Search = new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1);
		gd_Lbl_Search.heightHint = 48;
		lbl_Search.setLayoutData(gd_Lbl_Search);
		lbl_Search.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		lbl_Search.setText(" Maze Search Algorithm");
		lbl_Search.setAlignment(SWT.CENTER);
		new Label(quickSetupShell, SWT.NONE);

		//Searcher Section - Composite with buttons for DFS and BFS
		Composite cmp_Search = new Composite(quickSetupShell, SWT.NONE);
		GridData gd_Cmp_Search = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_Cmp_Search.heightHint = 32;
		cmp_Search.setLayoutData(gd_Cmp_Search);
		cmp_Search.setBackground(cyan);

		Button btn_Search_DFS = new Button(cmp_Search, SWT.RADIO);
		btn_Search_DFS.setSelection(true);
		btn_Search_DFS.setBackground(cyan);
		btn_Search_DFS.setBounds(10, 0, 16, 20);

		Button btn_Search_BFS = new Button(cmp_Search, SWT.RADIO);
		btn_Search_BFS.setBackground(cyan);
		btn_Search_BFS.setBounds(170, 0, 16, 20);

		Label lbl_Search_DFS = new Label(cmp_Search, SWT.NONE);
		lbl_Search_DFS.setText("DFS");
		lbl_Search_DFS.setForeground(white);
		lbl_Search_DFS.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_Search_DFS.setBackground(cyan);
		lbl_Search_DFS.setBounds(32, -3, 55, 23);

		Label lbl_Search_BFS = new Label(cmp_Search, SWT.NONE);
		lbl_Search_BFS.setText("BFS");
		lbl_Search_BFS.setForeground(white);
		lbl_Search_BFS.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_Search_BFS.setBackground(cyan);
		lbl_Search_BFS.setBounds(195, -3, 77, 26);
		new Label(quickSetupShell, SWT.NONE);
		new Label(quickSetupShell, SWT.NONE);

		//Margin composite+label for better separate with the next section
		Composite cmp_Space3 = new Composite(quickSetupShell, SWT.NONE);
		GridData gd_Cmp_Space3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_Cmp_Space3.heightHint = 24;
		cmp_Space3.setLayoutData(gd_Cmp_Space3);
		cmp_Space3.setBackground(cyan);
		new Label(quickSetupShell, SWT.NONE);

		//Save and apply button
		Button startButton = new Button(quickSetupShell, SWT.NONE);
		startButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		startButton.setText("Save and Apply");

		startButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				XMLEncoder encoder = null;
				try {
					encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Properites.xml")));
				} catch (FileNotFoundException e1) {
					display("Error", "File not found");
				}
				prop.setUi("GUI");
				//Send the chosen generate algorithm to prop
				if (btn_Generate_DFS.getSelection())
					prop.setGenerateAlgorithm("MyMaze3dGenerator");
				else if (btn_Generate_Prim.getSelection())
					prop.setGenerateAlgorithm("Prim");
				else if (btn_Generate_Simple.getSelection())
					prop.setGenerateAlgorithm("SimpleMaze3dGenerator");

				//Send the chosen search algorithm to prop
				if (btn_Search_DFS.getSelection())
					prop.setSearchAlgorithm("DFS");
				else
					prop.setSearchAlgorithm("BFS");

				//Encode prop to XML and close procedure

				encoder.writeObject(prop);
				encoder.flush();
				display("Saved successfully", "prop set and saved successfully.");
				encoder.close();
				quickSetupShell.close();
				shell.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		quickSetupShell.pack();
		quickSetupShell.open();
	}

	private void aboutGame(){
		display("About", "Project Maze3D\n\nCoded and Designed by: Yotam Levy and Tomer Brami.\n\nThis project is part of Algorithmic Programming in Java course,\nThe College of Management, Israel\n\nJune 2016");
	}


}
