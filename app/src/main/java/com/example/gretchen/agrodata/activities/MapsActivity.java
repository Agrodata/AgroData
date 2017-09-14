package com.example.gretchen.agrodata.activities;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;
import com.google.android.gms.internal.zzccb;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location loca;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //LocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationServices.getFusedLocationProviderClient(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    public void getLastLocation() {

        //Need to fix this crap


        LocationRequest mLocationRequest = new LocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);


        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        if ((Build.VERSION.SDK_INT >= 23) &&
                //(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            return;
        }


        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {

                    Bundle bundle = getIntent().getExtras();
                    UserRepo repo = new UserRepo(MapsActivity.this);
                    user = repo.getUserById(bundle.getInt(getString(R.string.id_key)));
                    user.setLocation(Double.toString(location.getLatitude())+","+Double.toString(location.getLongitude()));
                    repo.update(user);
                    setPinInMap(location);
                    String locTex = location.toString();


                }

            }


        });




    }
    private void setPinInMap(Location location)
    {
        LatLng puertorico = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(puertorico).title("Puerto Rico"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(puertorico).zoom(12.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }
    private void setPinInMap(Double latitud, Double longitud)
    {
        LatLng puertorico = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(puertorico).title("Puerto Rico"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(puertorico).zoom(12.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();

        if(bundle.getInt(getString(R.string.location_key))==0){
            getLastLocation();
        }
        else
        {
            UserRepo repo = new UserRepo(this);
            user = repo.getUserById(bundle.getInt(getString(R.string.id_key)));

            String coords[]= user.getLocation().split(",");

            setPinInMap(Double.parseDouble(coords[0]),Double.parseDouble(coords[1]));

        }



    }
}