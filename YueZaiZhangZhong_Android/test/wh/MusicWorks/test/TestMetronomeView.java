package wh.MusicWorks.test;

import android.test.ActivityInstrumentationTestCase2;
import wh.MusicWorks.Instrument.Metronome.MetronomeRunner;
import wh.MusicWorks.Instrument.Metronome.MetronomeView;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestMetronomeView extends ActivityInstrumentationTestCase2<MetronomeRunner> {
	MetronomeRunner mMetronomeRunner;
	MetronomeView mMetronomeView;
	
	public TestMetronomeView(String pkg, Class<MetronomeRunner> activityClass) {
		super(pkg, activityClass);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mMetronomeRunner=getActivity();
		mMetronomeView=new MetronomeView(mMetronomeRunner);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testExplode() {
		mMetronomeView.explode();
	}

	public final void testOnDestroy() {
		mMetronomeView.onDestroy();
	}

}
