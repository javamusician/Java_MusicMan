package wh.MusicWorks.Replay.DatabaseOperator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * @author ÍõºÆ
 * @version 1.0
 */
public class DatabaseOperator{
	static Context mContext;
	static SQLiteDatabase mSqLiteDatabase;
	static final String DATABASE_NAME="MusicMan";
	static final String TABLE_NAME="MusicMan";
	static final String PRIMARY_KEY_AUTOINCREMENT="Id Integer Primary Key Autoincrement";
	static final String[] COLUMN_LIST={"Data","Blob"};
	
	
	public static void initialize(Context context) {
		mContext=context;
		
		openOrCreateDatabase();
		createTableIfNotExists();
	}

	private static void openOrCreateDatabase() {
		mSqLiteDatabase=mContext.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
	}

	private static void createTableIfNotExists() {
		String createTableCommand="Create Table "+TABLE_NAME+" ("+PRIMARY_KEY_AUTOINCREMENT;
		
		for (int i=0;i<COLUMN_LIST.length/2;i++)
			createTableCommand+=","+COLUMN_LIST[i*2]+" "+COLUMN_LIST[i*2+1];
		
		createTableCommand+=")";
		try{
			mSqLiteDatabase.execSQL(createTableCommand);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void insertData(Object[] object){
		for (int i=0;i<COLUMN_LIST.length/2;i++)
		{
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try {
				oos=new ObjectOutputStream(bos);
				oos.writeObject(object[i]);
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ContentValues contentValues=new ContentValues();
			contentValues.put(COLUMN_LIST[COLUMN_LIST.length-2],bos.toByteArray());
			mSqLiteDatabase.insert(TABLE_NAME, "Id", contentValues);
		}
	}
	
	public static List<Object> getData()
	{
		List<Object> objectList=new ArrayList<Object>();
		Cursor mCursor = mSqLiteDatabase.query(true, TABLE_NAME, new String[]{"Id","Data"},null, null,null, null, null, null);
		while (mCursor.moveToNext()){
			ByteArrayInputStream in=new ByteArrayInputStream(mCursor.getBlob(1));
			ObjectInputStream ois;
			try {
				ois=new ObjectInputStream(in);
				try {
					objectList.add(ois.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return objectList;
	}
	
	public static void deleteDataById(int id){
		mSqLiteDatabase.delete(TABLE_NAME, "Id="+id, null);
	}
	
	public static void destroy()
	{
		mSqLiteDatabase.close();
	}
}