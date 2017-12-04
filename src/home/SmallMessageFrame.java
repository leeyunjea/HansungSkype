package home;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.logging.Handler;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainFrame;

public class SmallMessageFrame extends JFrame {
	private MainFrame mainFrame;
	private Container contentPane;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 80;
	private SmallMessagePanel s;
	private String buffer;
	private String buffers[];

	
	public SmallMessageFrame(MainFrame mainFrame, String buffer) {
		this.mainFrame = mainFrame;
		this.buffer = buffer;
		buffers = buffer.split("::::");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width-WIDTH), (dim.height-HEIGHT-60));
		setSize(WIDTH, HEIGHT);
		s = new SmallMessagePanel(this);
		setContentPane(s);
		setUndecorated(true);
		setBackground(new Color(0,0,0,170));
		setResizable(false);
		setVisible(true);
		repaint();
		
		MyThread myThread = new MyThread(this);
		myThread.start();
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		setBackground(new Color(0, 255, 0, 0));
	}
	
	class SmallMessagePanel extends JPanel {
		private SmallMessageFrame smallMessageFrame;
		private ImageIcon yourProfileimg;
		private ImageIcon exitimg;
		
		private JLabel yourprofile;
		private JLabel yourName;
		private JLabel yourMessage;
		private JLabel exit;
		
		private MouseListener mouseListener;
		
		public SmallMessagePanel(SmallMessageFrame smallMessageFrame) {
			this.smallMessageFrame = smallMessageFrame;
			setSize(SmallMessageFrame.WIDTH, SmallMessageFrame.HEIGHT);
			setLayout(null);
			setBackground(new Color(0,0,0,170));
			
			initPanel();
			
			setOpaque(true);
			repaint();
			
		}
		public void initPanel() {
			mouseListener = new MouseListener();
			
			yourProfileimg = new ImageIcon("");
			yourprofile = new JLabel(yourProfileimg);
			yourprofile.setBackground(Color.WHITE);
			yourprofile.setOpaque(true);
			yourprofile.setBounds(20, 20, 40, 40);
			add(yourprofile);
			
			//yourName = new JLabel(buffers[1]);
			String name = mainFrame.getUserName(buffers[1]);
			yourName = new JLabel(name);
			
			yourName.setBounds(90, 20, 150, 20);
			yourName.setForeground(Color.WHITE);
			yourName.setFont(new Font("Serif", Font.BOLD, 20));
			add(yourName);
			
			yourMessage = new JLabel(buffers[3]);
			yourMessage.setBounds(89, 47, 150, 20);
			yourMessage.setForeground(Color.WHITE);
			yourMessage.setFont(new Font("Serif", Font.PLAIN, 15));
			add(yourMessage);
			
			exitimg = new ImageIcon("images/x.png");
			exit = new JLabel(exitimg);
			exit.setBounds(SmallMessageFrame.WIDTH-16-5, 10, 16, 16);
			exit.addMouseListener(new MouseListener());
			add(exit);
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
				if(e.getSource() == exit) {
					smallMessageFrame.dispose();
					System.exit(0);
				}
			}
		}
		
	}
	
	class MyThread extends Thread {
		private SmallMessageFrame s;
		
		public MyThread(SmallMessageFrame s) {
			this.s = s;
		}
		
		public void run() {
			try{
				sleep(3000);
				s.dispose();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
