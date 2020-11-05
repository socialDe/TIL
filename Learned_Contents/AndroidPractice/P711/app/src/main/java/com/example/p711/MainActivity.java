package com.example.p711;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    TextView tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        FirebaseMessaging.getInstance().subscribeToTopic("car").addOnCompleteListener(
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
        tx = findViewById(R.id.tx);
        //App안에서 날아오는 Broadcast를 받을 준비
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("notification"));
    } // end onCreate

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                tx.setText(control+" "+data);
                Toast.makeText(MainActivity.this, title+" "+control+" "+data,Toast.LENGTH_SHORT).show();

                // 진동처리
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >=26){
                    vibrator.vibrate(VibrationEffect.createOneShot(1000, 10));
                }else{
                    vibrator.vibrate(1000);
                }

                // Notification 처리
                NotificationManager manager;
                manager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = null;
                if(Build.VERSION.SDK_INT >=26){
                    if(manager.getNotificationChannel("ch1")==null){
                        manager.createNotificationChannel(new NotificationChannel(
                                "ch1","chname",NotificationManager.IMPORTANCE_DEFAULT));
                    }
                    builder = new NotificationCompat.Builder(MainActivity.this,"ch1");
                }else{
                    builder = new NotificationCompat.Builder(MainActivity.this);
                }
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        MainActivity.this,101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                builder.setContentTitle(title);
                builder.setContentText(control);
                builder.setSmallIcon(R.drawable.p4);
                Notification noti = builder.build();
                manager.notify(1,noti);
            }
        }
    };
}