package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.MyCLIView;

public class Run {

	public static void main(String[] args) {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		MyModel model = new MyModel();
		MyCLIView view = new MyCLIView(in, out);
		Presenter presenter = new Presenter(model, view);	

		//Adding the presenter as observer for changes in the model and view.
		view.addObserver(presenter);
		model.addObserver(presenter);

		view.start();
	}

}
