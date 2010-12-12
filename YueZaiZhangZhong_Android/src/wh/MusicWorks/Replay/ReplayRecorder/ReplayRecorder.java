package wh.MusicWorks.Replay.ReplayRecorder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wh.MusicWorks.Replay.ReplayData.ReplayData;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class ReplayRecorder implements Serializable{
	List<ReplayData> mReplayDataList;
	public ReplayRecorder(){
		mReplayDataList=new ArrayList<ReplayData>();
	}
	
	public void insertReplayData(ReplayData replayData){
		mReplayDataList.add(replayData);
	}
	
	public int getReplaySize(){
		return mReplayDataList.size();
	}
	
	public ReplayData getReplayData(int i)
	{
		return mReplayDataList.get(i);
	}
}