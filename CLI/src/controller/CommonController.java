package controller;

import model.Model;
import view.View;

/**
 * Implements what every controller needs to do.
 * @author Tomer
 *
 */
public abstract class CommonController implements Controller {

	protected Model model;
	protected View view;
	
	public CommonController(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void setView(View view) {
		this.view = view;
	}
	
	@Override
	public void displayMessage(String[] strings){
		view.displayMessage(strings);
	}
	
	@Override
	public void displayMessage(String string){
		view.displayMessage(string);
	}
}
