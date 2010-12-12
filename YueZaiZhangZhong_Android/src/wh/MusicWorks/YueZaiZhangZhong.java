package wh.MusicWorks;

import wh.MusicWorks.Instrument.InstrumentSwitch;
import wh.MusicWorks.MusicPlayer.MusicListActivity;
import wh.MusicWorks.Replay.ReplayRunner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class YueZaiZhangZhong extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setWindowFullScreen();
		setContentView(R.layout.main);
	}

	public void onInstrumentButtonClick(View v){
		Intent intent=new Intent(YueZaiZhangZhong.this,InstrumentSwitch.class);
		startActivity(intent);
	}
	
	public void onMediaplayerButtonClick(View v){
		Intent intent=new Intent(YueZaiZhangZhong.this,MusicListActivity.class);
		startActivity(intent);
	}
	
	public void onRecordButtonClick(View v){
		Intent intent=new Intent(YueZaiZhangZhong.this,ReplayRunner.class);
		startActivity(intent);
	}
	
	public void onAboutButtonClick(View v){

	}
	
	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}