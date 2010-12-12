package JavaMusician.MusicMan.autoComposer;

import java.util.ArrayList;
import javax.sound.midi.Sequence;

/**
 * �Զ�����
 * @author ANDE
 *
 */
abstract public class AutoComposer {
	
	//���ģ�Ĭ��ΪSequence.PPQ
	float mRhythm = Sequence.PPQ;

	//ÿ�������������Ĭ��Ϊ5
	int mInsideRhythm = 5;
	
	//�������ͣ�Ĭ��Ϊ0
	int mInstrument = 0;
	
	//���ֳ��ȣ�Ĭ��Ϊ0
	int mMusicLength = 0;
	
	// ʱ�����еĸ߳����֣���ʼ��Ϊ0������û�и߳���
	int mHighTide = 0;
	
	// ������ƽ���ͼ��ٵĳ̶ȣ���Χ��0~31��Ĭ��Ϊ0
	int mMildToWild = 0;
	
	//��������������û�����Ľ���������
	ArrayList<Integer> mFocusNote = new ArrayList<Integer>();
	
	//���������ȣ���Ӧ��������
	ArrayList<Integer> mNoteStrength = new ArrayList<Integer>();
	
	/**
	 * precondition: mFocusNote�Լ�mNoteStrength�����Ѿ����úó��Ȳ����Ѿ��������û�����Ľ�������
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
		//�趨����������
		mNoteStrength = noteStrength;
		mNoteStrength.add(0, 0);
		mNoteStrength.add(mNoteStrength.size(), 0);
	}

	/**
	 * 
	 * @param focusNote
	 */
	private void setFocusNote(ArrayList<Integer> focusNote) {
		//�趨��������
		mFocusNote = focusNote;
		mFocusNote.add(0, 0);
		mFocusNote.add(mFocusNote.size(), 0);
	}

	/**
	 * 
	 * @param mildToWild
	 */
	private void setMildToWild(int mildToWild) {
		//�趨������ƽ���ͼ��ٵĳ̶�
		mMildToWild = mildToWild;
	}

	/**
	 * 
	 * @param highTide
	 */
	private void setHighTide(int highTide) {
		//�趨ʱ�����еĸ߳�����
		mHighTide = highTide;
	}

	/**
	 * 
	 * @param length
	 */
	private void setMusicLength(int length) {
		//�趨���ֳ���
		mMusicLength = length;
	}

	/**
	 * 
	 * @param instrument
	 */
	private void setInstrument(int instrument) {
		//�趨��������
		mInstrument = instrument;
	}

	/**
	 * 
	 * @param insideRhythm
	 */
	private void setInsideRhythm(int insideRhythm) {
		//�趨ÿ�����������
		mInsideRhythm = insideRhythm;
	}

	/**
	 * 
	 * @param rhythm
	 */
	private void setRhythm(int rhythm) {
		//�趨����
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
	 * ������Ҫʵ�ֵ����mFocusNote�Լ�mNoteStrength�ķ���
	 * precondition: mFocusNote�Լ�mNoteStrength�����Ѿ����úó��Ȳ����Ѿ��������û�����Ľ�������
	 */
	abstract protected void fillUsingProbability();
	
	/**
	 * ������Ҫʵ���Զ������㷨
	 */
	abstract public void autoComposing();
}
