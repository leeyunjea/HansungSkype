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
import java.io.File;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.AudioReceiver;
import chat.ChatRoom;
import database.UserInfo;
import login.Login;
import main.MainFrame;
import protocol.Protocol;
import thread.ReceiveThread;

public class VoiceReceiveFrame extends JFrame {
	private MainFrame mainFrame;
	private Container contentPane;
	public static final int WIDTH = 450;
	public static final int HEIGHT = 250;
	private VoiceReceivedPanel v2;
	private VoiceReceivingPanel v1;
	private UserInfo user1;
	private UserInfo user2;
	private ReceiveThread receiveThread;
	private boolean receiver;

	public VoiceReceiveFrame(UserInfo user1, UserInfo user2, ReceiveThread receiveThread, boolean b) {
		super("통화중");
		this.user1 = user1;
		this.user2 = user2;
		this.receiveThread = receiveThread;
		this.receiver = b;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		setLayout(null);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - WIDTH) - 50, 30);
		setSize(WIDTH, HEIGHT);
		v1 = new VoiceReceivingPanel(this);
		setContentPane(v1);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 170));
		setResizable(false);
		setVisible(true);
		repaint();
	}

	/*
	 * 원래는 이 생성자를 써야함. public VoiceReceiveFrame(MainFrame mainFrame) {
	 * this.mainFrame = mainFrame; }
	 */

	// 테스트 -> 통화가 될때 띄워야 하는데 아직 기능이 없으므로 main함수 테스트용으로 만듬
	// public static void main(String args[]) {
	// new VoiceReceiveFrame(new UserInfo(), new UserInfo());
	// }
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
			debug.Debug.log("VoiceReceivedPanel");
			setLayout(null);

			initPanel();

			setOpaque(false);
			setSize(VoiceReceiveFrame.WIDTH, VoiceReceiveFrame.HEIGHT);
		}

		public void initPanel() {
			mouseListener = new MouseListener();

			voicing = new JLabel("통화중...");
			voicing.setBounds(10, 15, 100, 20);
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
			loading.setBounds(185, 55, 80, 80);
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

			revalidate();
			repaint();
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
				if (e.getSource() == callexit) {
					voiceReceiveFrame.dispose();
					receiveThread.writeInt(Protocol.CALL_DISCONNECT);
					if (receiver)
						receiveThread.writeInt(Protocol.CALLING_OK);
				}
			}
		}

	}

	class VoiceReceivingPanel extends JPanel {
		private VoiceReceiveFrame voiceReceiveFrame;
		private ImageIcon myProfileimg;
		private ImageIcon yourProfileimg;
		private ImageIcon greencallimg;
		private ImageIcon plusimg;
		private ImageIcon callexitimg;
		private ImageIcon loadingimg;

		private JLabel voicing;
		private JLabel myprofile;
		private JLabel yourprofile;
		private JLabel loading;
		private JLabel greencall;
		private JLabel voice;
		private JLabel plus;
		private JLabel callexit;

		private MouseListener mouseListener;

		public VoiceReceivingPanel(VoiceReceiveFrame voiceReceiveFrame) {
			this.voiceReceiveFrame = voiceReceiveFrame;
			playSound("music/calling.wav");
			debug.Debug.log("VoiceReceivingPanel");
			setSize(VoiceReceiveFrame.WIDTH, VoiceReceiveFrame.HEIGHT);
			setLayout(null);
			setBackground(new Color(0, 0, 0, 170));

			initPanel();

			setOpaque(true);
			setVisible(true);
		}

		public void initPanel() {
			mouseListener = new MouseListener();

			voicing = new JLabel("통화요청이 들어왔습니다.");
			voicing.setBounds(10, 15, 300, 20);
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
			loading.setBounds(185, 55, 80, 80);
			loading.setOpaque(false);
			add(loading);

			yourprofile = new JLabel(yourProfileimg);
			yourprofile.setBounds(330, 50, 80, 80);
			yourprofile.setBackground(Color.WHITE);
			yourprofile.setOpaque(true);
			add(yourprofile);

			greencallimg = new ImageIcon("images/greencall.png");
			greencall = new JLabel(greencallimg);
			greencall.setBounds(95, 150, 64, 64);
			greencall.setBackground(Color.WHITE);
			greencall.setOpaque(false);
			greencall.addMouseListener(mouseListener);
			add(greencall);

			plusimg = new ImageIcon("images/plus.png");
			plus = new JLabel(plusimg);
			plus.setBounds(195, 150, 64, 64);
			plus.setOpaque(false);
			add(plus);

			callexitimg = new ImageIcon("images/callexit.png");
			callexit = new JLabel(callexitimg);
			callexit.setBounds(295, 150, 64, 64);
			callexit.setOpaque(false);
			callexit.addMouseListener(new MouseListener(this));
			add(callexit);
			revalidate();
		}

		class MouseListener implements java.awt.event.MouseListener {
			private VoiceReceivingPanel v;

			public MouseListener() {
			}

			public MouseListener(VoiceReceivingPanel v) {
				this.v = v;
			}

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
				JLabel labelTemp = (JLabel) e.getSource();
				if (e.getSource() == callexit) {
					voiceReceiveFrame.dispose();
					receiveThread.writeInt(Protocol.CALL_DISCONNECT);
					receiveThread.getAudioServer().remove();
					Vector<AudioReceiver> receivers = receiveThread.getAudioReceivers();
					for (int i = 0; i < receivers.size(); i++) {
						receivers.get(i).remove();
						receivers.remove(i);
					}
					debug.Debug.log("Client Disconnect");
				}
				if (labelTemp == greencall) {
					changePanel(voiceReceiveFrame);
					receiveThread.writeInt(Protocol.CALLING_OK);
				}
			}
		}

	}

	public void changePanel(VoiceReceiveFrame frame) {
		this.remove(v1);
		v2 = new VoiceReceivedPanel(frame);
		setContentPane(v2);
		revalidate();
		repaint();
	}

	public VoiceReceivedPanel getVoiceReceivedPanel() {
		return v2;
	}

	public VoiceReceivingPanel getVoiceReceivingPanel() {
		return v1;
	}

	public static synchronized void playSound(String file_url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					File file = new File(file_url);
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}