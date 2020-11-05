package com.example.p475;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random r1, r2;
    TextView textView, textView2;
    Handler handler, handler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r1 = new Random();
        r2 = new Random();
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        handler = new MyHandler();
        handler2 = new MyHandler2();

        MyThread t = new MyThread();
        MyThread2 t2 = new MyThread2();
        t.start();
        t2.start();

    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for(int i= 0; i<=100; i++) {
                int km = r1.nextInt(200);
                System.out.println(km);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("tdata", km);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView.setText(data+"km");
        }
    }

    class MyThread2 extends Thread{
        @Override
        public void run() {
            for(int i= 0; i<=100; i++) {
                int rpm = r1.nextInt(1500);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message2 = handler2.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("tdata", rpm);
                message2.setData(bundle);
                handler2.sendMessage(message2);
            }
        }
    }
    class MyHandler2 extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView2.setText(data+"rpm");
        }
    }
}