package JavaMusician.MusicMan.Instruments.Piano;

import javax.swing.*;
import JavaMusician.MusicMan.MIDIPlayer.MIDIPlayer;
import wh.XMLParser.XMLParser;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class PianoPlayer extends JFrame implements KeyListener, Runnable {
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
	ImageIcon icon;
	ImageIcon keyicon;
	Image image;
	int[] toneNum;
	MIDIPlayer midPlayer;
	List<String> keyIdList, valueList;

	public PianoPlayer() {
		initArg();
		setKeyIdList();
		translate();
		initToneNum();
		setPanel();
		getContentPane().add(scrollPane);
	}

	private void setPanel() {
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(400, 400));
		scrollPane = new JScrollPane(panel);
	}

	private void initToneNum() {
		toneNum = new int[36];
		for (int i = 0; i < 36; i++)
			toneNum[i] = 0;
	}

	private void setKeyIdList() {
		for (int i = 0; i < 26; i++) {
			char temp = (char) ('a' + i);
			String tempstring = new String();
			tempstring += temp;
			keyIdList.add(tempstring);
		}
		for (int i = 0; i <= 9; i++) {
			char temp = (char) ('0' + i);
			String tempstring = new String();
			tempstring += temp;
			keyIdList.add(tempstring);
		}
	}

	private void initArg() {
		icon = new ImageIcon("res/drawable/background.jpg");
		keyicon = new ImageIcon("res/drawable/key.jpg");
		midPlayer = new MIDIPlayer();
		keyIdList = new ArrayList<String>();
		valueList = new ArrayList<String>();
	}

	public void translate() {
		XMLParser.ParseXML("main.xml", keyIdList, valueList);
	}

	public void keyPressed(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		char keyChar = keyEvent.getKeyChar();
		if (keyChar <= 'z' && keyChar >= 'a')
			toneNum[keyChar - 'a'] = 1;
		if (keyChar <= '9' && keyChar >= '0')
			toneNum[keyChar - '1' + 27] = 1;
		repaint();
		String tempstring = new String();
		tempstring += keyChar;
		midPlayer.play(valueList.get(keyIdList.indexOf(tempstring)));
	}

	public void keyReleased(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		char keyChar = keyEvent.getKeyChar();
		keyEvent.getKeyCode();
		if (keyChar <= 'z' && keyChar >= 'a')
			toneNum[keyChar - 'a'] = 0;
		if (keyChar <= '9' && keyChar >= '0')
			toneNum[keyChar - '1' + 27] = 0;
		repaint();
	}

	public void keyTyped(KeyEvent keyEvent) {

	}

	public void paint(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, null);
		g.setColor(Color.RED);
		for (int i = 0; i < 36; i++) {
			if (toneNum[i] == 1)
				g.drawImage(keyicon.getImage(), i * 21 + i * 22 / 35 , 120,
						null);
		}
	}

	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}