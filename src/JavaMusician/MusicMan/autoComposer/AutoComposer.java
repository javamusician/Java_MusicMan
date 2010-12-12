package JavaMusician.MusicMan.autoComposer;

import java.util.ArrayList;
import javax.sound.midi.Sequence;

/**
 * 自动谱曲
 * @author ANDE
 *
 */
abstract public class AutoComposer {
	
	//节拍，默认为Sequence.PPQ
	float mRhythm = Sequence.PPQ;

	//每拍里面的拍数，默认为5
	int mInsideRhythm = 5;
	
	//乐器类型，默认为0
	int mInstrument = 0;
	
	//音乐长度，默认为0
	int mMusicLength = 0;
	
	// 时间轴中的高潮部分，初始化为0（即是没有高潮）
	int mHighTide = 0;
	
	// 乐曲的平缓和急促的程度，范围是0~31，默认为0
	int mMildToWild = 0;
	
	//音符（里面包含用户插入的焦点音符）
	ArrayList<Integer> mFocusNote = new ArrayList<Integer>();
	
	//音符的力度（对应于音符）
	ArrayList<Integer> mNoteStrength = new ArrayList<Integer>();
	
	/**
	 * precondition: mFocusNote以及mNoteStrength必须已经设置好长度并且已经包含了用户插入的焦点音符
	 * @param rhythm
	 * @param insideRhythm
	 * @param instrument
	 * @param length
	 * @param focusNote
	 * @param noteStrength
	 */
	public void iniParameter(int rhythm, int insideRhythm, int instrument, int length, int highTide, int mildToWild)
	{
		initializeRhythm(rhythm);
		initializeInsideRhythm(insideRhythm);
		initializeInstrument(instrument);
		initializeMusicLength(length);
		initializeHighTide(highTide);
		initializeMildToWid(mildToWild);
		initializeFocusNote();
		initializeNoteStrength();
	}

	private void initializeNoteStrength() {
		ArrayList<Integer> noteStrength = new ArrayList<Integer>();
		for(int index=0 ; index<mMusicLength ; ++index){
			noteStrength.add(index, 0);
		}
		setNoteStrength(noteStrength);
	}

	private void initializeFocusNote() {
		ArrayList<Integer> focusNote = new ArrayList<Integer>();
		for(int index=0 ; index<mMusicLength ; ++index){
			focusNote.add(index, 0);
		}
		setFocusNote(focusNote);
	}

	private void initializeMildToWid(int mildToWild) {
		setMildToWild(mildToWild);
	}

	private void initializeHighTide(int highTide) {
		setHighTide(highTide);
	}

	private void initializeMusicLength(int length) {
		setMusicLength(length);
	}

	private void initializeInstrument(int instrument) {
		setInstrument(instrument);
	}

	private void initializeInsideRhythm(int insideRhythm) {
		setInsideRhythm(insideRhythm);
	}

	private void initializeRhythm(int rhythm) {
		setRhythm(rhythm);
	}

	/**
	 * 
	 * @param noteStrength
	 */
	private void setNoteStrength(ArrayList<Integer> noteStrength) {
		//设定音符的力度
		mNoteStrength = noteStrength;
		mNoteStrength.add(0, 0);
		mNoteStrength.add(mNoteStrength.size(), 0);
	}

	/**
	 * 
	 * @param focusNote
	 */
	private void setFocusNote(ArrayList<Integer> focusNote) {
		//设定焦点音符
		mFocusNote = focusNote;
		mFocusNote.add(0, 0);
		mFocusNote.add(mFocusNote.size(), 0);
	}

	/**
	 * 
	 * @param mildToWild
	 */
	private void setMildToWild(int mildToWild) {
		//设定乐曲的平缓和急促的程度
		mMildToWild = mildToWild;
	}

	/**
	 * 
	 * @param highTide
	 */
	private void setHighTide(int highTide) {
		//设定时间轴中的高潮部分
		mHighTide = highTide;
	}

	/**
	 * 
	 * @param length
	 */
	private void setMusicLength(int length) {
		//设定音乐长度
		mMusicLength = length;
	}

	/**
	 * 
	 * @param instrument
	 */
	private void setInstrument(int instrument) {
		//设定乐器类型
		mInstrument = instrument;
	}

	/**
	 * 
	 * @param insideRhythm
	 */
	private void setInsideRhythm(int insideRhythm) {
		//设定每拍里面的拍数
		mInsideRhythm = insideRhythm;
	}

	/**
	 * 
	 * @param rhythm
	 */
	private void setRhythm(int rhythm) {
		//设定节拍
		switch(rhythm){
		case 0: 
			mRhythm = Sequence.PPQ; 
			break;
		case 1:
			mRhythm = Sequence.SMPTE_24; 
			break;
		case 2:
			mRhythm = Sequence.SMPTE_25; 
			break;
		case 3:
			mRhythm = Sequence.SMPTE_30; 
			break;
		case 4:
			mRhythm = Sequence.SMPTE_30DROP; 
			break;
		}
	}
	
	public float getRhythm() {
		return mRhythm;
	}

	public int getInsideRhythm() {
		return mInsideRhythm;
	}

	public int getInstrument() {
		return mInstrument;
	}

	public int getMusicLength() {
		return mMusicLength;
	}

	public int getHighTide() {
		return mHighTide;
	}

	public int getMildToWild() {
		return mMildToWild;
	}

	public ArrayList<Integer> getFocusNote() {
		return mFocusNote;
	}

	public ArrayList<Integer> getNoteStrength() {
		return mNoteStrength;
	}
	
	public void generateMusic(){
		fillUsingProbability();
		autoComposing();
	}
	
	/**
	 * 子类需要实现的填充mFocusNote以及mNoteStrength的方法
	 * precondition: mFocusNote以及mNoteStrength必须已经设置好长度并且已经包含了用户插入的焦点音符
	 */
	abstract protected void fillUsingProbability();
	
	/**
	 * 子类需要实现自动谱曲算法
	 */
	abstract public void autoComposing();
}
