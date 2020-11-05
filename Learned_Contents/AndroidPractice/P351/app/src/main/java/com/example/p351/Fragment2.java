package com.example.p351;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment2 extends Fragment {

    public Fragment2() {
        // Required empty public constructor
    }
    Button button5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);
        Context context = container.getContext();
        button5 = viewGroup.findViewById(R.id.button5);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert Dialog");
        builder.setMessage("Testing AlertDialog");
        builder.setIcon(R.drawable.unnamed);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return viewGroup;
    }
}