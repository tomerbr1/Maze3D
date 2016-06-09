package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.MyController;
import model.MyModel;
import view.MyView;

public class Run {

	public static void main(String[] args) {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		MyModel model = new MyModel();
		MyView view = new MyView(in, out);
		MyController controller = new MyController(model, view);	

		view.setController(controller);
		model.setController(controller);

		view.start();

	}

}
