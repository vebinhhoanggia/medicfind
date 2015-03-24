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

//	public ArrayList<Baihat> getAllSong(int start) {
//		ArrayList<Baihat> songList = new ArrayList<Baihat>();
//
//		String[] columns = { MySQLiteHelper.COL_ID,
//				MySQLiteHelper.COL_SONGNAME, MySQLiteHelper.COL_LYRIC };
//		String orderBy = MySQLiteHelper.COL_ID + " ASC";
//		String limit = start + "," + LIMIT;
//
//		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,
//				null, null, null, null, orderBy, limit);
//
//		if (cursor.moveToFirst()) {
//			while (!cursor.isAfterLast()) {
//				int id = cursor.getInt(cursor
//						.getColumnIndex(MySQLiteHelper.COL_ID));
//				String songName = cursor.getString(cursor
//						.getColumnIndex(MySQLiteHelper.COL_SONGNAME));
//				String lyric = cursor.getString(cursor
//						.getColumnIndex(MySQLiteHelper.COL_LYRIC));
//
//				Baihat baihat = new Baihat(id, songName, lyric);
//				songList.add(baihat);
//
//				cursor.moveToNext();
//			}
//		}
//
//		return songList;
//	}

//	public ArrayList<Baihat> getLimitSong() {
//		ArrayList<Baihat> songList = new ArrayList<Baihat>();
//
//		String[] columns = { MySQLiteHelper.COL_ID,
//				MySQLiteHelper.COL_SONGNAME, MySQLiteHelper.COL_LYRIC };
//		String orderBy = MySQLiteHelper.COL_ID + " ASC";
//
//		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,
//				null, null, null, null, orderBy, null);
//
//		if (cursor.moveToPosition(startAdd)) {
//			for (int i = 0; i < LIMIT && !cursor.isAfterLast(); i++) {
//				int id = cursor.getInt(cursor
//						.getColumnIndex(MySQLiteHelper.COL_ID));
//				String songName = cursor.getString(cursor
//						.getColumnIndex(MySQLiteHelper.COL_SONGNAME));
//				String lyric = cursor.getString(cursor
//						.getColumnIndex(MySQLiteHelper.COL_LYRIC));
//
//				Baihat baihat = new Baihat(id, songName, lyric);
//				songList.add(baihat);
//
//				cursor.moveToNext();
//				startAdd++;
//			}
//		}
//
//		return songList;
//	}

//	public ArrayList<Baihat> getSongByName(String keyWord) {
//		ArrayList<Baihat> baihatList = new ArrayList<Baihat>();
//
//		String[] columns = { MySQLiteHelper.COL_ID,
//				MySQLiteHelper.COL_SONGNAME, MySQLiteHelper.COL_LYRIC };
//		String selection = MySQLiteHelper.COL_SONGNAME2 + " Like ?";
//		String[] selectionArgs = { keyWord + "%" };
//
//		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, columns,
//				selection, selectionArgs, null, null, null);
//
//		if (cursor.moveToFirst()) {
//			while (!cursor.isAfterLast()) {
//				int id = cursor.getInt(cursor
//						.getColumnIndex(MySQLiteHelper.COL_ID));
//				String songName = cursor.getString(cursor
//						.getColumnIndex(MySQLiteHelper.COL_SONGNAME));
//				String lyric = cursor.getString(cursor
//						.getColumnIndex(MySQLiteHelper.COL_LYRIC));
//
//				Baihat baihat = new Baihat(id, songName, lyric);
//				baihatList.add(baihat);
//
//				cursor.moveToNext();
//			}
//		}
//
//		return baihatList;
//	}

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
                String cityName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_CITY_NAME));

                City city = new City(id, cityName);
                songList.add(city);

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
        ArrayList<District> districtList = new ArrayList<District>();

        String[] columns = { MySQLiteHelper.COL_DISTRICT_CITY_ID,
                MySQLiteHelper.COL_DISTRICT_ID, MySQLiteHelper.COL_DISTRICT_NAME };
        String orderBy = MySQLiteHelper.COL_DISTRICT_ID + " ASC";
        String selection = MySQLiteHelper.COL_DISTRICT_CITY_ID + " = ?";
        String[] selectionArgs = { String.valueOf(cityCd) };

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_DISTRICT, columns,
                selection, selectionArgs, null, null, orderBy, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DISTRICT_ID));
                String districtName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DISTRICT_NAME));

                District district = new District(cityCd, id, districtName);
                districtList.add(district);

                cursor.moveToNext();
            }
        }

        return districtList;
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

    /**
     * getAllDetails
     * 
     * @param start
     * @param cityCd
     * @param districtCd
     * @param type
     * @return
     */
	public ArrayList<Detail> getAllDetails(int start, int cityCd,
			int districtCd, int type) {
        ArrayList<Detail> districtList = new ArrayList<Detail>();

		String[] columns = { MySQLiteHelper.COL_DETAIL_CITY_ID,
				MySQLiteHelper.COL_DETAIL_DISTRICT_ID,
				MySQLiteHelper.COL_DETAIL_ID, MySQLiteHelper.COL_DETAIL_NAME,
				MySQLiteHelper.COL_DETAIL_TYPE,
				MySQLiteHelper.COL_DETAIL_ADDRESS,
				MySQLiteHelper.COL_DETAIL_LATITUDE,
				MySQLiteHelper.COL_DETAIL_LONGITUDE };

		String orderBy = MySQLiteHelper.COL_DISTRICT_ID + " ASC" + ", ";
		String selection = MySQLiteHelper.COL_DETAIL_CITY_ID + " = ? ";
		selection += " AND " + MySQLiteHelper.COL_DETAIL_DISTRICT_ID + " = ? ";
		selection += " AND " + MySQLiteHelper.COL_DETAIL_TYPE + " = ? ";
		String[] selectionArgs = { String.valueOf(cityCd),
				String.valueOf(districtCd), String.valueOf(type) };

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_DETAIL,
				columns, selection, selectionArgs, null, null, orderBy, null);

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor
						.getColumnIndex(MySQLiteHelper.COL_DETAIL_ID));
				String districtName = cursor.getString(cursor
						.getColumnIndex(MySQLiteHelper.COL_DETAIL_NAME));

				Detail district = new Detail(type, cityCd, districtCd, id,
						districtName);
				districtList.add(district);

				cursor.moveToNext();
			}
		}
