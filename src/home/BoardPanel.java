package home;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MainFrame;

public class BoardPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JTextField sns_field;

	public BoardPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setBounds(300, 0, mainFrame.WIDTH - 300, mainFrame.HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		
		UIinit();
		
		invalidate();
		repaint();
	}
	
	public void UIinit() {
		sns_field = new JTextField(" 친구에게 자신의 근황을 이야기해 보세요.");
		sns_field.setForeground(new Color(134, 219, 250));
		sns_field.setBounds(20, 50, 660, 30);
		sns_field.setBorder(BorderFactory.createLineBorder(new Color(0, 174, 239)));
		sns_field.addMouseListener(new MouseListener() {
			
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
				sns_field.setBackground(new Color(229, 246, 253));
				sns_field.setForeground(Color.BLACK);
				sns_field.setText("");
			}
		});
		add(sns_field);
	}
	
	public void setFriendsListBoardPanel(FriendsListBoardPanel f) {
		removeAll();
		add(f);
		invalidate();
		repaint();
	}
	
	

}
