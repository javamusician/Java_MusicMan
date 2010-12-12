package JavaMusician.MusicMan.Instruments.Piano;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyListener implements KeyListener {
	public void keyPressed(KeyEvent keyEvent) {
		printIt("Pressed", keyEvent);
	}

	public void keyReleased(KeyEvent keyEvent) {
		printIt("Released", keyEvent);
	}

	public void keyTyped(KeyEvent keyEvent) {
		printIt("Typed", keyEvent);
	}

	private void printIt(String title, KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();
		String keyText = KeyEvent.getKeyText(keyCode);
		System.out.println(title + " : " + keyText + " / "
				+ keyEvent.getKeyChar());
	}
};