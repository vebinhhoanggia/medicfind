package com.allexceedvn.medicfind;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


public class MainActivity extends ActionBarActivity {

    private Button btnSearch;
    private String typeSearch = "";
    private RadioGroup radioGroupTypeSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check Login Data
        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", 0); // 0 - for private mode
        typeSearch = pref.getString("typeSearch", "0");


        radioGroupTypeSearch = (RadioGroup) findViewById(R.id.rdiGroup);
        if (typeSearch.equals("1")) {
            radioGroupTypeSearch.check(R.id.rdiOffline);
        } else if (typeSearch.equals("0")) {
            radioGroupTypeSearch.check(R.id.rdiOnline);
        }
        radioGroupTypeSearch
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rdiOffline:
                                typeSearch = "1";
                                break;
                            case R.id.rdiOnline:
                                typeSearch = "0";
                                break;
                            default:
                                break;
                        }

                    }
                });

        btnSearch = (Button) findViewById(R.id.btnGo);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get data from EditText
//                name = txtName.getText().toString();

                // Check if user filled the form
                if (typeSearch.trim().length() > 0) {
                    // Launch Main Activity
                    Intent i = null;
                    if("1".equals(typeSearch)) { // Offline
                        i = new Intent(getApplicationContext(),
                                SearchOffline.class);
                    } else if("0".equals(typeSearch)){ // Online
                        i = new Intent(getApplicationContext(),
                                SearchOnline.class);
                    }

                    // Registering user on our server
                    // Sending registration details to MainActivity
//                    i.putExtra("name", name);
                    i.putExtra("typeSearch", typeSearch);
                    startActivity(i);
                    //finish();

                } else {
                    showAlertDialog(MainActivity.this,
                            "Start Error!", "Please choose type",
                            false);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Storing Login Data
        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        // Set params
        editor.putString("typeSearch", typeSearch);
        // Commit the edits!
        editor.commit();
    }

    //Function to display simple Alert Dialog
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Set Dialog Title
        alertDialog.setTitle(title);

        // Set Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Set alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Set OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Show Alert Message
        alertDialog.show();
    }

}
