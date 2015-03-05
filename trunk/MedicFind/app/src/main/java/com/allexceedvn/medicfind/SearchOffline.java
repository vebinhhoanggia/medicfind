package com.allexceedvn.medicfind;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class SearchOffline extends ActionBarActivity {

    private MySQLiteDatasource datasource;
    private ListView listView;
    private BaihatAdapter baiHatAdapter;
    private ArrayList<Baihat> arrSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offline);

        datasource = new MySQLiteDatasource(this);

        try {
            if (!datasource.isTableExists(MySQLiteHelper.TABLE_NAME)) {
                copyData();
            }
        } catch (IOException e) {
            Log.d("Sang", e.toString());
        }

        listView = (ListView) findViewById(R.id.lvSongs);

        arrSongs = datasource.getAllSong(0);
        baiHatAdapter = new BaihatAdapter(this, R.layout.item_layout_city, arrSongs);
        listView.setAdapter(baiHatAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                if (loadMore) {
                    ArrayList<Baihat> tempSongs = datasource.getAllSong(totalItemCount);
                    arrSongs.addAll(tempSongs);
                    baiHatAdapter.notifyDataSetChanged();
                }
            }
        });
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
                    arrSongs = datasource.getAllSong(0);
                    //baiHatAdapter.notifyDataSetChanged();
                    baiHatAdapter = new BaihatAdapter(SearchOffline.this, R.layout.item_layout_city, arrSongs);
                    listView.setAdapter(baiHatAdapter);
                    return false;
                }
                if (keyword.length() == 1) {
                    return false;
                }

                arrSongs.clear();
                arrSongs = datasource.getSongByName(keyword);
                //baiHatAdapter.notifyDataSetChanged();
                baiHatAdapter = new BaihatAdapter(SearchOffline.this, R.layout.item_layout_city, arrSongs);
                listView.setAdapter(baiHatAdapter);
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
