package wh.MusicWorks.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import wh.MusicWorks.MusicPlayer.MediaPlayerControlReceiver;
import wh.MusicWorks.MusicPlayer.MediaPlayerController;
import wh.MusicWorks.MusicPlayer.MusicListActivity;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestMediaPlayerControlReceiver extends ActivityInstrumentationTestCase2<MusicListActivity>  {
	MusicListActivity mMusicListActivity;
	
	public TestMediaPlayerControlReceiver() {
		super("wh.MusicWorks", MusicListActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mMusicListActivity=getActivity();
		Intent intent=new Intent(MediaPlayerControlReceiver.PLAY_BUTTON_PRESS);
		mMusicListActivity.sendBroadcast(intent);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testOnReceiveContextIntentPlayButtonPress() {
		assertTrue(MediaPlayerController.musicIsPlaying());
	}
	
	public final void testOnReceiveContextIntentPlayButtonPressTwice() {
		Intent intent=new Intent(MediaPlayerControlReceiver.PLAY_BUTTON_PRESS);
		mMusicListActivity.sendBroadcast(intent);
		assertTrue(!MediaPlayerController.musicIsPlaying());
	}
	
	public final void testOnReceiveContextIntentStopButtonPress() {
		Intent intent=new Intent(MediaPlayerControlReceiver.STOP_BUTTON_PRESS);
		mMusicListActivity.sendBroadcast(intent);
		assertTrue(!MediaPlayerController.musicIsPlaying());
	}
	
	public final void testOnReceiveContextIntentNextButtonPress() {
		Intent intent=new Intent(MediaPlayerControlReceiver.NEXT_BUTTON_PRESS);
		mMusicListActivity.sendBroadcast(intent);
		assertEquals(0,MediaPlayerController.getCurrentPlayingIndex());
	}
}
