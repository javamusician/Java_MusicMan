package testParse;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import JavaMusician.MusicMan.Parse.Parse;

public class testParse {
	private Parse parse=null;
	@Before
	public void setUp(){
		parse = new Parse(new File("res\\music\\test.mid"));
	}

	@Test
	public void testGetStart() {
		assertEquals("Start",false, parse.getStart());
	}

	@Test
	public void testGetResolution() {
		assertEquals("Resolution",192, parse.getResolution());
	}
	
	@After
	public void tearDown(){
		parse=null;
	}
}
