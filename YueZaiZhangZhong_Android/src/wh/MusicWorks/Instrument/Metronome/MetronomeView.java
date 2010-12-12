package wh.MusicWorks.Instrument.Metronome;

import java.util.Random;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class MetronomeView extends View{
	public static final int MAX_POINT_NUMBER=5000;
	public static int SCREEN_WIDTH=480;
	public static int SCREEN_HEIGHT=854;
	public static final int MAX_OBJECT_NUMBER=3000;
	public static final long TIME_INTERVAL = 150;

	private boolean mGameRunning;
	private Random mRandom=new Random();
	private int mColorIndex;
	
	public MetronomeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public MetronomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	private void initialize(){
		mColorIndex=0;
		startUpdate();
	}

	private void startUpdate() {
		mGameRunning=true;
		Runnable runnable=new Runnable() {
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
		};
		new Thread(runnable).start();
	}
	
	public void explode(){
		Runnable runnable=new ExplodeRunnable();
		new Thread(runnable).start();
	}
	
	public void onDestroy(){
		mGameRunning=false;
	}
	
	private class ExplodeRunnable implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int m=mRandom.nextInt(MAX_OBJECT_NUMBER)+MAX_OBJECT_NUMBER/3;
			synchronized (this) {
				mColorIndex++;
			}
		}
	}
}
