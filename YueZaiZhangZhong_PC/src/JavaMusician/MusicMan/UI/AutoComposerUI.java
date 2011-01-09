package JavaMusician.MusicMan.UI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import JavaMusician.MusicMan.Runnable.AutoComposerRunnable;
import JavaMusician.MusicMan.gui.StartUp.StartUp;

public class AutoComposerUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ±³¾°
	 */
	JLabel mBackground=new JLabel(new ImageIcon("res/drawable/ui/interface_zidongpuqu.png"));
	
	private JButton b1 = new JButton("ÔËÐÐ");
	private JButton b2 = new JButton("ÍË³ö");
	
	private JLabel j1 = new JLabel("rhythm");
	private JLabel j2 = new JLabel("insideRhythm");
	private JLabel j3 = new JLabel("instrument");
	private JLabel j4 = new JLabel("length");
	private JLabel j5 = new JLabel("highTide");
	private JLabel j6 = new JLabel("mildToWild");
	
	private JTextField t1 = new JTextField(30);
	private JTextField t2 = new JTextField(30);
	private JTextField t3 = new JTextField(30);
	private JTextField t4 = new JTextField(30);
	private JTextField t5 = new JTextField(30);
	private JTextField t6 = new JTextField(30);
	
	public AutoComposerUI(){
		super();
		
		setSize(800, 600+30);
		setResizable(false);
		setLayout(null);
		setTitle("AutoComposer");

		setVisible(true);
		
		Container contentPane = getContentPane();
		
		j1.setBounds(20+100, 200, 100, 30);
		j2.setBounds(120+100, 200, 100, 30);
		j3.setBounds(220+100, 200, 100, 30);
		j4.setBounds(320+100, 200, 100, 30);
		j5.setBounds(420+100, 200, 100, 30);
		j6.setBounds(520+100, 200, 100, 30);
		contentPane.add(j1);
		contentPane.add(j2);
		contentPane.add(j3);
		contentPane.add(j4);
		contentPane.add(j5);
		contentPane.add(j6);
		
		t1.setBounds(0+100, 230, 100, 30);
		t2.setBounds(101+100, 230, 100, 30);
		t3.setBounds(201+100, 230, 100, 30);
		t4.setBounds(301+100, 230, 100, 30);
		t5.setBounds(401+100, 230, 100, 30);
		t6.setBounds(501+100, 230, 100, 30);
		contentPane.add(t1);
		contentPane.add(t2);
		contentPane.add(t3);
		contentPane.add(t4);
		contentPane.add(t5);
		contentPane.add(t6);
		
		b1.setBounds(201, 280, 100, 30);
		b2.setBounds(501, 280, 100, 30);
		contentPane.add(b1);
		contentPane.add(b2);
		
		mBackground.setBounds(0, 0, 800, 600);
		contentPane.add(mBackground);
		
		b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() 
						&& !t4.getText().isEmpty() && !t5.getText().isEmpty() && !t6.getText().isEmpty()){
					AutoComposerRunnable ac = new AutoComposerRunnable(Integer.parseInt(t1.getText()), Integer.parseInt(t2.getText()), Integer.parseInt(t3.getText()), 
							Integer.parseInt(t4.getText()), Integer.parseInt(t5.getText()), Integer.parseInt(t6.getText()));
					Thread t = new Thread(ac);
					t.start();
				}
			}
		});
		
		b2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				StartUp.mFrame.setVisible(true);
			}
		});
	}

}
