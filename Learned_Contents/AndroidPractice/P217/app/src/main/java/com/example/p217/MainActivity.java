package com.example.p217;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);
    }

    public void clickb1(View v){
        Toast t = Toast.makeText(this,"Toast1...",Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,500,300);
        t.show();
    }
    public void clickb2(View v){

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout));
        TextView tv = view.findViewById(R.id.textView);
        tv.setText("INPUT TEXT");


        Toast t = new Toast(this);
        t.setGravity(Gravity.CENTER,0,0);
        t.setDuration(Toast.LENGTH_LONG);
        t.setView(view);
        t.show();

    }
    public void clickb3(View v){
        Snackbar.make(v,"Snack Bar",Snackbar.LENGTH_LONG).show();
    }
    public void clickb4(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Dialog");
        builder.setMessage("Are You Exit Now");
        builder.setIcon(R.drawable.d1);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
            int pdata =progressBar.getProgress();
            if(pdata < 10){
                progressBar.setProgress(pdata+1);
            }else{

            }

        }else if(v.getId() == R.id.button6){
            int pdata =progressBar.getProgress();
            progressBar.setProgress(pdata-1);
        }else if(v.getId() == R.id.button7){
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Downloading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }else if(v.getId() == R.id.button8){
            progressDialog.dismiss();
        }
    }
    
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast,
                (ViewGroup)findViewById(R.id.toast_layout));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Dialog");
        builder.setMessage("Message");
        builder.setView(view);
        builder.setIcon(R.drawable.d1);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
}