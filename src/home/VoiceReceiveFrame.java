package home;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chat.ChatRoom;
import login.Login;
import main.MainFrame;

public class VoiceReceiveFrame extends JFrame {
	private MainFrame mainFrame;
	private Container contentPane;
	public static final int WIDTH = 450;
	public static final int HEIGHT = 250;
	private VoiceReceivedPanel v;

	public VoiceReceiveFrame() {
		super("통화중");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		setLayout(null);

		// setUndecorated(true);
		setBackground(Color.WHITE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - WIDTH) - 50, 30);
		setSize(WIDTH, HEIGHT);
		v = new VoiceReceivedPanel(this);
		setContentPane(v);
		setUndecorated(true);
		setBackground(new Color(0,0,0,170));
		setResizable(false);
		setVisible(true);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		setBackground(new Color(0, 255, 0, 0));
	}

	/* 원래는 이 생성자를 써야함.
	 * public VoiceReceiveFrame(MainFrame mainFrame) { this.mainFrame =
	 * mainFrame; }
	 */

	// 테스트 -> 통화가 될때 띄워야 하는데 아직 기능이 없으므로 main함수 테스트용으로 만듬
	public static void main(String args[]) {
		new VoiceReceiveFrame();
	}
	//

	class VoiceReceivedPanel extends JPanel {
		private VoiceReceiveFrame voiceReceiveFrame;
		private ImageIcon myProfileimg;
		private ImageIcon yourProfileimg;
		private ImageIcon videoimg;
		private ImageIcon voiceimg;
		private ImageIcon plusimg;
		private ImageIcon callexitimg;
		private ImageIcon loadingimg;
		
		private JLabel voicing;
		private JLabel myprofile;
		private JLabel yourprofile;
		private JLabel loading;
		private JLabel video;
		private JLabel voice;
		private JLabel plus;
		private JLabel callexit;
		
		private MouseListener mouseListener;

		public VoiceReceivedPanel(VoiceReceiveFrame voiceReceiveFrame) {
			this.voiceReceiveFrame = voiceReceiveFrame;
			setSize(VoiceReceiveFrame.WIDTH, VoiceReceiveFrame.HEIGHT);
			setLayout(null);
			setBackground(new Color(0,0,0,170));
			
			initPanel();
			
			setOpaque(true);
			repaint();
		}

		public void initPanel() {
			mouseListener = new MouseListener();
			
			voicing = new JLabel("통화중...");
			voicing.setBounds(10,15,100,20);
			voicing.setForeground(Color.WHITE);
			voicing.setFont(new Font("Serif", Font.BOLD, 20));
			add(voicing);
			
			myProfileimg = new ImageIcon("");
			yourProfileimg = new ImageIcon("");
			
			myprofile = new JLabel(myProfileimg);
			myprofile.setBounds(50, 50, 80, 80);
			myprofile.setBackground(Color.WHITE);
			myprofile.setOpaque(true);
			add(myprofile);
			
			loadingimg = new ImageIcon("images/loading.gif");
			loading = new JLabel(loadingimg);
			loading.setBounds(180, 55, 80, 80);
			loading.setOpaque(false);
			add(loading);
			
			yourprofile = new JLabel(yourProfileimg);
			yourprofile.setBounds(330, 50, 80, 80);
			yourprofile.setBackground(Color.WHITE);
			yourprofile.setOpaque(true);
			add(yourprofile);
			
			videoimg = new ImageIcon("images/video-call.png");
			video = new JLabel(videoimg);
			video.setBounds(50, 150, 64, 64);
			video.setOpaque(false);
			add(video);
			
			voiceimg = new ImageIcon("images/microphone.png");
			voice = new JLabel(voiceimg);
			voice.setBounds(150, 150, 64, 64);
			voice.setOpaque(false);
			add(voice);
			
			plusimg = new ImageIcon("images/plus.png");
			plus = new JLabel(plusimg);
			plus.setBounds(250, 150, 64, 64);
			plus.setOpaque(false);
			add(plus);
			
			callexitimg = new ImageIcon("images/callexit.png");
			callexit = new JLabel(callexitimg);
			callexit.setBounds(350, 150, 64, 64);
			callexit.setOpaque(false);
			callexit.addMouseListener(mouseListener);
			add(callexit);
		}

		class MouseListener implements java.awt.event.MouseListener {

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
				if(e.getSource() == callexit) {
					voiceReceiveFrame.dispose();
					System.exit(0);
				}
			}
		}

	}
}
