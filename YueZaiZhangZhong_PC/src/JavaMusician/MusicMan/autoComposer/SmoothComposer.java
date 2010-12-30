package JavaMusician.MusicMan.autoComposer;

import java.util.ArrayList;
import java.util.Random;

public class SmoothComposer extends AutoComposer {
	int mRadius = 200;
	
	/**
	 * precondition: ʱ�����еĸ߳����ֺϷ������ǲ�Խ�磩
	 * �������亯��������ҪԤ�Ȳ��뽹������
	 */
	@Override
	protected void fillUsingProbability() {
		int radius = getCorrectRadius();
		generateLowTidePart(radius);
		generateHighTidePart(radius);
	}


	private void generateLowTidePart(int radius) {
		Random random = new Random();
		generateUpperLowTidePart(radius, random);
		generateLowerLowTidePart(radius, random);
	}


	private void generateLowerLowTidePart(int radius, Random random) {
		// ���ͳ����ֵĺ�벿��
		for(int index=mHighTide+radius+1 ; index<=mMusicLength ; ++index){
			// �����ͳ�������
			int lowTideStrength = 32+random.nextInt(32)*(random.nextInt(2)==0 ? -1 : 1);		
			// ���ͳ������Ȳ���mNoteStrength��
			mNoteStrength.set(index, lowTideStrength);		
			// �����ͳ��Ķ�Ӧ����
			int lowTideNote = random.nextInt(128);		
			// ���ͳ��Ķ�Ӧ�������뵽mFocusNote��
			mFocusNote.set(index, lowTideNote);
		}
	}


	private void generateUpperLowTidePart(int radius, Random random) {
		// ���ͳ����ֵ�ǰ�벿��
		for(int index=1 ; index<mHighTide-radius ; ++index){
			// �����ͳ�������
			int lowTideStrength = 32+random.nextInt(32)*(random.nextInt(2)==0 ? -1 : 1);	
			// ���ͳ������Ȳ���mNoteStrength��
			mNoteStrength.set(index, lowTideStrength);	
			// �����ͳ��Ķ�Ӧ����
			int lowTideNote = random.nextInt(128);
			// ���ͳ��Ķ�Ӧ�������뵽mFocusNote��
			mFocusNote.set(index, lowTideNote);
		}
	}


	private void generateHighTidePart(int radius) {
		Random random = new Random();
		// ���߳�����
		for(int index=mHighTide-radius ; index<=mHighTide+radius ; ++index){
			// �����ͳ�������
			int highTideStrength = 96+random.nextInt(32)*(random.nextInt(2)==0 ? -1 : 1);
			// ���ͳ������Ȳ���mNoteStrength��
			mNoteStrength.set(index, highTideStrength);
			// �����ͳ��Ķ�Ӧ����
			int highTideNote = random.nextInt(128);
			// ���ͳ��Ķ�Ӧ�������뵽mFocusNote��
			mFocusNote.set(index, highTideNote);
		}
	}


	private int getCorrectRadius() {
		int radius = mRadius;
		// ����߳������Ѿ�Խ�磬���С�߳����ֵİ뾶
		while(mHighTide-radius<0 || mHighTide+radius>mMusicLength){
			--radius;
		}
		return radius;
	}

	
	/**
	 * �Զ�����������Ҫ���������о��⻯
	 */
	@Override
	public void autoComposing() {
		ArrayList<Integer> noteNum = calculateAmountOfNote();
		equalizationNote(noteNum);
	}


	private void equalizationNote(ArrayList<Integer> noteNum) {
		// ͳ����ϣ���ʼ���о��⻯
		for(int index1=0 ; index1<128 ; ++index1){
			int num = 0;
			for(int index2=0 ; index2<=index1 ; ++index2){
				num += noteNum.get(index2);
			}
			int note = (int)(127.0*((double)num/(double)mMusicLength));
			mFocusNote.set(index1, note);
		}
	}


	private ArrayList<Integer> calculateAmountOfNote() {
		// ������Ҫͳ��ÿ������������
		ArrayList<Integer> noteNum = initializeNoteNumList();
		// ��ʼͳ��ÿ������������
		for(int index=1 ; index<=mMusicLength ; ++index){
			int num = noteNum.get(mFocusNote.get(index));
			++num;
			noteNum.set(mFocusNote.get(index), num);
		}
		return noteNum;
	}

	
	private ArrayList<Integer> initializeNoteNumList() {
		ArrayList<Integer> noteNum = new ArrayList<Integer>();
		// ��ʼ��noteNum
		for(int index=0 ; index<128 ; ++index){
			noteNum.add(0);
		}
		return noteNum;
	}
}
