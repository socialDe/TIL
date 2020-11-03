package com.example.tcpip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.msg.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView tx_list, tx_msg;
    EditText et_ip, et_msg;

    int port;
    String address;
    String id;
    Socket socket;


    Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_list = findViewById(R.id.tx_list);
        tx_msg = findViewById(R.id.tx_msg);
        et_ip = findViewById(R.id.et_ip);
        et_msg = findViewById(R.id.et_msg);

        //사전세팅
        port = 5555;
        address = "192.168.35.2";
        id = "[Android]";

        new Thread(con).start();

        //firebase setting
        FirebaseMessaging.getInstance().subscribeToTopic("tcpip").addOnCompleteListener(
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            Msg msg = new Msg(null,id,"q");
            sender.setMsg(msg);
            new Thread(sender).start();
            if(socket != null){
                socket.close();
            }
            finish();
            onDestroy();
        }catch(Exception e){

        }
    }

    Runnable con = new Runnable() {
        @Override
        public void run() {
            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void connect() throws IOException {
        try {
            socket = new Socket(address, port);
        } catch (Exception e) {
            while(true) {
                try {
                    Thread.sleep(2000);
                    socket = new Socket(address, port);
                    break;
                }catch(Exception e1) {
                    System.out.println("Retry...");
                }
            }
        }

        System.out.println("Connected Server:"+address);
        sender = new Sender(socket);
        new Receiver(socket).start();
        getList();
    }



    public void click(View b) throws IOException {
        String ip = et_ip.getText().toString();
        String message = et_msg.getText().toString();
        Msg msg = null;
        msg = new Msg(id,message);

        if(message.equals("1")){
            tx_list.setText(null);
            getList();
        }
        sender.setMsg(msg);
        new Thread(sender).start();

    }

    private void getList(){
        Msg msg = new Msg(null, "[Jaeuk]","1");
        sender.setMsg(msg);
        new Thread(sender).start();

    }

    class Receiver extends Thread{
        ObjectInputStream oi;
        public Receiver(Socket socket) throws IOException {
            oi = new ObjectInputStream(socket.getInputStream());
        }
        @Override
        public void run() {
            while(oi != null) {
                Msg msg = null;
                try {
                    msg = (Msg) oi.readObject();
                    if(msg.getMaps() != null) {
                        HashMap<String, Msg> hm = msg.getMaps();
                        Set<String> keys = hm.keySet();
                        for(final String key: keys) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String tx = tx_list.getText().toString();
                                    tx_list.setText(tx+key+"\n");
                                }
                            });
                            System.out.println(key);
                        }
                        continue;
                    }
                    final Msg finalMsg = msg;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String tx = tx_msg.getText().toString();
                            tx_msg.setText(finalMsg.getId()+ finalMsg.getMsg()+"\n"+tx);
                        }
                    });
                    System.out.println(msg.getId()+msg.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            } // end while
            try {
                if(oi != null) {
                    oi.close();
                }
                if(socket != null) {
                    socket.close();
                }
            }catch(Exception e) {

            }

        }

    }


    class Sender implements Runnable{
        Socket socket;
        ObjectOutputStream oo;
        Msg msg;
        public Sender(Socket socket) throws IOException {
            this.socket = socket;
            oo = new ObjectOutputStream(socket.getOutputStream());
        }

        public void setMsg(Msg msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if(oo != null) {
                try {
                    oo.writeObject(msg);
                } catch (IOException e) {
                    //e.printStackTrace();
                    try{
                        if(socket != null) {
                            socket.close();
                        }

                    }catch(Exception e1) {
                        e1.printStackTrace();
                    }

                    try {
                        Thread.sleep(2000);
                        connect();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

    }
}