/*
 * Parse接口功能如下：
 * 1、提供getStart方法，以供Controller类询问Parse类是否已经解析完音轨，
 * 由于Controller工作的条件是Parse类是否已经完成，但又为了不直接操作Parse类的属性，因此提供接口
 * 2、提过getAllInstrumentList方法，以供Controller类获得解析完毕的音轨信息
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       吴卓豪                      创建
 */
package JavaMusician.MusicMan.Parse;

import java.util.ArrayList;

import JavaMusician.MusicMan.Note.Note;

public interface IParse {
	public boolean getStart();
	public ArrayList<ArrayList<ArrayList<Note>>> getAllInstrumentList();
	public int getResolution();
}
