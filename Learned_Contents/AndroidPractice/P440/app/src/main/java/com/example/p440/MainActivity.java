package com.example.p440;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etx_name;
    EditText etx_bd;
    EditText etx_phone;
    ArrayList<Person> persons;
    ListView listView;
    LinearLayout container;
    TextView numP;
    ActionBar actionBar;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etx_name = findViewById(R.id.etx_name);
        etx_bd = findViewById(R.id.etx_bd);
        etx_phone = findViewById(R.id.etx_phone);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        persons = new ArrayList<>();
        numP = findViewById(R.id.numP);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Network");
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo mobile= null;
                NetworkInfo wifi = null;
                if(mobile != null && mobile.isConnected()){
                    actionBar.setLogo(R.drawable.wifi_wireless);

                }else if(wifi != null && wifi.isConnected()){
                    actionBar.setLogo(R.drawable.wifi);
                }else{

                }

                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if(mobile != null && mobile.isConnected()){
                        actionBar.setLogo(R.drawable.wifi_wireless);

                    }else if(wifi != null && wifi.isConnected()){
                        actionBar.setLogo(R.drawable.wifi);
                    }else{

                    }
                    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
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


    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person, container, true);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_bd = view.findViewById(R.id.tx_bd);
            TextView tx_phone = view.findViewById(R.id.tx_phone);
            Person p = datas.get(position);
            tx_name.setText(p.getName());
            tx_bd.setText(p.getBirthday());
            tx_phone.setText(p.getPhone());

            return view;
        }
    }

    public void setList(final ArrayList<Person> persons){
        final PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Remove");
                builder.setMessage("Do you want to remove "+persons.get(position).getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        persons.remove(position);
                        personAdapter.notifyDataSetChanged();
                        numP.setText(persons.size()+"명");
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    public void getData(String name, String birthday, String phone){

        persons.add(new Person(name, birthday, phone));
        setList(persons);
    }
    public void ckbt(View v){
        if(v.getId() == R.id.button){
            String name = etx_name.getText().toString();
            String birthday= etx_bd.getText().toString();
            String phone = etx_phone.getText().toString();
            getData(name, birthday, phone);
            numP.setText(persons.size()+"명");

        }
    }

}