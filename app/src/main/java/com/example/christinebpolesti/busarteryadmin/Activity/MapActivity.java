package com.example.christinebpolesti.busarteryadmin.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.Pojo.Driver;
import com.example.christinebpolesti.busarteryadmin.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;

    private String name, busNum, lat, lng, availableSeat, estimatedTime, currentLocation;

    private ArrayList<Buses> busInfoArray;
    private ArrayList<Driver> markerArray;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerDriver();
    }

    private void markerDriver() {
        markerArray = new ArrayList<>();
        getBusInfo();
//        Firebase ref = new Firebase(Config.FIREBASE_URL_DRIVER);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getChildrenCount() == 0) {
//                    markerInfo();
//                }
//                if (markerArray.size() > 0) {
//                    markerArray.clear();
//                    mMap.clear();
//                }
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    if (snapshot.child("availableSeat").exists() && snapshot.child("latitude").exists() && snapshot.child("longitude").exists()
//                            && snapshot.child("estimatedTime").exists() && snapshot.child("active").exists() && snapshot.child("active").getValue().equals("1")) {
//
//                        name = snapshot.child("driversName").getValue().toString().trim();
//                        busNum = snapshot.child("busNum").getValue().toString().trim();
//                        lat = snapshot.child("latitude").getValue().toString().trim();
//                        lng = snapshot.child("longitude").getValue().toString().trim();
//                        availableSeat = snapshot.child("availableSeat").getValue().toString().trim();
//                        estimatedTime = snapshot.child("estimatedTime").getValue().toString().trim();
//                        double latitude = Double.valueOf(lat);
//                        double longitude = Double.valueOf(lng);
//
//                        convertLatLong(latitude, longitude);
//
//                        for (int i = 0; i < busInfoArray.size(); i++) {
//                            if (busInfoArray.get(i).getBusNum().equals(busNum)) {
//                                markerArray.add(new Driver(name, busNum, busInfoArray.get(i).getRoute(),
//                                        (Integer.valueOf(busInfoArray.get(i).getCapacity()) - Integer.valueOf(availableSeat)),
//                                        latitude, longitude, currentLocation, estimatedTime));
//                            }
//                        }
//                    }
//                }
//
//                for (int i = 0; i < markerArray.size(); i++) {
//                    createMarker(markerArray.get(i).getDriversName(), markerArray.get(i).getBusNum(), markerArray.get(i).getRoute(), markerArray.get(i).getTotalPassenger(),
//                            markerArray.get(i).getLatitude(), markerArray.get(i).getLongitude(), markerArray.get(i).getLocation(), markerArray.get(i).getEstimatedTime());
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(MapActivity.this, "markerDriver: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void convertLatLong(double latitude, double longitude) {
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.get(0).getAddressLine(0) != null) {
                currentLocation = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality();
            } else {
                currentLocation = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getBusInfo() {
        busInfoArray = new ArrayList<>();
//        busRef = new Firebase(Config.FIREBASE_URL_BUSNUMBER);
//        busRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    busInfoArray.add(new Buses(snapshot.child("busNum").getValue().toString().trim(), snapshot.child("route").getValue().toString().trim(), snapshot.child("capacity").getValue().toString().trim()));
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(MapActivity.this, "getTotalPass: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    protected void createMarker(String driversName, final String busNum, String route, final int totalPass, double latitude, double longitude, String location, String estimatedTime) {
        marker = mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bus_marker))
                .position(new LatLng(latitude, longitude))
                .title(location)
                .snippet("Driver's Name: " + driversName + "\nBus number: " + busNum + "\nRoute: " + route + "\nTotal Passenger: " + totalPass + "\nEstimated time: " + estimatedTime));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                dialogInfo(marker);
                return true;
            }
        });
    }

    public void dialogInfo(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Current Location: " + marker.getTitle());
        builder.setMessage(marker.getSnippet())
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.veryLightBlue);
        alertDialog.show();
    }

    public void markerInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No bus available.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.veryLightBlue);
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng start = new LatLng(10.008154, 123.635460);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 9));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trackBus:
                Intent mIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, mIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(mIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, mIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}