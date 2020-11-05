package com.example.p362;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    String permissions[] = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
    };

    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        ActivityCompat.requestPermissions(this, permissions,101);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

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

                    if(mobile != null && mobile.isConnected()){

                    }else if(wifi != null && wifi.isConnected()){
                        imageView.setImageResource(R.drawable.wifi);
                    }else{
                        imageView.setImageResource(R.drawable.wifi_wireless);
                    }
                }else if(cmd.equals("android.provider.Telephony.SMS_RECEIVED")){
                    Toast.makeText(context, "SMS-RECEIVED",Toast.LENGTH_SHORT).show();
                    Bundle bundle = intent.getExtras();
                    Object [] obj =(Object [])bundle.get("pdus");
                    SmsMessage[] messages = new SmsMessage[obj.length];
                    for(int i=0; i<obj.length;i++){
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[])obj[i],format);
                    }
                    String msg = "";
                    if(messages != null && messages.length > 0){
                        msg += messages[0].getOriginatingAddress()+"\n";
                        msg += messages[0].getMessageBody()+"\n";
                        msg += new Date(messages[0].getTimestampMillis()).toString();
                        textView.setText(msg);
                    }

                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);

    } // end onCreate

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    public void ckbt(View v){
        if(v.getId() == R.id.button){
            int check = PermissionChecker.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if(check == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:010-9090-9898"));
                startActivity(intent);
            }else{
                Toast.makeText(this,"DENIED",
                        Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.button2){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(
                    "tel:010-2323-0432",
                    null,
                    "hi, Man...",
                    null,
                    null
            );
            Toast.makeText(this,"Send ..OK", Toast.LENGTH_SHORT).show();
        }
    }
}