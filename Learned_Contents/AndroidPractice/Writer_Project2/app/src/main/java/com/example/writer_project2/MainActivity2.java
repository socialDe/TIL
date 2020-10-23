package com.example.writer_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.writer_project2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity2 extends AppCompatActivity {
    Button write, read;
    ReadFragment readFragment;
    WriteFragment writeFragment;
    TextView contents;
    HttpAsync httpAsync;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        //Buttons
        write = findViewById(R.id.write);
        read = findViewById(R.id.read);

        //TextView(주제어)
        contents = findViewById(R.id.contents);

        //Fragments
        writeFragment = new WriteFragment();
        readFragment = new ReadFragment();

        //서버에서 주제를 받아 Set Text
        String url = "http://192.168.10.100/writerAndroid/title.jsp";
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

        //초기 화면에서 write fragment가 호출되므로, 버튼 색상처리
        write.setBackgroundColor(Color.RED);



        // 인터넷 네트워크 정보 받아 Toast 생성
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo mobile = null;
                NetworkInfo wifi = null;

                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    cm = (ConnectivityManager) context.getSystemService(
                            Context.CONNECTIVITY_SERVICE);
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    System.out.println("네트워크 변경");

                    if(mobile != null && mobile.isConnected()){
                        Toast.makeText(context,"셀룰러 데이터로 접속합니다.",Toast.LENGTH_LONG).show();
                    }else if(wifi != null && wifi.isConnected()){
                        Toast.makeText(context,"Wifi 접속입니다.",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"인터넷 연결이 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);


        //FCM 수신
        FirebaseMessaging.getInstance().subscribeToTopic("writer").addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "FCM Complete ...";
                        if(!task.isSuccessful()){
                            msg = "FCM Fail";
                        }
                        Log.d("[TAG]:",msg);
                    }
                }
        );

        //App안에서 날아오는 Broadcast를 받을 준비
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("notification"));

    }// end onCreate


    // FCM 푸시서비스 BroadcastReciver
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null) {
                String title = intent.getStringExtra("title");
                String data = intent.getStringExtra("data");


                // Notification 처리
                NotificationManager manager;
                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = null;
                if (Build.VERSION.SDK_INT >= 26) {
                    if (manager.getNotificationChannel("ch1") == null) {
                        manager.createNotificationChannel(new NotificationChannel(
                                "ch1", "chname", NotificationManager.IMPORTANCE_DEFAULT));
                    }
                    builder = new NotificationCompat.Builder(MainActivity2.this, "ch1");
                } else {
                    builder = new NotificationCompat.Builder(MainActivity2.this);
                }
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        MainActivity2.this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                builder.setContentTitle(title);
                builder.setSmallIcon(R.drawable.p4);
                Notification noti = builder.build();
                manager.notify(1, noti);
            }
        }
    };

    public void ckbt(View v){

        if(v.getId() == R.id.write){
            write.setBackgroundColor(Color.RED);
            read.setBackgroundColor(Color.BLUE);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, writeFragment).commit();
            System.out.println(R.id.fragment);
            System.out.println(writeFragment);
        }
        else if(v.getId() == R.id.read){
            write.setBackgroundColor(Color.BLUE);
            read.setBackgroundColor(Color.RED);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment, readFragment).commit();
            System.out.println(R.id.fragment);
            System.out.println(readFragment);
        }
    }

    class HttpAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            contents.setText(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}