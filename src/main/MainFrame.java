package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

import home.BoardPanel;
import home.Home;
import login.Login;

public class MainFrame extends JFrame {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	private Container contentPane;
	private Home home;
	

	public MainFrame() {
		super("HansungSkype");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - WIDTH) / 2, (dim.height - HEIGHT) / 2);
		setSize(WIDTH, HEIGHT);
		
		setContentPane(new Login(this));
		setResizable(false);
		setVisible(true);
	}
	
	public void setHome(Home home) {
		this.home = home;
	}
	
	public Home getHome() {
		return home;
	}

}
