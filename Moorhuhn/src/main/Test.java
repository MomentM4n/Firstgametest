package main;

import javax.swing.SwingUtilities;

import gui.AppFrame;
import gui.Start;

public class Test {

	private static Test testInstance = null;
	
	public static void main(String[] args) {
		
		AppFrame app = new AppFrame();
		app.initGameWindow();
	}
	

}
