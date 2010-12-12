package wh.MusicWorks.Replay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wh.MusicWorks.Replay.DatabaseOperator.DatabaseOperator;
import wh.MusicWorks.Replay.ReplayData.ReplayData;
import wh.MusicWorks.Replay.ReplayData.ReplayDataGuitar;
import wh.MusicWorks.Replay.ReplayRecorder.ReplayRecorder;
import wh.MusicWorks.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class ReplayRunner extends Activity{
	private static final int MAX_STRING_NUMBER=ReplayDataGuitar.GUITAR_MUSICAL_SCALE_MAX;
	MediaPlayer[] mGuitarMediaPlayer;
	MediaPlayer mMetronomeMediaPlayer;
	ListView mRecordListView;
	ArrayAdapter<String> mRecordAdapter;
	List<Object> mData;
	
	Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			mRecordListView.setClickable(true);
			mRecordListView.setLongClickable(true);
			mRecordListView.setEnabled(true);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setWindowFullScreen();
		setContentView(R.layout.layoutforreplay);
		doInitialization();
	}

	private void doInitialization() {
		initializeDatabase();
		initializeMediaPlayer();
    	
		String[] recordNameArray=loadRecordName();
		
		initializeRecordListView(recordNameArray);
	}

	private void initializeRecordListView(String[] recordNameArray) {
		mRecordListView=(ListView)findViewById(R.id.recordlistview);

		mRecordAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,recordNameArray);

		mRecordListView.setAdapter(mRecordAdapter);

		mRecordListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				setRecordListViewDisable();
				startPlayingRecord(arg2);
			}
		});
	}
	
	private void startPlayingRecord(final int arg2) {
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReplayRecorder replayRecorder=(ReplayRecorder)mData.get(arg2);
				playRecord(replayRecorder);
				mHandler.sendMessage(mHandler.obtainMessage());
			}

		};
		new Thread(runnable).start();
	}

	private void playRecord(ReplayRecorder replayRecorder) {
		int i=0;
		for (;i<replayRecorder.getReplaySize();i++)
		{
			switch(replayRecorder.getReplayData(i).mDataType)
			{
			case ReplayData.DATA_TYPE_METRONOME:
				playMusic(mMetronomeMediaPlayer);
				break;
			case ReplayData.DATA_TYPE_GUITAR:
				playMusic(mGuitarMediaPlayer[replayRecorder.getReplayData(i).mOperation]);
				break;
				default:;
			}
			if (i!=replayRecorder.getReplaySize()-1)
				try {
					Thread.sleep(replayRecorder.getReplayData(i+1).mTime-replayRecorder.getReplayData(i).mTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	private void setRecordListViewDisable() {
		mRecordListView.setClickable(false);
		mRecordListView.setLongClickable(false);
		mRecordListView.setEnabled(false);
	}
	private String[] loadRecordName() {
		mData=DatabaseOperator.getData();
		
		List<String> recordNameList=new ArrayList<String>();
		for (int i=0;i<mData.size();i++)
			recordNameList.add(mData.get(i).toString());
		
		String[] recordNameArray=new String[recordNameList.size()];
		recordNameList.toArray(recordNameArray);
		return recordNameArray;
	}

	private void initializeMediaPlayer() {
		mGuitarMediaPlayer=new MediaPlayer[MAX_STRING_NUMBER];
    	for (int i=0;i<MAX_STRING_NUMBER;i++)
    	{
    		int id=getResources().getIdentifier("t"+i, "raw", this.getPackageName());
    		mGuitarMediaPlayer[i]=MediaPlayer.create(ReplayRunner.this, id);
    		mGuitarMediaPlayer[i].setLooping(false);
    	}
    	mMetronomeMediaPlayer=MediaPlayer.create(ReplayRunner.this, R.raw.gu);
    	mMetronomeMediaPlayer.setLooping(false);
	}

	private void initializeDatabase() {
		DatabaseOperator.initialize(this);
	}

	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		DatabaseOperator.destroy();
		
		releaseMetronomeMedia();
		
		releaseGuitarMedia();
		
		super.onDestroy();
	}

	private void releaseGuitarMedia() {
		for (int i=0;i<mGuitarMediaPlayer.length;i++)
		{
			if (mGuitarMediaPlayer[i].isPlaying())
				mGuitarMediaPlayer[i].stop();
			mGuitarMediaPlayer[i].release();
		}
	}

	private void releaseMetronomeMedia() {
		if (mMetronomeMediaPlayer.isPlaying())
			mMetronomeMediaPlayer.stop();
		mMetronomeMediaPlayer.release();
	}
	
	private void playMusic(MediaPlayer mediaPlayer){
		if (mediaPlayer.isPlaying())
			mediaPlayer.seekTo(0);
		else
		{
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mediaPlayer.start();
		}
	}
}