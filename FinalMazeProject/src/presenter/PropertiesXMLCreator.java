package presenter;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;

import view.GUI.BasicWindow;

/**
 * Quick setup window - asks user to choose main parameters before starting game,
 * and saves the parameters into a XML file.
 * @author Tomer
 *
 */
public class PropertiesXMLCreator extends BasicWindow {

	private Properties properties;
	private MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
	
	//Common colors
	private final Color black;
	private final Color white;
	private final Color cyan;

	public PropertiesXMLCreator(String title, int width, int height, int style) {
		super(title, width, height, style);
		
		//Common colors
		black = new Color(null, 0, 0, 0);
		white = new Color(null, 255, 255, 255);
		cyan = new Color(null, 30, 144, 255);
		
		shell.setBackground(cyan);

		messageBox.setText("Message");
		
		this.properties = new Properties(); //initiate properties variable
		
		//Setting shell margins
		GridLayout gl_shell = new GridLayout(3, false);
		gl_shell.marginBottom = 25;
		gl_shell.marginWidth = 0;
		gl_shell.marginHeight = 0;
		shell.setLayout(gl_shell);
	}

	@Override
	protected void initWidgets() {
		
		//Icon and Title
		Composite cmp_Logo = new Composite(shell, SWT.NONE);
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
		new Label(shell, SWT.NONE);

		//Quick Setup title
		Label lbl_QuickSetup = new Label(shell, SWT.NONE);
		GridData gd_Lbl_QuickSetup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_Lbl_QuickSetup.widthHint = 216;
		lbl_QuickSetup.setLayoutData(gd_Lbl_QuickSetup);
		lbl_QuickSetup.setBackground(cyan);
		lbl_QuickSetup.setForeground(white);
		lbl_QuickSetup.setFont(new Font(null, "Segoe UI Light", 24, SWT.NORMAL));
		lbl_QuickSetup.setText("Quick Setup");

		//Add margin to the left
		Label lbl_MarginLeft = new Label(shell, SWT.NONE);
		lbl_MarginLeft.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lbl_MarginLeft.setBackground(cyan);
		lbl_MarginLeft.setText("         ");

		//UI Section
		Label lbl_Img_UI = new Label(shell, SWT.NONE);
		lbl_Img_UI.setBackground(cyan);
		lbl_Img_UI.setImage(new Image(null, "resources/viewmode.png"));
		GridData gd_Lbl_Img_UI = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 2);
		gd_Lbl_Img_UI.heightHint = 79;
		gd_Lbl_Img_UI.widthHint = 83;
		lbl_Img_UI.setLayoutData(gd_Lbl_Img_UI);

