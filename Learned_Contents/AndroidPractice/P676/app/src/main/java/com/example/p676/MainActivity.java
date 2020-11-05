package com.example.p676;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap gMap;

    TextView textView;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        getSupportActionBar().hide();

        String[] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, permission,101);

        supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                    return;
                }

                gMap = googleMap;
                gMap.setMyLocationEnabled(true);
                LatLng latlng = new LatLng(37.447196, 126.452927);
                gMap.addMarker(new MarkerOptions().position(latlng).
                        title("공항").
                        snippet("xxx")
                        );
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
            }
        });

        MyLocation myLocation = new MyLocation();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1, // 몇 초에 한 번씩 정보를 받을 것인지
                0, //이동거리 얼마당 한 번씩 정보를 받을 것인지
                myLocation
        );

    } // End onCreate

    class MyLocation implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            textView.setText(lat+" "+lng);
            LatLng latlng = new LatLng(lat,lng);
//            gMap.addMarker(new MarkerOptions().position(latlng).
//                    title("My Point").
//                    snippet("xxx")
//            );
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        if(gMap != null){
            gMap.setMyLocationEnabled(false); //suppressLint로 Permission Check 생략
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(gMap != null){
            gMap.setMyLocationEnabled(true); //suppressLint로 Permission Check 생략
        }
    }
}