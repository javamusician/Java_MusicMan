package wh.MusicWorks.test;

import java.util.List;

import wh.MusicWorks.YueZaiZhangZhong;
import wh.MusicWorks.Replay.DatabaseOperator.DatabaseOperator;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class TestDatabaseOperator extends ActivityInstrumentationTestCase2<YueZaiZhangZhong> {
	YueZaiZhangZhong mYueZaiZhangZhong;
	
	public TestDatabaseOperator() {
		super("wh.MusicWorks", YueZaiZhangZhong.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();
		mYueZaiZhangZhong=getActivity();
		DatabaseOperator.initialize(mYueZaiZhangZhong);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		DatabaseOperator.destroy();
	}

	public final void testMultipleInsertData() {
		String[] strings=new String[]{"a"};
		DatabaseOperator.insertData(strings);
		List<Object> stringsList=DatabaseOperator.getData();
		checkAreSameStrings(strings,stringsList);
	}

	private void checkAreSameStrings(String[] strings, List<Object> stringsList) {
		// TODO Auto-generated method stub
		boolean areTheSame=true;
		assertEquals(strings.length, stringsList.size());
		if(strings.length==stringsList.size())
		{
			for (int i=0;i<strings.length && areTheSame;i++)
			{
				assertEquals(strings[i], stringsList.get(i));
				if (strings[i].equals(stringsList.get(i)))
					areTheSame=false;
			}
		}
		else
			areTheSame=false;
		assertTrue(areTheSame);
	}
}
