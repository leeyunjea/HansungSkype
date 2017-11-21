package home;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import font.AppFont;
import main.MainFrame;

public class ProfilePanel extends JPanel {
	private JLabel profile;
	private ImageIcon icon;
	private JLabel myName;
	private JLabel stateMessage;
	
	private AppFont appFont;

	public ProfilePanel(MainFrame mainFrame) {
		System.out.println("ProfilePanel");
		setBounds(0, 0, 300, 100);
		setLayout(null);
		
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216, 229, 239)));
		
		UIInit();
		
		setVisible(true);
	}
	
	
	public String getProfileImage() {
		return icon.getImage().toString();
	}


	public String getMyName() {
		return myName.getText().toString();
	}


	public String getStateMessage() {
		return stateMessage.getText();
	}


	public void UIInit() {
		appFont = new AppFont();
		icon = new ImageIcon("images/user.png");
		profile = new JLabel(icon, JLabel.CENTER);
		profile.setBounds(30,17,75,70);
		add(profile);
		
		myName = new JLabel("¿Ã¿±¿Á");
		myName.setFont(appFont.getNameFont());
		myName.setBounds(120, 27, 120, 30);
		add(myName);
		
		stateMessage = new JLabel("«Ï¿Ã ∏µŒµÈ æ»≥Á");
		stateMessage.setFont(appFont.getStateMessageFont());
		stateMessage.setBounds(120, 58, 120, 15);
		add(stateMessage);
		
	}
}
