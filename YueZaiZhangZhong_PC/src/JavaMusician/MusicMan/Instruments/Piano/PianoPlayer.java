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
	List<String> keyIdList;
	public List<String> valueList;

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
			private static final long serialVersionUID = 1L;
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
			char tempChar = (char) ('a' + i);
			String tempString = new String();
			tempString += tempChar;
			keyIdList.add(tempString);
		}
		for (int i = 0; i <= 9; i++) {
			char tempChar = (char) ('0' + i);
			String tempString = new String();
			tempString += tempChar;
			keyIdList.add(tempString);
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

	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent keyEvent) {

		String tempString = new String();
		tempString = analyKeyCode(keyEvent, tempString);
		repaint();
		if (tempString.length() > 0)
			midPlayer.play(valueList.get(keyIdList.indexOf(tempString)));
	}

	public String analyKeyCode(KeyEvent keyEvent, String tempString) {
		@SuppressWarnings("unused")
		int keyCode = keyEvent.getKeyCode();
		char keyChar = keyEvent.getKeyChar();
		if (keyChar <= 'z' && keyChar >= 'a') {
			toneNum[keyChar - 'a'] = 1;
			tempString += keyChar;
		}
		if (keyChar <= '9' && keyChar >= '0') {
			toneNum[keyChar - '1' + 27] = 1;
			tempString += keyChar;
		}
		return tempString;
	}

	public void keyReleased(KeyEvent keyEvent) {
		@SuppressWarnings("unused")
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
				g.drawImage(keyicon.getImage(), i * 21 + i * 22 / 35, 120, null);
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