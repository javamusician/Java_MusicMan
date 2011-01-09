package wh.MusicWorks.test;

import wh.MusicWorks.Instrument.Guitar.GuitarRunner;
import wh.MusicWorks.Instrument.Guitar.GuitarView;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestGuitarView extends ActivityInstrumentationTestCase2<GuitarRunner> {
	GuitarRunner mActivity;
	GuitarView mGuitarView;

	public TestGuitarView() {
		super("wh.MusicWorks", GuitarRunner.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity=(GuitarRunner)getActivity();
		mGuitarView=new GuitarView(mActivity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSetStringShake(){
		mGuitarView.setStringShake(0);
	}
	
	public void testOnDestroy(){
		mGuitarView.onDestroy();
	}
}
