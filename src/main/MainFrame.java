package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;

import chat.ChatRoom;
import home.BoardPanel;
import home.Home;
import login.Login;
import sns.SNS;
import thread.ReceiveThread;
import database.UserInfo;

public class MainFrame extends JFrame {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	public static final String SERVER_IP = "223.194.156.88";
	public static final int SERVER_PORT = 9000;
	private Container contentPane;
	private Home home;
	private UserInfo user;
	private Vector<UserInfo> users;
	private Login login;
	private ReceiveThread receiveThread = null;
	private Vector<ChatRoom> rooms;
	private Vector<SNS> snss;

	public MainFrame() {
		super("HansungSkype");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		// rooms = new Vector<ChatRoom>();
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

	public void createUser(UserInfo user) {
		this.user = user;
	}

	public UserInfo getUser() {
		return user;
	}

	public Login getLogin() {
		return login;
	}

	public void setUsers(Vector<UserInfo> users) {
		this.users = users;
	}

	public Vector<UserInfo> getUsers() {
		return users;
	}

	public void setReceiveThread(ReceiveThread receiveThread) {
		this.receiveThread = receiveThread;
	}

	public ReceiveThread getReceiveThread() {
		return receiveThread;
	}

	public void addRoom(ChatRoom chatRoom) {
		rooms.add(chatRoom);
	}

	public ChatRoom getChatRoom(int RoomId) {
		if (rooms != null) {
			for (int i = 0; i < rooms.size(); i++) {
				if (rooms.get(i).getRoomId() == RoomId)
					return rooms.get(i);
			}
		}
		return null;
	}

	public Vector<ChatRoom> getChatRooms() {
		return rooms;
	}

	public void setChatRooms(Vector<ChatRoom> rooms) {
		this.rooms = rooms;
	}

	public String getUserName(String id) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId().equals(id))
				return users.get(i).getName();
		}
		return null;
	}

	public ChatRoom getChatRoom(String names) {
		if (rooms != null) {
			for (int i = 0; i < rooms.size(); i++) {
				if (rooms.get(i).getNames().equals(names))
					return rooms.get(i);
			}
		}
		return null;
	}
	
	public String getId() {
		return user.getId();
	}
	
	public UserInfo getUser(String id) {
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).getId().equals(id)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public Vector<SNS> getListSNS() {
		//return receiveThread.getListSNS();
		return snss;
	}
	
	public void setListSNS(Vector<SNS> snss) {
		this.snss = snss;
	}

}
