package home;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.MainFrame;

public class MenuPanel extends JPanel {

	private MainFrame mainFrame;
	private JButton homeBtn;
	private JButton tmpBtn;
	private JButton tmpBtn2;
	private Object hover = null;
	private Object clicked = null;
	// private ImageIcon homeicon;

	public MenuPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		//System.out.println("MenuPanel");
		setLayout(null);
		setBounds(0, 100, 300, 50);
		setBackground(new Color(240, 244, 248));
		setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));

		// setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(216,
		// 229, 239)));

		UIInit();
	}

	public void UIInit() {

		homeBtn = new JButton(new ImageIcon("images/home-iloveimg-resized.png"));
		homeBtn.setBounds(15, 15, 20, 20);
		homeBtn.setBorderPainted(false);
		homeBtn.setBackground(Color.WHITE);
		homeBtn.setOpaque(false);
		homeBtn.setToolTipText("HansungSkype홈에서 연락처의 업데이트 내용을 확인하세요.");
		homeBtn.addMouseListener(new MenuPanelMouseListener());
		homeBtn.addActionListener(new MenuPanelMouseActionListener());
		homeBtn.setFocusPainted(false);
		add(homeBtn);

		tmpBtn = new JButton(new ImageIcon("images/home-iloveimg-resized.png"));
		tmpBtn.setBounds(138, 15, 20, 20);
		tmpBtn.setBorderPainted(false);
		tmpBtn.setBackground(Color.WHITE);
		tmpBtn.setOpaque(false);
		tmpBtn.setToolTipText("HansungSkype홈에서 연락처의 업데이트 내용을 확인하세요.");
		tmpBtn.addMouseListener(new MenuPanelMouseListener());
		tmpBtn.addActionListener(new MenuPanelMouseActionListener());
		tmpBtn.setFocusPainted(false);
		add(tmpBtn);

		tmpBtn2 = new JButton(new ImageIcon("images/add-iloveimg-resized.png"));
		tmpBtn2.setBounds(266, 15, 20, 20);
		tmpBtn2.setBorderPainted(false);
		tmpBtn2.setBackground(Color.WHITE);
		tmpBtn2.setOpaque(false);
		tmpBtn2.setToolTipText("누구든지 참여할 수 있는 새 대회를 만듭니다");
		tmpBtn2.addMouseListener(new MenuPanelMouseListener());
		tmpBtn2.addActionListener(new MenuPanelMouseActionListener());
		tmpBtn2.setFocusPainted(false);
		add(tmpBtn2);

	}

	class MenuPanelMouseActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(homeBtn)) {
				mainFrame.getHome().setBorad();
			}
			else if (e.getSource().equals(homeBtn)) {

			}
			else if (e.getSource().equals(homeBtn)) {

			}
		}

	}

	class MenuPanelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(clicked)) {
				clicked = e.getSource();
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			hover = e.getSource();
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			hover = null;
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			clicked = e.getSource();
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (clicked != null) {
			g.setColor(new Color(212, 225, 235));
			if (clicked.equals(homeBtn)) {
				g.fillOval(5, 5, 40, 40);
			}
			if (clicked.equals(tmpBtn)) {
				g.fillOval(128, 5, 40, 40);
			}
			if (clicked.equals(tmpBtn2)) {
				g.fillOval(255, 5, 40, 40);
			}
		}

		if (hover != null) {
			g.setColor(new Color(221, 233, 244));
			if (hover.equals(homeBtn)) {
				g.fillOval(5, 5, 40, 40);
			}
			if (hover.equals(tmpBtn)) {
				g.fillOval(128, 5, 40, 40);
			}
			if (hover.equals(tmpBtn2)) {
				g.fillOval(255, 5, 40, 40);
			}
		}

		paintComponents(g);
	}

}
