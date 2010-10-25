package JavaMusician.MusicMan.gui.StartUp;

import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

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
	JLabel mBackground=new JLabel(new ImageIcon("res/drawable/startup.png"));
	
	/**
	 * 演奏按钮
	 */
	static JLabel mPlay=new JLabel(new ImageIcon("res/drawable/Play_Unpress.png"));
	
	/**
	 * 游戏集按钮
	 */
	static JLabel mGame=new JLabel(new ImageIcon("res/drawable/Game_Unpress.png"));
	
	/**
	 * 回放按钮
	 */
	static JLabel mReplay=new JLabel(new ImageIcon("res/drawable/Replay_Unpress.png"));
	
	/**
	 * 右上角的菜单按钮
	 */
	static JLabel mMenu=new JLabel(new ImageIcon("res/drawable/Menu.png"));
	
	/**
	 * 连接演奏按钮的线
	 */
	static JLabel mLineToPlay=new JLabel(new ImageIcon("res/drawable/LineToPlay.png"));
	
	/**
	 * 连接游戏按钮的线
	 */
	static JLabel mLineToGame=new JLabel(new ImageIcon("res/drawable/LineToGame.png"));
	
	/**
	 * 连接回放按钮的线
	 */
	static JLabel mLineToReplay=new JLabel(new ImageIcon("res/drawable/LineToReplay.png"));
	
	/**
	 * 右上角的轮盘，初始化为不可见
	 */
	static JLabel mRound=new JLabel(new ImageIcon("res/drawable/Round.png"));
	
	/**
	 * 右上角的退出按钮，初始化为不可见
	 */
	static JLabel mExit=new JLabel(new ImageIcon("res/drawable/Exit.png"));
	
	/**
	 * 右上角的关于按钮，初始化为不可见
	 */
	static JLabel mAbout=new JLabel(new ImageIcon("res/drawable/About.png"));
	
	/**
	 * 右上角的帮助按钮，初始化为不可见
	 */
	static JLabel mHelp=new JLabel(new ImageIcon("res/drawable/Help.png"));;
	
	/**
	 * 用于菜单按钮的切换
	 */
	static boolean mMenuClick=true;
	
	/**
	 * 退出对话框
	 */
	JLabel mExitDialog=new JLabel(new ImageIcon("res/drawable/ExitDialog.png"));
	
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
	JLabel mAboutDialog=new JLabel(new ImageIcon("res/drawable/AboutDialog.png"));
	
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
		mFrame.setSize(800, 600);
		mFrame.setLayout(null);
		mFrame.setTitle(title);
		
		//添加退出对话框以及确认和取消按钮
		mExitYes.setBounds(240, 300, 169, 62);
		mFrame.add(mExitYes);
		mExitNo.setBounds(400, 300, 169, 62);
		mFrame.add(mExitNo);
		mExitYes.setVisible(false);
		mExitNo.setVisible(false);
		mExitDialog.setBounds(0, 0, 800, 600);
		mFrame.add(mExitDialog);
		mExitDialog.setVisible(false);
		
		//添加关于对话框以及返回按钮
		mBack.setBounds(320, 390, 169, 62);
		mFrame.add(mBack);
		mBack.setVisible(false);
		mAboutDialog.setBounds(0, 0, 800, 600);
		mFrame.add(mAboutDialog);
		mAboutDialog.setVisible(false);
		
		//添加三条连接按钮的线
		mLineToPlay.setBounds(0, 150, 71, 35);
		mLineToGame.setBounds(0, 240, 124, 33);
		mLineToReplay.setBounds(0, 330, 177, 37);
		mFrame.add(mLineToPlay);
		mFrame.add(mLineToGame);
		mFrame.add(mLineToReplay);
		
		//添加演奏按钮
		mPlay.setBounds(35, 130, 354, 89);
		mFrame.add(mPlay);
		
		//添加游戏集按钮
		mGame.setBounds(90, 220, 354, 89);
		mFrame.add(mGame);
		
		//添加回放按钮
		mReplay.setBounds(140, 310, 354, 89);
		mFrame.add(mReplay);
		
		//添加右上角的菜单按钮
		mMenu.setBounds(729, 29, 55, 55);
		mFrame.add(mMenu);
		
		//添加轮盘中的退出按钮
		mExit.setBounds(678, 22, 55, 54);
		mFrame.add(mExit);
		mExit.setVisible(false);
		
		//添加路轮盘中的关于按钮
		mAbout.setBounds(693, 72, 55, 55);
		mFrame.add(mAbout);
		mAbout.setVisible(false);
		
		//添加路轮盘中的帮助按钮
		mHelp.setBounds(745, 80, 55, 55);
		mFrame.add(mHelp);
		mHelp.setVisible(false);
		
		//添加右上角的轮盘，并设置为不可见
		mRound.setBounds(660, 25, 144, 124);
		mFrame.add(mRound);
		mRound.setVisible(false);
	
		//添加背景
		mBackground.setBounds(0, 0, 800, 600);
		mFrame.add(mBackground);
		
		//锁定窗口大小
		mFrame.setResizable(false);
		
		//显示框架
		mFrame.setVisible(true);
		
		//监听演奏按钮
		mPlay.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mPlay.setIcon(new ImageIcon("res/drawable/Play_Press.png"));
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mPlay.setIcon(new ImageIcon("res/drawable/Play_Unpress.png"));
			}
		});
		
		//监听游戏按钮
		mGame.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mGame.setIcon(new ImageIcon("res/drawable/Game_Press.png"));
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mGame.setIcon(new ImageIcon("res/drawable/Game_Unpress.png"));
			}
		});
		
		//监听回放按钮
		mReplay.addMouseListener(new MouseAdapter() {
			//鼠标按下
			public void mousePressed(MouseEvent e) {
				//退出取消按钮显示按下的状态
				mReplay.setIcon(new ImageIcon("res/drawable/Replay_Press.png"));
			}
			
			//鼠标释放
			public void mouseReleased(MouseEvent e) {
				//鼠标释放时，退出取消按钮图标改变
				mReplay.setIcon(new ImageIcon("res/drawable/Replay_Unpress.png"));
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
				//显示/隐藏演奏、游戏、回放按钮以及连接它们的线
				mPlay.setVisible(!mMenuClick);
				mGame.setVisible(!mMenuClick);
				mReplay.setVisible(!mMenuClick);
				mLineToPlay.setVisible(!mMenuClick);
				mLineToGame.setVisible(!mMenuClick);
				mLineToReplay.setVisible(!mMenuClick);
				
				//显示/隐藏右上角的轮盘以及退出、关于、帮助按钮
				mRound.setVisible(mMenuClick);
				mExit.setVisible(mMenuClick);
				mAbout.setVisible(mMenuClick);
				mHelp.setVisible(mMenuClick);
				
				//切换
				mMenuClick=!mMenuClick;
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
	
	/**
	 * 测试使用的main函数
	 */
	public static void main(String[] args)
	{
		StartUp su=new StartUp("乐在掌中");
	}
}
