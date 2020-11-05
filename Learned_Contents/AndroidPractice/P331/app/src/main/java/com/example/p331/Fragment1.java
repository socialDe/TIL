package com.example.p331;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.*;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = null;
        viewGroup =(ViewGroup)inflater.inflate(R.layout.fragment_1,container,false);
        button = viewGroup.findViewById(R.id.button);
        final Context context = container.getContext();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Clicked on Fragment1", LENGTH_SHORT).show();
            }
        });
        return viewGroup;
    }

}