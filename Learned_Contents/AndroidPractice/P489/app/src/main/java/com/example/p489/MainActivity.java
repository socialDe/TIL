package com.example.p489;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button button, button2;
    SeekBar seekBar;
    TextView textView;
    ImageView imageView;
    MyAsynch myAsynch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setEnabled(true);
        button2.setEnabled(false);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);

    }

    public void ckbt1(View v){
        myAsynch = new MyAsynch();
        myAsynch.execute(100);

    }
    public void ckbt2(View v){
        myAsynch.cancel(true);
        myAsynch.onCancelled();

    }
    class MyAsynch extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            button.setEnabled(false);
            button2.setEnabled(true);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int sum = 0;
            int a = integers[0].intValue();
            for(int i = 1; i<= a; i++){
                if(isCancelled()==true){
                    break;
                }
                sum+=i;
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "result:" + sum;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int i = values[0].intValue();
            seekBar.setProgress(i);
            if(i < 30){
                imageView.setImageResource(R.drawable.down);
            }else if(i<=70){
                imageView.setImageResource(R.drawable.middle);
            }else{
                imageView.setImageResource(R.drawable.up);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            button.setEnabled(true);
            button2.setEnabled(false);
            textView.setText(s);
        }

        @Override
        protected void onCancelled() {
            button.setEnabled(true);
            button2.setEnabled(false);
            seekBar.setProgress(0);
            textView.setText("취소되었습니다.");
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}