package com.example.p667;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        String[] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        ActivityCompat.requestPermissions(this, permission,101);
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"Finish",Toast.LENGTH_SHORT).show();
            finish();
        }
        MyLocation myLocation = new MyLocation();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1, // 몇 초에 한 번씩 정보를 받을 것인지
                0, //이동거리 얼마당 한 번씩 정보를 받을 것인지
                myLocation

        );
    }

    public void ckbt(View v){
        startMyLocation();
    }
    public void startMyLocation(){
        Location location = null;
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"Finish",Toast.LENGTH_SHORT).show();
            finish();
        }
        location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        textView.setText(lat+" "+lon);
    }

    class MyLocation implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            textView.setText(lat+" "+lng);
        }
    }
}