//		float orig_lat = 0;
//		float orig_lon = 0;
//		float dist = 10;
//		String sql = "";
//		// sql += MySQLiteHelper.COL_DETAIL_CITY_ID;
//		// sql += MySQLiteHelper.COL_DETAIL_DISTRICT_ID;
//		// sql += MySQLiteHelper.COL_DETAIL_ID;
//		// sql += MySQLiteHelper.COL_DETAIL_NAME;
//		// sql += MySQLiteHelper.COL_DETAIL_TYPE;
//		// sql += MySQLiteHelper.COL_DETAIL_ADDRESS;
//		// sql += MySQLiteHelper.COL_DETAIL_LATITUDE;
//		// sql += MySQLiteHelper.COL_DETAIL_LONGITUDE;
//		sql += "" + "SELECT *" + " , 3956 * 2 * ASIN(SQRT(" + "POWER(SIN(("
//				+ orig_lat + " - abs(dest."
//				+ MySQLiteHelper.COL_DETAIL_LATITUDE
//				+ ")) * pi()/180 / 2), 2) +  COS(" + orig_lat
//				+ " * pi()/180 ) * COS(abs(dest."
//				+ MySQLiteHelper.COL_DETAIL_LATITUDE
//				+ ") * pi()/180) *  POWER(SIN((" + orig_lon + " - dest."
//				+ MySQLiteHelper.COL_DETAIL_LONGITUDE
//				+ ") * pi()/180 / 2), 2) )) as  distance" + "FROM"
//				+ MySQLiteHelper.TABLE_NAME_DETAIL + " dest "
//				+ "having distance < " + dist + " ORDER BY distance limit"
//				+ " 10 ";
//		database.rawQuery(sql, null);
//		
//		
//		Cursor cursor = database.rawQuery(sql, null);
//
//		if (cursor.moveToFirst()) {
//			while (!cursor.isAfterLast()) {
//				int id = cursor.getInt(cursor
//                        .getColumnIndex(MySQLiteHelper.COL_DETAIL_ID));
//                String districtName = cursor.getString(cursor
//                        .getColumnIndex(MySQLiteHelper.COL_DETAIL_NAME));
//
//                Detail district = new Detail(type, cityCd, districtCd, id, districtName);
//                districtList.add(district);
//
//                cursor.moveToNext();
//            }
//        }
				
        return districtList;
    }

	/**
	 * getDetailsByName
	 * 
	 * @param districtCd
	 * @param type
	 * @param cityCd
	 * @param keyWord
	 * @return
	 */
	public ArrayList<Detail> getDetailsByName(int districtCd, int type,
			int cityCd, String keyWord) {
        ArrayList<Detail> cityList = new ArrayList<Detail>();

        String[] columns = { MySQLiteHelper.COL_DISTRICT_CITY_ID,
                MySQLiteHelper.COL_DISTRICT_ID, MySQLiteHelper.COL_DISTRICT_NAME };
        String selection = MySQLiteHelper.COL_DISTRICT_CITY_ID + " = ?"
                +" AND " + MySQLiteHelper.COL_DISTRICT_NAME + " Like ?";
        String[] selectionArgs = { String.valueOf(cityCd),keyWord + "%" };

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_DETAIL, columns,
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DETAIL_ID));
                String districtName = cursor.getString(cursor
                        .getColumnIndex(MySQLiteHelper.COL_DETAIL_NAME));

                Detail city = new Detail(type, cityCd, districtCd, id, districtName);
                cityList.add(city);

                cursor.moveToNext();
            }
        }

        return cityList;
    }
}
