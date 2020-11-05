package com.example.p287;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    ActionBar actionBar;

    SupportMapFragment supportMapFragment;// fragment3 내부의 fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new Fragment1();
//        fragment1 = (Fragment1)getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Fragment");
        actionBar.setLogo(R.drawable.customer);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_USE_LOGO);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    return;
                }


            }

        });



        //actionBar.hide();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.m1){
            //fragment1.setTx("Receive From MainActivity");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment1).commit();
        }else if(item.getItemId() == R.id.m2){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment2).commit();
        }else if(item.getItemId() == R.id.m3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment3).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            getSupportFragmentManager().beginTransaction().replace(
                  R.id.fragment,fragment1).commit();
        }else if(v.getId() == R.id.button2){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment2).commit();
        }else if(v.getId() == R.id.button3){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment3).commit();
        }
    }
}