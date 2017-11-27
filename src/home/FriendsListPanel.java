package home;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import database.UserInfo;
import font.AppFont;
import main.MainFrame;

public class FriendsListPanel extends JPanel {

	private Boolean bookmark;

	private ImageIcon image;

	private JLabel imagelb;
	private JLabel namelb;
	private JLabel stateMessagelb;

	private AppFont appFont;
	private MainFrame mainFrame;

	/* 팝업메뉴 */
	private JPopupMenu popup;
	private FriendsListMouseActionListener listener;
	private UserInfo user;
	private String[] menuText = { "메세지 보내기", "전화 걸기", "친구 삭제" };

	public FriendsListPanel(MainFrame mainFrame, UserInfo user, boolean bookmark) {
		System.out.println("FriendsListPanel");
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

		JMenuItem items[] = null;
		items = new JMenuItem[menuText.length];
		for (int i = 0; i < menuText.length; i++) {
			items[i] = new JMenuItem(menuText[i]);
			add(items[i]);
			popup.add(items[i]);
		}

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
				System.out.println(namelb.getText() + " 우클릭");
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
			mainFrame.getHome().setBoard(new FriendsListBoardPanel(mainFrame, (FriendsListPanel) e.getComponent()));
			invalidate();
			repaint();
		}

	}

}
