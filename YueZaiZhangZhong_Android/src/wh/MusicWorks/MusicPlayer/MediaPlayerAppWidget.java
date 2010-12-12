package wh.MusicWorks.MusicPlayer;

import wh.MusicWorks.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.text.BoringLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * ×ÀÃæWidget
 * @author ÍõºÆ
 * @version 1.0 2010-11-3
 */
public class MediaPlayerAppWidget extends AppWidgetProvider{
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.app_widget_provider);
		
		setPendingIntentToTitleTextView(context, views);
		setPendingIntentToPlayButton(context, views);
		setPendingIntentToStopButton(context, views);
		
		updateWidget(context, views);
		
		super.onEnabled(context);
	}

	private void updateWidget(Context context, RemoteViews views) {
		AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
		ComponentName componentName=new ComponentName(context, MediaPlayerAppWidget.class);
		appWidgetManager.updateAppWidget(appWidgetManager.getAppWidgetIds(componentName), views);
	}

	private void setPendingIntentToStopButton(Context context, RemoteViews views) {
		Intent stopPlayingIntent=new Intent(MediaPlayerControlReceiver.STOP_BUTTON_PRESS);
		PendingIntent stopPlayingPendingIntent=PendingIntent.getBroadcast(context, 0, stopPlayingIntent, 0);
		views.setOnClickPendingIntent(R.id.stopbutton, stopPlayingPendingIntent);
	}

	private void setPendingIntentToPlayButton(Context context, RemoteViews views) {
		Intent startPlayingIntent=new Intent(MediaPlayerControlReceiver.PLAY_BUTTON_PRESS);
		PendingIntent startPlayingPendingIntent=PendingIntent.getBroadcast(context, 0, startPlayingIntent, 0);
		views.setOnClickPendingIntent(R.id.playbutton, startPlayingPendingIntent);
	}

	private void setPendingIntentToTitleTextView(Context context,
			RemoteViews views) {
		Intent startMusicListIntent=new Intent(context,MusicListActivity.class);
		PendingIntent startMusicListPendingIntent=PendingIntent.getActivity(context, 0, startMusicListIntent, 0);
		views.setOnClickPendingIntent(R.id.currentplayingfilename, startMusicListPendingIntent);
	}
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		MediaPlayerControlReceiver.destroy();
		MediaPlayerController.musicStop();
		super.onDisabled(context);
	}
}