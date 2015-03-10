package com.allexceedvn.medicfind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
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


public class SearchOffline extends ActionBarActivity {

    private MySQLiteDatasource datasource;
    private ListView listView;
    private CityAdapter cityAdapter;
    private ArrayList<City> arrCitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offline);

        datasource = new MySQLiteDatasource(this);

        try {
            if (!datasource.isTableExists(MySQLiteHelper.TABLE_NAME_CITY)) {
                copyData();
            }
        } catch (IOException e) {
            Log.d("Sang", e.toString());
        }

        listView = (ListView) findViewById(R.id.lvSongs);
        listView.setItemsCanFocus(true);

//        arrCitys = datasource.getAllSong(0);
        arrCitys = datasource.getAllCity(0);
        cityAdapter = new CityAdapter(this, R.layout.item_layout_city, arrCitys);
        listView.setAdapter(cityAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
//                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
//                if (loadMore) {
//                    ArrayList<City> tempSongs = datasource.getAllCity(totalItemCount);
//                    arrCitys.addAll(tempSongs);
//                    cityAdapter.notifyDataSetChanged();
//                }
            }
        });

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // Getting the Container Layout of the ListView
                LinearLayout linearLayoutParent = (LinearLayout) container;

                /* get cityname*/
//                // Getting the inner Linear Layout
//                LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);
//
//                // Getting the Country TextView
//                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);

                /* get id*/
                TextView tvId = (TextView) linearLayoutParent.getChildAt(0);

                String text1 =  tvId.getText().toString();

                Toast.makeText(getBaseContext(), text1, Toast.LENGTH_SHORT).show();
                Intent intent;
                Bundle b = new Bundle();
                b.putInt("cityCd", Integer.valueOf(text1)); //Your id
                intent = new Intent(getApplicationContext(),
                        DistrictSearch.class);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        };

        // Setting the item click listener for the listview
        listView.setOnItemClickListener(itemClickListener);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_offline, menu);
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
                    arrCitys = datasource.getAllCity(0);
                    //cityAdapter.notifyDataSetChanged();
                    cityAdapter = new CityAdapter(SearchOffline.this, R.layout.item_layout_city, arrCitys);
                    listView.setAdapter(cityAdapter);
                    return false;
                }
                if (keyword.length() == 1) {
                    return false;
                }

                arrCitys.clear();
                arrCitys = datasource.getCityByName(keyword);
                //cityAdapter.notifyDataSetChanged();
                cityAdapter = new CityAdapter(SearchOffline.this, R.layout.item_layout_city, arrCitys);
                listView.setAdapter(cityAdapter);
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
}
