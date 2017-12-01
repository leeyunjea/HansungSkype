package home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import chat.ChatRoom;
import database.UserInfo;
import home.MenuPanel.MenuPanelMouseListener;
import main.MainFrame;

public class FriendsPanel extends JScrollPane {

	private MainFrame mainFrame;
	private JLabel friends;
	private JLabel bookmark;
	private int height = 30;
	private JScrollPane scroll;
	private Home home;

	// private Vector<BookMarkListPanel> boookmarkPanels;
	private Vector<FriendsListPanel> friendsPanels;
	private Object hover = null;
	private Vector<UserInfo> friendsVector;

	public FriendsPanel(MainFrame mainFrame) {
		System.out.println("FriendsPanel");
		this.mainFrame = mainFrame;
		friendsVector = mainFrame.getUser().getFriends();
		setLayout(null);
		//setLayout(new FlowLayout());
		setBounds(0, 200, 300, 500);
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));

		/*
		 * scroll = new JScrollPane(); Dimension size = new Dimension();
		 * size.setSize(300, 500); this.setPreferredSize(size);
		 * scroll.setViewportView(this); System.out.println("scrollsize = " +
		 * size.getWidth() + " " + size.getHeight());
		 */

		friendsPanels = new Vector<FriendsListPanel>();
		for(int i=0; i<friendsVector.size(); i++) {
			UserInfo user = friendsVector.get(i);
			friendsPanels.add(new FriendsListPanel(mainFrame, user, false));
		}

		UIinit();
		System.out.println("FriendsPanel");
	}

	public void UIinit() {
		bookmark = new JLabel("즐겨찾기");
		//bookmark.setLocation(10, 10);
		//bookmark.setSize(300, 15);
		// bookmark.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// add(bookmark);

		// addBookMarkList(friendsPanels);

		friends = new JLabel("모두");
		friends.setBounds(10, height, 300, 15);
		add(friends);

		addFriendsList(friendsPanels);
	}

	public void addBookMarkList(Vector<FriendsListPanel> f) {
		int i = 0;
		for (i = 0; i < f.size(); i++) {
			if (f.get(i).getBookmark()) {
				f.get(i).setBounds(0, height, 300, 70);
				add(f.get(i));
				height += 70;
				System.out.println(" height = " + height);
			}
		}
		invalidate();
		repaint();
	}

	public void addFriendsList(Vector<FriendsListPanel> f) {
		for (int i = 0; i < f.size(); i++) {
			f.get(i).setBounds(0, height + 15, 300, 70);
			f.get(i).addMouseListener(new MouseListener() {

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
			add(f.get(i));
			height += 70;
			System.out.println(" height = " + height);
		}
		invalidate();
		repaint();
	}
	
	public void setChatRoom(String ids, ChatRoom chatRoom) {
		String id[] = ids.split(",");
		String partner;
		if(!id[0].equals(mainFrame.getUser().getId())) {
			partner = id[0];
		}
		else 
			partner = id[1];
		for(int i=0; i<friendsPanels.size(); i++) {
			if(friendsPanels.get(i).getId().equals(partner)) {
				friendsPanels.get(i).setChatRoom(chatRoom);
			}
		}
	}
	


}
