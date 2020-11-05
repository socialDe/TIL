package com.example.p675;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportMapFragment = (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap = googleMap;
                        LatLng latlng = new LatLng(37.447196, 126.452927);
                        googleMap.addMarker(new MarkerOptions().position(latlng).
                                title("공항").
                                snippet("xxx").
                                icon(BitmapDescriptorFactory.fromResource(R.drawable.incheon)));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));

            }
        });
    }
    public void ckbt(View v){ //39.044403, 125.753292 평양개선문 이동
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latlng = new LatLng(39.044403, 125.753292);
                googleMap.addMarker(new MarkerOptions().position(latlng).title("평양개선문").
                        snippet("xxx").
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.archtriumph)));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
            }
        });
    }
    public void ckbt2(View v){ //37.858353, -122.482794 소살리토 이동
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latlng = new LatLng(37.858353, -122.482794);
                googleMap.addMarker(new MarkerOptions().position(latlng).title("소살리토").
                        snippet("xxx").
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.sausalito)));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,15));
            }
        });
    }
}