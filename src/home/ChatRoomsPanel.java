package home;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import chat.ChatRoom;
import main.MainFrame;
import protocol.Protocol;

public class ChatRoomsPanel extends JPanel {

	private MainFrame mainFrame;
	private Vector<ChatRoom> rooms;

	public ChatRoomsPanel(MainFrame mainFrame) {
		System.out.println("FriendsPanel");
		this.mainFrame = mainFrame;
		setBounds(0, 200, 300, 500);
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));
	}
	
	
	
	@Override
	public void print(Graphics g) {
		super.print(g);
	}
	
	public void setChatRooms() {
		mainFrame.getReceiveThread().conversationListRequest();
//		rooms = (Vector<ChatRoom>)mainFrame.getReceiveThread().getConversation();
//		debug.Debug.log("ChatRoomsPanel Send : CONVERSATION_REQUEST");
	}

	
	
}
