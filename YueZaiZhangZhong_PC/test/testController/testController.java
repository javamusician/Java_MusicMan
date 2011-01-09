package testController;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import JavaMusician.MusicMan.Controller.Controller;

public class testController {
	private Controller controller = null;
	
	@Before
	public void setUp(){
		controller =new Controller();
	}
	
	@Test
	public void testKeyDown() {
		assertEquals("Key", 0, controller.getKey());
	}

	@Test
	public void testKeyUp() {
		assertEquals("Key", 0, controller.getKey());
	}

	@After
	public void tearDown(){
		controller = null;
	}
}
