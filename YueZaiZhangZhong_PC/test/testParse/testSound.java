package testParse;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import JavaMusician.MusicMan.Note.Note;
import JavaMusician.MusicMan.Note.NoteTrackPosition;
import JavaMusician.MusicMan.Parse.Sound;

public class testSound {
	private Note note1;
	private Note note2;
	private ArrayList<Note> list;
	private NoteTrackPosition noteTrack;
	private Sound sound;

	@Before
	public void setUp(){
		note1 = new Note((long) 0, new byte[]{(byte) 0x90,60,127}, 
				new float[]{(float) 0.0,(float) 2.0,(float) 3.0,(float) 4.0,(float)5.0}, new int[]{0xc0,60});
		note2 = new Note((long) 10, new byte[]{(byte) 0x90,80,100}, 
				new float[]{(float) 0.0,(float) 2.0,(float) 3.0,(float) 4.0,(float)5.0}, new int[]{0xc0,60});
		list = new ArrayList<Note>();
		list.add(note1);
		list.add(note2);
		noteTrack = new NoteTrackPosition(list);
	}

	@Test
	public void testSoundCon() {
		sound = new Sound(noteTrack);
	}

	@Test
	public void testPlay() {
		sound = new Sound(noteTrack);
		sound.play();
	}
	
	@After
	public void tearDown(){
		sound = null;
	}

}
