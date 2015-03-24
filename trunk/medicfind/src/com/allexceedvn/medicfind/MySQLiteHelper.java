package com.allexceedvn.medicfind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	

	public static final String DATABASE_NAME = "SongDB";
	public static final String TABLE_NAME = "song";

	 /*table song*/
	public static final String COL_ID = "_id";
	public static final String COL_SONGNAME = "song_name";
	public static final String COL_SONGNAME2 = "song_name2";
	public static final String COL_LYRIC = "song_lyric";

	 /*table city*/
    public static final String TABLE_NAME_CITY = "city";
    public static final String COL_CITY_ID = "city_cd";
    public static final String COL_CITY_NAME = "city_name";

    /*table district*/
    public static final String TABLE_NAME_DISTRICT = "district";
    public static final String COL_DISTRICT_CITY_ID = "city_cd";
    public static final String COL_DISTRICT_ID = "district_cd";
    public static final String COL_DISTRICT_NAME = "district_name";

    /*table detail*/
    public static final String TABLE_NAME_DETAIL = "detail";
    public static final String COL_DETAIL_TYPE = "type_search";
    public static final String COL_DETAIL_CITY_ID = "city_cd";
    public static final String COL_DETAIL_DISTRICT_ID = "district_cd";
    public static final String COL_DETAIL_ID = "_id";
    public static final String COL_DETAIL_NAME = "name";
    public static final String COL_DETAIL_ADDRESS = "address";
    public static final String COL_DETAIL_LATITUDE = "latitude";
    public static final String COL_DETAIL_LONGITUDE = "longitude";

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