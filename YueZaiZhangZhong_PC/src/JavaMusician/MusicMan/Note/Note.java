/*
 * Note�๦�����£�
 * 1����¼�¼���Ԫ��ʱ���
 * 2����¼�¼���Ԫ��״̬����Ϣ����������Ϣ
 * 3����¼���ֵĲ����ʵ���Ϣ
 * 4����¼���ֵ���ɫ�Լ�����ͨ���ϵ���Ϣ
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       ��׿��                      ����
 */
package JavaMusician.MusicMan.Note;

public class Note {
	/**
	 * ������ʱ���
	 */
	private long mTick;
	/**
	 * �����ķ�����Ϣ����������ͨ�������׺�����
	 */
	private int[] imessage;
	/**
	 * ������Ϣ����ĳ���
	 */
	private int size;
	/**
	 * ���ֲ����������ϢΪmDivisiontype,mResolution,mTempofactor,mTempoinbpm,mTempoinmpq
	 */
	private float[] musicParameters;
	/**
	 * ���ڴ洢��ǰ������������������ɫ���Լ����Ǹ�ͨ����
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
	 * ��ȡ������ʱ���
	 * @return mTick ������ʱ���
	 */
	public Long getTick()
	{
		return mTick;
	}
	
	/**
	 * ��ȡ״̬�֣������š�ֹͣ������������ɫ�Ŀ�����
	 * @return ͨ��״̬��
	 */
	public int getState()
	{
		return size<3?0:(imessage[0]);
	}
	
	/**
	 * ��ȡҪ����������
	 * @return ����������
	 */
	public int getNote()
	{
		return size<3?0:(imessage[1]);
	}
	
	/**
	 * ��ȡҪ�Զ����ٶȽ��з���
	 * @return ����������
	 */
	public int getStrong()
	{
		return size<3?0:(imessage[2]);
	}
	/**
	 * ���������ķ���������������ʡ������ʵ�
	 * @param par ��������
	 */
	public void setMusicParameters(float[] par)
	{
		musicParameters=new float[par.length];
		for(int i=0;i<par.length;i++)
			musicParameters[i]=par[i];
	}
	/**
	 * ��ȡ�����ķ�������
	 * @return �����ķ����������飬������ʡ������ʵ�
	 */
	public float[] getMusicParameters()
	{
		return musicParameters;
	}
	/**
	 * ������������ɫ
	 * @param toneIn ��������ɫ���飬��������ͨ������ɫ
	 */
	public void setToneInfo(int[] toneIn)
	{
		toneInfo=new int[toneIn.length];
		for(int i=0;i<toneIn.length;i++){
			toneInfo[i]=toneIn[i]&0xff;
		}
	}
	/**
	 * ��ȡ��������ɫ����
	 * @return ��������ɫ���飬��������ͨ������ɫ
	 */
	public int[] getToneInfo()
	{
		return toneInfo;
	}
}
