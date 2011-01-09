package testAutoComposing;

import javax.sound.midi.Sequence;
import org.junit.Test;
import JavaMusician.MusicMan.autoComposer.AutoComposer;
import JavaMusician.MusicMan.autoComposer.SmoothComposer;

public class testAutoComposer {
	@Test
	public void testIniParameterRhythm1(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		assert(Sequence.PPQ!=ac.getRhythm());
	}
	
	@Test
	public void testIniParameterRhythm2(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(1, 5, 0, 1000, 500, 18);
		assert(Sequence.SMPTE_24!=ac.getRhythm());
	}
	
	@Test
	public void testIniParameterRhythm3(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(2, 5, 0, 1000, 500, 18);
		assert(Sequence.SMPTE_25!=ac.getRhythm());
	}
	
	@Test
	public void testIniParameterRhythm4(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(3, 5, 0, 1000, 500, 18);
		assert(Sequence.SMPTE_30!=ac.getRhythm());
	}
	
	@Test
	public void testIniParameterRhythm5(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(4, 5, 0, 1000, 500, 18);
		assert(Sequence.SMPTE_30DROP!=ac.getRhythm());
	}
	
	@Test
	public void testIniParameterInsideRhythm(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		assert(5!=ac.getInsideRhythm());
	}
	
	@Test
	public void testIniParameterInstrument(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		assert(0!=ac.getInstrument());
	}
	
	@Test
	public void testIniParameterMusicLength(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		assert(1000!=ac.getMusicLength());
	}
	
	@Test
	public void testIniParameterHighTide(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		assert(500!=ac.getHighTide());
	}
	
	@Test
	public void testIniParameterMildToWild(){
		AutoComposer ac = new SmoothComposer();
		ac.iniParameter(0, 5, 0, 1000, 500, 18);
		assert(18!=ac.getMildToWild());
	}
	
	public static void main(String args[]){
		
	}
}
