package wh.MusicWorks.test;

import wh.MusicWorks.Replay.ReplayData.ReplayDataGuitar;
import wh.MusicWorks.Replay.ReplayRecorder.ReplayRecorder;
import junit.framework.TestCase;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestReplayRecorder extends TestCase {

	private ReplayRecorder mReplayRecorder;

	protected void setUp() throws Exception {
		super.setUp();
		mReplayRecorder=new ReplayRecorder();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testInsertSingleReplayData() {
		ReplayDataGuitar replayData=new ReplayDataGuitar(1,100);
		mReplayRecorder.insertReplayData(replayData);
		
		assertEquals(1, mReplayRecorder.getReplaySize());
	}
	
	public final void testInsertMultipleReplayData() {
		ReplayDataGuitar replayData=new ReplayDataGuitar(1,100);
		mReplayRecorder.insertReplayData(replayData);
		replayData=new ReplayDataGuitar(2,100);
		mReplayRecorder.insertReplayData(replayData);
		replayData=new ReplayDataGuitar(3,100);
		mReplayRecorder.insertReplayData(replayData);
		
		assertEquals(3, mReplayRecorder.getReplaySize());
	}

	public final void testGetReplaySize() {
		ReplayDataGuitar replayData=new ReplayDataGuitar(1,100);
		mReplayRecorder.insertReplayData(replayData);
		replayData=new ReplayDataGuitar(2,100);
		mReplayRecorder.insertReplayData(replayData);
		replayData=new ReplayDataGuitar(3,100);
		mReplayRecorder.insertReplayData(replayData);
		
		assertEquals(3, mReplayRecorder.getReplaySize());
	}

	public final void testGetReplayData() {
		ReplayDataGuitar replayData=new ReplayDataGuitar(1,100);
		mReplayRecorder.insertReplayData(replayData);
		replayData=new ReplayDataGuitar(2,100);
		mReplayRecorder.insertReplayData(replayData);
		replayData=new ReplayDataGuitar(3,100);
		mReplayRecorder.insertReplayData(replayData);
		
		assertEquals("1/3/100", mReplayRecorder.getReplayData(2).toString());
	}
}
