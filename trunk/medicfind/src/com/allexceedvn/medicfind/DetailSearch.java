package com.allexceedvn.medicfind;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

public class DetailSearch extends ActionBarActivity {

	private MySQLiteDatasource datasource;
    private ListView listView;
    private DetailAdapter detailAdapter;
    private ArrayList<Detail> arrDetails;
    private int cityCd;
    private int districtCd;
    private int type;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_search);
		
		Bundle b = getIntent().getExtras();
        cityCd = b.getInt("cityCd");
        districtCd = b.getInt("districtCd");
        type = b.getInt("type");

        datasource = new MySQLiteDatasource(this);

        try {
            if (!datasource.isTableExists(MySQLiteHelper.TABLE_NAME_DETAIL)) {
                copyData();
            }
        } catch (IOException e) {
            Log.d("IOException.", e.toString());
        }

        listView = (ListView) findViewById(R.id.lvDetails);

//        arrDistricts = datasource.getAllSong(0);
        arrDetails = datasource.getAllDetails(0, cityCd, districtCd, type);
        detailAdapter = new DetailAdapter(this, R.layout.item_layout_detail, arrDetails);
        listView.setAdapter(detailAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
//                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
//                if (loadMore) {
//                    ArrayList<District> tempDistricts = datasource.getAllDistrict(totalItemCount, cityCd);
//                    arrDistricts.addAll(tempDistricts);
//                    cityAdapter.notifyDataSetChanged();
//                }
            }
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_search, menu);
		 MenuItem itemSearch = (MenuItem) menu.findItem(R.id.action_search);
	        SearchView searchView = (SearchView) MenuItemCompat
	                .getActionView(itemSearch);

	        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
	            @Override
	            public boolean onQueryTextSubmit(String arg0) {
	                return false;
	            }

	            @Override
	            public boolean onQueryTextChange(String keyword) {
	                if (keyword.length() == 0) {
	                    arrDetails = datasource.getAllDetails(0, cityCd, districtCd, type);
	                    //cityAdapter.notifyDataSetChanged();
	                    detailAdapter = new DetailAdapter(DetailSearch.this, R.layout.item_layout_detail, arrDetails);
	                    listView.setAdapter(detailAdapter);
	                    return false;
	                }
	                if (keyword.length() == 1) {
	                    return false;
	                }

	                arrDetails.clear();
	                arrDetails = datasource.getDetailsByName(districtCd, type, cityCd, keyword);
	                //cityAdapter.notifyDataSetChanged();
	                detailAdapter = new DetailAdapter(DetailSearch.this, R.layout.item_layout_detail, arrDetails);
	                listView.setAdapter(detailAdapter);
	                return false;
	            }
	        });
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void copyData() throws IOException {
        String DB_NAME = MySQLiteHelper.DATABASE_NAME;
        int length;

        InputStream myInput = getAssets().open(DB_NAME);

        String outFileName = getFilesDir().getParent() + "/databases/"
                + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

	/**
	 * Calculates the end-point from a given source at a given range (meters)
	 * and bearing (degrees). This methods uses simple geometry equations to
	 * calculate the end-point.
	 * 
	 * @param point
	 *            Point of origin
	 * @param range
	 *            Range in meters
	 * @param bearing
	 *            Bearing in degrees
	 * @return End-point from the source given the desired range and bearing.
	 */
	public static PointF calculateDerivedPosition(PointF point, double range,
			double bearing) {
		double EarthRadius = 6371000; // m

		double latA = Math.toRadians(point.x);
		double lonA = Math.toRadians(point.y);
		double angularDistance = range / EarthRadius;
		double trueCourse = Math.toRadians(bearing);

		double lat = Math.asin(Math.sin(latA) * Math.cos(angularDistance)
				+ Math.cos(latA) * Math.sin(angularDistance)
				* Math.cos(trueCourse));

		double dlon = Math.atan2(
				Math.sin(trueCourse) * Math.sin(angularDistance)
						* Math.cos(latA),
				Math.cos(angularDistance) - Math.sin(latA) * Math.sin(lat));

		double lon = ((lonA + dlon + Math.PI) % (Math.PI * 2)) - Math.PI;

		lat = Math.toDegrees(lat);
		lon = Math.toDegrees(lon);

		PointF newPoint = new PointF((float) lat, (float) lon);

		return newPoint;

	}

	public static double getDistanceBetweenTwoPoints(PointF p1, PointF p2) {
		double R = 6371000; // m
		double dLat = Math.toRadians(p2.x - p1.x);
		double dLon = Math.toRadians(p2.y - p1.y);
		double lat1 = Math.toRadians(p1.x);
		double lat2 = Math.toRadians(p2.x);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;

		return d;
	}

	public static boolean pointIsInCircle(PointF pointForCheck, PointF center,
			double radius) {
		if (getDistanceBetweenTwoPoints(pointForCheck, center) <= radius)
			return true;
		else
			return false;
	}

}
