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


public class Fragment3 extends Fragment {

    public Fragment3() {
        // Required empty public constructor
    }
    Button button3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup =(ViewGroup)inflater.inflate(R.layout.fragment_1,container,false);
        button3 = viewGroup.findViewById(R.id.button);
        final Context context = container.getContext();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button3 Clicked on Fragment3", LENGTH_SHORT).show();
            }
        });
        return viewGroup;
    }
}