package wh.MusicWorks.test;

import wh.MusicWorks.Replay.ReplayData.ReplayDataGuitar;
import junit.framework.TestCase;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestReplayDataGuitar extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testReplayDataGuitar() {
		ReplayDataGuitar replayDataGuitar=new ReplayDataGuitar(0, 100);
		assertEquals("1/0/100", replayDataGuitar.toString());
	}

}
