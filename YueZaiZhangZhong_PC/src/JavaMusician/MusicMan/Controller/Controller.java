/*
 * Controller�๦�����£�
 * 1����ʼ��Ҫ�����ڽ����ϵ�����������Ϸһ��ʼ����������
 * 2���ж��Ƿ��а������룬����DrawPanel������Ӧ�ı�־���Ա����ػ������ʱ����Ը��ݲ�ͬ��������԰��������ػ�
 * 3��ͨ���ӿ�֪ͨDrawPanel�ػ����
 * 4��ͨ���ӿ�֪ͨSound��������������
 * 5�����³����ڽ���������������ͨ���ӿ�֪ͨDrawPanel�����ػ�����
 * 
 * Version          Date            Author		   Modified
 * 1.0             2010/11/25       ��׿��                               ����
 * 1.1             2010/12/3        ��׿��                 ���Ӱ�����������
 */
package JavaMusician.MusicMan.Controller;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import JavaMusician.MusicMan.Note.Note;
import JavaMusician.MusicMan.Note.NoteTrackPosition;
import JavaMusician.MusicMan.Panel.DrawPanel;
import JavaMusician.MusicMan.Panel.IDraw;
import JavaMusician.MusicMan.Parse.IParse;
import JavaMusician.MusicMan.Parse.Parse;
import JavaMusician.MusicMan.Parse.Sound;

public class Controller extends JFrame implements Runnable{
	/**
	 * ���ڵ����к�
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ONESECOND����1���ӳ��ȣ���λ΢�룩
	 */
	private static final int ONESECOND=1000000;
	/**
	 * initDate��¼��Ϸ��ʼʱʱ��
	 */
	private Date mInitDate;
	/**
	 * ��������Ľӿ�
	 */
	private IDraw panelImp;	
	/**
	 * ��������������Ľӿ�
	 */
	private IParse parseImp;
	/**
	 * ��ȡ���ɽ���������Parse�õ��ĳ������
	 */
	private ArrayList<ArrayList<ArrayList<Note>>> mOriginlist=new ArrayList<ArrayList<ArrayList<Note>>>();
	/**
	 * �洢����������Ӧ����������ͬʱ�����ɵ�����
	 */
	private ArrayList<ArrayList<NoteTrackPosition>> mListAllInstrument=new ArrayList<ArrayList<NoteTrackPosition>>();
	/**
	 * ����ʵ���ػ�����죬���洢Ҫ�����ڽ����ϵ������������й����
	 */
	private ArrayList<ArrayList<NoteTrackPosition>> mListOnScreenAllChannel=new ArrayList<ArrayList<NoteTrackPosition>>();
	/**
	 * ��¼��Ӧ�ڸ���ͨ���Ѿ��ڽ����ϵ�����������λ�ã�������ͨ������ͬ
	 */
	private int[] mNoteIndexInEachInstrument;
	/**
	 * ��¼����Ľ�����
	 */
	private int mResolution;
	
	public Controller()
	{
		setLocationByPlatform(true);
		panelImp=new DrawPanel();
		add((Component) panelImp);
		addKeyListener(new KeyListenerImp());
		setSize(350+8, 768+8);
		setVisible(true);
		
		parseImp=new Parse(new File("res\\music\\test.mid"));
		mResolution=parseImp.getResolution();
		
		gameInit();
		
		new Thread((Parse)parseImp).start();
		
		mInitDate=new Date();
		
		while(!parseImp.getStart());
		
		panelImp.drawAllNoteOnScreen(mListOnScreenAllChannel);
	}

	/**
	 * ��Ϸ��ʼǰ�ĳ�ʼ������
	 */
	private void gameInit()
	{
		
		unionAllMessageOfAllInstrumentInSameTime();
		
		initListOnScreen();
	}
	
	/**
	 * ������������Ӧ�������е���ͬʱ�����¼���Ԫ�ϳ�����洢�������У�����ĳ��ȶ�ӦΪ�����ĸ������������������
	 */
	private void unionAllMessageOfAllInstrumentInSameTime()
	{
		mOriginlist=parseImp.getAllInstrumentList();
		
		mNoteIndexInEachInstrument=new int[mOriginlist.size()];
		
		for(int i=0;i<mOriginlist.size();i++){
			
			ArrayList<NoteTrackPosition> listOneInstrument=new ArrayList<NoteTrackPosition>();
			
			for(int j=0;j<mOriginlist.get(i).size();j++){
				NoteTrackPosition noteTrackPosition=new NoteTrackPosition(mOriginlist.get(i).get(j));
				
				listOneInstrument.add(noteTrackPosition);
			}//�Ը������������
			
			mListAllInstrument.add(listOneInstrument);
			
			listOneInstrument=new ArrayList<NoteTrackPosition>();
			
		}//��ÿһ���������Ѿ��������
	}
	
