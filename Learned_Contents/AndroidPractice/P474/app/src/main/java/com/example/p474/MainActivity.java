package com.example.p474;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar, progressBar2;
    TextView textView, textView2;
    Button button, button2, button3;
    MyHandler myHandler;
    MyHandler2 myHandler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar.setMax(50);
        progressBar2.setMax(50);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        myHandler = new MyHandler();
        myHandler2 = new MyHandler2();

    }

    public void ckbt(View v)  {
        if(v.getId() == R.id.button){
            MyThread t = new MyThread();
            t.start();  // 시간이 걸리는 동작 되는 와중에 아래 내용을 바로 실행
            button.setEnabled(false);
        }else if(v.getId() == R.id.button2){
            Thread t = new Thread(new MyThread2());
            t.start();
            button2.setEnabled(false);
        }else if(v.getId() == R.id.button3){
            progress();
        }
    }

    public void progress(){
        final ProgressDialog progressDialog = new ProgressDialog(this);

        AlertDialog.Builder dailog = new AlertDialog.Builder(this);
        dailog.setTitle("progress");
        dailog.setMessage("5 seconds");
        final Handler handler = new Handler();
        dailog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Downloading...");
                progressDialog.show();
                //스레드를 이용하여 5초 후에 progressDialog를 없엔다
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 5000);
            }
        });
        dailog.show();
    }




    // Thread t = new Thread(){}; 이렇게도 만들 수 있다
    class MyThread extends Thread{  // inner class 로 만든다
        @Override
        public void run() {
            for(int i=1; i<=50; i++){
                //스레드 안에서 조종
                progressBar.setProgress(i);
                textView.setText(i+"");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //runOnUiThread는 서브스레드에서 메인스레드의 특정 위젯을 변경 하고자 할 때!!
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(true);

                }
            });
        }
    };

    //핸들러는 서브스레드 안에서 동작되어지는 변경되어지는 상태를 메인스레드에게 알려준다
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView.setText("Handler1:"+data);
            progressBar.setProgress(data);
        }
    }

    class MyHandler2 extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView2.setText("Handler2:"+data);
            progressBar2.setProgress(data);
            if(data == 50){
                button2.setEnabled(true);
            }
        }
    }

    //Runnable이라는 인터페이스에서 상속받는다
    class MyThread2 implements Runnable{

        @Override
        public void run() {
            for(int i=1; i<=50; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //스레드에서 발생하는 데이터를 여러군대로 보낸다
                Message message = myHandler.obtainMessage();
                Message message2 = myHandler2.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("tdata",i);
                message.setData(bundle);
                message2.setData(bundle);
                //myHandler.sendMessage(message);
                myHandler2.sendMessage(message2);

            }
        }
    }


}