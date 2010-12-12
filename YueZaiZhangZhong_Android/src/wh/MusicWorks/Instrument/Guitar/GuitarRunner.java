package wh.MusicWorks.Instrument.Guitar;

import java.io.IOException;
import wh.MusicWorks.R;
import wh.MusicWorks.Replay.DatabaseOperator.DatabaseOperator;
import wh.MusicWorks.Replay.ReplayData.ReplayDataGuitar;
import wh.MusicWorks.Replay.ReplayRecorder.ReplayRecorder;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @author 王浩
 * @version 1.1
 */
public class GuitarRunner extends Activity {
	private static final int MAX_STRING_NUMBER=ReplayDataGuitar.GUITAR_MUSICAL_SCALE_MAX;
	private static final int SCREEN_WIDTH=480;
	CheckBox mBeginRecordCheckBox;
	MediaPlayer[] mMediaPlayer;
	int mLastMediaPlayerOrder;
	Vibrator mVibrator;
	ReplayRecorder mReplayRecorder;
	GuitarView mGuitarView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setWindowFullScreen();
        setContentView(R.layout.layoutforguitar);
        
    	doInitializations();
    }

	private void doInitializations() {
		initializeDatabaseOperator();
		initializeVibrator();
        initializeMedia();
    	initializeButton();
    	initializeRecorder();
    	initializeGuitarView();
	}

	private void initializeDatabaseOperator() {
		DatabaseOperator.initialize(this);
	}
	
	private void initializeVibrator() {
		mVibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	private void initializeMedia() {
		mMediaPlayer=new MediaPlayer[MAX_STRING_NUMBER];
    	for (int i=0;i<MAX_STRING_NUMBER;i++)
    		loadMediaFromResourceByIndex(i);
    	lastPlayingOrderReset();
	}

	private void loadMediaFromResourceByIndex(int index) {
		int id=getResources().getIdentifier("t"+index, "raw", this.getPackageName());
		mMediaPlayer[index]=MediaPlayer.create(this, id);
		mMediaPlayer[index].setLooping(false);
	}

	private void initializeButton() {
		mBeginRecordCheckBox=(CheckBox)findViewById(R.id.beginrecord);
    	OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
					Toast.makeText(GuitarRunner.this, "开始记录乐曲", Toast.LENGTH_SHORT).show();
				else if (mReplayRecorder.getReplaySize()!=0)
					{
						DatabaseOperator.insertData(new Object[]{mReplayRecorder});
						initializeRecorder();
						Toast.makeText(GuitarRunner.this, "成功保存乐曲", Toast.LENGTH_SHORT).show();
					}
			}
		};
		mBeginRecordCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
	}
	
	private void initializeRecorder() {
		mReplayRecorder=new ReplayRecorder();
	}
	
	private void initializeGuitarView() {
		mGuitarView=(GuitarView)findViewById(R.id.guitarview);
	}

	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
    	if(event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE)
    		updateActivity(event);
    	else if (event.getAction()==MotionEvent.ACTION_UP)
			lastPlayingOrderReset();
    	
    	return super.onTouchEvent(event);
    }

	private void updateActivity(MotionEvent event) {
		int stringOrder=getStringOrderByCoordinate(event.getX());
		if (stringOrder!=mLastMediaPlayerOrder)
		{
			playSoundByStringOrder(stringOrder);
			
			mVibrator.vibrate(30);
			
			mGuitarView.setStringShake(stringOrder);
			
			if (mBeginRecordCheckBox.isChecked())
				mReplayRecorder.insertReplayData(new ReplayDataGuitar(stringOrder, System.currentTimeMillis()));

			mLastMediaPlayerOrder=stringOrder;
		}
	}

	private void lastPlayingOrderReset() {
		mLastMediaPlayerOrder=-1;
	}

	private void playSoundByStringOrder(int stringOrder) {
		if (mMediaPlayer[stringOrder].isPlaying())
			mMediaPlayer[stringOrder].seekTo(0);
		else
		{
	    	try {
	    		mMediaPlayer[stringOrder].prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mMediaPlayer[stringOrder].start();
		}
	}
    
    @Override
    protected void onDestroy(){
    	// TODO Auto-generated method stub
    	DatabaseOperator.destroy();
    	releaseMedia();
    	mVibrator.cancel();
    	mGuitarView.onDestroy();
    	super.onDestroy();
    }

	private void releaseMedia() {
		for (int i=0;i<MAX_STRING_NUMBER;i++)
    	{
	    	if (mMediaPlayer[i].isPlaying())
	    		mMediaPlayer[i].stop();
	    	mMediaPlayer[i].release();
    	}
	}
    
    public int getStringOrderByCoordinate(float x) {
		return (int)x*MAX_STRING_NUMBER/(SCREEN_WIDTH+1);
	}
}