package com.example.p351;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.zip.Inflater;

public class Fragment3 extends Fragment {

    public Fragment3() {
        // Required empty public constructor
    }
    Button button6;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("[TEST]", "onCreateView: ");
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_3,container,false);
        button6 = viewGroup.findViewById(R.id.button6);
        final Context context = container.getContext();
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = null;
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Downloading...");
                progressDialog.show();
            }
        });

        return viewGroup;
    }
}