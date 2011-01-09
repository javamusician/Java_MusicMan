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
 * 该类用于创建开始界面
 * @author lindi
 *
 */
public class StartUp {
	/**
	 * 程序底层框架
	 */
	static public Frame mFrame;
	
	/**
	 * 背景
	 */
	JLabel mBackground=new JLabel(new ImageIcon("res/drawable/ui/interface_main.png"));
	
	/**
	 * 演奏按钮
	 */
	static JLabel mAutoComposer=new JLabel(new ImageIcon("res/drawable/ui/button_zidongpuqu_unpress.png"));
	
	/**
	 * 游戏集按钮
	 */
	static JLabel mGame=new JLabel(new ImageIcon("res/drawable/ui/button_yinyueyouxi_unpress.png"));
	
	/**
	 * 回放按钮
	 */
	static JLabel mReplay=new JLabel(new ImageIcon("res/drawable/ui/button_yuyinpuqu_unpress.png"));
	
	/**
	 * 弹钢琴按钮
	 */
	static JLabel mDemo=new JLabel(new ImageIcon("res/drawable/ui/button_yinyueyanzou_unpress.png"));
	
	/**
	 * 右上角的菜单按钮
	 */
	static JLabel mMenu=new JLabel(new ImageIcon("res/drawable/ui/button_menu.png"));
	
	/**
	 * 右上角的退出按钮，初始化为不可见
	 */
	static JLabel mExit=new JLabel(new ImageIcon("res/drawable/ui/button_exit.png"));
	
	/**
	 * 右上角的关于按钮，初始化为不可见
	 */
	static JLabel mAbout=new JLabel(new ImageIcon("res/drawable/ui/button_about.png"));
	
	/**
	 * 右上角的帮助按钮，初始化为不可见
	 */
	static JLabel mHelp=new JLabel(new ImageIcon("res/drawable/ui/button_help.png"));
	
	/**
	 * 退出对话框
	 */
	JLabel mExitDialog=new JLabel(new ImageIcon("res/drawable/ui/interface_exit.png"));
	
	/**
	 * 退出确认按钮
	 */
	JLabel mExitYes=new JLabel(new ImageIcon("res/drawable/Exit_Yes_Unpress.png"));
	
	/**
	 * 退出取消按钮
	 */
	JLabel mExitNo=new JLabel(new ImageIcon("res/drawable/Exit_No_Unpress.png"));
	
	/**
	 * 关于对话框
	 */
	JLabel mAboutDialog=new JLabel(new ImageIcon("res/drawable/ui/interface_about.png"));
	
	/**
	 * 关于对话框的返回按钮
	 */
	JLabel mBack=new JLabel(new ImageIcon("res/drawable/Back_Unpress.png"));

	
	/**
	 * 构造函数
	 * @param title
	 */
	public StartUp(String title)
	{	
		//定义框架
		mFrame=new Frame();
		mFrame.setSize(800, 600+30);
		mFrame.setLayout(null);
		mFrame.setTitle(title);
		
		//添加退出对话框以及确认和取消按钮
		mExitYes.setBounds(240, 330, 169, 62);
		mFrame.add(mExitYes);
		mExitNo.setBounds(400, 330, 169, 62);
		mFrame.add(mExitNo);
		mExitYes.setVisible(false);
		mExitNo.setVisible(false);
		mExitDialog.setBounds(0, 0+26, 800, 600);
		mFrame.add(mExitDialog);
		mExitDialog.setVisible(false);
		
		//添加关于对话框以及返回按钮
		mBack.setBounds(320, 430, 169, 62);
		mFrame.add(mBack);
		mBack.setVisible(false);
		mAboutDialog.setBounds(0, 0+20, 800, 600);
		mFrame.add(mAboutDialog);
		mAboutDialog.setVisible(false);	
		
		//添加菜单按钮
		mMenu.setBounds(400, 30, 85, 50);
		mFrame.add(mMenu);
		
		//添加帮助按钮
		mHelp.setBounds(501, 30, 85, 50);
		mFrame.add(mHelp);
		
		//添加关于按钮
		mAbout.setBounds(601, 30, 85, 50);
		mFrame.add(mAbout);
		
		//添加退出按钮
		mExit.setBounds(701, 30, 85, 50);
		mFrame.add(mExit);
		
		//添加自动谱曲按钮
		mAutoComposer.setBounds(100, 130, 260, 80);
		mFrame.add(mAutoComposer);
		
		//添加语音谱曲按钮
		mReplay.setBounds(200, 230, 260, 80);
		mFrame.add(mReplay);
		
		//添加音乐游戏按钮
		mGame.setBounds(300, 330, 260, 80);
		mFrame.add(mGame);
		
		//添加音乐演奏按钮
		mDemo.setBounds(400, 430, 260, 80);
		mFrame.add(mDemo);
	
		//添加背景
		mBackground.setBounds(0, 0+26, 800, 600);
		mFrame.add(mBackground);
		
		//锁定窗口大小
		mFrame.setResizable(false);
		
		//显示框架
		mFrame.setVisible(true);
		
		//监听演奏按钮
		mAutoComposer.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mAutoComposer.setIcon(new ImageIcon("res/drawable/ui/button_zidongpuqu_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				new AutoComposerUI();
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mAutoComposer.setIcon(new ImageIcon("res/drawable/ui/button_zidongpuqu_unpress.png"));
			}
		});
		
