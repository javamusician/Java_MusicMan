package JavaMusician.MusicMan.MediaPlayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import JavaMusician.MusicMan.autoComposer.AutoComposer;

public class MediaMidiPlayer implements MediaPlayer{
	// 自动谱曲类的实例
	AutoComposer mAutoComposer = null;
	
	// midi序列管理器
	Sequencer mMidi = null;
	
	// midi序列
	Sequence mSeq = null;
	
	// 音轨
	Track mTrack = null;
	
	public MediaMidiPlayer(AutoComposer autoComposer){
		mAutoComposer = autoComposer;
	}
	
	@Override
	public void play() throws MidiUnavailableException, InvalidMidiDataException{
		initializeMidi();
		playMidi();
	}

	private void playMidi() throws MidiUnavailableException,
			InvalidMidiDataException {
		//播放
		mMidi.open();
		mMidi.setSequence(mSeq);
		mMidi.setTempoInBPM(330);
		mMidi.start();
		while(mMidi.isRunning());
		mMidi.stop();
		mMidi.close();
	}

	private void initializeMidi() throws MidiUnavailableException,
			InvalidMidiDataException {
		initializeSequence();
		initializeTrack();
	}

	private void initializeTrack() throws InvalidMidiDataException {
		// 获取音轨
		mTrack = mSeq.createTrack();
		setMidiTone();
		setMidiNoteAndStrength();
	}

	private void setMidiNoteAndStrength() throws InvalidMidiDataException {
		// 将mFocusNote和mNoteStrength置入midi中
		for(int index=1 ; index<=mAutoComposer.getMusicLength() ; ++index){
			ShortMessage shortMessageSound = new ShortMessage();
			shortMessageSound.setMessage(0x90, mAutoComposer.getFocusNote().get(index), mAutoComposer.getNoteStrength().get(index));
			MidiEvent midiEvent2=new MidiEvent(shortMessageSound, index);
			mTrack.add(midiEvent2);
		}
	}

	private void setMidiTone() throws InvalidMidiDataException {
		// 设置音色
		ShortMessage shortMessageInstrument = new ShortMessage();
		shortMessageInstrument.setMessage(0xc0, mAutoComposer.getInstrument(), 0);
		MidiEvent midiEvent1 = new MidiEvent(shortMessageInstrument, 0);
		mTrack.add(midiEvent1);
	}

	private void initializeSequence() throws MidiUnavailableException,
			InvalidMidiDataException {
		// 获取序列管理器
		mMidi = MidiSystem.getSequencer();
		// 设置序列
		mSeq = new Sequence(mAutoComposer.getRhythm(), mAutoComposer.getInsideRhythm());
	}
}
