/*
 * Controller类功能如下：
 * 1、初始化要出现在界面上的音符，即游戏一开始的音符绘制
 * 2、判断是否有按键输入，并在DrawPanel设置相应的标志，以便在重画界面的时候可以根据不同按键情况对按键进行重绘
 * 3、通过接口通知DrawPanel重绘界面
 * 4、通过接口通知Sound发出音符的音阶
 * 5、更新出现在界面上音符链表，并通过接口通知DrawPanel更新重绘链表
 * 
 * Version          Date            Author		   Modified
 * 1.0             2010/11/25       吴卓豪                               创建
 * 1.1             2010/12/3        吴卓豪                 增加按键监听处理
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
	 * 窗口的序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ONESECOND代表1秒钟长度（单位微秒）
	 */
	private static final int ONESECOND=1000000;
	/**
	 * initDate记录游戏开始时时间
	 */
	private Date mInitDate;
	/**
	 * 操作画板的接口
	 */
	private IDraw panelImp;	
	/**
	 * 操作解析音轨类的接口
	 */
	private IParse parseImp;
	/**
	 * 获取经由解析音轨类Parse得到的初步结果
	 */
	private ArrayList<ArrayList<ArrayList<Note>>> mOriginlist=new ArrayList<ArrayList<ArrayList<Note>>>();
	/**
	 * 存储所有乐器对应的由所有相同时间点组成的音轨
	 */
	private ArrayList<ArrayList<NoteTrackPosition>> mListAllInstrument=new ArrayList<ArrayList<NoteTrackPosition>>();
	/**
	 * 用于实际重绘的音轨，即存储要出现在界面上的音符，即所有轨道上
	 */
	private ArrayList<ArrayList<NoteTrackPosition>> mListOnScreenAllChannel=new ArrayList<ArrayList<NoteTrackPosition>>();
	/**
	 * 记录对应于各自通道已经在界面上的音符的索引位置，长度与通道数相同
	 */
	private int[] mNoteIndexInEachInstrument;
	/**
	 * 记录音轨的节拍率
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
	 * 游戏开始前的初始化工作
	 */
	private void gameInit()
	{
		
		unionAllMessageOfAllInstrumentInSameTime();
		
		initListOnScreen();
	}
	
	/**
	 * 将所有乐器对应的由所有的相同时间点的事件单元合成音轨存储到链表中，链表的长度对应为乐器的个数，针对于所有乐器
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
			}//对该乐器分析完毕
			
			mListAllInstrument.add(listOneInstrument);
			
			listOneInstrument=new ArrayList<NoteTrackPosition>();
			
		}//对每一种乐器都已经分析完毕
	}
	
	/**
	 * 初始化在界面中显示的音符，即对listAllInstrument进行操作
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
			}//for循环结束，对某种特定乐器音轨分析完毕，针对于某种特定的乐器
			
			mListOnScreenAllChannel.add(listOnScreenOneChannel);
			
			listOnScreenOneChannel=new ArrayList<NoteTrackPosition>();
			
		}//for循环结束，对所有乐器音轨分析完毕，针对于所有乐器
	}
	
	/**
	 * key用于记录那个键被按下，以便从链表中删除相应的键
	 */
	int key=0;
	
	public int getKey() {
		return key;
	}

	/**
	 * 判断在没有按键按下的情况下，哪个音符到达临界状态，并将其删除
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
	 * 当按键按下时，删除音符，并将其返回
	 * @return 删除的节点
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
	 * 记录游戏已经开始的时间，单位为毫秒
	 */
	private long gameStartedTime=0;
	
	/**
	 * 向链表添加音符
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
				}//for循环结束，对一个通道分析完毕
			}//for循环结束，对所有通道都分析完毕了
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
