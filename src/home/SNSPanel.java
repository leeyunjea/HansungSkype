package home;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import sns.SNS;

public class SNSPanel extends JPanel {
	private ImageIcon profile;
	
	private JLabel profilelb;
	private JLabel writerlb;
	private JLabel datelb;
	private JTextArea area;
	private SNS sns;
	
	public SNSPanel(ImageIcon img, SNS sns) {
		debug.Debug.log("SNSPanel");
		this.sns = sns;
		this.profile = img;
		setLayout(null);
		
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		
		UIinit();
		invalidate();
		repaint();
		
	}
	
	public void UIinit() {
	
		
		profilelb = new JLabel(profile);
		profilelb.setBounds(10, 10, 40, 40);
		profilelb.setBackground(Color.WHITE);
		add(profilelb);
		
		writerlb = new JLabel(sns.getWriter());
		writerlb.setBounds(60, 10, 100, 20);
		add(writerlb);
		
		datelb = new JLabel(sns.getDate());
		datelb.setBounds(60, 30, 130, 20);
		add(datelb);
		
		area = new JTextArea();
		area.removeAll();
		area.setBounds(10, 60, 180, 70);
		area.setBackground(Color.WHITE);
		area.setBorder(BorderFactory.createLineBorder(new Color(216, 229, 239)));
		area.append(sns.getMsg());
		add(area);
		
		
		invalidate();
		repaint();
	}

}
