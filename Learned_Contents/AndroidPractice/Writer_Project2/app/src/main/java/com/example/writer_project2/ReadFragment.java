package com.example.writer_project2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.writer_project2.R;


public class ReadFragment extends Fragment {
    TextView loadtx;
    HttpAsync httpAsync;

    public ReadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewgroup = null;
        viewgroup = (ViewGroup) inflater.inflate(R.layout.fragment_read, container, false);
        loadtx = viewgroup.findViewById(R.id.loadtx);

//        String url = "http://192.168.10.100/writerAndroid/archive.jsp";
//        url += "?contents="+"load"+"&type=load";
//        System.out.println("load");
//        httpAsync = new HttpAsync();
//        httpAsync.execute(url);


        return viewgroup;
    }

    class HttpAsync extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            System.out.println(result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(),"불러오기 완료",Toast.LENGTH_SHORT);
            loadtx.setText(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


    }// end HttpAsync
}