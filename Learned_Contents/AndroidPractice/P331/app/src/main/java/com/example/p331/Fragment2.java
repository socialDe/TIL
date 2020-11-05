package com.example.p331;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;


public class Fragment2 extends Fragment {


    public Fragment2() {
        // Required empty public constructor
    }
    Button button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup =(ViewGroup)inflater.inflate(R.layout.fragment_1,container,false);
        button2 = viewGroup.findViewById(R.id.button);
        final Context context = container.getContext();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button2 Clicked on Fragment2", LENGTH_SHORT).show();
            }
        });
        return viewGroup;
    }
}