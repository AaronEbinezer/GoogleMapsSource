package com.eagleemperor.googlemapssource;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MapActivity";
    private static final String Fine_Location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String Coarse_Location = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int Location_Permission_Request_Code = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS=new LatLngBounds(new LatLng(-40,-168),new LatLng(71,136));

    AutoCompleteTextView search_Text;

    private boolean permissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo placeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        search_Text= (AutoCompleteTextView) findViewById(R.id.search_text);

        checkPermission();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Connection failed",Toast.LENGTH_SHORT).show();
    }

    private void init()
    {
        Log.d(TAG,"init : method");


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        search_Text.setOnItemClickListener(mAutoCompleteListener);
        placeAutocompleteAdapter =new PlaceAutocompleteAdapter(this,mGoogleApiClient,LAT_LNG_BOUNDS,null);
        search_Text.setAdapter(placeAutocompleteAdapter);
        search_Text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId== EditorInfo.IME_ACTION_SEARCH || actionId==EditorInfo.IME_ACTION_DONE
                        || event.getAction()==KeyEvent.ACTION_DOWN
                        || event.getAction()==KeyEvent.KEYCODE_ENTER)
                {
                    geolocate();
                }

                return false;
            }
        });

        
    }

    private void geolocate()
    {
        Log.d(TAG,"geolocate ");

        Geocoder geocoder=new Geocoder(MapActivity.this);
        String search_String=search_Text.getText().toString();

        List<Address> list=new ArrayList<>();
        try
        {
            list=geocoder.getFromLocationName(search_String,1);

        }catch(Exception e)
        {
            Log.d(TAG,"Geolocate error "+e);
        }
        if(list.size()>0)
        {
            Address address=list.get(0);
            Log.d(TAG,"Geolocate "+address);

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));

        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation : getting the device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (permissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Getting Current Location in GetDeviceLocation");
                            Location currentLocation = (Location) task.getResult();
                            String title="My Location";
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM,title);
                        } else {
                            Toast.makeText(MapActivity.this, "Unable to get Current Location ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDevice Current Location :" + e.getMessage());
        }
    }
    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "Move Camera : Lat :" + latLng.latitude + " Long :" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();

        if(!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().title(title).position(latLng);
            mMap.addMarker(options);
        }
    }

    //getMapAsync() method is called to call the below method.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (permissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);//Blue mark in the map
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }

    public void initMap(){
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void checkPermission()
    {
        String[] permissions={Fine_Location,Coarse_Location};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Fine_Location)== PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Coarse_Location)==PackageManager.PERMISSION_GRANTED)
            {
                permissionGranted=true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this,permissions,Location_Permission_Request_Code);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions, Location_Permission_Request_Code);
        }
    }
    //When ActivityCompat.requestPermissions() is called. The override method onRequestPermissionsResult will be called.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionGranted=false;
        switch (requestCode)
        {
            case Location_Permission_Request_Code:
            {
                if(grantResults.length>0)
                {
                    for(int i=0;i<grantResults.length;i++)
                    {
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        {
                            permissionGranted=false;
                            return;
                        }
                    }
                    permissionGranted=true;
                    initMap();
                }
            }
        }
    }

    private AdapterView.OnItemClickListener mAutoCompleteListener=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item=placeAutocompleteAdapter.getItem(position);
            final String placeId=item.getPlaceId();

            PendingResult<PlaceBuffer> pendingResult=Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
            pendingResult.setResultCallback(updatePendingResultsCallBack);
        }
    };

    private ResultCallback<PlaceBuffer> updatePendingResultsCallBack=new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess())
            {
                Log.d(TAG,"onResult query cannot be completed "+places.getStatus().toString());
                places.release();
                return;
            }
            try
            {
                final Place place=places.get(0);
                placeInfo=new PlaceInfo(place.getName().toString(),place.getAddress().toString(),place.getLatLng());
                Log.d(TAG,"onResult place details "+placeInfo.toString());
            }catch (Exception e)
            {
                Log.d(TAG,"onResult Error "+e);
            }
            places.release();
            moveCamera(placeInfo.getLatlng(),DEFAULT_ZOOM,placeInfo.getName());
        }
    };

}
