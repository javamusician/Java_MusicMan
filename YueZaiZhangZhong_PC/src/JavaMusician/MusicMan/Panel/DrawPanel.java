/*
 * DrawPanel类功能如下：
 * 1、根据应该要出现在界面上的链表结构画出所有的音符
 * 2、实现IDraw接口，以便Controller操作DrawPanel的draw方法和setKeyPress方法（用于设置按键键值）
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       吴卓豪                      创建
 */
package JavaMusician.MusicMan.Panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;
import JavaMusician.MusicMan.Note.NoteTrackPosition;

public class DrawPanel extends JPanel implements IDraw{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 存储游戏过程中要使用到的音符图片
	 */
	private Image mNote;
	/**
	 * 存储游戏过程中要使用到的按键
	 */
	private Image[] mPressKey;
	
	public DrawPanel() {
		setSize(350, 768);
		
		setBackground(Color.GRAY);
		
		loadAllPicture();
	}
	
	protected void paintComponent(Graphics g){
		
		g.setColor(Color.GRAY);
		
		g.fillRect(0, 0,this.getSize().width,this.getSize().height);
		
		drawAllNoteInList(g);			
		
		drawKey(g);
	}
	
	/**
	 * 载入游戏元素
	 */
	private void loadAllPicture() {
		mNote=Toolkit.getDefaultToolkit().getImage("res/drawable/note.png");
		
		mPressKey=new Image[6];
		
		mPressKey[0]=Toolkit.getDefaultToolkit().getImage("res/drawable/keybroad.png");
		
		mPressKey[1]=Toolkit.getDefaultToolkit().getImage("res/drawable/presskey1.png");
		mPressKey[2]=Toolkit.getDefaultToolkit().getImage("res/drawable/presskey2.png");
		mPressKey[3]=Toolkit.getDefaultToolkit().getImage("res/drawable/presskey3.png");
		mPressKey[4]=Toolkit.getDefaultToolkit().getImage("res/drawable/presskey4.png");
		mPressKey[5]=Toolkit.getDefaultToolkit().getImage("res/drawable/presskey5.png");
	}
	/**
	 * 画出按键
	 * @param g 要画按键的画板
	 */
	private void drawKey(Graphics g) {
		g.drawImage(mPressKey[0],0,650,mPressKey[0].getWidth(null),mPressKey[0].getHeight(null),null);
		
		if(key!=0)
			g.drawImage(mPressKey[key],0,650,
					mPressKey[key].getWidth(null),mPressKey[key].getHeight(null),null);
	}
	/**
	 * 画出链表中的所有音符
	 * @param g 要画音符的画板
	 */
	private void drawAllNoteInList(Graphics g) {
		int height=0;
		for(int i=0;i<listOnScreenAllChannel.size();i++){
			int j=0;
			for(j=0;j<listOnScreenAllChannel.get(i).size();j++){
				height=listOnScreenAllChannel.get(i).get(j).getHeight();
				g.drawImage(mNote, listOnScreenAllChannel.get(i).get(j).getPosition()*50+10, height, mNote.getWidth(null), mNote.getHeight(null), null);
				listOnScreenAllChannel.get(i).get(j).setHeight(height+100);
			}
		}
	}
	
	/**
	 * key用于记录那个键被按下，以便到重绘的时候进行判断
	 */
	private int key=0;

	/**
	 * 设置那个键被按下，传入参数key为被按下的键的键值
	 */
	@Override
	public void setKeyPress(int keyCode) {
		key=keyCode;
	}
	
	/**
	 * 链表存储的是要出现在界面上的所有音符组成的链表，链表中包含的链表表示某个通道上要出现的音符
	 */
	private ArrayList<ArrayList<NoteTrackPosition>> listOnScreenAllChannel=new ArrayList<ArrayList<NoteTrackPosition>>();

	/**
	 * 重绘界面
	 */
	@Override
	public void drawAllNoteOnScreen(
			ArrayList<ArrayList<NoteTrackPosition>> listOnScreen) {
		listOnScreenAllChannel=listOnScreen;
		repaint();
	}

}
