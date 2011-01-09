package wh.MusicWorks.Replay.ReplayData;

import java.io.Serializable;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class ReplayData implements Serializable{
	public static final int DATA_TYPE_METRONOME=0;
	public static final int DATA_TYPE_GUITAR=1;
	public int mDataType;
	public int mOperation;
	public long mTime;
	
	public ReplayData(int dataType,int operation,long time){
		mDataType=dataType;
		mOperation=operation;
		mTime=time;
	}
	
	public String toString(){
		return ""+mDataType+"/"+mOperation+"/"+mTime;
	}
}