package com.example.p207;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText et;
    TextView textView2;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show("onCreate");
        Log.d("[TEST]","Oncreate");
        button = findViewById(R.id.button);
        et = findViewById(R.id.et);
        textView2 = findViewById(R.id.textView2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                str = et.getText().toString();
//                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "LandScape", Toast.LENGTH_SHORT).show();
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "Portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        show("onStart");
        Log.d("[TEST]","Onstart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        show("onStop");
        Log.d("[TEST]","Onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        show("onDestroy");
    }

    public void show(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}