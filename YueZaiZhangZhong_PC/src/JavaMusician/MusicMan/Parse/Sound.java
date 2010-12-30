/*
 * Sound�๦�����£�
 * 1�������������д���
 * 2������ʵ����Runnable�ӿڣ�����Ҫ����run�����Բ������֣����Ź����в����߳�˯�ߵķ������ó�CPU
 * 
 * Version          Date            Author
 * 1.0             2010/11/25       ��׿��
 */
package JavaMusician.MusicMan.Parse;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import JavaMusician.MusicMan.Note.NoteTrackPosition;

public class Sound implements Runnable{
	/**
	 * �洢ָ��������������
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
	 * ��ʼ��ָ������Ĳ��Ų���
	 * @param note ָ��������
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
	 * ��������
	 */
	public void play()
	{
			mSequencer.start();
	}
	/**
	 * ��ȡ�����ʱ�䳤��
	 * @return �����ʱ�䳤��
	 */
	public long getSoundLength()
	{
		return mSequencer.getMicrosecondLength()/1000;
	}
	/**
	 * ֹͣ����
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
