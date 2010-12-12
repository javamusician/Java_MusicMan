/*
 * NotePosition�๦�����£�
 * 1���洢�ڵ㼰����ֵĹ��λ��
 * 2����1��������¼���Ԫ�ϲ���һ�����
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       ��׿��                      ����
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
	 * ��¼���λ��
	 */
	private int position=0;
	/**
	 * ��������
	 */
	private Sequence mSequence;
	/**
	 * ����
	 */
	private Track mTrack;
	/**
	 * ������ʾ�ڽ����ϵĸ߶�
	 */
	private int mHeight;
	/**
	 * �洢��ɫ������
	 */
	private int[] toneInfo;
	/**
	 * �洢����ʱ���
	 */
	private long tickBase;
	
	/**
	 * ���ֲ����������ϢΪmDivisiontype,mResolution,mTempofactor,mTempoinbpm,mTempoinmpq
	 */
	private float[] musicParameters;

	private float DIVISIONTYPE;
	private float RESOLUTION;
	private float TEMPOFACTOR;
	private float TEMPOINBPM;
	private float TEMPOINMPQ;
	
	/**
	 * ���¼���ͬ�����нڵ�ϳ�һ�����
	 * @param listInSameTime
	 */
	public NoteTrackPosition(ArrayList<Note> listInSameTime) {
		// TODO Auto-generated constructor stub
		initMusicParameters(listInSameTime.get(0));
		
		initToneArray(listInSameTime.get(0));
		
		//��ʼ�����еĽ�����Ϣ
		try {
			mSequence=new Sequence(DIVISIONTYPE, (int)RESOLUTION);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mTrack=mSequence.createTrack();
		
		//����������ɫ���¼���Ԫ
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
		
		//����������λ��
		dispatchPosition();
	}

	/**
	 * ��������ͬʱ������Ϣ�ϳ�һ������
	 * @param listInSameTime Ҫ�ϳɵ�����
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
		}//forѭ�����������Ѿ������е��¼���Ԫ�ϲ�����ͬһ��������
	}

	/**
	 * ������ɫ�������Ϣ
	 * @param note ��¼����ɫ�Ľڵ�
	 */
	private void initToneArray(Note note) {
		toneInfo=new int[note.getToneInfo().length];
		toneInfo=note.getToneInfo();
	}
	/**
	 * ��ʼ������Ĳ���
	 * @param listInSameTime �������
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
	 * �����������ϢResolution
	 * @param f ΪҪ���õ�Resolution
	 */
	private void setResolution(float f) {
		// TODO Auto-generated method stub
		RESOLUTION=f;
	}

	/**
	 * �����������ϢDivisitonType
	 * @param f ΪҪ���õ�DivisitonType
	 */
	private void setDivisitonType(float f) {
		// TODO Auto-generated method stub
		DIVISIONTYPE=f;
	}

	/**
	 * ����λ��
	 */
	public void dispatchPosition()
	{
		position=(int)(Math.random()*5);
	}
	/**
	 * ���λ��
	 * @return λ��
	 */
	public int getPosition()
	{
		return position;
	}
	/**
	 * ���ʱ���
	 * @return ʱ���
	 */
	public long getTick()
	{
		return tickBase;
	}
	/**
	 * �����������
	 * @return ��������
	 */
	public Sequence getSequence()
	{
		return mSequence;
	}
	/**
	 * @return �������Ϣtempofactor
	 */
	public float getTempofactor()
	{
		return TEMPOFACTOR;
	}
	/**
	 * @return �������Ϣtempoinbpm
	 */
	public float getTempoinbpm()
	{
		return TEMPOINBPM;
	}
	/**
	 * @return �������Ϣtempoinmpq
	 */
	public float getTempoinmpq()
	{
		return TEMPOINMPQ;
	}
	/**
	 * �����������Ϣtempofactor
	 * @param temp ΪҪ���õ�tempofactor
	 */
	public void setTempofactor(float temp)
	{
		TEMPOFACTOR=temp;
	}
	/**
	 * �����������Ϣtempoinbpm
	 * @param temp ΪҪ���õ�tempoinbpm
	 */
	public void setTempoinbpm(float temp)
	{
		TEMPOINBPM=temp;
	}
	/**
	 * �����������Ϣtempoinmpq
	 * @param temp ΪҪ���õ�tempoinmpq
	 */
	public void setTempoinmpq(float temp)
	{
		TEMPOINMPQ=temp;
	}
	/**
	 * ��ȡ�����ĸ߶�
	 * @return �����ĸ߶�
	 */
	public int getHeight()
	{
		return mHeight;
	}
	/**
	 * ���������ĸ߶�
	 * @param h �����ĸ߶�
	 */
	public void setHeight(int h)
	{
		mHeight=h;
	}
}
