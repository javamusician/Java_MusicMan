package wh.MusicWorks.test;

import wh.MusicWorks.Replay.ReplayData.ReplayData;
import junit.framework.TestCase;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestReplayData extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testReplayData() {
		ReplayData replayData=new ReplayData(0,0,100);
		assertEquals("0/0/100", replayData.toString());
	}
}
