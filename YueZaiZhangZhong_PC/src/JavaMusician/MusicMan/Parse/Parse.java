/*
 * Parse�๦�����£�
 * 1������ָ��·����MIDI�ļ�
 * 2�������ȡ��Ƶ���
 * 3������ȡ��������Ƶ�����������ṹ�У��Թ�Controllerʹ�ý���λ�õķ���
 * 4����������
 * 5��ֹͣ����
 * 
 * Version          Date            Author       Modified
 * 1.0             2010/11/25       ��׿��                     ����
 * 1.0.1           2010/11/25       ��׿��                     �޸�����洢�ṹ
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
 * @author ��׿��
 * ParseMIDI���ָ��·����MIDI�ļ����н�����
 * ��������ָ�������죬���ܹ�������ʣ��������ɵĸ�����
 * ���������洢������ṹlistsum�У�
 * �����������ҲΪ�������ڴ洢ÿ����ɾ��������¼���Ϣ��
 *
 */
public class Parse implements Runnable,IParse
{
	/**
	 * �洢ָ�������ļ�����������
	 */
    private static Sequence seq;
    /**
     * �洢ָ�������ļ�������������
     */
    private static Sequencer midi;
    /**
     * �洢ָ�������ļ�����������
     */
    private static Track[] tracks;
    /**
     * �洢������������ֲ��� 
     */
    private float[] musicParameters;
    
    /**
     * �洢������mDivisiontype
     */
    static float mDivisiontype;
    /**
     * �洢����Ĳ��Ų���resolution
     */
    static int mResolution;
    /**
     * �洢����Ĳ��Ų���tempofactor
     */
    static float mTempofactor;
    /**
     * �洢����Ĳ��Ų���tempoinbpm
     */
    static float mTempoinbpm;
    /**
     * �洢����Ĳ��Ų���tempoinmpq
     */
    static float mTempoinmpq; 
    
    /**
     * 
     * @param filenameΪָ��MIDI�ļ���·��
     * ��ʼ����ص���Ϣ
     */
    public Parse(File sound)
    {
    	try
    	{
    		seq=MidiSystem.getSequence(sound);
    		midi=MidiSystem.getSequencer();
    		midi.open();
    		
    		//��ȡ��Ƶ�ļ��ĸ��ֲ���
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
     * �洢���������������Ϣ�㣬ÿ��������һ��Ϊ��������¼���Ԫ�ķָ�
     */
    private ArrayList<ArrayList<ArrayList<Note>>>  listAllInstrument=new ArrayList<ArrayList<ArrayList<Note>>>();
    /**
     * getPlayTracksByTick���ڴ洢��Ҫ��ɾ������������Ϣ
     * @param numΪָ��ɾ��������ŵ�����
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
    		}//�ڲ�for����һ������������
    		
    		if(listOneInstrument.size()!=0)
    		{
    			listAllInstrument.add(listOneInstrument);
    			
    			Tick=1000000;
    		}
    		
    	}//���for�������й���������
    }
    
    /**
     * Play���ڲ���ʣ������
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
     * Stopֹͣ����
     */
    public static void Stop()
    {
    	if(midi.isRunning())
			midi.stop();
    	
    	if(midi.isOpen())
    		midi.close();
    }
    /**
     * ���ڱ�ʶ�����Ƿ��Ѿ���ʼ����
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
	 * @return isStart ��ʶ�����Ƿ��Ѿ���ʼ����
	 */
	@Override
	public boolean getStart() {
		// TODO Auto-generated method stub
		return isStart;
	}
	/**
	 * @return ��ȡ������������������
	 */
	@Override
	public ArrayList<ArrayList<ArrayList<Note>>> getAllInstrumentList() {
		// TODO Auto-generated method stub
		return listAllInstrument;
	}
	/**
	 * @return ��ȡ����Ľ�����
	 */
	@Override
	public int getResolution() {
		// TODO Auto-generated method stub
		return mResolution;
	}
} 

