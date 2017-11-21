package home;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.MainFrame;

public class ChatRoomsPanel extends JPanel {

	private MainFrame mainFrame;

	public ChatRoomsPanel(MainFrame mainFrame) {
		System.out.println("FriendsPanel");
		this.mainFrame = mainFrame;
		setBounds(0, 200, 300, 500);
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));
	}
	
}
