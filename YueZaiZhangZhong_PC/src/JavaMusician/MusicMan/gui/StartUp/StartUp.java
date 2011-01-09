package JavaMusician.MusicMan.gui.StartUp;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import JavaMusician.MusicMan.Controller.Controller;
import JavaMusician.MusicMan.Instruments.Piano.PianoPlayer;
import JavaMusician.MusicMan.UI.AutoComposerUI;
import JavaMusician.MusicMan.UI.RecordPlayUI;

/**
 * �������ڴ�����ʼ����
 * @author lindi
 *
 */
public class StartUp {
	/**
	 * ����ײ���
	 */
	static public Frame mFrame;
	
	/**
	 * ����
	 */
	JLabel mBackground=new JLabel(new ImageIcon("res/drawable/ui/interface_main.png"));
	
	/**
	 * ���ఴť
	 */
	static JLabel mAutoComposer=new JLabel(new ImageIcon("res/drawable/ui/button_zidongpuqu_unpress.png"));
	
	/**
	 * ��Ϸ����ť
	 */
	static JLabel mGame=new JLabel(new ImageIcon("res/drawable/ui/button_yinyueyouxi_unpress.png"));
	
	/**
	 * �طŰ�ť
	 */
	static JLabel mReplay=new JLabel(new ImageIcon("res/drawable/ui/button_yuyinpuqu_unpress.png"));
	
	/**
	 * �����ٰ�ť
	 */
	static JLabel mDemo=new JLabel(new ImageIcon("res/drawable/ui/button_yinyueyanzou_unpress.png"));
	
	/**
	 * ���ϽǵĲ˵���ť
	 */
	static JLabel mMenu=new JLabel(new ImageIcon("res/drawable/ui/button_menu.png"));
	
	/**
	 * ���Ͻǵ��˳���ť����ʼ��Ϊ���ɼ�
	 */
	static JLabel mExit=new JLabel(new ImageIcon("res/drawable/ui/button_exit.png"));
	
	/**
	 * ���ϽǵĹ��ڰ�ť����ʼ��Ϊ���ɼ�
	 */
	static JLabel mAbout=new JLabel(new ImageIcon("res/drawable/ui/button_about.png"));
	
	/**
	 * ���Ͻǵİ�����ť����ʼ��Ϊ���ɼ�
	 */
	static JLabel mHelp=new JLabel(new ImageIcon("res/drawable/ui/button_help.png"));
	
	/**
	 * �˳��Ի���
	 */
	JLabel mExitDialog=new JLabel(new ImageIcon("res/drawable/ui/interface_exit.png"));
	
	/**
	 * �˳�ȷ�ϰ�ť
	 */
	JLabel mExitYes=new JLabel(new ImageIcon("res/drawable/Exit_Yes_Unpress.png"));
	
	/**
	 * �˳�ȡ����ť
	 */
	JLabel mExitNo=new JLabel(new ImageIcon("res/drawable/Exit_No_Unpress.png"));
	
	/**
	 * ���ڶԻ���
	 */
	JLabel mAboutDialog=new JLabel(new ImageIcon("res/drawable/ui/interface_about.png"));
	
