package wh.MusicWorks.Instrument.Guitar;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class ShakingString{
	public static final float ACCELERATION=300;
	public static final int SHAKE_TIMES=10;
	public static final float SHAKE_MARGIN=0.8f;
	public static int SCREEN_HEIGHT=854;
	protected static Random mRandom=new Random();
	private float mBeginningX;
	private int mTimes;
	private int mDifficulty;
	private Paint mPaint;
		
	public float getCurrentX(){
		switch (mTimes%4) {
		case 0:
			return mBeginningX;
		case 1:
			return mBeginningX-SHAKE_MARGIN;
		case 2:
			return mBeginningX;
		case 3:
			return mBeginningX+SHAKE_MARGIN;
		default:
			return mBeginningX;
		}
	}
	
	public ShakingString(float beginningX,int screenHeight){
		mBeginningX=beginningX;
		SCREEN_HEIGHT=screenHeight;
		mTimes=0;
		mPaint=new Paint();
		mPaint.setColor(Color.WHITE);
		mDifficulty=0;
	}
	
	public int getDifficulty(){
		return mDifficulty;
	}
	
	public void onDraw(Canvas canvas){

		canvas.drawLine(getCurrentX(), 0, getCurrentX(), SCREEN_HEIGHT, mPaint);
		
		if (mTimes!=0)
			mTimes--;
	}
	
	public void startShake(){
		mTimes=SHAKE_TIMES;
	}
}