	/**
	 * ��ʼ���ڽ�������ʾ������������listAllInstrument���в���
	 */
	private void initListOnScreen()
	{
		for(int i=0;i<mListAllInstrument.size();i++){
			
			ArrayList<NoteTrackPosition> listOnScreenOneChannel=new ArrayList<NoteTrackPosition>();
			
			for(int j=0;j<mListAllInstrument.get(i).size();j++){
				long tick=mListAllInstrument.get(i).get(j).getTick();
				if( ( (tick/mResolution) *ONESECOND) > ONESECOND)
				{
					mNoteIndexInEachInstrument[i]=j;
					
					listOnScreenOneChannel.add(mListAllInstrument.get(i).get(j));
					
					break;
				}				
			}//forѭ����������ĳ���ض��������������ϣ������ĳ���ض�������
			
			mListOnScreenAllChannel.add(listOnScreenOneChannel);
			
			listOnScreenOneChannel=new ArrayList<NoteTrackPosition>();
			
		}//forѭ���������������������������ϣ��������������
	}
	
	/**
	 * key���ڼ�¼�Ǹ��������£��Ա��������ɾ����Ӧ�ļ�
	 */
	int key=0;
	
	public int getKey() {
		return key;
	}

	/**
	 * �ж���û�а������µ�����£��ĸ����������ٽ�״̬��������ɾ��
	 */
	private void deleteNoteReachDeadLine()
	{
		Date deleteDate=new Date();
		long deleteTime=deleteDate.getTime()-mInitDate.getTime();
		for(int i=0;i<mListAllInstrument.size();i++){
			long tick=mListAllInstrument.get(i).get(0).getTick();
			if(deleteTime > ( (tick) ) )
			{
				key=i+1;
				deleteNote();
			}
		}
	}
	
	/**
	 * ����������ʱ��ɾ�������������䷵��
	 * @return ɾ���Ľڵ�
	 */
	private NoteTrackPosition deleteNote()
	{
		NoteTrackPosition note=null;
		try{
			ArrayList<NoteTrackPosition> listtemp = mListOnScreenAllChannel.get(key-1);
			note=listtemp.get(0);
		
			if(note==null)
				note=null;
			else{
				mListOnScreenAllChannel.get(key-1).remove(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return note;
	}
	
	/**
	 * ��¼��Ϸ�Ѿ���ʼ��ʱ�䣬��λΪ����
	 */
	private long gameStartedTime=0;
	
	/**
	 * �������������
	 */
	private void addNewNoteToOnScreen()
	{
		Date redrawDate=new Date();
		gameStartedTime=redrawDate.getTime()-mInitDate.getTime();
		for(int i=0;i<mListAllInstrument.size();i++){
			for(int j=mNoteIndexInEachInstrument[i];j<mListAllInstrument.get(i).size();j++){
				NoteTrackPosition noteTrackPosition=mListAllInstrument.get(i).get(j);
				long tick=noteTrackPosition.getTick();
				if( ( (tick/mResolution) ) <gameStartedTime)
				{
					mListOnScreenAllChannel.get(mNoteIndexInEachInstrument[i]).add(noteTrackPosition);
				}
				else
					break;
				}//forѭ����������һ��ͨ���������
			}//forѭ��������������ͨ�������������
	}
	
	@Override
	public void run() {
		while(parseImp.getStart())
		{
				deleteNoteReachDeadLine();
		
				panelImp.drawAllNoteOnScreen(mListOnScreenAllChannel);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				addNewNoteToOnScreen();
		}//while
	}
	
	class KeyListenerImp implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_D:key=1;break;
				case KeyEvent.VK_F:key=2;break;
				case KeyEvent.VK_SPACE:key=3;break;
				case KeyEvent.VK_J:key=4;break;
				case KeyEvent.VK_K:key=5;break;
				default:key=0;break;
			}
			
			panelImp.setKeyPress(key);
			panelImp.drawAllNoteOnScreen(mListOnScreenAllChannel);
			NoteTrackPosition note=deleteNote();
			if(note!=null)
				new Thread(new Sound(note)).start();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			panelImp.setKeyPress(0);
			panelImp.drawAllNoteOnScreen(mListOnScreenAllChannel);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
	}
}
