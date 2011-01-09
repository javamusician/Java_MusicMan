package testNote;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import JavaMusician.MusicMan.Note.Note;
import JavaMusician.MusicMan.Note.NoteTrackPosition;

public class testNoteTrackPosition {
	private Note note1 = null;
	private Note note2 = null;
	private ArrayList<Note> list = null;
	private NoteTrackPosition noteTrack = null;
	
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
	public void testGetPosition() {
		assertTrue("Position", noteTrack.getPosition()!=0);
	}

	@Test
	public void testGetTick() {
		assertEquals("Tick",0, noteTrack.getTick());
	}
	
	@After
	public void tearDown(){
		note1 = null;
		note2 = null;
		list = null;
		noteTrack = null;
	}

}
