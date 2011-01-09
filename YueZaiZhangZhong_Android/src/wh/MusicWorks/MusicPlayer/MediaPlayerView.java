package wh.MusicWorks.MusicPlayer;

import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * 
 * @author ÍõºÆ
 * @version 1.1
 */
public class MediaPlayerView extends View{
	public static int SCREEN_WIDTH=854;
	public static int SCREEN_HEIGHT=480;
	public static final int TIME_INTERVAL=50;
	public static final long MAX_GAME_TIME=80000;
	public static final int MIN_NOTE_NUMBER=3;
	public static final int MAX_NOTE_NUMBER=10;
	public static final int MIN_VOLUME=1;
	public static final int MAX_VOLUME=10;
	public static final int SAMPLING_LENGTH=500;
	public static final int MIN_VIBRATE=15;
	public static final int MAX_VIBRATE=35;
	public static final int SOURCE_POSITION_X=338;
	public static final int SOURCE_POSITION_Y=455;
	
	public static Random mRandom=new Random();

	private long mGameTime;
	private Paint mPaint;
	private boolean mGameRunning;

	public MediaPlayerView(Context context) {
		super(context);
		initialize(context);
	}
	
	public MediaPlayerView(Context context, AttributeSet attrs) {
		super(context,attrs);
		initialize(context);
	}
	
	private void initialize(Context context){
		loadScreenInformation(context);
		
		initializeGameTime();	
		initializePaint();
		
		startUpdate();
	}

	private void initializeGameTime() {
		mGameTime=0;
	}

	private void initializePaint() {
		mPaint=new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(50);
	}

	private void startUpdate() {
		mGameRunning=true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (mGameRunning)
				{
					try {
						Thread.sleep(TIME_INTERVAL);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					postInvalidate();
				}
			}
		}).start();
	}

	private void loadScreenInformation(Context context) {
		DisplayMetrics dm=new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH=dm.widthPixels;
		SCREEN_HEIGHT=dm.heightPixels;
	}
	
	public static Random getRandom(){
		return mRandom;
	}
	
	public interface GameObject{
		public abstract boolean isToRemove();
		public abstract void onDraw(Canvas canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		update();
				
		mGameTime+=TIME_INTERVAL;
		
		super.onDraw(canvas);
	}

	private void update() {
		if (isToUpdate())
			vibrate(getCurrentVolume());
		else if (mGameTime>=MAX_GAME_TIME)
			initializeGameTime();
		
	}

	private boolean isToUpdate() {
		return mGameTime<=MAX_GAME_TIME && mGameTime%SAMPLING_LENGTH==0 && MediaPlayerController.musicIsPlaying();
	}
	
	public int getCurrentVolume(){
		return mRandom.nextInt(MAX_VOLUME-MIN_VOLUME)+MIN_VOLUME;
	}
	
	public void vibrate(int volume){
	}
	
	public int getNoteNumber(int volume){
		return (volume-MIN_VOLUME)/(MAX_VOLUME-MIN_VOLUME)*(MAX_NOTE_NUMBER-MIN_NOTE_NUMBER)+MIN_NOTE_NUMBER;
	}

	public void stopRunning() {
		mGameRunning=false;
	}
}