package wh.MusicWorks.test;

import wh.MusicWorks.GreetingScene;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestGreetingScene extends ActivityInstrumentationTestCase2<GreetingScene> {
	Activity mActivity;
	
	public TestGreetingScene() {
		super("wh.MusicWorks", GreetingScene.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity=getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testOnKeyDown(){
		sendKeys(KeyEvent.KEYCODE_BACK);
		assertEquals("Activity should not be finished.", false, mActivity.isFinishing());
	}
}
