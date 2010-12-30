/*
 * DrawPanel�๦�����£�
 * 1������Ӧ��Ҫ�����ڽ����ϵ�����ṹ�������е�����
 * 2��ʵ��IDraw�ӿڣ��Ա�Controller����DrawPanel��draw������setKeyPress�������������ð�����ֵ��
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       ��׿��                      ����
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
	 * ���к�
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * �洢��Ϸ������Ҫʹ�õ�������ͼƬ
	 */
	private Image mNote;
	/**
	 * �洢��Ϸ������Ҫʹ�õ��İ���
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
	 * ������ϷԪ��
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
	 * ��������
	 * @param g Ҫ�������Ļ���
	 */
	private void drawKey(Graphics g) {
		g.drawImage(mPressKey[0],0,650,mPressKey[0].getWidth(null),mPressKey[0].getHeight(null),null);
		
		if(key!=0)
			g.drawImage(mPressKey[key],0,650,
					mPressKey[key].getWidth(null),mPressKey[key].getHeight(null),null);
	}
	/**
	 * ���������е���������
	 * @param g Ҫ�������Ļ���
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
	 * key���ڼ�¼�Ǹ��������£��Ա㵽�ػ��ʱ������ж�
	 */
	private int key=0;

	/**
	 * �����Ǹ��������£��������keyΪ�����µļ��ļ�ֵ
	 */
	@Override
	public void setKeyPress(int keyCode) {
		key=keyCode;
	}
	
	/**
	 * ����洢����Ҫ�����ڽ����ϵ�����������ɵ����������а����������ʾĳ��ͨ����Ҫ���ֵ�����
	 */
	private ArrayList<ArrayList<NoteTrackPosition>> listOnScreenAllChannel=new ArrayList<ArrayList<NoteTrackPosition>>();

	/**
	 * �ػ����
	 */
	@Override
	public void drawAllNoteOnScreen(
			ArrayList<ArrayList<NoteTrackPosition>> listOnScreen) {
		listOnScreenAllChannel=listOnScreen;
		repaint();
	}

}
