package JavaMusician.MusicMan.Instruments.Piano;
import java.awt.event.*;
import javax.swing.*;

import JavaMusician.MusicMan.MIDIPlayer.MIDIPlayer;

import wh.XMLParser.XMLParser;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class PlayGround extends JFrame implements KeyListener, Runnable {
	JScrollPane scrollPane;
	ImageIcon icon;
	ImageIcon keyicon;
	Image image;
	int[] a;
	MIDIPlayer midPlayer;
	List<String> keyIdList, valueList;

	public PlayGround() {
		icon = new ImageIcon("background.jpg");
		keyicon = new ImageIcon("key.jpg");
		midPlayer = new MIDIPlayer();
		keyIdList = new ArrayList<String>();
		valueList = new ArrayList<String>();
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
		translate();
//		System.out.println(valueList.get(25));
		a = new int[36];
		for (int i = 0; i < 36; i++)
			a[i] = 0;
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				// g.drawImage(keyicon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(400, 400));
		scrollPane = new JScrollPane(panel);
		getContentPane().add(scrollPane);
	}

	public void translate() {
		XMLParser.ParseXML("main.xml", keyIdList, valueList);
	}

	public void keyPressed(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		char keyChar = keyEvent.getKeyChar();
		if (keyChar <= 'z' && keyChar >= 'a')
			a[keyChar - 'a'] = 1;
		if (keyChar <= '9' && keyChar >= '0')
			a[keyChar - '1' + 27] = 1;
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
			a[keyChar - 'a'] = 0;
		if (keyChar <= '9' && keyChar >= '0')
			a[keyChar - '1' + 27] = 0;
		repaint();
	}

	public void keyTyped(KeyEvent keyEvent) {

	}

	public void paint(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, null);
		g.setColor(Color.RED);
		for (int i = 0; i < 36; i++) {
//			System.out.print(a[i]);

			if (a[i] == 1)
				g.drawImage(keyicon.getImage(), i * 21 + i * 22 / 35 , 120,
						null);
			// g.fillRect(i * 22 - i / 3, 130, 20, 47);
		}
		System.out.println();
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