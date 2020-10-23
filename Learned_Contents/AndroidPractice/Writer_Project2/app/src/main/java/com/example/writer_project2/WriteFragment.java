package com.example.writer_project2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.writer_project2.R;

public class WriteFragment extends Fragment {
    Button close, complete;
    EditText texts;
    HttpAsync httpAsync;

    public WriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewgroup = null;
        viewgroup = (ViewGroup) inflater.inflate(R.layout.fragment_write, container, false);
        close = viewgroup.findViewById(R.id.close);
        complete = viewgroup.findViewById(R.id.complete);
        texts = viewgroup.findViewById(R.id.texts);

        // Button Event 처리
        complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("complete button");
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("글 저장하기");
                dialog.setMessage("글을 저장하시겠습니까?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = texts.getText().toString();
                        String url = "http://192.168.10.100/writerAndroid/archive.jsp";
                        url += "?content="+result+"&type=save";
                        System.out.println(result);
                        httpAsync = new HttpAsync();
                        httpAsync.execute(url);

                    }
                });
                dialog.show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("close button");
            }
        });



        return viewgroup;
    }

//    public void ckbt(View v){
//        if(v.getId() == R.id.close){
//            System.out.println("close button");
//
//        }else if(v.getId() == R.id.complete){
//            System.out.println("complete button");
//
//            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//            dialog.setTitle("글 저장하기");
//            dialog.setMessage("글을 저장하시겠습니까?");
//            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    String result = text.getText().toString();
//                    String url = "http://192.168.10.100/writerAndroid/archive.jsp";
//                    url += "?contents="+result;
//                    System.out.println(result);
//                    httpAsync = new HttpAsync();
//                    httpAsync.execute(url);
//
//                    return;
//                }
//            });
//            dialog.show();
//        }
//    }
    class HttpAsync extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("저장중...");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
            progressDialog.dismiss();
            Toast.makeText(getActivity(),"저장 완료",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


    }// end HttpAsync


}