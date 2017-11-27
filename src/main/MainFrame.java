package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

import home.BoardPanel;
import home.Home;
import login.Login;
import user.UserInfo;

public class MainFrame extends JFrame {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	public static final String SERVER_IP = "169.254.81.49";
	public static final int SERVER_PORT = 9000;
	private Container contentPane;
	private Home home;
	private UserInfo user;
	private Login login;

	public MainFrame() {
		super("HansungSkype");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - WIDTH) / 2, (dim.height - HEIGHT) / 2);
		setSize(WIDTH, HEIGHT);
		login = new Login(this);
		setContentPane(login);
		setResizable(false);
		setVisible(true);
	}
	
	public void setHome(Home home) {
		this.home = home;
	}
	
	public Home getHome() {
		return home;
	}
	
	public void createUser(String id, String pw, String name, String stateMsg, String image) {
		user = new UserInfo(id, pw, name, stateMsg, image);
	}
	
	public UserInfo getUser() {
		return user;
	}

	public Login getLogin() {
		return login;
	}

}
