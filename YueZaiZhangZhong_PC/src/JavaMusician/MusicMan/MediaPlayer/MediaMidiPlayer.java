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
	// �Զ��������ʵ��
	AutoComposer mAutoComposer = null;
	
	// midi���й�����
	Sequencer mMidi = null;
	
	// midi����
	Sequence mSeq = null;
	
	// ����
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
		//����
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
		// ��ȡ����
		mTrack = mSeq.createTrack();
		setMidiTone();
		setMidiNoteAndStrength();
	}

	private void setMidiNoteAndStrength() throws InvalidMidiDataException {
		// ��mFocusNote��mNoteStrength����midi��
		for(int index=1 ; index<=mAutoComposer.getMusicLength() ; ++index){
			ShortMessage shortMessageSound = new ShortMessage();
			shortMessageSound.setMessage(0x90, mAutoComposer.getFocusNote().get(index), mAutoComposer.getNoteStrength().get(index));
			MidiEvent midiEvent2=new MidiEvent(shortMessageSound, index);
			mTrack.add(midiEvent2);
		}
	}

	private void setMidiTone() throws InvalidMidiDataException {
		// ������ɫ
		ShortMessage shortMessageInstrument = new ShortMessage();
		shortMessageInstrument.setMessage(0xc0, mAutoComposer.getInstrument(), 0);
		MidiEvent midiEvent1 = new MidiEvent(shortMessageInstrument, 0);
		mTrack.add(midiEvent1);
	}

	private void initializeSequence() throws MidiUnavailableException,
			InvalidMidiDataException {
		// ��ȡ���й�����
		mMidi = MidiSystem.getSequencer();
		// ��������
		mSeq = new Sequence(mAutoComposer.getRhythm(), mAutoComposer.getInsideRhythm());
	}
}
