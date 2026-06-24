package gruntmanesMetode;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class WindowGUI extends JFrame { // extends initializes the class as a JFrame window
	
	public WindowGUI() { // window constructor
		setTitle(Main.APP_NAME);
        setSize(Main.WIDTH, Main.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        addGUIComponents(); // adding components after window setup
	}
	
	public void displayWindow() {
		setVisible(true);
	}
	
	public void addGUIComponents() {
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		
		JLabel title = new JLabel("Gruntmanes Metode");
		title.setFont(new Font("JetBrainsMono", Font.PLAIN, 24));
		
		panel.add(title);
		
		this.getContentPane().add(panel); // accessing the content pane to visually add the panel
	}
}
