package wh.MusicWorks.test;

import wh.MusicWorks.R;
import wh.MusicWorks.YueZaiZhangZhong;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestYueZaiZhangZhong extends ActivityInstrumentationTestCase2<YueZaiZhangZhong> {
	YueZaiZhangZhong mActivity;
	Button mInstrumentButton,mMediaplayerButton,mRecordButton;

	public TestYueZaiZhangZhong() {
		super("wh.MusicWorks", YueZaiZhangZhong.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity=(YueZaiZhangZhong)getActivity();
		mInstrumentButton=(Button)mActivity.findViewById(R.id.instrumentbutton);
		mMediaplayerButton=(Button)mActivity.findViewById(R.id.mediaplayerbutton);
		mRecordButton=(Button)mActivity.findViewById(R.id.recordbutton);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SmallTest
	public void testOnInstrumentButtonClick(){
		mActivity.onInstrumentButtonClick(mInstrumentButton);
	}
	
	@SmallTest
	public void testOnMediaplayerButtonClick(){
		mActivity.onInstrumentButtonClick(mMediaplayerButton);
	}
	
	@SmallTest
	public void testOnRecordButtonClick(){
		mActivity.onInstrumentButtonClick(mRecordButton);
	}
}
