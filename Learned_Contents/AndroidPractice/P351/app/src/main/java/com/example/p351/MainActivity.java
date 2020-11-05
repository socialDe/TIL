package com.example.p351;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    FragmentSet fragmentSet;

    //ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragmentSet = new FragmentSet();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.m1){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragmentSet).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, fragment1).commit();
        }else if(v.getId() == R.id.button2){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, fragment2).commit();
        }else if(v.getId() == R.id.button3){
            Log.d("[TEST]", "clicked button3: ");
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, fragment3).commit();
        }
    }
}