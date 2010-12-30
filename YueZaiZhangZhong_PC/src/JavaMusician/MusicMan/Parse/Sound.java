/*
 * Sound类功能如下：
 * 1、传入音符进行创建
 * 2、由于实现了Runnable接口，所以要操作run函数以播放音乐，播放过程中采用线程睡眠的方法来让出CPU
 * 
 * Version          Date            Author
 * 1.0             2010/11/25       吴卓豪
 */
package JavaMusician.MusicMan.Parse;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import JavaMusician.MusicMan.Note.NoteTrackPosition;

public class Sound implements Runnable{
	/**
	 * 存储指定音符的序列器
	 */
	private Sequencer mSequencer;

	public Sound(NoteTrackPosition note) {
		try {
			mSequencer=MidiSystem.getSequencer();
			mSequencer.open();
			initMusicParameter(note);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化指定音轨的播放参数
	 * @param note 指定的音轨
	 * @throws InvalidMidiDataException
	 */
	private void initMusicParameter(NoteTrackPosition note)
			throws InvalidMidiDataException {
		mSequencer.setSequence(note.getSequence());
		mSequencer.setTempoFactor(note.getTempofactor());
		mSequencer.setTempoInBPM(note.getTempoinbpm());
		mSequencer.setTempoInMPQ(note.getTempoinmpq());
	}
	/**
	 * 播放音轨
	 */
	public void play()
	{
			mSequencer.start();
	}
	/**
	 * 获取音轨的时间长度
	 * @return 音轨的时间长度
	 */
	public long getSoundLength()
	{
		return mSequencer.getMicrosecondLength()/1000;
	}
	/**
	 * 停止播放
	 */
	public void stop()
	{
		mSequencer.stop();
		mSequencer.close();
		mSequencer=null;
	}
	@Override
	public void run() {
		play();
		long time = mSequencer.getMicrosecondLength()/1000;
		try 
    	{
    		Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stop();
	}
}
