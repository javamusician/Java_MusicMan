/*
 * Parse�ӿڹ������£�
 * 1���ṩgetStart�������Թ�Controller��ѯ��Parse���Ƿ��Ѿ����������죬
 * ����Controller������������Parse���Ƿ��Ѿ���ɣ�����Ϊ�˲�ֱ�Ӳ���Parse������ԣ�����ṩ�ӿ�
 * 2�����getAllInstrumentList�������Թ�Controller���ý�����ϵ�������Ϣ
 * 
 * Version          Date            Author		Modified
 * 1.0             2010/11/25       ��׿��                      ����
 */
package JavaMusician.MusicMan.Parse;

import java.util.ArrayList;

import JavaMusician.MusicMan.Note.Note;

public interface IParse {
	public boolean getStart();
	public ArrayList<ArrayList<ArrayList<Note>>> getAllInstrumentList();
	public int getResolution();
}
