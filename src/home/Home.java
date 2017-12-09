package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chat.ChatRoom;
import main.MainFrame;
import database.UserInfo;

public class Home extends JPanel {
	
	private MainFrame mainFrame;
	private ProfilePanel profilePanel;
	private FriendsPanel friendsPanel;
	private BoardPanel boardPanel;
	private MenuPanel menuPanel;
	private ListSelectPanel listSelectPanel;
	private ChatRoomsPanel chatRoomsPanel;
	private JScrollPane scroll;
	private FriendsListBoardPanel friendsListBoardPanel = null;
	private JPanel selectedBoardPanel; 
	private Vector<ChatRoom> rooms = null;
	
	private UserInfo user;
	
	public Home(MainFrame mainFrame, UserInfo user) {
		this.mainFrame = mainFrame;
		this.user = mainFrame.getUser();
		setBackground(Color.MAGENTA);
		setLayout(null);
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		init();
	}
	
	public void init() {
		profilePanel = new ProfilePanel(mainFrame);
		friendsPanel = new FriendsPanel(mainFrame);
		boardPanel = new BoardPanel(mainFrame);
		menuPanel = new MenuPanel(mainFrame);
		listSelectPanel = new ListSelectPanel(mainFrame);
		chatRoomsPanel = new ChatRoomsPanel(mainFrame);
		add(profilePanel);
		
		//scroll = new JScrollPane(friendsPanel);
		//scroll.setBounds(0, 200, 300, 500);
		//add(scroll);
		
		
		add(friendsPanel);
		
		add(boardPanel);
		selectedBoardPanel = boardPanel;
		add(menuPanel);
		add(listSelectPanel);
	}
	
	public ProfilePanel getProfilePanel() {
		return profilePanel;
	}
	
	/*public void setChatRooms(Vector<ChatRoom> rooms) {
		//this.rooms = rooms;
		chatRoomsPanel.setChatRoom(rooms);
	}*/
	
	public Vector<ChatRoom> getChatRooms() {
		return rooms;
	}
	
/*	public void setChatRoomsPanel(Vector<ChatRoom> rooms) {
		chatRoomsPanel.setVectorRooms(rooms);
	}*/
	
	public void selectedChatRooms() {
		remove(friendsPanel);
		add(chatRoomsPanel);
		invalidate();
		repaint();
		chatRoomsPanel.repaint();
		System.out.println("chatRoomsPanel");
	}
	
	public void selectedFriends() {
		remove(chatRoomsPanel);
		add(friendsPanel);
		invalidate();
		repaint();
		System.out.println("friendsPanel");
	}
	
	public void setBoard(FriendsListBoardPanel f) {
		remove(selectedBoardPanel);
		selectedBoardPanel = f;
		add(selectedBoardPanel);
		repaint();
	}
	
	public void setBorad() {
		remove(selectedBoardPanel);
		selectedBoardPanel = boardPanel;
		add(boardPanel);
		repaint();
	}
	
	public JPanel getBoard() {
		return selectedBoardPanel;
	}
	
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
	
	public FriendsListBoardPanel getFriendsListBoardPanel() {
		if(selectedBoardPanel instanceof FriendsListBoardPanel)
			return (FriendsListBoardPanel) selectedBoardPanel;
		else {
			return null;
		}
	}
	
	public MultiChatBoardPanel getMultiChatBoardPanel() {
		if(selectedBoardPanel instanceof MultiChatBoardPanel)
			return (MultiChatBoardPanel) selectedBoardPanel;
		else {
			return null;
		}
	}
	
	public FriendsPanel getFriendsPanel() {
		return friendsPanel;
	}

	public ChatRoomsPanel getChatRoomsPanel() {
		return chatRoomsPanel;
	}

	public void setBoard(MultiChatBoardPanel multiChatBoardPanel) {
		remove(selectedBoardPanel);
		selectedBoardPanel = multiChatBoardPanel;
		add(multiChatBoardPanel);
		repaint();
	}

}
