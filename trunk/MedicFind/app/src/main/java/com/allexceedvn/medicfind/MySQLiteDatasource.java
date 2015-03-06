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


    public ArrayList<City> getAllCity(int start) {
        ArrayList<City> songList = new ArrayList<City>();

        String[] columns = { MySQLiteHelper.COL_CITY_ID,
                MySQLiteHelper.COL_CITY_NAME};
        String orderBy = MySQLiteHelper.COL_CITY_ID + " ASC";
        String limit = start + "," + LIMIT;

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_CITY, columns,
                null, null, null, null, orderBy, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MySQLiteHelper.COL_CITY_ID));
                String songName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_CITY_NAME));

                City baihat = new City(id, songName);
                songList.add(baihat);

                cursor.moveToNext();
            }
        }

        return songList;
    }

    public ArrayList<City> getCityByName(String keyWord) {
        ArrayList<City> cityList = new ArrayList<City>();

        String[] columns = { MySQLiteHelper.COL_CITY_ID,
                MySQLiteHelper.COL_CITY_NAME };
        String selection = MySQLiteHelper.COL_CITY_NAME + " Like ?";
        String[] selectionArgs = { keyWord + "%" };

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_CITY, columns,
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MySQLiteHelper.COL_CITY_ID));
                String cityName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_CITY_NAME));

                City city = new City(id, cityName);
                cityList.add(city);

                cursor.moveToNext();
            }
        }

        return cityList;
    }


    public ArrayList<District> getAllDistrict(int start, int cityCd) {
        ArrayList<District> songList = new ArrayList<District>();

        String[] columns = { MySQLiteHelper.COL_DISTRICT_CITY_ID,
                MySQLiteHelper.COL_DISTRICT_ID, MySQLiteHelper.COL_DISTRICT_NAME };
        String orderBy = MySQLiteHelper.COL_DISTRICT_ID + " ASC";
        String selection = MySQLiteHelper.COL_DISTRICT_CITY_ID + " = ?";
        String[] selectionArgs = { String.valueOf(cityCd) };

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_DISTRICT, columns,
                null, null, null, null, orderBy, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DISTRICT_ID));
                String songName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DISTRICT_NAME));

                District baihat = new District(cityCd, id, songName);
                songList.add(baihat);

                cursor.moveToNext();
            }
        }

        return songList;
    }

    public ArrayList<District> getDistrictByName(int cityCd, String keyWord) {
        ArrayList<District> cityList = new ArrayList<District>();

        String[] columns = { MySQLiteHelper.COL_DISTRICT_CITY_ID,
                MySQLiteHelper.COL_DISTRICT_ID, MySQLiteHelper.COL_DISTRICT_NAME };
        String selection = MySQLiteHelper.COL_DISTRICT_CITY_ID + " = ?"
                +" AND " + MySQLiteHelper.COL_DISTRICT_NAME + " Like ?";
        String[] selectionArgs = { String.valueOf(cityCd),keyWord + "%" };

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_DISTRICT, columns,
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DISTRICT_ID));
                String cityName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DISTRICT_NAME));

                District city = new District(cityCd, id, cityName);
                cityList.add(city);

                cursor.moveToNext();
            }
        }

        return cityList;
    }

}
