package com.example.hireme.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hireme.Activity.ShowProfile;
import com.example.hireme.R;


public class ProfileFragment extends Fragment {
     TextView mshowProfile;
    Activity context;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.profile_fragment, container, false);
        mshowProfile=view.findViewById(R.id.showProfile);
        mshowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ShowProfile.class));
            }
        });

        return view;
    }
}