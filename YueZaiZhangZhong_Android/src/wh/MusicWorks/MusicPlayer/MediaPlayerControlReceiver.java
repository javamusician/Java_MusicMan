package wh.MusicWorks.MusicPlayer;

import wh.MusicWorks.R;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * 音频控制广播接收器
 * @author 王浩
 * @version 1.1 2010-11-20
 */
public class MediaPlayerControlReceiver extends BroadcastReceiver{
	public static final String PLAY_BUTTON_PRESS="PLAY_BUTTON_PRESS";
	public static final String STOP_BUTTON_PRESS="STOP_BUTTON_PRESS";
	public static final String NEXT_BUTTON_PRESS="NEXT_BUTTON_PRESS";
	public static boolean mRunning;
	public static RemoteViews mRemoteViews=null;
	public static AppWidgetManager mAppWidgetManager=null;
	public static ComponentName mComponentName=null;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		initializeReceiver(context);

		if (intent.getAction().equals(PLAY_BUTTON_PRESS))
			onPlayButtonPressed(context, intent);
		else if (intent.getAction().equals(STOP_BUTTON_PRESS))
			onStopButtonPressed();
		else if (intent.getAction().equals(NEXT_BUTTON_PRESS))
			onNextButtonPressed();
		
		mAppWidgetManager.updateAppWidget(mAppWidgetManager.getAppWidgetIds(mComponentName), mRemoteViews);
	}

	private void onNextButtonPressed() {
		mRemoteViews.setTextViewText(R.id.currentplayingfilename, MediaPlayerController.getMusicName());
		MediaPlayerController.musicNext();
	}

	private void onStopButtonPressed() {
		mRemoteViews.setTextViewText(R.id.currentplayingfilename, "no music is playing");
		mRemoteViews.setImageViewResource(R.id.playbutton, R.drawable.ic_appwidget_music_play);
		
		mRunning=false;
		MediaPlayerController.musicStop();
	}

	private void onPlayButtonPressed(Context context, Intent intent) {
		if (MediaPlayerController.musicIsPlaying())
			musicPause();
		else
			musicPlay(context, intent);
	}

	private void musicPause() {
		mRemoteViews.setImageViewResource(R.id.playbutton, R.drawable.ic_appwidget_music_play);
		mRunning=false;
		MediaPlayerController.musicPause();
	}

	private void musicPlay(Context context, Intent intent) {
		mRunning=true;
		startPlayingMusic(context, intent);	
		updateWidgetUIToPlayState();
		startUpdateProgressbar();
	}

	private void startPlayingMusic(Context context, Intent intent) {
		boolean fromResource=intent.getBooleanExtra("FromResource", true);
		int index=intent.getIntExtra("Index", 0);
		MediaPlayerController.musicStart(context,fromResource,index);
	}

	private void updateWidgetUIToPlayState() {
		mRemoteViews.setImageViewResource(R.id.playbutton, R.drawable.ic_appwidget_music_pause);
		mRemoteViews.setTextViewText(R.id.currentplayingfilename, MediaPlayerController.getMusicName());
	}

	private void startUpdateProgressbar() {
		Runnable runnable = new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				while (mRunning)
				{
					updateProgressBar();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(runnable).start();
	}
	
	private void updateProgressBar() {
		mRemoteViews.setProgressBar(R.id.playingprogress,
				MediaPlayerController.getMusicDunation(), 
				MediaPlayerController.getMusicCurrentPosition(),
				false);
		mAppWidgetManager.updateAppWidget(mAppWidgetManager.getAppWidgetIds(mComponentName), mRemoteViews);
	}
	
	private void initializeReceiver(Context context) {
		if (mRemoteViews==null)
			mRemoteViews=new RemoteViews(context.getPackageName(),R.layout.app_widget_provider);
		if (mAppWidgetManager==null)
			mAppWidgetManager=AppWidgetManager.getInstance(context);
		if (mComponentName==null)
			mComponentName=new ComponentName(context, MediaPlayerAppWidget.class);
	}
	
	public static void destroy(){
		mRunning=false;
		mAppWidgetManager=null;
		mComponentName=null;
	}
}