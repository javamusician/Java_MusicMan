package wh.MusicWorks.test;

import wh.MusicWorks.R;
import wh.MusicWorks.MusicPlayer.MediaPlayerController;
import wh.MusicWorks.MusicPlayer.MusicListActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import junit.framework.TestCase;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestMusicListActivity extends ActivityInstrumentationTestCase2<MusicListActivity>  {
	private MusicListActivity mMusicListActivity;
	Button mStartButton,mStopButton,mNextButton;

	public TestMusicListActivity() {
		super("wh.MusicWorks", MusicListActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mMusicListActivity=(MusicListActivity)getActivity();
		mStartButton=(Button) mMusicListActivity.findViewById(R.id.start);
		mStopButton=(Button) mMusicListActivity.findViewById(R.id.stop);
		mNextButton=(Button) mMusicListActivity.findViewById(R.id.next);
		mMusicListActivity.onClickStart(mStartButton);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testOnClickStart() {
		assertTrue(MediaPlayerController.musicIsPlaying());
	}

	public final void testOnClickStartTwice() {
		mMusicListActivity.onClickStart(mStartButton);
		assertTrue(!MediaPlayerController.musicIsPlaying());
	}
	
	public final void testOnClickStop() {
		mMusicListActivity.onClickStop(mStopButton);
		assertTrue(!MediaPlayerController.musicIsPlaying());
	}

	public final void testOnClickNext() {
		mMusicListActivity.onClickNext(mNextButton);
		assertEquals(0,MediaPlayerController.getCurrentPlayingIndex());
	}

	public final void testOnKeyDown() {
		sendKeys(KeyEvent.KEYCODE_BACK);
		assertTrue(mMusicListActivity.isFinishing());
	}
}
