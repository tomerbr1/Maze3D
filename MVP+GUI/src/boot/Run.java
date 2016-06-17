package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import presenter.PropertiesXMLCreator;
import view.CLI.MyView;
import view.GUI.MazeWindow;

public class Run {

	public static void main(String[] args) {

		Properties prop = new Properties();
		PropertiesXMLCreator propCreator = new PropertiesXMLCreator("3D Maze - Quick Setup", 540, 590);
		propCreator.run();
		prop = propCreator.getProperties();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		MyModel model = new MyModel();

		if (prop.getUi().equals("GUI"))
		{
			MazeWindow mw = new MazeWindow("3D Maze Game", 1024, 768);
			mw.run();

		}
		else if (prop.getUi().equals("CLI"))
		{
			MyView view = new MyView(in, out);
			Presenter presenter = new Presenter(model, view, prop);	

			//Adding the presenter as observer for changes in the model and view.
			view.addObserver(presenter);
			model.addObserver(presenter);

			view.start();
		}
	}

}
