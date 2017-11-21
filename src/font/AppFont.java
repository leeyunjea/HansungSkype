package font;

import java.awt.Font;

public class AppFont {
	
	private Font nameFont = new Font("Serif", Font.PLAIN, 18);
	private Font stateMessageFont = new Font("Serif", Font.PLAIN, 12);
	private Font listSelectedFont = new Font("Serif", Font.PLAIN, 16);
	private Font loginFont = new Font("Serif", Font.PLAIN, 16);
	
	public Font getNameFont() {
		return nameFont;
	}
	
	public Font getStateMessageFont() {
		return stateMessageFont; 
	}

	public Font getListSelectedFont() {
		return listSelectedFont;
	}
	
	public Font getLoginFont() {
		return loginFont;
	}
	
}
