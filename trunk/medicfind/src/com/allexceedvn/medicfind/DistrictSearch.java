package com.allexceedvn.medicfind;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DistrictSearch extends ActionBarActivity {

    private MySQLiteDatasource datasource;
    private ListView listView;
    private DistrictAdapter districtAdapter;
    private ArrayList<District> arrDistricts;
    private int cityCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_search);

        Bundle b = getIntent().getExtras();
        cityCd = b.getInt("cityCd");

        datasource = new MySQLiteDatasource(this);

        try {
            if (!datasource.isTableExists(MySQLiteHelper.TABLE_NAME_DISTRICT)) {
                copyData();
            }
        } catch (IOException e) {
            Log.d("IOException.", e.toString());
        }

        listView = (ListView) findViewById(R.id.lvDistricts);

//        arrDistricts = datasource.getAllSong(0);
        arrDistricts = datasource.getAllDistrict(0, cityCd);
        districtAdapter = new DistrictAdapter(this, R.layout.item_layout_district, arrDistricts);
        listView.setAdapter(districtAdapter);

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
        
		AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View container,
					int position, long id) {
				// Getting the Container Layout of the ListView
				LinearLayout linearLayoutParent = (LinearLayout) container;

				/* get cityname */
				// // Getting the inner Linear Layout
				// LinearLayout linearLayoutChild = (LinearLayout )
				// linearLayoutParent.getChildAt(1);
				//
				// // Getting the Country TextView
				// TextView tvCountry = (TextView)
				// linearLayoutChild.getChildAt(0);

				/* get id */
				TextView tvId = (TextView) linearLayoutParent.getChildAt(0);

				String text1 = tvId.getText().toString();

				Toast.makeText(getBaseContext(), text1, Toast.LENGTH_SHORT)
						.show();
				Intent intent;
				Bundle b = new Bundle();
				b.putInt("cityCd", cityCd);
				b.putInt("districtCd", Integer.valueOf(text1));
				b.putInt("type", Integer.valueOf(1));
				intent = new Intent(getApplicationContext(),
						DetailSearch.class);
				intent.putExtras(b); // Put your id to your next Intent
				startActivity(intent);
			}
		};

		// Setting the item click listener for the listview
		listView.setOnItemClickListener(itemClickListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_district_search, menu);
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
                    arrDistricts = datasource.getAllDistrict(0, cityCd);
                    //cityAdapter.notifyDataSetChanged();
                    districtAdapter = new DistrictAdapter(DistrictSearch.this, R.layout.item_layout_district, arrDistricts);
                    listView.setAdapter(districtAdapter);
                    return false;
                }
                if (keyword.length() == 1) {
                    return false;
                }

                arrDistricts.clear();
                arrDistricts = datasource.getDistrictByName(cityCd, keyword);
                //cityAdapter.notifyDataSetChanged();
                districtAdapter = new DistrictAdapter(DistrictSearch.this, R.layout.item_layout_district, arrDistricts);
                listView.setAdapter(districtAdapter);
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

        //noinspection SimplifiableIfStatement
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
