package com.example.p251;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] permissions ={
                Manifest.permission.CALL_PHONE
        };
        ActivityCompat.requestPermissions(this, permissions,101);

    }
    public void ckbt(View v){
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

        intent.putExtra("data",100);
        intent.putExtra("str","String Data");
        startActivity(intent);
    }
}