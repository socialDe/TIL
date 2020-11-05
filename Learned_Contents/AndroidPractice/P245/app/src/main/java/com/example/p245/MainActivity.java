package com.example.p245;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout container;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);



    }

    public void bt(View v){
        if(v.getId() == R.id.button){
            container.removeAllViews();

            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.sub1, container,true);

            progressBar = findViewById(R.id.progressBar);
            progressBar.setMax(10);

        }else if(v.getId() == R.id.button2){
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.sub2, container,true);


        }else if(v.getId() == R.id.button3){
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.sub3, container,true);

        }else if(v.getId() == R.id.button4){
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.sub4, container,true);
        }
    }

    public  void btsignup(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원가입");
        builder.setMessage("회원가입 하시겠습니까?");
        builder.setIcon(R.drawable.d1);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public  void btmodify(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원 정보 수정");
        builder.setMessage("정보를 변경하시겠습니까?");
        builder.setIcon(R.drawable.d1);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void btprogress(View v){
        ProgressDialog progressDialog = null;
        if(v.getId() == R.id.button5){
            int pdata = progressBar.getProgress();
            if(pdata < 10){
                progressBar.setProgress(pdata+1);
            }
        }
    }
}
