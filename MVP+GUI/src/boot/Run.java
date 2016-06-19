package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import org.eclipse.swt.SWT;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import presenter.PropertiesXMLCreator;
import view.View;
import view.CLI.MyView;
import view.GUI.GUI;
import view.GUI.GUI;

public class Run {

	public static void main(String[] args) {

		Properties prop = new Properties();
		PropertiesXMLCreator propCreator = new PropertiesXMLCreator("3D Maze - Quick Setup", 540, 590, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		propCreator.run();
		prop = propCreator.getProperties();

		MyModel model = new MyModel();
		View view = null;

		if (prop.getUi().equals("GUI"))
		{
			view = new GUI("3D Maze Game", 1024, 768);
			view.setProperties(prop);
			Presenter presenter = new Presenter(model, view, prop);
			
			//Adding the presenter as observer for changes in the model and view.
			((Observable) view).addObserver(presenter);
			model.addObserver(presenter);
			
			view.start();

		}
		else if (prop.getUi().equals("CLI"))
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(System.out);
			
			view = new MyView(in, out);
			Presenter presenter = new Presenter(model, view, prop);	

			//Adding the presenter as observer for changes in the model and view.
			((MyView)view).addObserver(presenter);
			model.addObserver(presenter);

			view.start();
		}
	}

}
