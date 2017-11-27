package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import font.AppFont;
import home.Home;
import main.MainFrame;
import thread.ReceiveThread;
import database.UserInfo;

public class Login extends JPanel{
	private JButton loginBtn;
	private MainFrame mainFrame;
	private JLabel id_lb;
	private JLabel pw_lb;
	private JTextField id;
	private JTextField pw;
	private ImageIcon logImg = new ImageIcon("images/hansungskype.png");
	private ImageIcon backgroundImg = new ImageIcon("images/sky2.jpg");
	private AppFont appFont;
	private LoginMouseListener loginMouseListener;

	public Login(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setLayout(null);
		//setBackground(Color.WHITE);
		//setBackground(new Color(225, 246, 251));
		setBackground(Color.WHITE);
		
		initPanel();
		setOpaque(true);
		setVisible(true);
		repaint();
	}
	
	public void initPanel() {
		appFont = new AppFont();
		loginMouseListener = new LoginMouseListener();
		
		id = new JTextField();
		id.setBounds(350, 290, 330, 30);
		id.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		//id.setBackground(new Color(225, 246, 251));
		id.setText("아이디를 입력하세요.");
		id.setForeground(Color.LIGHT_GRAY);
		id.setBackground(new Color(225, 246, 251));
		id.addMouseListener(loginMouseListener);
		add(id);
		
		pw = new JTextField();
		pw.setBounds(350, 360, 330, 30);
		pw.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		pw.setText("비밀번호를 입력하세요.");
		pw.setForeground(Color.LIGHT_GRAY);
		pw.setBackground(new Color(225, 246, 251));
		pw.addMouseListener(loginMouseListener);
		add(pw);
		
		
		loginBtn = new JButton("Sign in");
		loginBtn.setBounds(460, 450, 100, 40);
		loginBtn.setBackground(new Color(199, 237, 252));
		loginBtn.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(216, 229, 239)));
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ReceiveThread receiveThread = new ReceiveThread(id.getText(), pw.getText(), mainFrame);
				receiveThread.start();
				debug.Debug.log("로그인!!!");
			}
		});
		
		
		setOpaque(true);
		add(loginBtn);
		setVisible(true);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logImg.getImage(), 320, 40, 400, 150, null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Jokeman", Font.BOLD, 50));
		g.drawString("로그인", 430, 240);
		
		repaint();
		
	}
	
	public void loginSuccess(UserInfo userInfo) {
		mainFrame.remove(getParent());
		mainFrame.setHome(new Home(mainFrame, userInfo));
		mainFrame.setContentPane(mainFrame.getHome());
	}

	class LoginMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getSource() == id) {
				id.setText("");
			}
			if(e.getSource() == pw) {
				pw.setText("");
			}
		}
		
	}

	
	
}
