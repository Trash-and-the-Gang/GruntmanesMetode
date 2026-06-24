package gruntmanesMetode;

import javax.swing.SwingUtilities;

//import gruntmanesMetode.WindowGUI;

public class Main {
	
	// defining constants for the superclass window
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String APP_NAME = "Gruntmanes Metode";
	

	public static void main(String[] args) {
		// using invokeLater for thread-safety (Swing Single Threading Rule)
		SwingUtilities.invokeLater( () -> {
			WindowGUI gui = new WindowGUI();
			gui.displayWindow();
		});
	}
}
