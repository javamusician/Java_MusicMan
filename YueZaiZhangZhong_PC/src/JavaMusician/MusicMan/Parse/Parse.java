/*
 * Parse类功能如下：
 * 1、载入指定路径的MIDI文件
 * 2、随机抽取音频轨道
 * 3、将抽取出来的音频轨道载入链表结构中，以供Controller使用进行位置的分配
 * 4、播放音乐
 * 5、停止音乐
 * 
 * Version          Date            Author       Modified
 * 1.0             2010/11/25       吴卓豪                     创建
 * 1.0.1           2010/11/25       吴卓豪                     修改链表存储结构
 */
package JavaMusician.MusicMan.Parse;

import java.io.File;
import java.util.ArrayList;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import JavaMusician.MusicMan.Note.Note;

/**
 * 
 * @author 吴卓豪
 * ParseMIDI类对指定路径的MIDI文件进行解析，
 * 抽离其中指定的音轨，并能够播放由剩余音轨组成的歌曲。
 * 抽离的音轨存储在链表结构listsum中，
 * 该链表的内容也为链表（用于存储每个被删除音轨的事件信息）
 *
 */
public class Parse implements Runnable,IParse
{
	/**
	 * 存储指定音乐文件的音乐序列
	 */
    private static Sequence seq;
    /**
     * 存储指定音乐文件的音乐序列器
     */
    private static Sequencer midi;
    /**
     * 存储指定音乐文件的所有音轨
     */
    private static Track[] tracks;
    /**
     * 存储音轨的所有音乐参数 
     */
    private float[] musicParameters;
    
    /**
     * 存储采样率mDivisiontype
     */
    static float mDivisiontype;
    /**
     * 存储音轨的播放参数resolution
     */
    static int mResolution;
    /**
     * 存储音轨的播放参数tempofactor
     */
    static float mTempofactor;
    /**
     * 存储音轨的播放参数tempoinbpm
     */
    static float mTempoinbpm;
    /**
     * 存储音轨的播放参数tempoinmpq
     */
    static float mTempoinmpq; 
    
    /**
     * 
     * @param filename为指定MIDI文件的路径
     * 初始化相关的信息
     */
    public Parse(File sound)
    {
    	try
    	{
    		seq=MidiSystem.getSequence(sound);
    		midi=MidiSystem.getSequencer();
    		midi.open();
    		
    		//获取音频文件的各种参数
    		mDivisiontype=seq.getDivisionType();
    	    mResolution=seq.getResolution();
    	    mTempofactor=midi.getTempoFactor();
    	    mTempoinbpm=midi.getTempoInBPM();
    	    mTempoinmpq=midi.getTempoInMPQ();
    	    musicParameters=new float[]{mDivisiontype,mResolution,mTempofactor,mTempoinbpm,mTempoinmpq};
    	    
    		tracks=seq.getTracks();
    		
    		int[] a=new int[]{6};
    		
    		getPlayTracksByTick(a);
    		
    		for(int i=0;i<a.length;i++)
    			seq.deleteTrack(tracks[a[i]]);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /**
     * 存储所有乐器的相关信息点，每种乐器以一秒为间隔进行事件单元的分割
     */
    private ArrayList<ArrayList<ArrayList<Note>>>  listAllInstrument=new ArrayList<ArrayList<ArrayList<Note>>>();
    /**
     * getPlayTracksByTick用于存储将要被删除的音轨组信息
     * @param num为指定删除的音轨号的数组
     */
    public void getPlayTracksByTick(int[] num){
    	int[] tone=new int[2];
    	
    	byte[] message;
    	
    	long Tick=1000000,tick=0;
    	
    	for(int j=0;j<num.length;j++){
		    ArrayList<ArrayList<Note>> listOneInstrument=new ArrayList<ArrayList<Note>>();
		    ArrayList<Note> listMessageInOneSecond=new ArrayList<Note>();
    		
    		for (int i=0;i<tracks[num[j]].size();i++){
    			
    			message=tracks[num[j]].get(i).getMessage().getMessage();
    			
    			tick=tracks[num[j]].get(i).getTick()*0xc0;
    			
    			if(message[0]>=0xffffffc0 && message[0]<=0xffffffcf)
    			{    				
    				tone[0]=message[0] & 0xff;
    				tone[1]=message[1] & 0xff;
    				
    				continue;
    			}
    			
    			if(message[0]>=0xffffff90 && message[0]<=0xffffff9f)
    			{        			
    				Note note=new Note(tick,message,musicParameters,tone);
        			
        			if(tick<=Tick)
        			{        				
        				listMessageInOneSecond.add(note);
        			}
        			else
        			{
        				Tick+=1000000;
        				
        				if(listMessageInOneSecond.size()!=0)
        				{
        					listOneInstrument.add(listMessageInOneSecond);
        				}
        				
        				listMessageInOneSecond=new ArrayList<Note>();
        				
        				listMessageInOneSecond.add(note);
        			}
    			}
    		}//内层for，对一条轨道分析完毕
    		
    		if(listOneInstrument.size()!=0)
    		{
    			listAllInstrument.add(listOneInstrument);
    			
    			Tick=1000000;
    		}
    		
    	}//外层for，对所有轨道分析完毕
    }
    
    /**
     * Play用于播放剩余音轨
     */
    public static void Play()
    {
    	 try{
             midi.setSequence(seq);
             
             if(!midi.isRunning())
             	midi.start();
             
         } 
    	 catch (Exception ex) {
         }
    }
    
    /**
     * Stop停止播放
     */
    public static void Stop()
    {
    	if(midi.isRunning())
			midi.stop();
    	
    	if(midi.isOpen())
    		midi.close();
    }
    /**
     * 用于标识音乐是否已经开始播放
     */
    private boolean isStart=false;
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Play();
		isStart=true;
		long time = midi.getMicrosecondLength()/1000;
		try 
    	{
    		Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stop();
		isStart=false;
	}
	/**
	 * @return isStart 标识音乐是否已经开始播放
	 */
	@Override
	public boolean getStart() {
		// TODO Auto-generated method stub
		return isStart;
	}
	/**
	 * @return 获取所有乐器的音符链表
	 */
	@Override
	public ArrayList<ArrayList<ArrayList<Note>>> getAllInstrumentList() {
		// TODO Auto-generated method stub
		return listAllInstrument;
	}
	/**
	 * @return 获取音轨的节拍率
	 */
	@Override
	public int getResolution() {
		// TODO Auto-generated method stub
		return mResolution;
	}
} 

