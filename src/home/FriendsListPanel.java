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

	/* �˾��޴� */
	private JPopupMenu popup;
	private FriendsListMouseActionListener listener;
	private UserInfo user;

	private ChatRoom chatRoom = null;

	public FriendsListPanel(MainFrame mainFrame, UserInfo user, boolean bookmark) {
		// System.out.println("FriendsListPanel");
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
		image = new ImageIcon("images/add-iloveimg-resized.png");
		imagelb = new JLabel(image, JLabel.CENTER);
		imagelb.setBounds(10, 18, 30, 30);
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

	public void menuLayout() {
		popup = new JPopupMenu();
		JMenuItem message = new JMenuItem("�޼��� ������");
		JMenuItem call = new JMenuItem("��ȭ �ɱ�");
		JMenuItem remove = new JMenuItem("ģ�� ����");
		message.addActionListener(new MenuActionListener());
		call.addActionListener(new MenuActionListener());
		remove.addActionListener(new MenuActionListener());
		popup.add(message);
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
				// System.out.println(namelb.getText() + " ��Ŭ��");
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
				if (e.getActionCommand().equals("��ȭ �ɱ�")) {
					System.out.println("��ȭ �ɱ�");
				} else if (e.getActionCommand().equals("�޼��� �������")) {
					System.out.println("�޼��� ������");
				} else if (e.getActionCommand().equals("ģ�� ����")) {
					System.out.println("ģ�� ����");
				}
			} else {
				if (e.getActionCommand().equals("��ȭ �ɱ�")) {
					System.out.println("������ �������� �ƴմϴ�.");
				} else if (e.getActionCommand().equals("�޼��� �������")) {
					System.out.println("�޼��� ������");
				} else if (e.getActionCommand().equals("ģ�� ����")) {
					System.out.println("ģ�� ����");
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
