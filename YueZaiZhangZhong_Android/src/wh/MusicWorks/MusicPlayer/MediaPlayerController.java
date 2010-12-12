package wh.MusicWorks.MusicPlayer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import wh.MusicWorks.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * 音频播放器控制类
 * @author 王浩
 * @version 1.0 2010-11-20
 */
public class MediaPlayerController{
	private static Context mContext;
	private static MediaPlayer mMediaPlayer=null;
	private static int[] mResourceMusicId={R.raw.game2};
	private static String[] mResourceMusicName={"ResourceMusic1"};
	private static ArrayList<File> mSDCardMusicFile=null;
	private static String[] mSDCardMusicName;
	private static String mCurrentPlayingMusicName=null;
	private static int mCurrentPlayingIndex=0;
	private static boolean mFromResource=true,mLooping=true;
	private static CompletionCallback mCompletionCallback=null;
	
	public static void musicStart(Context context,boolean fromResource,int index){
		if (mMediaPlayer==null)
		{
			mContext=context;
			mCurrentPlayingIndex=index;
			mFromResource=fromResource;
			
			initializeMediaPlayer(context, fromResource);
			mMediaPlayer.start();
		}
		else if (!mMediaPlayer.isPlaying()){
			mMediaPlayer.start();
		}
	}

	private static void initializeMediaPlayer(Context context,
			boolean fromResource) {

		if (fromResource)
			loadMediaFromResource(context);
		else
			loadMediaFromSDCard(context);
		
		mMediaPlayer.setLooping(mLooping);
				
		try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setCompletionListener();
	}


	private static void loadMediaFromSDCard(Context context) {
		if (mCurrentPlayingIndex==mSDCardMusicFile.size())
			mCurrentPlayingIndex=0;
		mMediaPlayer=MediaPlayer.create(context, Uri.fromFile(mSDCardMusicFile.get(mCurrentPlayingIndex).getAbsoluteFile()));
		mCurrentPlayingMusicName=mSDCardMusicName[mCurrentPlayingIndex];
	}

	private static void loadMediaFromResource(Context context) {
		if (mCurrentPlayingIndex==mResourceMusicId.length)
			mCurrentPlayingIndex=0;
		mMediaPlayer=MediaPlayer.create(context, mResourceMusicId[mCurrentPlayingIndex]);
		mCurrentPlayingMusicName=mResourceMusicName[mCurrentPlayingIndex];
	}
	
	public static void musicPause(){
		if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}
	
	public static void musicStop(){
		if (mMediaPlayer!=null)
		{
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer=null;
		}
	}
	
	public static boolean musicIsPlaying(){
		return mMediaPlayer!=null && mMediaPlayer.isPlaying();
	}
	
	public static int getMusicDunation(){
		try
		{
			if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
				return mMediaPlayer.getDuration();
			else
				return 0;
		}catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	public static int getMusicCurrentPosition(){
		try
		{
			if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
				return mMediaPlayer.getCurrentPosition();
			else
				return 0;
		}catch (Exception e) {
			return 0;
		}
	}
	
	public static void musicNext(){
		musicStop();
		if (mContext!=null)
			musicStart(mContext, mFromResource, getCurrentPlayingIndex()+1);
	}
	
	public static void setLooping(boolean looping){
		mLooping=looping;
		if (mMediaPlayer!=null)
			mMediaPlayer.setLooping(mLooping);
	}
	
	public static int getCurrentPlayingIndex(){
		return mCurrentPlayingIndex;
	}
	
	public static int getMaxPlayingIndex(){
		return mFromResource?mResourceMusicId.length:mSDCardMusicFile.size();
	}
	
	public static boolean isFromResource(){
		return mFromResource;
	}
	
	public static void initialize(Context context,String path,String SortKey,boolean isDESC){
		mSDCardMusicFile=getMusicFileList(context,path,SortKey,isDESC);
		
		if (mSDCardMusicFile!=null)
		{
			mSDCardMusicName=new String[mSDCardMusicFile.size()];
			for (int i=0;i<mSDCardMusicName.length;i++)
				mSDCardMusicName[i]=mSDCardMusicFile.get(i).getName();
		}
		else
			mSDCardMusicName=new String[0];
		
		setLooping(false);
	}
	
    public static String[] getResourceMusicName(){
    	return mResourceMusicName;
    }
    
    public static String[] getSDCardMusicName(){
    	return mSDCardMusicName;
    }
    
    public static String getMusicName(){
    	return mCurrentPlayingMusicName;
    }
    
    private static void setCompletionListener(){
    	if (mMediaPlayer!=null) {
			OnCompletionListener onCompletionListener=new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					if (!mLooping)
					{
						Intent intent=new Intent(MediaPlayerControlReceiver.NEXT_BUTTON_PRESS);
						mContext.sendBroadcast(intent);
					}
					if (mCompletionCallback!=null)
						mCompletionCallback.onCallback();
				}
			};
			mMediaPlayer.setOnCompletionListener(onCompletionListener);
		}
    }
    
    public static void setCompletionCallback(CompletionCallback completionCallback){
    	mCompletionCallback=completionCallback;
    }

    public static ArrayList<File> getMusicFileList(Context context,String path,String SortKey,boolean isDESC)
    {
	    Cursor cursor=loadCursorByPathAndSortKey(context, path, SortKey, isDESC);
	    
	    ArrayList<File> files=new ArrayList<File>();
	    cursor.moveToFirst();
    	while (!cursor.isAfterLast()) {
             files.add(new File( cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))));
             cursor.moveToNext();
	    }
        return files;
    }

	private static Cursor loadCursorByPathAndSortKey(Context context,
			String path, String SortKey, boolean isDESC) {
		Cursor cursor;
	    if(path==null||path.equals(""))
	    	path = "/sdcard/";
	    if (SortKey==null||SortKey.equals("")) {
	    	SortKey = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
	    }
	    if (isDESC) {
	    	cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,  
	    			MediaStore.Audio.Media.DATA+" LIKE '"+path+"%'" , null, SortKey+" DESC"); 
	    }else {
	    	cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,  
	    			MediaStore.Audio.Media.DATA+" LIKE '"+path+"%'" , null,  SortKey); 
	    }
		return cursor;
	}
    
    public static interface CompletionCallback{
    	public void onCallback();
    }
}