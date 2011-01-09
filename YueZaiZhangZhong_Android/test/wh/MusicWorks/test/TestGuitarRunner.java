package wh.MusicWorks.test;

import wh.MusicWorks.R;
import wh.MusicWorks.YueZaiZhangZhong;
import wh.MusicWorks.Instrument.Guitar.GuitarRunner;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestGuitarRunner extends ActivityInstrumentationTestCase2<GuitarRunner> {
	GuitarRunner mActivity;

	public TestGuitarRunner() {
		super("wh.MusicWorks", GuitarRunner.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity=(GuitarRunner)getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetStringOrderByCoordinate(){
		int stringOrder=mActivity.getStringOrderByCoordinate(15);
		assertEquals(0, stringOrder);
	}
}
