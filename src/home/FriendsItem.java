package home;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FriendsItem{
	private final String icon;
	private final String name;
	private final String stateMessage;
	
	private ImageIcon image;
	
	public FriendsItem(String icon, String name,  String stateMessage) {
		this.icon = icon;
		this.name = name;
		this.stateMessage = stateMessage;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getStateMessage() {
		return stateMessage;
	}
	
	public ImageIcon getImage() {
		if(image == null)
			image = new ImageIcon(icon);
		return image;
	}
	
}