package wh.MusicWorks.test;

import android.test.ActivityInstrumentationTestCase2;
import wh.MusicWorks.Instrument.Guitar.GuitarRunner;
import wh.MusicWorks.Instrument.Guitar.ShakingString;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestShakingString extends ActivityInstrumentationTestCase2<GuitarRunner> {
	ShakingString mShakingString;

	public TestShakingString() {
		super("wh.MusicWorks", GuitarRunner.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();
		mShakingString=new ShakingString(240, 854);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCurrentX() {
		assertEquals(240, mShakingString.getCurrentX(), 0.1f);
	}

	public void testGetDifficulty() {
		assertEquals(0, mShakingString.getDifficulty());
	}

	public void testStartShake() {
		mShakingString.startShake();
		assertEquals(240, mShakingString.getCurrentX(), 0.1f);
	}

}
