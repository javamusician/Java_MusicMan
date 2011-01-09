package wh.MusicWorks.test;

import wh.MusicWorks.MusicPlayer.MediaPlayerView;
import wh.MusicWorks.MusicPlayer.MusicListActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestMediaPlayerView extends ActivityInstrumentationTestCase2<MusicListActivity>  {
	MusicListActivity mMusicListActivity;
	MediaPlayerView mMediaPlayerView;
	
	public TestMediaPlayerView() {
		super("wh.MusicWorks", MusicListActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mMusicListActivity=getActivity();
		mMediaPlayerView=new MediaPlayerView(mMusicListActivity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetCurrentVolume() {
		int currentVolume=mMediaPlayerView.getCurrentVolume();
		assertTrue(currentVolume<=MediaPlayerView.MAX_VOLUME && currentVolume>=MediaPlayerView.MIN_VOLUME);
	}

	public final void testGetNoteNumber() {
		assertEquals(3, mMediaPlayerView.getNoteNumber(0));
	}
}
