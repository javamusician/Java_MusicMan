/*
 * IDraw�ӿڹ������£�
 * 1���ṩdraw�������Թ�Controller�����
 * 2���ṩsetKeyPress�������Թ�Controller������Ϣ��DrawPanel�࣬�Ա�DrawPanel������ض�״̬�ػ����
 * 
 * Version          Date            Author
 * 1.0             2010/11/25       ��׿��
 */
package JavaMusician.MusicMan.Panel;

import java.util.ArrayList;

import JavaMusician.MusicMan.Note.NoteTrackPosition;

public interface IDraw {
	public void drawAllNoteOnScreen(ArrayList<ArrayList<NoteTrackPosition>> listOnScreenAllChannel);
	public void setKeyPress(int keyCode);
}
