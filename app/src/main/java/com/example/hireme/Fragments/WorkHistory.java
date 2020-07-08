package com.example.hireme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hireme.R;


public class WorkHistory extends Fragment {
    Button addwork,nextdu;
LinearLayout l12;
    public WorkHistory() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_work_history, container, false);
        addwork=view.findViewById(R.id.btnaddwork);
        nextdu=view.findViewById(R.id.nextedu);
        l12=view.findViewById(R.id.workexper2);
        nextdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                Fragment someFragment = new EducationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        addwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          addwork.setVisibility(View.INVISIBLE);
           l12.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}