	/**
	 * ���ڶԻ���ķ��ذ�ť
	 */
	JLabel mBack=new JLabel(new ImageIcon("res/drawable/Back_Unpress.png"));

	
	/**
	 * ���캯��
	 * @param title
	 */
	public StartUp(String title)
	{	
		//������
		mFrame=new Frame();
		mFrame.setSize(800, 600+30);
		mFrame.setLayout(null);
		mFrame.setTitle(title);
		
		//����˳��Ի����Լ�ȷ�Ϻ�ȡ����ť
		mExitYes.setBounds(240, 330, 169, 62);
		mFrame.add(mExitYes);
		mExitNo.setBounds(400, 330, 169, 62);
		mFrame.add(mExitNo);
		mExitYes.setVisible(false);
		mExitNo.setVisible(false);
		mExitDialog.setBounds(0, 0+26, 800, 600);
		mFrame.add(mExitDialog);
		mExitDialog.setVisible(false);
		
		//��ӹ��ڶԻ����Լ����ذ�ť
		mBack.setBounds(320, 430, 169, 62);
		mFrame.add(mBack);
		mBack.setVisible(false);
		mAboutDialog.setBounds(0, 0+20, 800, 600);
		mFrame.add(mAboutDialog);
		mAboutDialog.setVisible(false);	
		
		//��Ӳ˵���ť
		mMenu.setBounds(400, 30, 85, 50);
		mFrame.add(mMenu);
		
		//��Ӱ�����ť
		mHelp.setBounds(501, 30, 85, 50);
		mFrame.add(mHelp);
		
		//��ӹ��ڰ�ť
		mAbout.setBounds(601, 30, 85, 50);
		mFrame.add(mAbout);
		
		//����˳���ť
		mExit.setBounds(701, 30, 85, 50);
		mFrame.add(mExit);
		
		//����Զ�������ť
		mAutoComposer.setBounds(100, 130, 260, 80);
		mFrame.add(mAutoComposer);
		
		//�������������ť
		mReplay.setBounds(200, 230, 260, 80);
		mFrame.add(mReplay);
		
		//���������Ϸ��ť
		mGame.setBounds(300, 330, 260, 80);
		mFrame.add(mGame);
		
		//����������ఴť
		mDemo.setBounds(400, 430, 260, 80);
		mFrame.add(mDemo);
	
		//��ӱ���
		mBackground.setBounds(0, 0+26, 800, 600);
		mFrame.add(mBackground);
		
		//�������ڴ�С
		mFrame.setResizable(false);
		
		//��ʾ���
		mFrame.setVisible(true);
		
		//�������ఴť
		mAutoComposer.addMouseListener(new MouseAdapter() {
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȡ����ť��ʾ���µ�״̬
				mAutoComposer.setIcon(new ImageIcon("res/drawable/ui/button_zidongpuqu_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				new AutoComposerUI();
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȡ����ťͼ��ı�
				mAutoComposer.setIcon(new ImageIcon("res/drawable/ui/button_zidongpuqu_unpress.png"));
			}
		});
		
		//������Ϸ��ť
		mGame.addMouseListener(new MouseAdapter() {
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȡ����ť��ʾ���µ�״̬
				mGame.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyouxi_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				new Thread(new Controller()).start();
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȡ����ťͼ��ı�
				mGame.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyouxi_unpress.png"));
			}
		});
		
		//�����طŰ�ť
		mReplay.addMouseListener(new MouseAdapter() {
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȡ����ť��ʾ���µ�״̬
				mReplay.setIcon(new ImageIcon("res/drawable/ui/button_yuyinpuqu_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				new RecordPlayUI();
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȡ����ťͼ��ı�
				mReplay.setIcon(new ImageIcon("res/drawable/ui/button_yuyinpuqu_unpress.png"));
			}
		});
		
		//�����رտ�ܰ�ť
		mFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mFrame.setVisible (false);
	            System.exit(0);
	        }
		});
		
		//�����طŰ�ť
		mDemo.addMouseListener(new MouseAdapter() {
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȡ����ť��ʾ���µ�״̬
				mDemo.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyanzou_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				playPiano();
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȡ����ťͼ��ı�
				mDemo.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyanzou_unpress.png"));
			}
		});
		
		//�����رտ�ܰ�ť
		mFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mFrame.setVisible (false);
	            System.exit(0);
	        }
		});
		
		//�����˵���ť
		mMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		//�����˳���ť
		mExit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//��ʾ�˳��Ի���
				mExitDialog.setVisible(true);
				mExitYes.setVisible(true);
				mExitNo.setVisible(true);
			}
		});
		
		//�����˳�ȷ�ϰ�ť
		mExitYes.addMouseListener(new MouseAdapter() {
			//�����
			public void mouseClicked(MouseEvent e) {
				//��ʾ�˳��Ի���
				mFrame.setVisible (false);
	            System.exit(0);
			}
			
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȷ�ϰ�ť��ʾ���µ�״̬
				mExitYes.setIcon(new ImageIcon("res/drawable/Exit_Yes_Press.png"));
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȷ�ϰ�ťͼ��ı�
				mExitYes.setIcon(new ImageIcon("res/drawable/Exit_Yes_Unpress.png"));
			}
		});
		
		//�����˳�ȡ����ť
		mExitNo.addMouseListener(new MouseAdapter() {
			//�����
			public void mouseClicked(MouseEvent e) {
				//�����˳��Ի���
				mExitDialog.setVisible(false);
				mExitYes.setVisible(false);
				mExitNo.setVisible(false);
			}
			
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȡ����ť��ʾ���µ�״̬
				mExitNo.setIcon(new ImageIcon("res/drawable/Exit_No_Press.png"));
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȡ����ťͼ��ı�
				mExitNo.setIcon(new ImageIcon("res/drawable/Exit_No_Unpress.png"));
			}
		});
		
		//�������ڰ�ť
		mAbout.addMouseListener(new MouseAdapter() {
			//�����
			public void mouseClicked(MouseEvent e) {
				//���ع��ڶԻ���
				mAboutDialog.setVisible(true);
				mBack.setVisible(true);
			}
		});
			
		mBack.addMouseListener(new MouseAdapter() {	
			//�����
			public void mouseClicked(MouseEvent e) {
				//���ع��ڶԻ���
				mAboutDialog.setVisible(false);
				mBack.setVisible(false);
			}
			
			//��갴��
			public void mousePressed(MouseEvent e) {
				//�˳�ȡ����ť��ʾ���µ�״̬
				mBack.setIcon(new ImageIcon("res/drawable/Back_Press.png"));
			}
			
			//����ͷ�
			public void mouseReleased(MouseEvent e) {
				//����ͷ�ʱ���˳�ȡ����ťͼ��ı�
				mBack.setIcon(new ImageIcon("res/drawable/Back_Unpress.png"));
			}
		});
	}
	
	private void playPiano() {
		PianoPlayer frame = new PianoPlayer();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(frame);
		frame.setSize(781, 580);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
