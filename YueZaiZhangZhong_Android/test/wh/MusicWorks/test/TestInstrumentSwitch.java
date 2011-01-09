package wh.MusicWorks.test;

import wh.MusicWorks.R;
import wh.MusicWorks.Instrument.InstrumentSwitch;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestInstrumentSwitch extends ActivityInstrumentationTestCase2<InstrumentSwitch> {
	InstrumentSwitch mActivity;
	Button mMetronomeButton,mGuitarButton;
	
	public TestInstrumentSwitch() {
		super("wh.MusicWorks", InstrumentSwitch.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity=(InstrumentSwitch)getActivity();
		mMetronomeButton=(Button)mActivity.findViewById(R.id.metronomebutton);
		mGuitarButton=(Button)mActivity.findViewById(R.id.guitarbutton);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOnMetronomeButtonClick(){
		mActivity.onMetronomeButtonClick(mMetronomeButton);
	}
	
	public void testOnGuitarButtonClick(){
		mActivity.onGuitarButtonClick(mGuitarButton);
	}
}
