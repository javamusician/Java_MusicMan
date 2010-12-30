package JavaMusician.MusicMan.autoComposer;

import java.util.ArrayList;
import java.util.Random;

public class SmoothComposer extends AutoComposer {
	int mRadius = 200;
	
	/**
	 * precondition: 时间轴中的高潮部分合法（即是不越界）
	 * 这里的填充函数不再需要预先插入焦点音符
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
		// 填充低潮部分的后半部分
		for(int index=mHighTide+radius+1 ; index<=mMusicLength ; ++index){
			// 产生低潮的力度
			int lowTideStrength = 32+random.nextInt(32)*(random.nextInt(2)==0 ? -1 : 1);		
			// 将低潮的力度插入mNoteStrength中
			mNoteStrength.set(index, lowTideStrength);		
			// 产生低潮的对应音符
			int lowTideNote = random.nextInt(128);		
			// 将低潮的对应音符插入到mFocusNote中
			mFocusNote.set(index, lowTideNote);
		}
	}


	private void generateUpperLowTidePart(int radius, Random random) {
		// 填充低潮部分的前半部分
		for(int index=1 ; index<mHighTide-radius ; ++index){
			// 产生低潮的力度
			int lowTideStrength = 32+random.nextInt(32)*(random.nextInt(2)==0 ? -1 : 1);	
			// 将低潮的力度插入mNoteStrength中
			mNoteStrength.set(index, lowTideStrength);	
			// 产生低潮的对应音符
			int lowTideNote = random.nextInt(128);
			// 将低潮的对应音符插入到mFocusNote中
			mFocusNote.set(index, lowTideNote);
		}
	}


	private void generateHighTidePart(int radius) {
		Random random = new Random();
		// 填充高潮部分
		for(int index=mHighTide-radius ; index<=mHighTide+radius ; ++index){
			// 产生低潮的力度
			int highTideStrength = 96+random.nextInt(32)*(random.nextInt(2)==0 ? -1 : 1);
			// 将低潮的力度插入mNoteStrength中
			mNoteStrength.set(index, highTideStrength);
			// 产生低潮的对应音符
			int highTideNote = random.nextInt(128);
			// 将低潮的对应音符插入到mFocusNote中
			mFocusNote.set(index, highTideNote);
		}
	}


	private int getCorrectRadius() {
		int radius = mRadius;
		// 如果高潮部分已经越界，则减小高潮部分的半径
		while(mHighTide-radius<0 || mHighTide+radius>mMusicLength){
			--radius;
		}
		return radius;
	}

	
	/**
	 * 自动谱曲函数主要对音符进行均衡化
	 */
	@Override
	public void autoComposing() {
		ArrayList<Integer> noteNum = calculateAmountOfNote();
		equalizationNote(noteNum);
	}


	private void equalizationNote(ArrayList<Integer> noteNum) {
		// 统计完毕，开始进行均衡化
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
		// 首先需要统计每种音符的数量
		ArrayList<Integer> noteNum = initializeNoteNumList();
		// 开始统计每种音符的数量
		for(int index=1 ; index<=mMusicLength ; ++index){
			int num = noteNum.get(mFocusNote.get(index));
			++num;
			noteNum.set(mFocusNote.get(index), num);
		}
		return noteNum;
	}

	
	private ArrayList<Integer> initializeNoteNumList() {
		ArrayList<Integer> noteNum = new ArrayList<Integer>();
		// 初始化noteNum
		for(int index=0 ; index<128 ; ++index){
			noteNum.add(0);
		}
		return noteNum;
	}
}
