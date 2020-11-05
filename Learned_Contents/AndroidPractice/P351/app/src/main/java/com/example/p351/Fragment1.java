package com.example.p351;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }
    Button button4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_1, container, false);
        button4 = viewGroup.findViewById(R.id.button4);
        final Context context = container.getContext();
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Clicked on Fragment1",Toast.LENGTH_SHORT).show();
            }
        });
        return viewGroup;
    }
}