package wh.MusicWorks.Instrument.Guitar;

import java.util.ArrayList;
import java.util.List;
import wh.MusicWorks.Replay.ReplayData.ReplayDataGuitar;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class GuitarView extends View{
	public static final int MAX_POINT_NUMBER=5000;
	public static final long TIME_INTERVAL=150;
	public static int SCREEN_WIDTH=480;
	public static int SCREEN_HEIGHT=854;
	private boolean mGameRunning;
	private List<ShakingString> mShakingStringList;
	
	public GuitarView(Context context) {
		super(context);
		initialize();
	}
	
	public GuitarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
	
	private void initialize(){
		mShakingStringList=new ArrayList<ShakingString>();
		for (int i=0;i<ReplayDataGuitar.GUITAR_MUSICAL_SCALE_MAX;i++)
		{
			float beginningX=(float)(i+0.5f)*(SCREEN_WIDTH+1)/ReplayDataGuitar.GUITAR_MUSICAL_SCALE_MAX;
			ShakingString shakingString=new ShakingString(beginningX, SCREEN_HEIGHT);
			mShakingStringList.add(shakingString);
		}
		
		startUpdate();
	}

	private void startUpdate() {
		mGameRunning=true;
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
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
	
	@Override
	protected void onDraw(Canvas canvas) {
			
		drawShakingString(canvas);
		
		super.onDraw(canvas);
	}

	private void drawShakingString(Canvas canvas) {
		for (int i=0;i<mShakingStringList.size();i++)
			mShakingStringList.get(i).onDraw(canvas);
	}
	
	public void setStringShake(int index){
		mShakingStringList.get(index).startShake();
	}
	
	public void onDestroy(){
		mGameRunning=false;
	}
}