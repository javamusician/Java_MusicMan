package wh.MusicWorks.MusicPlayer;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import wh.MusicWorks.MusicPlayer.MediaPlayerController.CompletionCallback;
import wh.MusicWorks.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

/**
 * 音频列表
 * @author 王浩
 * @version 1.1 2010-11-20
 */
public class MusicListActivity extends Activity {
	boolean mFromResource;
	int mIndexResource,mIndexSDCard;
	private List<String> mItemList=null;
	private List<String> mPathList=null;
	private String mRootPath="/sdcard";
	private String mCurrentPath="";
	private ListView mMusicListView;
	private MediaPlayerView mMediaPlayerView;
	private ArrayAdapter<String> mResourceMusicAdapter;
	private ArrayAdapter<String> mSdCardMusicAdapter;
	private boolean mRunning;
	private Spinner mMusicSpinner;
	private View mPlayButton;
	private RadioGroup mRadioGroup;
	private SlidingDrawer mSlidingDrawer;
	private ImageView mHandlerImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setWindowFullScreen();
        setContentView(R.layout.layoutformediaplayer);
        
        doInitialization();
    }

	private void doInitialization() {
		SharedPreferences setting=loadAndInitialzePreferences();
        
		initializeMediaPlayerController(setting);
		initializeUI(setting);
		
        startUpdate();
	}

	private void startUpdate() {
		mRunning=true;
		Runnable runnable=new Runnable() {
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
		ProgressBar progressBar=(ProgressBar)findViewById(R.id.seekbar);
		progressBar.setMax(MediaPlayerController.getMusicDunation());
		progressBar.setProgress(MediaPlayerController.getMusicCurrentPosition());
	}
	
	private void initializeUI(SharedPreferences setting) {
		initializeMusicSpinner();
		initializePlayButton();
        initializeRedioGroup();
        initializeMusicListView();
        initializeFileAndDirectory(setting.getString("currentPath", mRootPath));
        initializeSlidingDrawer();  
        initializeMediaPlayerView();
	}

	private void initializeMediaPlayerView() {
		mMediaPlayerView=(MediaPlayerView)findViewById(R.id.mediaplayerview);
	}

	private void initializeSlidingDrawer() {
		mSlidingDrawer=(SlidingDrawer)findViewById(R.id.slidingdrawer);
		mHandlerImageView=(ImageView)findViewById(R.id.handler);

		OnDrawerOpenListener onDrawerOpenListener=new OnDrawerOpenListener() {
			public void onDrawerOpened() {
				mHandlerImageView.setImageResource(R.drawable.menupanelbackfocus);
			}
		};
		mSlidingDrawer.setOnDrawerOpenListener(onDrawerOpenListener);
		
        OnDrawerCloseListener onDrawerCloseListener=new OnDrawerCloseListener() {
			public void onDrawerClosed() {
				// TODO Auto-generated method stub
				saveCurrentPath();
		        MediaPlayerController.initialize(MusicListActivity.this,mCurrentPath,MediaStore.Audio.Media.TITLE,false);
		        updateUI();
			}
		};
		mSlidingDrawer.setOnDrawerCloseListener(onDrawerCloseListener);
	}
	
	private void updateUI() {
		mSdCardMusicAdapter=new ArrayAdapter<String>(MusicListActivity.this, android.R.layout.simple_spinner_item, MediaPlayerController.getSDCardMusicName());
        
		RadioButton fromSdcardRadioButton=(RadioButton)findViewById(R.id.fromsdcard);
		if (fromSdcardRadioButton.isChecked())
        	mMusicSpinner.setAdapter(mSdCardMusicAdapter);
		
        mHandlerImageView.setImageResource(R.drawable.functionmenu);
	}
	
	private void saveCurrentPath() {
		SharedPreferences setting=getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor=setting.edit();
		editor.putString("currentPath", mCurrentPath);
		editor.commit();
	}
	
	private void initializeMusicListView() {
		mMusicListView=(ListView)findViewById(R.id.content);
        mMusicListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				onListItemClick((ListView)arg0, arg1, arg2, arg3);
			}
		});
	}

	private void initializeRedioGroup() {
		mRadioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.fromresource:
			        mMusicSpinner.setAdapter(mResourceMusicAdapter);
			        mFromResource=true;
					break;
				case R.id.fromsdcard:
			        mMusicSpinner.setAdapter(mSdCardMusicAdapter);
			        mFromResource=false;
					break;
				default:
					break;
				}
			}
		});
	}

	private void initializeMusicSpinner() {
		mResourceMusicAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MediaPlayerController.getResourceMusicName());
        mSdCardMusicAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MediaPlayerController.getSDCardMusicName());
        
        mMusicSpinner=(Spinner)findViewById(R.id.musicspinner);
        mMusicSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (mFromResource)
					mIndexResource=arg2;
				else
					mIndexSDCard=arg2;
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
        
        mMusicSpinner.setAdapter(mResourceMusicAdapter);
	}

	private void initializePlayButton() {
		mPlayButton=findViewById(R.id.start);
		if (!MediaPlayerController.musicIsPlaying())
			mPlayButton.setBackgroundResource(R.drawable.toplay);
		else
		{
			mPlayButton.setBackgroundResource(R.drawable.topause);
			mMusicSpinner.setEnabled(false);
		}
	}

	private SharedPreferences loadAndInitialzePreferences() {
		mFromResource=true;
        mIndexResource=0;
        mIndexSDCard=0;
		SharedPreferences setting=getPreferences(MODE_PRIVATE);
		return setting;
	}

	private void initializeMediaPlayerController(SharedPreferences setting) {
		MediaPlayerController.initialize(this,setting.getString("currentPath", mRootPath),MediaStore.Audio.Media.TITLE,false);
        MediaPlayerController.setCompletionCallback(new CompletionCallback() {
			@Override
			public void onCallback() {
				// TODO Auto-generated method stub
		    	if (currentPlayingAndShowingResourceAreTheSame()) {
					int position;
					if (MediaPlayerController.getCurrentPlayingIndex()+1>=mMusicSpinner.getCount())
						position=0;
					else
						position=MediaPlayerController.getCurrentPlayingIndex()+1;
					mMusicSpinner.setSelection(position);
				}
			}
		});
	}
	
	private boolean currentPlayingAndShowingResourceAreTheSame() {
		RadioButton fromResourceButton=(RadioButton)findViewById(R.id.fromresource);
		return (fromResourceButton.isChecked() && MediaPlayerController.isFromResource()) ||
    			(!fromResourceButton.isChecked() && !MediaPlayerController.isFromResource());
	}
	
	private void setWindowFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

    public void onClickStart(View v){
    	if (mMusicSpinner.getChildCount()!=0)
    	{
			if (!MediaPlayerController.musicIsPlaying())
			{
	    		tellControllerToPlayMusic();
	    		updateUIToPlayState(v);
			}
			else
			{
	    		tellControllerToPauseMusic();
	    		updateUIToPauseState(v);
			}
    	}
    }

	private void updateUIToPauseState(View v) {
		v.setBackgroundResource(R.drawable.playbutton);
		mMusicSpinner.setEnabled(false);
		findViewById(R.id.content).setEnabled(false);
	}

	private void updateUIToPlayState(View v) {
		v.setBackgroundResource(R.drawable.pausebutton);
		mMusicSpinner.setEnabled(false);
		findViewById(R.id.content).setEnabled(false);
	}

	private void tellControllerToPauseMusic() {
		Intent intent=new Intent(MediaPlayerControlReceiver.PLAY_BUTTON_PRESS);
		sendBroadcast(intent);
	}

	private void tellControllerToPlayMusic() {
		Intent intent=new Intent(MediaPlayerControlReceiver.PLAY_BUTTON_PRESS);
		intent.putExtra("FromResource", mFromResource);
		if (mFromResource)
			intent.putExtra("Index", mIndexResource);
		else
			intent.putExtra("Index", mIndexSDCard);
		sendBroadcast(intent);
	}
    
    public void onClickStop(View v){
		tellControllerToStopMusic();
		updateUIToStopState();
    }

	private void updateUIToStopState() {
		((Button)findViewById(R.id.start)).setBackgroundResource(R.drawable.toplay);
		mMusicSpinner.setEnabled(true);
		findViewById(R.id.content).setEnabled(true);
	}

	private void tellControllerToStopMusic() {
		Intent intent=new Intent(MediaPlayerControlReceiver.STOP_BUTTON_PRESS);
		sendBroadcast(intent);
	}

    public void onClickNext(View v){
    	if (currentPlayingAndShowingResourceAreTheSame())
    	{
			int position;
			if (MediaPlayerController.getCurrentPlayingIndex()+1>=mMusicSpinner.getCount())
				position=0;
			else
				position=MediaPlayerController.getCurrentPlayingIndex()+1;
			mMusicSpinner.setSelection(position);
    	}
		Intent intent=new Intent(MediaPlayerControlReceiver.NEXT_BUTTON_PRESS);
		sendBroadcast(intent);
    }
    
	private void initializeFileAndDirectory(String filePath)
	{
		mCurrentPath=filePath;
		
		mItemList=new ArrayList<String>();
		mPathList=new ArrayList<String>();
		
		File f=new File(filePath);  
		File[] files=f.listFiles();

		if(!filePath.equals(mRootPath))
			addRootAndBackIconToList(f);
		
		addFileAndDirectoryUnderCurrentPathToList(files);
		
		mMusicListView.setAdapter(new FileListAdapter(this,mItemList,mPathList));
	}

	private void addFileAndDirectoryUnderCurrentPathToList(File[] files) {
		for(int i=0;i<files.length;i++)
		{
			File file=files[i];
			if (file.isDirectory())
			{
				mItemList.add(file.getName());
				mPathList.add(file.getPath());
			}
		}
	}

	private void addRootAndBackIconToList(File f) {
		mItemList.add("b1");
		mPathList.add(mRootPath);
		mItemList.add("b2");
		mPathList.add(f.getParent());
	}
    
	protected void onListItemClick(ListView l,View v,int position,long id)
	{
		File file=new File(mPathList.get(position));
		if(file.canRead())
		{
			if (file.isDirectory())
				initializeFileAndDirectory(mPathList.get(position));
			else
				openFile(file);
		}
		else
			showPermissionLimitedDialog();
	}

	private void showPermissionLimitedDialog() {
		new AlertDialog.Builder(this)
					.setTitle("Message")
					.setMessage("权限不足!")
					.setPositiveButton("OK",null)
					.show();
	}
    
	private void openFile(File f) 
	{
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		  
		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f),type);
		startActivity(intent); 
	}

	private String getMIMEType(File f)
	{
		String type="";
		String fName=f.getName();
		String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase(); 
	  
		if(isAudioFile(end))
			type = "audio"; 
		else if(isVideoFile(end))
			type = "video";
		else if(isImageFile(end))
			type = "image";
		else
			type="*";
		type += "/*"; 
		return type; 
	}

	private boolean isImageFile(String end) {
		return end.equals("jpg")||end.equals("gif")||end.equals("png")||
				end.equals("jpeg")||end.equals("bmp");
	}

	private boolean isVideoFile(String end) {
		return end.equals("3gp")||end.equals("mp4");
	}

	private boolean isAudioFile(String end) {
		return end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
				end.equals("xmf")||end.equals("ogg")||end.equals("wav");
	}
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	mRunning=false;
    	mMediaPlayerView.stopRunning();
    	MediaPlayerController.setCompletionCallback(null);
    	super.onDestroy();
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK && mSlidingDrawer.isOpened())
		{
			mSlidingDrawer.close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}