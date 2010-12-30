/*
 * Note类功能如下：
 * 1、记录事件单元的时间点
 * 2、记录事件单元的状态字信息和数据字信息
 * 3、记录音乐的采样率等信息
 * 4、记录音乐的音色以及其在通道上的信息
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       吴卓豪                      创建
 */
package JavaMusician.MusicMan.Note;

public class Note {
	/**
	 * 音符的时间点
	 */
	private long mTick;
	/**
	 * 音符的发音信息，即发音的通道、音阶和力度
	 */
	private int[] imessage;
	/**
	 * 音符信息数组的长度
	 */
	private int size;
	/**
	 * 音乐采样率相关信息为mDivisiontype,mResolution,mTempofactor,mTempoinbpm,mTempoinmpq
	 */
	private float[] musicParameters;
	/**
	 * 用于存储当前的音符的乐器，即音色，以及在那个通道上
	 */
	private int[] toneInfo;
	
	public Note(Long tmpTick,byte[] tmpMessage,float[] par,int[] toneinfo)
	{
		mTick=tmpTick;
		size=tmpMessage.length;
		imessage=new int[size];
		for(int i=0;i<tmpMessage.length;i++){
			imessage[i]=tmpMessage[i]&0xff;
		}
		setMusicParameters(par);
		setToneInfo(toneinfo);
	}
	
	/**
	 * 获取音符的时间点
	 * @return mTick 音符的时间点
	 */
	public Long getTick()
	{
		return mTick;
	}
	
	/**
	 * 获取状态字，即播放、停止或者是设置音色的控制字
	 * @return 通道状态字
	 */
	public int getState()
	{
		return size<3?0:(imessage[0]);
	}
	
	/**
	 * 获取要发音的音阶
	 * @return 发音的音阶
	 */
	public int getNote()
	{
		return size<3?0:(imessage[1]);
	}
	
	/**
	 * 获取要以多大的速度进行发音
	 * @return 发音的力度
	 */
	public int getStrong()
	{
		return size<3?0:(imessage[2]);
	}
	/**
	 * 设置音符的发音参数，如采样率、节拍率等
	 * @param par 发音参数
	 */
	public void setMusicParameters(float[] par)
	{
		musicParameters=new float[par.length];
		for(int i=0;i<par.length;i++)
			musicParameters[i]=par[i];
	}
	/**
	 * 获取音符的发音参数
	 * @return 音符的发音参数数组，如采样率、节拍率等
	 */
	public float[] getMusicParameters()
	{
		return musicParameters;
	}
	/**
	 * 设置音符的音色
	 * @param toneIn 音符的音色数组，即发音的通道和音色
	 */
	public void setToneInfo(int[] toneIn)
	{
		toneInfo=new int[toneIn.length];
		for(int i=0;i<toneIn.length;i++){
			toneInfo[i]=toneIn[i]&0xff;
		}
	}
	/**
	 * 获取音符的音色数组
	 * @return 音符的音色数组，即发音的通道和音色
	 */
	public int[] getToneInfo()
	{
		return toneInfo;
	}
}
