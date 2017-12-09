package home;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MainFrame;
import protocol.Protocol;
import sns.SNS;

public class BoardPanel extends JPanel {

	private MainFrame mainFrame;
	private JTextField sns_field;
	private Vector<SNS> snss;

	public BoardPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.snss = mainFrame.getListSNS();
		setBounds(300, 0, mainFrame.WIDTH - 300, mainFrame.HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));

		UIinit();

		invalidate();
		repaint();
	}

	public void UIinit() {
		addTextFiled();

		if (snss != null) {
			addSNSListPanel();
		}
	}
	
	class boardActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == sns_field) {
				if (!sns_field.getText().equals("")) {
					SNS sns = new SNS(mainFrame.getUser().getName(), sns_field.getText());
					try {
						mainFrame.getReceiveThread().getDataOutputStream().writeInt(Protocol.SNS_REQUEST);
						mainFrame.getReceiveThread().getObjectOutputStream().writeObject(sns);
						System.out.println("À±Àç Å¬¶óÀÌ¾ðÆ® SNS_REQUEST sns = " + sns.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					sns_field.setText("");
				}
			}
		}

	}

	public void setFriendsListBoardPanel(FriendsListBoardPanel f) {
		removeAll();
		add(f);
		invalidate();
		repaint();
	}

	// À±Àç
	public void setListSNS(Vector<SNS> snss) {
		removeAll();
		addTextFiled();
		this.snss = snss;
		addSNSListPanel();
	}

	public void addTextFiled() {
		sns_field = new JTextField(" Ä£±¸¿¡°Ô ÀÚ½ÅÀÇ ±ÙÈ²À» ÀÌ¾ß±âÇØ º¸¼¼¿ä.");
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
		sns_field.addActionListener(new boardActionListener());
		add(sns_field);
	}

	public void addSNSListPanel() {
		int width = 25;
		int width2 = 25;
		System.out.println("À±Àç BoardPanel SNS List size = " + snss.size());
		if (snss.size() != 0) {
			for (int i = 0; i < snss.size() && i < 3; i++) {
				System.out.println("À±Àç i = " + i);
				ImageIcon img = null;
				if (snss.size() != 0) {
					switch (snss.get(i).getWriter()) {
					case "È«¼º¹®":
						img = new ImageIcon("images/userone.png");
						break;
					case "ÀÌÀ±Àç":
						img = new ImageIcon("images/usertwo.png");
						break;
					case "¹é½ÂÈ¯":
						img = new ImageIcon("images/userthree.png");
						break;
					case "ÀÌÅÂÀ±":
						img = new ImageIcon("images/userfour.png");
						break;
					case "¹ÎÅÂ¼º":
						img = new ImageIcon("images/userfive.png");
						break;
					case "ÃÖ¿ø±Õ":
						img = new ImageIcon("images/usersix.png");
						break;
					}
					SNSPanel snsPanel = new SNSPanel(img, snss.get(i));
					snsPanel.setBounds(width, 200, 200, 150);
					add(snsPanel);
					width += 220;
				}
			}
			for (int i = 3; i < snss.size(); i++) { // ÃÑ 6°³¿©¾ßÇÔ
				System.out.println("À±Àç i = " + i);
				ImageIcon img = null;
				switch (snss.get(i).getWriter()) {
				case "È«¼º¹®":
					img = new ImageIcon("images/userone.png");
					break;
				case "ÀÌÀ±Àç":
					img = new ImageIcon("images/usertwo.png");
					break;
				case "¹é½ÂÈ¯":
					img = new ImageIcon("images/userthree.png");
					break;
				case "ÀÌÅÂÀ±":
					img = new ImageIcon("userfour.png");
					break;
				case "¹ÎÅÂ¼º":
					img = new ImageIcon("images/userfive.png");
					break;
				}
				SNSPanel snsPanel = new SNSPanel(img, snss.get(i));
				snsPanel.setBounds(width2, 400, 200, 150);
				add(snsPanel);
				width2 += 220;
			}
		}

		invalidate();
		repaint();
	}

}
