package com.allexceedvn.medicfind;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchOnline extends FragmentActivity implements LocationListener {

    private static final String GOOGLE_API_KEY = "AIzaSyD1PHJ-7V0jBoWcyl8Ti1TtO5JLijX-fJc";
    protected static final String TAG = "MedicFind";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double latitude = 0;
    private double longitude = 0;
    private int PROXIMITY_RADIUS = 5000;
    GPSTracker mGPS = null;
    private Spinner spinner;

    public final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }

        /**
         * Called when the provider status changes. This method is called when
         * a provider is unable to fetch a location or if the provider has recently
         * become available after a period of unavailability.
         *
         * @param provider the name of the location provider associated with this
         *                 update.
         * @param status   {@link android.location.LocationProvider#OUT_OF_SERVICE} if the
         *                 provider is out of service, and this is not expected to change in the
         *                 near future; {@link android.location.LocationProvider#TEMPORARILY_UNAVAILABLE} if
         *                 the provider is temporarily unavailable but is expected to be available
         *                 shortly; and {@link android.location.LocationProvider#AVAILABLE} if the
         *                 provider is currently available.
         * @param extras   an optional Bundle which will contain provider specific
         *                 status variables.
         *                 <p/>
         *                 <p> A number of common key/value pairs for the extras Bundle are listed
         *                 below. Providers that use any of the keys on this list must
         *                 provide the corresponding value as described below.
         *                 <p/>
         *                 <ul>
         *                 <li> satellites - the number of satellites used to derive the fix
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        /**
         * Called when the provider is enabled by the user.
         *
         * @param provider the name of the location provider associated with this
         *                 update.
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * Called when the provider is disabled by the user. If requestLocationUpdates
         * is called on an already disabled provider, this method is called
         * immediately.
         *
         * @param provider the name of the location provider associated with this
         *                 update.
         */
        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_online);
//        setUpMapIfNeeded();

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            mMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            mMap.setMyLocationEnabled(true);

            // Invoke LoaderCallbacks to retrieve and draw already saved locations in map
//            getSupportLoaderManager().initLoader(0, null, this);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Drawing marker on the map
                drawMarker(point);
//                Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
            }
        });


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

                // Removing all markers from the Google Map
                mMap.clear();

//                Toast.makeText(getBaseContext(), "All markers are removed", Toast.LENGTH_LONG).show();

            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("hospital");
        list.add("pharmacy");
        list.add("clinics");
        list.add("Shop Medical Instruments");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        Button btnFind = (Button) findViewById(R.id.btnFind);

        /* getLastKnownLocation */
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        /* set current location */
        mGPS = new GPSTracker(this);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            setCurrentLocation(mGPS);
        }else{
            System.out.println("Unable");
        }

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = String.valueOf(spinner.getSelectedItem());

                Log.e(TAG, type);
                if (mGPS.getLatitude() != 0 && mGPS.getLongitude() != 0) {
                    mMap.clear();
                    new GetPlaces(SearchOnline.this, type).execute();
                }
            }
        });

    }

    /**
     * drawMarker
     *
     * @param point
     */
    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }

    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link android.location.LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link android.location.LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link android.location.LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *                 <p/>
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *                 <p/>
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * GetPlaces
     */
    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

        private ProgressDialog dialog;
        private Context context;
        private String places;

        public GetPlaces(Context context, String places) {
            this.context = context;
            this.places = places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            for (int i = 0; i < result.size(); i++) {
                mMap.addMarker(new MarkerOptions()
                        .title(result.get(i).getName())
                        .position(
                                new LatLng(result.get(i).getLatitude(), result
                                        .get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.pin))
                        .snippet(result.get(i).getVicinity()));
            }
            if(result!= null && result.size() > 0) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(result.get(0).getLatitude(), result
                                .get(0).getLongitude())) // Sets the center of the map to
                                // Mountain View
                        .zoom(14) // Sets the zoom
                        .tilt(30) // Sets the tilt of the camera to 30 degrees
                        .build(); // Creates a CameraPosition from the builder

                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            } else {
                setCurrentLocation(mGPS);
                Toast.makeText(getBaseContext(), "No result", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
        }

        @Override
        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(GOOGLE_API_KEY);
            ArrayList<Place> findPlaces = service.findPlaces(mGPS.getLatitude(), // 28.632808
                    mGPS.getLongitude(), places); // 77.218276

            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                Log.e(TAG, "places : " + placeDetail.getName());
            }
            return findPlaces;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_online, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

    }

    /**
     * setCurrentLocation
     *
     * @param mGPS
     */
    private void setCurrentLocation(GPSTracker mGPS) {
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = mGPS.getLatitude();

        // Get longitude of the current location
        double longitude = mGPS.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));

    }

}