		Label lbl_UI = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		lbl_UI.setForeground(white);
		lbl_UI.setBackground(cyan);
		GridData gd_Lbl_UI = new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1);
		gd_Lbl_UI.heightHint = 45;
		gd_Lbl_UI.widthHint = 378;
		lbl_UI.setLayoutData(gd_Lbl_UI);
		lbl_UI.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		lbl_UI.setText(" Game Viewing Mode");
		new Label(shell, SWT.NONE);

		//UI - Composite with buttons for GUI and CLI
		Composite cmp_UI = new Composite(shell, SWT.NONE);
		cmp_UI.setBackground(cyan);
		GridData gd_Cmp_UI = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_Cmp_UI.heightHint = 31;
		gd_Cmp_UI.widthHint = 447;
		cmp_UI.setLayoutData(gd_Cmp_UI);

		Label lbl_UI_GUI = new Label(cmp_UI, SWT.NONE);
		lbl_UI_GUI.setBackground(cyan);
		lbl_UI_GUI.setForeground(white);
		lbl_UI_GUI.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_UI_GUI.setBounds(35, -3, 111, 20);
		lbl_UI_GUI.setText("GUI Window");

		Label lbl_UI_CLI = new Label(cmp_UI, SWT.NONE);
		lbl_UI_CLI.setForeground(white);
		lbl_UI_CLI.setFont(new Font(null, "Segoe UI", 11, SWT.NORMAL));
		lbl_UI_CLI.setText("CLI Console");
		lbl_UI_CLI.setBackground(cyan);
		lbl_UI_CLI.setBounds(195, -3, 102, 20);

		Button btn_UI_GUI = new Button(cmp_UI, SWT.RADIO);
		btn_UI_GUI.setSelection(true);
		btn_UI_GUI.setBackground(cyan);
		btn_UI_GUI.setBounds(10, 0, 23, 20);

		Button btn_UI_CLI = new Button(cmp_UI, SWT.RADIO);
		btn_UI_CLI.setBackground(cyan);
		btn_UI_CLI.setBounds(170, 0, 16, 20);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		//Margin composite+label for better separate with the next section
		Composite cmp_Margin = new Composite(shell, SWT.NONE);
		cmp_Margin.setBackground(cyan);
		GridData gd_MarginComposite = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_MarginComposite.heightHint = 22;
		cmp_Margin.setLayoutData(gd_MarginComposite);
		new Label(shell, SWT.NONE);

		//Maze Generator Algorithm Section
		Label lbl_Img_Generate = new Label(shell, SWT.NONE);
		lbl_Img_Generate.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 2));
		lbl_Img_Generate.setImage(new Image(null, "resources/generate.png"));
		lbl_Img_Generate.setBackground(cyan);

		Label lbl_Generate = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		lbl_Generate.setBackground(cyan);
		lbl_Generate.setForeground(white);
		GridData gd_Lbl_Generate = new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1);
		gd_Lbl_Generate.widthHint = 403;
		gd_Lbl_Generate.heightHint = 47;
		lbl_Generate.setLayoutData(gd_Lbl_Generate);
		lbl_Generate.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		lbl_Generate.setText(" Maze Generator Algorithm            ");
		new Label(shell, SWT.NONE);

		//Generator Section - Composite with buttons for DFS and SimpleMazeGenerator
		Composite cmp_Generate = new Composite(shell, SWT.NONE);
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
		
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		//Margin composite+label for better separate with the next section
		Composite cmp_Space2 = new Composite(shell, SWT.NONE);
		GridData gd_Cmp_Space2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_Cmp_Space2.heightHint = 22;
		cmp_Space2.setLayoutData(gd_Cmp_Space2);
		cmp_Space2.setBackground(cyan);
		new Label(shell, SWT.NONE);

		//Maze Search Algorithm Section
		Label lbl_Img_Searcher = new Label(shell, SWT.NONE);
		GridData gd_Lbl_Img_Searcher = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 2);
		gd_Lbl_Img_Searcher.heightHint = 70;
		lbl_Img_Searcher.setLayoutData(gd_Lbl_Img_Searcher);
		lbl_Img_Searcher.setImage(new Image(null, "resources/searcher.png"));
		lbl_Img_Searcher.setBackground(cyan);

		Label lbl_Search = new Label(shell, SWT.READ_ONLY | SWT.BOTTOM);
		lbl_Search.setForeground(white);
		lbl_Search.setBackground(cyan);
		GridData gd_Lbl_Search = new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1);
		gd_Lbl_Search.heightHint = 48;
		lbl_Search.setLayoutData(gd_Lbl_Search);
		lbl_Search.setFont(new Font(null, "Segoe UI Semibold", 18, SWT.NORMAL));
		lbl_Search.setText(" Maze Search Algorithm");
		lbl_Search.setAlignment(SWT.CENTER);
		new Label(shell, SWT.NONE);

		//Searcher Section - Composite with buttons for DFS and BFS
		Composite cmp_Search = new Composite(shell, SWT.NONE);
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
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		//Margin composite+label for better separate with the next section
		Composite cmp_Space3 = new Composite(shell, SWT.NONE);
		GridData gd_Cmp_Space3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_Cmp_Space3.heightHint = 24;
		cmp_Space3.setLayoutData(gd_Cmp_Space3);
		cmp_Space3.setBackground(cyan);
		new Label(shell, SWT.NONE);

		//Save and apply button
		Button startButton = new Button(shell, SWT.NONE);
		startButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		startButton.setText("Save and Apply");

		startButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				XMLEncoder encoder = null;
				try {
					encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Properites.xml")));
				} catch (FileNotFoundException e1) {
					showMessage("Error", "File not found");
				}
				//Send the chosen UI to properties
				if (btn_UI_GUI.getSelection())
					properties.setUi("GUI");
				else
					properties.setUi("CLI");
				
				//Send the chosen generate algorithm to properties
				if (btn_Generate_DFS.getSelection())
					properties.setGenerateAlgorithm("MyMaze3dGenerator");
				else if (btn_Generate_Prim.getSelection())
					properties.setGenerateAlgorithm("Prim");
				else if (btn_Generate_Simple.getSelection())
					properties.setGenerateAlgorithm("SimpleMaze3dGenerator");
				
				//Send the chosen search algorithm to properties
				if (btn_Search_DFS.getSelection())
					properties.setSearchAlgorithm("DFS");
				else
					properties.setSearchAlgorithm("BFS");
				
				//Encode properties to XML and close procedure
				encoder.writeObject(properties);
				encoder.flush();
				showMessage("Saved successfully", "Properties set and saved successfully.");
				encoder.close();
				shell.close();	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	/**
	 * Show a String message inside message box.
	 * @param title
	 * @param message
	 */
	public void showMessage(String title, String message){
		messageBox.setText(title);
		messageBox.setMessage(message);
		messageBox.open();
	}

	/**
	 * Returns the properties object after user filled it up, in order to use it's properties in the Maze class.
	 * @return
	 */
	public Properties getProperties(){
		return properties;
	}
}
