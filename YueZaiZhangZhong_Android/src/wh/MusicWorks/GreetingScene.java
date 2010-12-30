package wh.MusicWorks;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author ÍõºÆ
 * @version 1.1
 */
public class GreetingScene extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setWindowFullScreen();

		setContentView(R.layout.layoutforgreetingscene);
	    changeActivityDelay();
	}

	private void changeActivityDelay() {
		Timer timer=new Timer();
	    TimerTask task=new TimerTask(){
	    	public void run(){
            	Intent intent=new Intent(GreetingScene.this,YueZaiZhangZhong.class);
				startActivity(intent);
            	finish();
            	cancel();
	    	}
	    };
	    timer.schedule(task,1500,1000);
	}

	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK)
			return true;
		else
			return super.onKeyDown(keyCode, event);
	}
}