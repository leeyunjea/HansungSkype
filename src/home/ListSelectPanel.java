package home;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import font.AppFont;
import main.MainFrame;

public class ListSelectPanel extends JPanel {

	private MainFrame mainFrame;
	private JLabel friends;
	private JLabel chatRooms;
	private AppFont appFont;
	private int selected = 1;
	private Cursor defaultCursor;
	private Cursor handCursor;
	private Color selectedColor;

	public ListSelectPanel(MainFrame mainFrame) {
		System.out.println("ListSelectPanel");
		this.mainFrame = mainFrame;
		setLayout(null);
		setBounds(0, 150, 300, 50);
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(216, 229, 239)));

		UIInit();
	}

	public void UIInit() {
		selectedColor = new Color(7, 125, 180);
		defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		handCursor = new Cursor(Cursor.HAND_CURSOR);
		appFont = new AppFont();
		friends = new JLabel("연락처");
		chatRooms = new JLabel("대화");
		friends.setFont(appFont.getListSelectedFont());
		chatRooms.setFont(appFont.getListSelectedFont());
		friends.setBounds(18, 18, 50, 14);
		chatRooms.setBounds(86, 18, 35, 14);
		friends.addMouseListener(new ListSelectedListener());
		chatRooms.addMouseListener(new ListSelectedListener());
		friends.setForeground(selectedColor);
		add(friends);
		add(chatRooms);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	class ListSelectedListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(handCursor);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(defaultCursor);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(friends) && selected != 1) {
				friends.setForeground(selectedColor);
				chatRooms.setForeground(Color.BLACK);
				selected = 1;
				mainFrame.getHome().selectedFriends();
			} else if (e.getSource().equals(chatRooms) && selected != 2) {
				chatRooms.setForeground(selectedColor);
				friends.setForeground(Color.BLACK);
				selected = 2;
				mainFrame.getHome().getChatRoomsPanel().setChatRooms();
				mainFrame.getHome().selectedChatRooms();
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
