/*
 * NotePosition类功能如下：
 * 1、存储节点及其出现的轨道位置
 * 2、将1秒的所有事件单元合并成一个轨道
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       吴卓豪                      创建
 */
package JavaMusician.MusicMan.Note;

import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class NoteTrackPosition {	
	/**
	 * 记录轨道位置
	 */
	private int position=0;
	/**
	 * 音轨序列
	 */
	private Sequence mSequence;
	/**
	 * 音轨
	 */
	private Track mTrack;
	/**
	 * 音轨显示在界面上的高度
	 */
	private int mHeight;
	/**
	 * 存储音色控制字
	 */
	private int[] toneInfo;
	/**
	 * 存储发音时间点
	 */
	private long tickBase;
	
	/**
	 * 音乐采样率相关信息为mDivisiontype,mResolution,mTempofactor,mTempoinbpm,mTempoinmpq
	 */
	private float[] musicParameters;

	private float DIVISIONTYPE;
	private float RESOLUTION;
	private float TEMPOFACTOR;
	private float TEMPOINBPM;
	private float TEMPOINMPQ;
	
	/**
	 * 将事件相同的所有节点合成一个轨道
	 * @param listInSameTime
	 */
	public NoteTrackPosition(ArrayList<Note> listInSameTime) {
		// TODO Auto-generated constructor stub
		initMusicParameters(listInSameTime.get(0));
		
		initToneArray(listInSameTime.get(0));
		
		//初始化序列的节拍信息
		try {
			mSequence=new Sequence(DIVISIONTYPE, (int)RESOLUTION);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mTrack=mSequence.createTrack();
		
		//创建设置音色的事件单元
		ShortMessage mShortMessageTone=new ShortMessage();
		
		try {
			mShortMessageTone.setMessage(toneInfo[0]&0x000000ff, toneInfo[1], 0);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MidiEvent mMidiEvent1=new MidiEvent(mShortMessageTone, 0);
		
		mTrack.add(mMidiEvent1);
		
		unionAllMessageInSameTime(listInSameTime);
		
		//分配音符的位置
		dispatchPosition();
	}

	/**
	 * 将所有相同时间点的信息合成一个序列
	 * @param listInSameTime 要合成的链表
	 */
	private void unionAllMessageInSameTime(ArrayList<Note> listInSameTime) {
		tickBase=listInSameTime.get(0).getTick();
		
		for(int i=0;i<listInSameTime.size();i++){
			int statetmp=listInSameTime.get(i).getState();
			
			long tick=listInSameTime.get(i).getTick();
			
			if(statetmp>=0xc0 && statetmp<=0xcf)
				continue;
			
			int notetmp=listInSameTime.get(i).getNote();
			
			int strongtmp=listInSameTime.get(i).getStrong();
			
			ShortMessage shortMessageSound=new ShortMessage();
			try {
				shortMessageSound.setMessage(statetmp, notetmp, strongtmp);
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			MidiEvent mMidiEvent2=new MidiEvent(shortMessageSound, tick-tickBase);

			mTrack.add(mMidiEvent2);
		}//for循环结束，即已经将所有的事件单元合并到了同一个音轨中
	}

	/**
	 * 设置音色数组的信息
	 * @param note 记录了音色的节点
	 */
	private void initToneArray(Note note) {
		toneInfo=new int[note.getToneInfo().length];
		toneInfo=note.getToneInfo();
	}
	/**
	 * 初始化音轨的参数
	 * @param listInSameTime 音轨参数
	 */
	private void initMusicParameters(Note note) {
		musicParameters=new float[note.getMusicParameters().length];
		musicParameters=note.getMusicParameters();
		setDivisitonType(musicParameters[0]);
		setResolution(musicParameters[1]);
		setTempofactor(musicParameters[2]);
		setTempoinbpm(musicParameters[3]);
		setTempoinmpq(musicParameters[4]);
	}
	/**
	 * 设置音轨的信息Resolution
	 * @param f 为要设置的Resolution
	 */
	private void setResolution(float f) {
		// TODO Auto-generated method stub
		RESOLUTION=f;
	}

	/**
	 * 设置音轨的信息DivisitonType
	 * @param f 为要设置的DivisitonType
	 */
	private void setDivisitonType(float f) {
		// TODO Auto-generated method stub
		DIVISIONTYPE=f;
	}

	/**
	 * 分派位置
	 */
	public void dispatchPosition()
	{
		position=(int)(Math.random()*5);
	}
	/**
	 * 获得位置
	 * @return 位置
	 */
	public int getPosition()
	{
		return position;
	}
	/**
	 * 获得时间点
	 * @return 时间点
	 */
	public long getTick()
	{
		return tickBase;
	}
	/**
	 * 获得音轨序列
	 * @return 音轨序列
	 */
	public Sequence getSequence()
	{
		return mSequence;
	}
	/**
	 * @return 音轨的信息tempofactor
	 */
	public float getTempofactor()
	{
		return TEMPOFACTOR;
	}
	/**
	 * @return 音轨的信息tempoinbpm
	 */
	public float getTempoinbpm()
	{
		return TEMPOINBPM;
	}
	/**
	 * @return 音轨的信息tempoinmpq
	 */
	public float getTempoinmpq()
	{
		return TEMPOINMPQ;
	}
	/**
	 * 设置音轨的信息tempofactor
	 * @param temp 为要设置的tempofactor
	 */
	public void setTempofactor(float temp)
	{
		TEMPOFACTOR=temp;
	}
	/**
	 * 设置音轨的信息tempoinbpm
	 * @param temp 为要设置的tempoinbpm
	 */
	public void setTempoinbpm(float temp)
	{
		TEMPOINBPM=temp;
	}
	/**
	 * 设置音轨的信息tempoinmpq
	 * @param temp 为要设置的tempoinmpq
	 */
	public void setTempoinmpq(float temp)
	{
		TEMPOINMPQ=temp;
	}
	/**
	 * 获取音符的高度
	 * @return 音符的高度
	 */
	public int getHeight()
	{
		return mHeight;
	}
	/**
	 * 设置音符的高度
	 * @param h 音符的高度
	 */
	public void setHeight(int h)
	{
		mHeight=h;
	}
}
