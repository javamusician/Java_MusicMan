package testAutoComposing;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import JavaMusician.MusicMan.autoComposer.AutoComposer;
import JavaMusician.MusicMan.autoComposer.SmoothComposer;


public class testSmoothComposer {
	private AutoComposer mAutoComposer;
	
	private int findMinimun(ArrayList<Integer> array, int low, int high){
		int min = array.get(low);
		for(int index = low+1 ; index < high ; ++index){
			if(min > array.get(index)){
				min = array.get(index);
			}
		}
		return min;
	}
	
	private int findMaximun(ArrayList<Integer> array, int low, int high){
		int max = array.get(low);
		for(int index = low ; index < high ; ++index){
			if(max < array.get(index)){
				max = array.get(index);
			}
		}
		return max;
	}
	
	@Before 
	public void setUp(){ 
		mAutoComposer = new SmoothComposer();
		mAutoComposer.iniParameter(0, 5, 0, 1000, 500, 18);
		mAutoComposer.generateMusic();
	} 

	@After
	public void tearDown(){
		mAutoComposer = null;
	}

	@Test
	public void testFocusNoteFeature(){
		int lowerLowTidePartMax = findMaximun(mAutoComposer.getFocusNote(), 1, 299);
		int upperLowTidePartMax = findMaximun(mAutoComposer.getFocusNote(), 701, 1000);
		int highTidePart = findMinimun(mAutoComposer.getFocusNote(), 300, 700);
		assert(lowerLowTidePartMax > highTidePart);
		assert(upperLowTidePartMax > highTidePart);
	}
	
	@Test
	public void testNoteStrengthFeature(){
		int noteStrengthMax = findMaximun(mAutoComposer.getNoteStrength(), 1, 1000);
		int noteStrengthMin = findMinimun(mAutoComposer.getNoteStrength(), 1, 1000);
		assert(noteStrengthMin<0);
		assert(noteStrengthMax>127);
	}
}
