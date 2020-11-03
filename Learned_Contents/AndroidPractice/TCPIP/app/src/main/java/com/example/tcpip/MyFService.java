package com.example.tcpip;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFService extends FirebaseMessagingService {
    public MyFService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();

        // 제목을 제외한 나머지 내용은 직접 써줘야 한다.
        String control = remoteMessage.getData().get("control");
        String data = remoteMessage.getData().get("data");

        // Log 생성
        Log.d("[TAG]:", title+" "+control+" "+data);

    }

}
