package testMediaPlaying;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;
import JavaMusician.MusicMan.MediaPlayer.MediaMidiPlayer;
import JavaMusician.MusicMan.MediaPlayer.MediaPlayer;
import JavaMusician.MusicMan.autoComposer.AutoComposer;
import JavaMusician.MusicMan.autoComposer.SmoothComposer;


public class testMediaMidiPlayer {
	
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayFailed() throws MidiUnavailableException, InvalidMidiDataException{
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 128, 1000, 500, 18);
		ac.generateMusic();
		MediaPlayer mp = new MediaMidiPlayer(ac);
		mp.play();
	}
	
	@Test
	public void testPlaySuccess() throws MidiUnavailableException, InvalidMidiDataException{
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 127, 1000, 500, 18);
		ac.generateMusic();
		MediaPlayer mp = new MediaMidiPlayer(ac);
		mp.play();
	}
}
