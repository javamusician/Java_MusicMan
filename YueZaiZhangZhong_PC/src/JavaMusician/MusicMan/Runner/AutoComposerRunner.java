package JavaMusician.MusicMan.Runner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import JavaMusician.MusicMan.MediaPlayer.MediaMidiPlayer;
import JavaMusician.MusicMan.MediaPlayer.MediaPlayer;
import JavaMusician.MusicMan.autoComposer.AutoComposer;
import JavaMusician.MusicMan.autoComposer.SmoothComposer;

public class AutoComposerRunner {
	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException{
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		ac.generateMusic();
		MediaPlayer mp = new MediaMidiPlayer(ac);
		mp.play();
	}
}