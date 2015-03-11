package com.allexceedvn.medicfind;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
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
}
