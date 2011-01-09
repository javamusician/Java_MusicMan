package JavaMusician.MusicMan.Runner;

import javax.swing.JFrame;
import JavaMusician.MusicMan.Instruments.Piano.PianoPlayer;


public class PianoRunner {
	public static void main(String args[]) {
		PianoPlayer frame = new PianoPlayer();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(frame);
		frame.setSize(781, 580);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
