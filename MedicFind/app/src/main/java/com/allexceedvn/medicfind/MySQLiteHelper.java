package com.allexceedvn.medicfind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	

	public static final String DATABASE_NAME = "SongDB";
	public static final String TABLE_NAME = "song";

	public static final String COL_ID = "_id";
	public static final String COL_SONGNAME = "song_name";
	public static final String COL_SONGNAME2 = "song_name2";
	public static final String COL_LYRIC = "song_lyric";

    public static final String COL_CITY_ID = "song_lyric";
    public static final String COL_CITY_NAME = "song_lyric";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}