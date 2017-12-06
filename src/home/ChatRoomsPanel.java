package home;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chat.ChatRoom;
import main.MainFrame;
import protocol.Protocol;

public class ChatRoomsPanel extends JPanel {

	private MainFrame mainFrame;
	private Vector<ChatRoom> rooms;
	private Vector<ChatRoomListPanel> chatRoomListPanels;
	private int height = 30;
	
	private JLabel chatroomname;

	public ChatRoomsPanel(MainFrame mainFrame) {
		System.out.println("ChaatRoomsPanel");
		this.mainFrame = mainFrame;
		this.rooms = mainFrame.getChatRooms();
		setBounds(0, 200, 300, 500);
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));
		
		chatRoomListPanels = new Vector<ChatRoomListPanel>();
		/*for(int i=0; i<rooms.size(); i++) {
			System.out.println("yunjae rooms size = " + rooms.size());
			ChatRoom room = rooms.get(i);
			roomsPanels.add(new ChatRoomListPanel(mainFrame, room));
		}*/
		
		UIinit();
	}
	
	public void setChatRoom(Vector<ChatRoom> rooms) {
		if(this.rooms != null)
			this.rooms.removeAllElements();
		for(int i=0; i<chatRoomListPanels.size(); i++) {
			this.remove(chatRoomListPanels.get(i));
		}
		chatRoomListPanels = new Vector<ChatRoomListPanel>();
		this.rooms = rooms;
		drawListPanel(rooms);
	}
	
	public void drawListPanel(Vector<ChatRoom> rooms) {
		height = 30;
		for(int i=0; i<rooms.size(); i++) {
			ChatRoom room = rooms.get(i);
			chatRoomListPanels.add(new ChatRoomListPanel(mainFrame, room));
		}
		addChatRoomListPanels(chatRoomListPanels);
		invalidate();
		repaint();
	}
	
	public void UIinit() {
		chatroomname = new JLabel("´ëÈ­¹æ");
		chatroomname.setBounds(10, height, 300, 15);
		add(chatroomname);
		
		
		
		//addChatRoomListPanels(roomsPanels);
		repaint();
		
	}
	//À±Àç
	public ChatRoomListPanel getChatRoomListPanel(int id) {
		for(int i=0; i<chatRoomListPanels.size(); i++) {
			if(chatRoomListPanels.get(i).getRoomId() == id)
				return chatRoomListPanels.get(i);
		}
		return null;
	}
	
	public Vector<ChatRoom> getVectorChatRoom() {
		return rooms;
	}
	public void addChatRoomListPanels(Vector<ChatRoomListPanel> roomsPanels) {
		System.out.println(roomsPanels.size());
		for (int i = 0; i < roomsPanels.size(); i++) {
			roomsPanels.get(i).setBounds(0, height + 15, 300, 70);
			roomsPanels.get(i).addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					e.getComponent().setBackground(new Color(240, 244, 248));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					e.getComponent().setBackground(new Color(199, 237, 252));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			add(roomsPanels.get(i));
			height += 70;
			//System.out.println(" height = " + height);
		}
		invalidate();
		repaint();
	}
	
	@Override
	public void print(Graphics g) {
		super.print(g);
	}
	
	
	public void setChatRooms() {
		if(rooms != null) 
			this.rooms.removeAllElements();
		mainFrame.getReceiveThread().conversationListRequest();
		this.rooms = mainFrame.getChatRooms();
//		rooms = (Vector<ChatRoom>)mainFrame.getReceiveThread().getConversation();
//		debug.Debug.log("ChatRoomsPanel Send : CONVERSATION_REQUEST");
	}
	
}
