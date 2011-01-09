package JavaMusician.MusicMan.Runnable;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import JavaMusician.MusicMan.MediaPlayer.MediaMidiPlayer;
import JavaMusician.MusicMan.MediaPlayer.MediaPlayer;
import JavaMusician.MusicMan.autoComposer.AutoComposer;
import JavaMusician.MusicMan.autoComposer.SmoothComposer;

public class AutoComposerRunnable implements Runnable{
	
	private AutoComposer ac;

	public AutoComposerRunnable(int rhythm, int insideRhythm, int instrument, int length, int highTide, int mildToWild) {
		ac = new SmoothComposer();
		ac.iniParameter(rhythm, insideRhythm, instrument, length, highTide, mildToWild);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ac.generateMusic();
		MediaPlayer mp = new MediaMidiPlayer(ac);
		try {
			mp.play();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}