package testPianoPlaying;

import java.awt.event.KeyEvent;
import org.junit.Test;
import JavaMusician.MusicMan.Instruments.Piano.PianoPlayer;

public class testPianoPlayer {
	@Test
	public void testTranslate() {
		PianoPlayer tester = new PianoPlayer();
		tester.translate();
		assert (tester.valueList.size() != 0);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAnalyKey() {
		PianoPlayer tester = new PianoPlayer();
		String testTempString = new String();
		testTempString = tester.analyKeyCode(
				new KeyEvent(tester, 0, 0, 0, 'a'), testTempString);
		assert(testTempString=="a");
	}
}