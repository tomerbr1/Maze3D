package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Controller;
import controller.commands.Command;

public abstract class CommonView implements View {

	protected BufferedReader in;
	protected PrintWriter out;
	protected Controller controller;
	
	public abstract void start();
	public abstract void displayMessage(String message);
	public abstract void displayMessage(String[] messages);
	public abstract void setCommands(HashMap<String, Command> commands);

	public CommonView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void setController(Controller controller){
		this.controller=controller;
	}

	@Override
	public void exit() {
		out.write("Exiting...\n");
	}
}
