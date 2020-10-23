package com.example.writer_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.writer_project2.R;

// Login Activity
public class MainActivity extends AppCompatActivity {

    EditText tx_id,tx_pwd;
    HttpAsync httpAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);

    }

    public void ckbt(View view){
        if(view.getId() == R.id.login){
            String id = tx_id.getText().toString();
            String pwd = tx_pwd.getText().toString();
            String url = "http://192.168.10.100/writerAndroid/login.jsp";
            url += "?id="+id+"&pwd="+pwd;
            System.out.println(url);
            httpAsync = new HttpAsync();
            httpAsync.execute(url);
        }else if(view.getId() == R.id.register){
            // 회원가입 기능 추후 처리

        }
    }// end ckbt

    class HttpAsync extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Login ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            System.out.println(result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            String result = s.trim();
            if(result.equals("1")){
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }else{
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Login Failed");
                dialog.setMessage("Try Again");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


    }// end HttpAsync

}