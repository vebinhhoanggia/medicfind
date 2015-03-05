package com.allexceedvn.medicfind;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MySQLiteDatasource {
	private SQLiteDatabase database;
	private static int startAdd = 0;
	private static final int LIMIT = 10;

	public MySQLiteDatasource(Context context) {
		MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context);
		database = mySQLiteHelper.getReadableDatabase();
	}

	public ArrayList<Baihat> getAllSong(int start) {
		ArrayList<Baihat> songList = new ArrayList<Baihat>();

		String[] columns = { MySQLiteHelper.COL_ID,
				MySQLiteHelper.COL_SONGNAME, MySQLiteHelper.COL_LYRIC };
		String orderBy = MySQLiteHelper.COL_ID + " ASC";
		String limit = start + "," + LIMIT;

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,
				null, null, null, null, orderBy, limit);

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor
						.getColumnIndex(MySQLiteHelper.COL_ID));
				String songName = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_SONGNAME));
				String lyric = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_LYRIC));

				Baihat baihat = new Baihat(id, songName, lyric);
				songList.add(baihat);

				cursor.moveToNext();
			}
		}

		return songList;
	}

	public ArrayList<Baihat> getLimitSong() {
		ArrayList<Baihat> songList = new ArrayList<Baihat>();

		String[] columns = { MySQLiteHelper.COL_ID,
				MySQLiteHelper.COL_SONGNAME, MySQLiteHelper.COL_LYRIC };
		String orderBy = MySQLiteHelper.COL_ID + " ASC";

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,
				null, null, null, null, orderBy, null);

		if (cursor.moveToPosition(startAdd)) {
			for (int i = 0; i < LIMIT && !cursor.isAfterLast(); i++) {
				int id = cursor.getInt(cursor
						.getColumnIndex(MySQLiteHelper.COL_ID));
				String songName = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_SONGNAME));
				String lyric = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_LYRIC));

				Baihat baihat = new Baihat(id, songName, lyric);
				songList.add(baihat);

				cursor.moveToNext();
				startAdd++;
			}
		}

		return songList;
	}

	public ArrayList<Baihat> getSongByName(String keyWord) {
		ArrayList<Baihat> baihatList = new ArrayList<Baihat>();

		String[] columns = { MySQLiteHelper.COL_ID,
				MySQLiteHelper.COL_SONGNAME, MySQLiteHelper.COL_LYRIC };
		String selection = MySQLiteHelper.COL_SONGNAME2 + " Like ?";
		String[] selectionArgs = { keyWord + "%" };

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,
				selection, selectionArgs, null, null, null);

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor
						.getColumnIndex(MySQLiteHelper.COL_ID));
				String songName = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_SONGNAME));
				String lyric = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_LYRIC));

				Baihat baihat = new Baihat(id, songName, lyric);
				baihatList.add(baihat);

				cursor.moveToNext();
			}
		}

		return baihatList;
	}

	public boolean isTableExists(String tableName) {
		try {
			Cursor cursor = database.rawQuery(
					"select DISTINCT tbl_name from sqlite_master where tbl_name = '"
							+ tableName + "'", null);
			if (cursor != null) {
				if (cursor.getCount() > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
