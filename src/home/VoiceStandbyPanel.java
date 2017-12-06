package home;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainFrame;

public class VoiceStandbyPanel extends JPanel {
	private MainFrame mainFrame;
	private FriendsListPanel f;
	private ProfilePanel p;
	
	private ImageIcon myProfileimg;
	private ImageIcon yourProfileimg;
	private ImageIcon loadingimg;
	private ImageIcon videoimg;
	private ImageIcon voiceimg;
	private ImageIcon plusimg;
	private ImageIcon callexitimg;
	
	private JLabel myprofile;
	private JLabel yourprofile;
	private JLabel loading;
	private JLabel video;
	private JLabel voice;
	private JLabel plus;
	private JLabel callexit;
	
	private VoiceStandbyPanelMouseListener voiceMouse;

	public VoiceStandbyPanel(MainFrame mainFrame, FriendsListPanel f, ProfilePanel p) {
		this.mainFrame = mainFrame;
		this.f = f;
		this.p = p;
		setLayout(null);
		setBounds(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
		//setBackground(new Color(178,230,251));
	//	System.out.println("f name = " + f.getName());
		//System.out.println("p name = " + p.getMyName());
		
		UIinit();
		
		setVisible(true);
	}
	
	public void UIinit() {
		voiceMouse = new VoiceStandbyPanelMouseListener();
		
		myProfileimg = new ImageIcon(p.getProfileImage());
		yourProfileimg = new ImageIcon(f.getIcon());
		
		myprofile = new JLabel(myProfileimg);
		myprofile.setBounds(140, 150, 250, 250);
		myprofile.setBackground(Color.WHITE);
		myprofile.setOpaque(true);
		add(myprofile);
		
		yourprofile = new JLabel(yourProfileimg);
		yourprofile.setBounds(590, 150, 250, 250);
		yourprofile.setBackground(Color.WHITE);
		yourprofile.setOpaque(true);
		add(yourprofile);
		
		loadingimg = new ImageIcon("images/loading3.gif");
		loading = new JLabel(loadingimg);
		loading.setBounds(440, 230, 100, 100);
		loading.setBackground(new Color(38, 38, 38));
		loading.setOpaque(false);
		add(loading);
		
		videoimg = new ImageIcon("images/video-call.png");
		video = new JLabel(videoimg);
		video.setBounds(300, 470, 64, 64);
		//video.setBackground(new Color(38, 38, 38));
		video.setOpaque(false);
		video.addMouseListener(voiceMouse);
		add(video);
		
		voiceimg = new ImageIcon("images/microphone.png");
		voice = new JLabel(voiceimg);
		voice.setBounds(400, 470, 64, 64);
		//voice.setBackground(new Color(38, 38, 38));
		voice.setOpaque(false);
		voice.addMouseListener(voiceMouse);
		add(voice);
		
		plusimg = new ImageIcon("images/plus.png");
		plus = new JLabel(plusimg);
		plus.setBounds(500, 470, 64, 64);
		//plus.setBackground(new Color(38, 38, 38));
		plus.setOpaque(false);
		plus.addMouseListener(voiceMouse);
		add(plus);
		
		callexitimg = new ImageIcon("images/callexit.png");
		callexit = new JLabel(callexitimg);
		callexit.setBounds(600, 470, 64, 64);
		//callexit.setBackground(new Color(38, 38, 38));
		callexit.setOpaque(false);
		callexit.addMouseListener(voiceMouse);
		add(callexit);
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	class VoiceStandbyPanelMouseListener implements MouseListener {
		
		private VoiceStandbyPanel voice;

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
				mainFrame.setContentPane(mainFrame.getHome());
			}
		}
		
	}
	
	
}
