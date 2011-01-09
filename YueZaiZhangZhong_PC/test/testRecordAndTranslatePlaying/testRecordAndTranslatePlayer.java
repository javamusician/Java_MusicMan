package testRecordAndTranslatePlaying;

import org.junit.Test;
import JavaMusician.MusicMan.RecordPlay.RecordAndTranslatePlayer;

public class testRecordAndTranslatePlayer {

	@Test
	public void testCaptureRecord() {
		RecordAndTranslatePlayer tester = new RecordAndTranslatePlayer();
		tester.captureRecord();
		
	}

	@Test
	public void testStopRecord() {
		RecordAndTranslatePlayer tester = new RecordAndTranslatePlayer();
		tester.stopRecord();
	}
}
