package JavaMusician.MusicMan.MediaPlayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public interface MediaPlayer {
	public void play() throws MidiUnavailableException, InvalidMidiDataException;
}
