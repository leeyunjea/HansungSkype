package home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.ChatRoom;
import database.UserInfo;
import font.AppFont;
import main.MainFrame;
import protocol.Protocol;

public class MultiChatBoardPanel extends JPanel implements ActionListener {
	private MainFrame mainFrame;
	private JLabel face; // 영상통화버튼
	private JLabel voice; // 음성통화버튼
	private JLabel plus; // 대화상대 추가버튼
	private JLabel file;
	private JLabel send;
	private JLabel name;
	private ImageIcon image;
	private ImageIcon face_img;
	private ImageIcon voice_img;
	private ImageIcon plus_img;
	private ImageIcon file_img;
	private ImageIcon send_img;
	private JLabel icon_lb;
	private JLabel stateMessage;
	private AppFont appFont;
	private JTextArea chatArea;
	private JTextField chatField;
	private FriendsMouseListener friendsMouseListener = new FriendsMouseListener();
	private int count = 0; // 야매
	private String partnerId;
	private UserInfo user;
	private ChatRoom chatRoom;
	private String names;
	private String members;
	private int roomId;

	public MultiChatBoardPanel(MainFrame mainFrame, String members, ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		this.mainFrame = mainFrame;
		this.members = members;
		user = mainFrame.getUser();
		this.roomId = chatRoom.getRoomId();
		setBounds(300, 0, mainFrame.WIDTH - 300, mainFrame.HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		System.out.println("MultiChatBoardPanel " + "members = " + members);

		UIinit();

		if (chatRoom != null) {
			if (chatRoom.getChatMessages() != null) {
				for (int i = 0; i < chatRoom.getChatMessages().size(); i++) {
					chatArea.append(chatRoom.getChatMessages().get(i) + "\n");
				}

			}
		}

		invalidate();
		repaint();

	}

	public void UIinit() {
		appFont = new AppFont();
		image = new ImageIcon("images/user.png");
		icon_lb = new JLabel(image, JLabel.CENTER);
		icon_lb.setBounds(20, 30, 75, 70);
		add(icon_lb);

		name = new JLabel();
		name.setText(members);
		name.setFont(appFont.getNameFont());
		name.setBounds(110, 40, 100, 30);
		add(name);

		stateMessage = new JLabel();
		stateMessage.setBounds(110, 75, 250, 15);
		stateMessage.setText(members);
		stateMessage.setFont(appFont.getStateMessageFont());
		add(stateMessage);

		face_img = new ImageIcon("images/skype.png");
		face = new JLabel(face_img);
		face.setBounds(500, 40, 50, 50);
		face.setBackground(Color.WHITE);
		face.setOpaque(false);
		face.setBorder(null);
		// face.setFocusPainted(false);
		add(face);

		voice_img = new ImageIcon("images/voicecall32.png");
		voice = new JLabel(voice_img);
		voice.setBounds(560, 40, 50, 50);
		voice.setBackground(Color.WHITE);
		voice.setOpaque(false);
		voice.setBorder(null);
		// voice.setFocusPainted(false);
		voice.addMouseListener(friendsMouseListener);
		add(voice);

		plus_img = new ImageIcon("images/multichat.png");
		plus = new JLabel(plus_img);
		plus.setBounds(620, 40, 50, 50);
		plus.setBackground(Color.WHITE);
		plus.setOpaque(false);
		plus.setBorder(null);
		// plus.setFocusPainted(false);
		add(plus);

		chatArea = new JTextArea();
		chatArea.setBounds(10, 140, mainFrame.WIDTH - 325, 450);
		chatArea.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(216, 229, 239)));
		chatArea.setEditable(false);

		JScrollPane scroll = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(10, 140, mainFrame.WIDTH - 325, 450);
		scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(216, 229, 239)));
		scroll.setBackground(Color.WHITE);

		// this.add(new JScrollPane(chatArea));
		// add(chatArea);
		add(scroll);

		chatField = new JTextField();
		chatField.setBounds(20, 615, 520, 30);
		chatField.setText(" 여기에 메세지 입력");
		chatField.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		chatField.addMouseListener(friendsMouseListener);
		chatField.addActionListener(this);
		add(chatField);

		file_img = new ImageIcon("images/file.png");
		file = new JLabel(file_img);
		file.setBounds(570, 615, 30, 30);
		file.setBackground(Color.WHITE);
		file.setOpaque(false);
		file.setBorder(null);
		add(file);

		send_img = new ImageIcon("images/send.png");
		send = new JLabel(send_img);
		send.setBounds(620, 610, 40, 40);
		send.setBackground(Color.WHITE);
		send.setOpaque(false);
		send.setBorder(null);
		send.addMouseListener(friendsMouseListener);
		add(send);
	}

	public String getMembers() {
		return members;
	}

	class FriendsMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getSource() == chatField) {
				chatField.setText("");
			}
			if (e.getSource() == send) {
				/*
				 * chatArea.append(chatField.getText() + "\n");
				 * chatArea.setCaretPosition(chatArea.getDocument().getLength())
				 * ; chatField.setText(" 여기에 메세지 입력");
				 */
				sendMessage();
			}

		}

	}

	public void exitVoiceStandbyPanel() {
		mainFrame.setContentPane(mainFrame.getHome());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		sendMessage();

	}

	public void sendMessage() {
		String msg = chatField.getText();
		if (!msg.equals("")) {
			chatArea.setCaretPosition(chatArea.getDocument().getLength());
			chatField.setText("");
			mainFrame.getReceiveThread().writeInt(Protocol.MSG_REQUEST);
			String buffer = mainFrame.getChatRoom(members).getRoomId() + "::::" + user.getId() + "::::" + chatRoom.getNames() + "::::" + msg;
			mainFrame.getReceiveThread().writeUTF(buffer);
		}
	}

	public JTextArea getTextArea() {
		return chatArea;
	}

	public String getPartnerId() {
		return partnerId;
	}
	
	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
	
	public void setNames(String names) {
		this.names = names;
		name.setText(names);
		this.members = names;
		stateMessage.setText(names);
		invalidate();
		repaint();
	}

	public int getRoomId() {
		return roomId;
	}
	
	public ChatRoom getChatRoom() {
		return chatRoom;
	}
	
	public String getNames() {
		return names;
	}
}
