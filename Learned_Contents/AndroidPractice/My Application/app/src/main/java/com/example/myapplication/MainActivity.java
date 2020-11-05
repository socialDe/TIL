package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView himg;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        himg = findViewById(R.id.himg);
        button2 = findViewById(R.id.button2);
    }

    public void clickBt(View view){
//        himg.setVisibility(View.INVISIBLE);
        button2.setText(R.string.bt_test);
        Log.d("[Test]","-----------------");
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }
//    public void clickBt2(View view){
//        Intent intent = new Intent(intent.ACTION_VIEW,
//                Uri.parse("http://m.naver.com"));
//
//        startActivity(intent);
//    }
//    public void clickBt3(View view){
//        Intent intent = new Intent(intent.ACTION_VIEW,
//                Uri.parse("tel:010-9878-9838"));
//        startActivity(intent);
//
//    }

    public void clickBts(View view){
        if(view.getId()==R.id.button2){
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://m.naver.com"));
        }else if(view.getId() == R.id.button3){
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("tel:010-9878-9838"));
        }else if(view.getId() == R.id.button4) {
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:010-9878-9838"));
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }
    }
}