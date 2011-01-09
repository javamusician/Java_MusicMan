package wh.MusicWorks.test;

import android.test.ActivityInstrumentationTestCase2;
import wh.MusicWorks.MusicPlayer.MediaPlayerController;
import wh.MusicWorks.MusicPlayer.MusicListActivity;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestMediaPlayerController extends ActivityInstrumentationTestCase2<MusicListActivity>  {
	private MusicListActivity mMusicListActivity;

	public TestMediaPlayerController() {
		super("wh.MusicWorks", MusicListActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mMusicListActivity=(MusicListActivity)getActivity();
		MediaPlayerController.musicStart(mMusicListActivity, true, 0);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		MediaPlayerController.musicStop();
	}

	public final void testMusicStart() {
		assertTrue(MediaPlayerController.musicIsPlaying());
	}

	public final void testMusicPause() {
		MediaPlayerController.musicPause();
		assertTrue(!MediaPlayerController.musicIsPlaying());
	}

	public final void testMusicStop() {
		MediaPlayerController.musicStop();
		assertTrue(!MediaPlayerController.musicIsPlaying());
	}

	public final void testMusicIsPlaying() {
		assertTrue(MediaPlayerController.musicIsPlaying());
	}

	public final void testGetMusicDunation() {
		assertEquals(189910, MediaPlayerController.getMusicDunation());
	}

	public final void testMusicNext() {
		MediaPlayerController.musicNext();
		assertEquals(0, MediaPlayerController.getCurrentPlayingIndex());
	}

	public final void testSetLooping() {
		MediaPlayerController.setLooping(true);
		assertEquals(0, MediaPlayerController.getCurrentPlayingIndex());
	}
	
	public final void testGetCurrentPlayingIndex() {
		assertEquals(0, MediaPlayerController.getCurrentPlayingIndex());
	}

	public final void testGetMaxPlayingIndex() {
		assertEquals(1, MediaPlayerController.getMaxPlayingIndex());
	}

	public final void testIsFromResource() {
		assertTrue(MediaPlayerController.isFromResource());
	}

	public final void testInitialize() {
		assertTrue(MediaPlayerController.isFromResource());
	}

	public final void testGetResourceMusicName() {
		assertMusicName(new String[]{"ResourceMusic1"},MediaPlayerController.getResourceMusicName());
	}

	private void assertMusicName(String[] stringList,
			String[] resourceMusicName) {
		// TODO Auto-generated method stub
		boolean flag=true;
		if (stringList.length==resourceMusicName.length)
		{
			for (int i=0;i<stringList.length && flag;i++)
				if (!stringList[i].equals(resourceMusicName[i]))
					flag=false;
		}
		else
			flag=false;
		
		assertTrue(flag);
	}

	public final void testGetSDCardMusicName() {
		MediaPlayerController.initialize(mMusicListActivity, "/sdcard/test", null, false);
		assertMusicName(new String[]{"ÐÇÐÇ.mp3"},MediaPlayerController.getSDCardMusicName());
	}

	public final void testGetMusicName() {
		assertEquals("ResourceMusic1",MediaPlayerController.getMusicName());
	}
}