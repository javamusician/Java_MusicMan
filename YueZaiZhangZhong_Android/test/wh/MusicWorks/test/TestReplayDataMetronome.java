package wh.MusicWorks.test;

import wh.MusicWorks.Replay.ReplayData.ReplayDataGuitar;
import wh.MusicWorks.Replay.ReplayData.ReplayDataMetronome;
import junit.framework.TestCase;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestReplayDataMetronome extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testReplayDataMetronome() {
		ReplayDataMetronome replayDataMetronome=new ReplayDataMetronome(0, 100);
		assertEquals("0/0/100", replayDataMetronome.toString());
	}

}