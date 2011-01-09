package wh.MusicWorks.test;

import java.util.ArrayList;

import wh.MusicWorks.MusicPlayer.FileListAdapter;
import wh.MusicWorks.MusicPlayer.MusicListActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestFileListAdapter extends ActivityInstrumentationTestCase2<MusicListActivity> {
	MusicListActivity mMusicListActivity;
	FileListAdapter mFileListAdapter;

	public TestFileListAdapter() {
		super("wh.MusicWorks", MusicListActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mMusicListActivity=(MusicListActivity)getActivity();
		ArrayList<String> item = new ArrayList<String>();
		ArrayList<String> path = new ArrayList<String>();
		item.add("sdcard");
		path.add("/sdcard/");
		mFileListAdapter=new FileListAdapter(mMusicListActivity, item, path);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetCount() {
		assertEquals(1,mFileListAdapter.getCount());
	}

	public final void testGetItem() {
		assertEquals("sdcard",mFileListAdapter.getItem(0).toString());
	}

	public final void testGetItemId() {
		assertEquals(1,mFileListAdapter.getCount());
	}
}
