/*
 * IDraw接口功能如下：
 * 1、提供draw方法，以供Controller类调用
 * 2、提供setKeyPress方法，以供Controller传递信息到DrawPanel类，以便DrawPanel类根据特定状态重绘界面
 * 
 * Version          Date            Author
 * 1.0             2010/11/25       吴卓豪
 */
package JavaMusician.MusicMan.Panel;

import java.util.ArrayList;

import JavaMusician.MusicMan.Note.NoteTrackPosition;

public interface IDraw {
	public void drawAllNoteOnScreen(ArrayList<ArrayList<NoteTrackPosition>> listOnScreenAllChannel);
	public void setKeyPress(int keyCode);
}
