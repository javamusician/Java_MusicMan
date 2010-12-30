package wh.MusicWorks.Instrument.Metronome;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import wh.MusicWorks.R;
import wh.MusicWorks.Replay.DatabaseOperator.DatabaseOperator;
import wh.MusicWorks.Replay.ReplayData.ReplayDataMetronome;
import wh.MusicWorks.Replay.ReplayRecorder.ReplayRecorder;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @author 王浩
 * @version 1.1
 */
public class MetronomeRunner extends Activity{
	static final int MIN_NODE=10;
	static final int MAX_NODE=30;
	static final int MIN_SIZE=30;
	static final int MAX_SIZE=100;
	public static final int SCREEN_WIDTH=480;
	public static final int SCREEN_HEIGHT=854;
	
	Random mRandom=new Random();
    CheckBox mBeginRecordCheckBox;
    boolean mRunning=false;
    SensorManager mSensorManager;
	List<Sensor> mSensorAccelerometerList;
	SensorEventListener mAccelerometerSensorEventListener;
	MediaPlayer mMediaPlayer;
	Vibrator mVibrator;
	ReplayRecorder mReplayRecorder;

	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setWindowFullScreen();
        setContentView(R.layout.layoutformetronome);
        doInitializations();
	}

	private void doInitializations() {
		initializeDatabaseOperator();
		initializeVibrator();	
		initializeButton();	
        initializeRecorder();
        initializeSensor();
        initializeMedia();
	}

	private void initializeMedia() {
		mMediaPlayer=MediaPlayer.create(this, R.raw.gu);
        mMediaPlayer.setLooping(false);
	}

	private void initializeRecorder() {
		mReplayRecorder=new ReplayRecorder();
	}

	private void initializeVibrator() {
		mVibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void initializeDatabaseOperator() {
		DatabaseOperator.initialize(this);
	}

	private void initializeButton() {
		mBeginRecordCheckBox=(CheckBox)findViewById(R.id.beginrecord);
    	OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					Toast.makeText(MetronomeRunner.this, "开始记录乐曲", Toast.LENGTH_SHORT).show();
				else if (mReplayRecorder.getReplaySize()!=0)
					{
						DatabaseOperator.insertData(new Object[]{mReplayRecorder});
						initializeRecorder();
						Toast.makeText(MetronomeRunner.this, "成功保存乐曲", Toast.LENGTH_SHORT).show();
					}
			}
		};
		mBeginRecordCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
	}
	
	private void initializeSensor() {
		mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        mSensorAccelerometerList=mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		mAccelerometerSensorEventListener=new AccelerometerSensorEventListener();
		
        if (!mSensorAccelerometerList.isEmpty())
        	mSensorManager.registerListener(mAccelerometerSensorEventListener,mSensorAccelerometerList.get(0),
        			SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	protected void onDestroy(){
		// TODO Auto-generated method stub
		DatabaseOperator.destroy();
		mSensorManager.unregisterListener(mAccelerometerSensorEventListener);
		releaseMedia();
		mVibrator.cancel();
		MetronomeView metronomeView=(MetronomeView)findViewById(R.id.metronomeview);
		metronomeView.onDestroy();
		super.onDestroy();
	}

	private void releaseMedia() {
		if (mMediaPlayer.isPlaying())
			mMediaPlayer.stop();
		mMediaPlayer.release();
	}
	
	private class AccelerometerSensorEventListener implements SensorEventListener{
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.values[SensorManager.DATA_X]>20 && !mMediaPlayer.isPlaying())
			{
				updateMedia();
				updateVibrator();
				updateRecord();
				updateMetronomeView();
			}
		}

		private void updateRecord() {
			if (mBeginRecordCheckBox.isChecked()) {
				ReplayDataMetronome replayData=new ReplayDataMetronome(ReplayDataMetronome.METRONOME_MUSICAL_SCALE_00,
						System.currentTimeMillis());
				mReplayRecorder.insertReplayData(replayData);
			}
		}

		private void updateMetronomeView() {
			MetronomeView metronomeView=(MetronomeView)findViewById(R.id.metronomeview);
			metronomeView.explode();
		}

		private void updateVibrator() {
			mVibrator.vibrate(new long[]{100, 100}, 1);
		}

		private void updateMedia() {
			if(mMediaPlayer.isPlaying())
				mediaReset();
			else
				mediaStart();
		}
		
		private void mediaStart() {
			try {
				mMediaPlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mMediaPlayer.start();
		}
		
		private void mediaReset() {
			mMediaPlayer.seekTo(0);
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}
}