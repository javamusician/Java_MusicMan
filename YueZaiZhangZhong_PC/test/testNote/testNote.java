package testNote;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import JavaMusician.MusicMan.Note.Note;

public class testNote {
	private Note note=null;
	@Before
	public void setUp(){
		note = new Note((long) 0, new byte[]{(byte) 0x90,60,127}, 
				new float[]{(float) 0.0,(float) 2.0,(float) 3.0,(float) 4.0,(float)5.0}, new int[]{0xc0,60});
	}

	@Test
	public void testGetState() {
		assertEquals("State", 0x90, note.getState());
	}

	@Test
	public void testGetNote() {
		assertEquals("Note", 60, note.getNote());
	}

	@Test
	public void testGetStrong() {
		assertEquals("Strong", 127, note.getStrong());
	}

	@Test
	public void testGetTick() {
		assertEquals("Tick",(long)0, note.getTick());
	}

	@Test
	public void testGetMusicParameters() {
		float[] tempMusicParameters = note.getMusicParameters().clone();
		assertArrayEquals(new float[]{(float) 0.0,(float) 2.0,(float) 3.0,(float) 4.0,(float)5.0}, tempMusicParameters, 0);
	}

	@Test
	public void testGetToneInfo() {
		int[] tempTone = note.getToneInfo().clone();
		assertEquals((long)0xc0, tempTone[0]);
		assertEquals((long)60, tempTone[1]);
	}
	
	@Test(expected=AssertionError.class)
	public void testGetToneInfoNotEqual() {
		int[] tempTone = note.getToneInfo().clone();
		assertEquals((long)0xc0, tempTone[0]);
		assertEquals((long)80, tempTone[1]);
	}
	
	@After
	public void tearDown(){
		note=null;
	}

}
