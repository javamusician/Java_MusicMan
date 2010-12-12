package wh.MusicWorks.Instrument;

import wh.MusicWorks.R;
import wh.MusicWorks.Instrument.Guitar.GuitarRunner;
import wh.MusicWorks.Instrument.Metronome.MetronomeRunner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class InstrumentSwitch extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setWindowFullScreen();
		setContentView(R.layout.layoutforinstrumentswitch);
	}

	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	public void onMetronomeButtonClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(InstrumentSwitch.this,MetronomeRunner.class);
		startActivity(intent);
	}
	
	public void onGuitarButtonClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(InstrumentSwitch.this,GuitarRunner.class);
		startActivity(intent);
	}
}