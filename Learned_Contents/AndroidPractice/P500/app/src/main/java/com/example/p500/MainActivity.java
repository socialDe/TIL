package com.example.p500;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tx_id, tx_pwd;
    HttpAsync httpAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);


    }
    public void ckbt(View v){
        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
        String url = "http://192.168.10.100/android/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
        //Toast.makeText(this, "id+pwd", Toast.LENGTH_SHORT).show();

    }

    class HttpAsync extends AsyncTask<String,String,String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Login....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s.equals("1")){
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Sorry...");
                builder.setMessage("Invalid ID or Password");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {

            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }
    }
}