package home;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import chat.ChatRoom;
import database.UserInfo;
import font.AppFont;
import main.MainFrame;
import protocol.Protocol;

public class FriendsListPanel extends JPanel {

	private Boolean bookmark;

	private ImageIcon image;

	private JLabel imagelb;
	private JLabel namelb;
	private JLabel stateMessagelb;

	private AppFont appFont;
	private MainFrame mainFrame;
	private JMenuItem call;
	private JMenuItem message;
	private JMenuItem remove;
	private int menuCount = 3;

	/* 팝업메뉴 */
	private JPopupMenu popup;
	private FriendsListMouseActionListener listener;
	private UserInfo user;

	private ChatRoom chatRoom = null;

	public FriendsListPanel(MainFrame mainFrame, UserInfo user, boolean bookmark) {
		// System.out.println("FriendsListPanel");
		System.out.println(user.getId());
		this.mainFrame = mainFrame;
		this.user = user;
		this.bookmark = bookmark;

		setLayout(null);

		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));

		listener = new FriendsListMouseActionListener();

		UIInit();
		menuLayout();
		this.addMouseListener(listener);

		setVisible(true);
	}

	public String getName() {
		return user.getName();
	}

	public String getStateMessage() {
		return user.getStateMessage();
	}

	public String getIcon() {
		return user.getName();
	}

	public ImageIcon getImage() {
		return image;
	}

	public Boolean getBookmark() {
		return bookmark;
	}

	public void setBookmark(Boolean bookmark) {
		this.bookmark = bookmark;
	}

	public void UIInit() {
		appFont = new AppFont();
		// image = new ImageIcon("images/add-iloveimg-resized.png");
		image = new ImageIcon(getImg());
		imagelb = new JLabel(image, JLabel.CENTER);
		imagelb.setBounds(10, 18, 40, 40);
		add(imagelb);

		namelb = new JLabel(getName());
		namelb.setFont(appFont.getNameFont());
		namelb.setBounds(60, 7, 120, 30);
		add(namelb);

		stateMessagelb = new JLabel(getStateMessage());
		stateMessagelb.setFont(appFont.getStateMessageFont());
		stateMessagelb.setBounds(60, 42, 200, 15);
		add(stateMessagelb);

	}
	
	public String getImg() {
		String img = null;
		switch(user.getName()) {
		case "홍성문":
			img = "images/userone.png";
			break;
		case "이윤재":
			img = "images/usertwo.png";
			break;
		case "백승환":
			img = "images/userthree.png";
			break;
		case "이태윤":
			img = "images/userfour.png";
			break;
		case "민태성":
			img = "images/userfive.png";
			break;
		case "최원균":
			img = "images/usersix.png";
			break;
		}
		return img;
	}

	public void menuLayout() {
		popup = new JPopupMenu();
		JMenuItem message = new JMenuItem("메세지 보내기");
		JMenuItem invite = new JMenuItem("대화방 초대");
		JMenuItem call = new JMenuItem("전화 걸기");
		JMenuItem remove = new JMenuItem("친구 삭제");
		message.addActionListener(new MenuActionListener());
		invite.addActionListener(new MenuActionListener());
		call.addActionListener(new MenuActionListener());
		remove.addActionListener(new MenuActionListener());
		popup.add(message);
		popup.add(invite);
		popup.add(call);
		popup.add(remove);

		add(popup);
	}

	public class FriendsListMouseActionListener implements MouseListener {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
				// System.out.println(namelb.getText() + " 우클릭");
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
			mainFrame.getReceiveThread().writeInt(Protocol.CONVERSATION_REQUEST);
			mainFrame.getReceiveThread().writeUTF(mainFrame.getId());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (chatRoom != null)
				if (chatRoom.getChatMessages() != null)
					System.out.println(chatRoom.getChatMessages().toString());
			String name1 = ((FriendsListPanel) e.getSource()).getId();
			String name2 = mainFrame.getId();
			String names[] = { name1, name2 };
			Arrays.sort(names);
			String name = names[0] + "," + names[1];
			System.out.println("mouseClick : name :" + name);
			ChatRoom room = mainFrame.getChatRoom(name);
			setChatRoom(room);
			if (room != null)
				System.out.println("FriendsList : " + room.getChatMessages().toString());
			else
				System.out.println("ChatRoom is Null");
			// chatRoom = mainFrame.getChatRoom()
			mainFrame.getHome()
					.setBoard(new FriendsListBoardPanel(mainFrame, (FriendsListPanel) e.getComponent(), room));
			invalidate();
			repaint();

		}

	}

	public class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (user.isConnectionState()) {
				if (e.getActionCommand().equals("전화 걸기")) {
					mainFrame.getReceiveThread().writeInt(Protocol.CALL_REQUEST);
					mainFrame.getReceiveThread().writeUTF(user.getId());
					System.out.println("전화 걸기");
				} else if (e.getActionCommand().equals("메세지 보내기기")) {
					System.out.println("메세지 보내기");
				} else if (e.getActionCommand().equals("대화방 초대")) {
					UserInfo user3 = mainFrame.getUser(user.getId());
					if (mainFrame.getHome().getBoard() instanceof FriendsListBoardPanel) {
						UserInfo user1 = mainFrame.getUser(mainFrame.getId()); // 홍성문
						UserInfo user2 = mainFrame
								.getUser(((FriendsListBoardPanel) (mainFrame.getHome().getBoard())).getPartnerId()); // 이윤재
						String nameArr[] = { user1.getId(), user2.getId(), user3.getId() };
						Arrays.sort(nameArr);
						String name = nameArr[0] + "," + nameArr[1] + "," + nameArr[2];
						if (mainFrame.getChatRoom(name) == null) {
							mainFrame.getReceiveThread().writeInt(Protocol.CHAT_ROOM_REQUEST);
							String buffer = user.getId() + "::::" + name + "::::" + user1.getId() + "님께서 "
									+ user2.getId() + "님과  " + user3.getId() + "님을 초대하였습니다.";
							mainFrame.getReceiveThread().writeUTF(buffer);
						}
					} else if (mainFrame.getHome().getBoard() instanceof MultiChatBoardPanel) {
						if (!((MultiChatBoardPanel) mainFrame.getHome().getBoard()).getChatRoom().getNames()
								.contains(user3.getId())) {
							mainFrame.getReceiveThread().writeInt(Protocol.MSG_ADD_USER_REQUEST);
							String members = ((MultiChatBoardPanel) mainFrame.getHome().getBoard()).getChatRoom().getNames();
							mainFrame.getReceiveThread().writeUTF(members);
							mainFrame.getReceiveThread().writeUTF(user3.getId());
							System.out.println("대화방 초대  id : " + user3.getId() + "  members : " + members);
						}
					}
					System.out.println("대화방 초대  id : " + user3.getId());
				} else if (e.getActionCommand().equals("친구 삭제")) {
					System.out.println("친구 삭제");
				}
			} else {
				if (e.getActionCommand().equals("전화 걸기")) {
					System.out.println("상대방이 접속중이 아닙니다.");
				} else if (e.getActionCommand().equals("메세지 보내기기")) {
					System.out.println("메세지 보내기");
				} else if (e.getActionCommand().equals("친구 삭제")) {
					System.out.println("친구 삭제");
				}
			}
		}

	}

	public String getId() {
		return user.getId();
	}

	public boolean isConnectionState() {
		return user.isConnectionState();
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}
}
