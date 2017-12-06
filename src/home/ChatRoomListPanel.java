package home;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import chat.ChatRoom;
import database.UserInfo;
import font.AppFont;
import home.FriendsListPanel.FriendsListMouseActionListener;
import home.FriendsListPanel.MenuActionListener;
import main.MainFrame;

public class ChatRoomListPanel extends JPanel {
	private MainFrame mainFrame;
	// private Vector<ChatRoom> rooms;
	private ChatRoom room;
	private AppFont appFont;
	private Vector<String> messages;
	private JPopupMenu popup;

	private ImageIcon profileimg;

	private JLabel profile;
	private JLabel roomName;
	private JLabel message;

	private String names;
	private String message_str;
	private String[] buffer;
	private String latestMessage;
	private int roomId;

	private ChanRoomListMouseActionListener listener;

	public ChatRoomListPanel(MainFrame mainFrame, ChatRoom room) {
		System.out.println("ChatRoomListPanel");
		this.mainFrame = mainFrame;
		this.room = room;
		messages = room.getChatMessages();
		//debug.Debug.log(messages.toString());
		//System.out.println("messages = " + messages);
		names = room.getNames();
		roomId = room.getRoomId();

		setLayout(null);

		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));

		listener = new ChanRoomListMouseActionListener();

		UIInit();
		menuLayout();
		this.addMouseListener(listener);

		setVisible(true);

		invalidate();
		repaint();
	}

	public String getName() {
		return room.getNames();
	}

	public String latestMessage() {
		message_str = messages.get(messages.size() - 1);
		buffer = message_str.split("::::");
		latestMessage = buffer[3];
		//System.out.println("yunjae message_str = " + message_str + "      latestMessage = "+latestMessage);
		return latestMessage;

	}

	public void setLatestMessage(ChatRoom room) {
		this.room = room;
		messages = room.getChatMessages();
		message_str = messages.get(messages.size() - 1);
		buffer = message_str.split("::::");
		latestMessage = buffer[3];
		invalidate();
		repaint();
	}

	public void UIInit() {
		appFont = new AppFont();

		profileimg = new ImageIcon("images/add-iloveimg-resized.png");
		profile = new JLabel(profileimg, JLabel.CENTER);
		profile.setBounds(10, 18, 30, 30);
		add(profile);

		roomName = new JLabel(names);
		roomName.setFont(appFont.getNameFont());
		roomName.setBounds(60, 7, 120, 30);
		add(roomName);

		latestMessage = latestMessage();
		
		message = new JLabel(latestMessage);
		message.setFont(appFont.getStateMessageFont());
		message.setBounds(60, 42, 200, 15);
		add(message);
		
		invalidate();
		repaint();
	}

	public void menuLayout() {
		popup = new JPopupMenu();
		JMenuItem message = new JMenuItem("메세지 보내기");
		JMenuItem call = new JMenuItem("전화 걸기");
		JMenuItem remove = new JMenuItem("친구 삭제");
		message.addActionListener(new MenuActionListener());
		call.addActionListener(new MenuActionListener());
		remove.addActionListener(new MenuActionListener());
		popup.add(message);
		popup.add(call);
		popup.add(remove);

		add(popup);
	}

	public class ChanRoomListMouseActionListener implements MouseListener {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
				//System.out.println(roomName.getText() + " 우클릭");
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// mainFrame.getHome()
			// .setBoard(new FriendsListBoardPanel(mainFrame, (FriendsListPanel)
			// e.getComponent(), chatRoom));
			// invalidate();
			// repaint();
		}

	}

	public class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// if (user.isConnectionState()) {
			if (e.getActionCommand().equals("전화 걸기")) {
				System.out.println("전화 걸기");
			} else if (e.getActionCommand().equals("메세지 보내기기")) {
				System.out.println("메세지 보내기");
			} else if (e.getActionCommand().equals("친구 삭제")) {
				System.out.println("친구 삭제");
			}
			// } else {
			if (e.getActionCommand().equals("전화 걸기")) {
				System.out.println("상대방이 접속중이 아닙니다.");
			} else if (e.getActionCommand().equals("메세지 보내기기")) {
				System.out.println("메세지 보내기");
			} else if (e.getActionCommand().equals("친구 삭제")) {
				System.out.println("친구 삭제");
			}
			// }
		}

	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public ChatRoom getChatRoom() {
		return room;
	}
	
	public void setLatestMessage(String msg) {
		this.latestMessage = msg;
	}
}
