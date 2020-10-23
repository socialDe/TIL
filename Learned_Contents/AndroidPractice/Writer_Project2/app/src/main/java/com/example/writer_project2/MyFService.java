package com.example.writer_project2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// 푸시메시지를 받기 위한 서비스
public class MyFService extends FirebaseMessagingService {
    public MyFService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();

        // 제목을 제외한 나머지 내용은 직접 써줘야 한다.
        String data = remoteMessage.getData().get("data");

        // Log 생성
        Log.d("[TAG]:", title+" "+data);

        Intent intent = new Intent("notification");
        intent.putExtra("title",title);
        intent.putExtra("data",data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