		//监听游戏按钮
		mGame.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mGame.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyouxi_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				new Thread(new Controller()).start();
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mGame.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyouxi_unpress.png"));
			}
		});
		
		//监听回放按钮
		mReplay.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mReplay.setIcon(new ImageIcon("res/drawable/ui/button_yuyinpuqu_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				new RecordPlayUI();
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mReplay.setIcon(new ImageIcon("res/drawable/ui/button_yuyinpuqu_unpress.png"));
			}
		});
		
		//监听关闭框架按钮
		mFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mFrame.setVisible (false);
	            System.exit(0);
	        }
		});
		
		//监听回放按钮
		mDemo.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mDemo.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyanzou_press.png"));
			}
			
			public void mouseClicked(MouseEvent e) {
				mFrame.setVisible (false);
				playPiano();
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mDemo.setIcon(new ImageIcon("res/drawable/ui/button_yinyueyanzou_unpress.png"));
			}
		});
		
		//监听关闭框架按钮
		mFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mFrame.setVisible (false);
	            System.exit(0);
	        }
		});
		
		//监听菜单按钮
		mMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		//监听退出按钮
		mExit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//显示退出对话框
				mExitDialog.setVisible(true);
				mExitYes.setVisible(true);
				mExitNo.setVisible(true);
			}
		});
		
		//监听退出确认按钮
		mExitYes.addMouseListener(new MouseAdapter() {
			//鼠标点击
			public void mouseClicked(MouseEvent e) {
				//显示退出对话框
				mFrame.setVisible (false);
	            System.exit(0);
			}
			
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出确认按钮显示按下的状态
				mExitYes.setIcon(new ImageIcon("res/drawable/Exit_Yes_Press.png"));
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出确认按钮图标改变
				mExitYes.setIcon(new ImageIcon("res/drawable/Exit_Yes_Unpress.png"));
			}
		});
		
		//监听退出取消按钮
		mExitNo.addMouseListener(new MouseAdapter() {
			//鼠标点击
			public void mouseClicked(MouseEvent e) {
				//隐藏退出对话框
				mExitDialog.setVisible(false);
				mExitYes.setVisible(false);
				mExitNo.setVisible(false);
			}
			
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mExitNo.setIcon(new ImageIcon("res/drawable/Exit_No_Press.png"));
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mExitNo.setIcon(new ImageIcon("res/drawable/Exit_No_Unpress.png"));
			}
		});
		
		//监听关于按钮
		mAbout.addMouseListener(new MouseAdapter() {
			//鼠标点击
			public void mouseClicked(MouseEvent e) {
				//隐藏关于对话框
				mAboutDialog.setVisible(true);
				mBack.setVisible(true);
			}
		});
			
		mBack.addMouseListener(new MouseAdapter() {	
			//鼠标点击
			public void mouseClicked(MouseEvent e) {
				//隐藏关于对话框
				mAboutDialog.setVisible(false);
				mBack.setVisible(false);
			}
			
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mBack.setIcon(new ImageIcon("res/drawable/Back_Press.png"));
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
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
