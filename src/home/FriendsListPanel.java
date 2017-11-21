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
import javax.swing.JPanel;

import font.AppFont;
import main.MainFrame;

public class FriendsListPanel extends JPanel {

	private String name;
	private String stateMessage;
	private String icon;
	private Boolean bookmark;

	private ImageIcon image;

	private JLabel imagelb;
	private JLabel namelb;
	private JLabel stateMessagelb;

	private AppFont appFont;
	private MainFrame mainFrame;

	public FriendsListPanel(MainFrame mainFrame, String icon, String name, String stateMessage, boolean bookmark) {
		System.out.println("FriendsListPanel");
		this.mainFrame = mainFrame;
		this.name = name;
		this.stateMessage = stateMessage;
		this.icon = icon;
		this.bookmark = bookmark;

		setLayout(null);

		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));

		UIInit();
		this.addMouseListener(new FriendsListMouseActionListener());

		setVisible(true);
	}

	public String getName() {
		return name;
	}

	public String getStateMessage() {
		return stateMessage;
	}

	public String getIcon() {
		return icon;
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
		//image = new ImageIcon("images/add-iloveimg-resized.png");
		image = new ImageIcon("images/add-iloveimg-resized.png");
		imagelb = new JLabel(image, JLabel.CENTER);
		imagelb.setBounds(10, 18, 30, 30);
		add(imagelb);

		namelb = new JLabel(name);
		namelb.setFont(appFont.getNameFont());
		namelb.setBounds(60, 7, 120, 30);
		add(namelb);

		stateMessagelb = new JLabel(stateMessage);
		stateMessagelb.setFont(appFont.getStateMessageFont());
		stateMessagelb.setBounds(60, 42, 120, 15);
		add(stateMessagelb);

	}
	
	public class FriendsListMouseActionListener implements MouseListener {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

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
			mainFrame.getHome().setBoard(
					new FriendsListBoardPanel(mainFrame, (FriendsListPanel) e.getComponent()));
			invalidate();
			repaint();
		}
		
	}

}
