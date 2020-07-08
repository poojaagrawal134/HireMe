package com.example.hireme.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hireme.Activity.MenuActivity;
import com.example.hireme.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class WorkFragment extends Fragment {
    Button addskill,next;
    TextInputEditText skill1,level1,skill2,level2;
    LinearLayout l2,l3;
    FirebaseFirestore fstore;
    public static final String TAG = "TAG";
    String userID;
    private FirebaseAuth fAuth;
    public WorkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.work_fragment, container, false);
        addskill=view.findViewById(R.id.addskill1);
        skill1=view.findViewById(R.id.etCompanyName);
        level1=view.findViewById(R.id.level1edt);
        fAuth= FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        skill2=view.findViewById(R.id.etCompanyName2);
        level2=view.findViewById(R.id.level2edt);
        l2=view.findViewById(R.id.ll2);
        l3=view.findViewById(R.id.ll3);
        next=view.findViewById(R.id.nextex);
        addskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addskill.setVisibility(View.INVISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID=fAuth.getCurrentUser().getUid();
                final String skillone =skill1.getText().toString();
                final String levelone = level1.getText().toString();
                final String skilltwo = skill2.getText().toString();
                final String leveltwo = level2.getText().toString();
                DocumentReference documentReference=fstore.collection("System").document(userID).collection("Skills").document();
                Map<String,Object> social=new HashMap<>();
                social.put("Language1",skillone);
                social.put("Level1",levelone);
                social.put("Language2",skilltwo);
                social.put("Level2",leveltwo);
                documentReference.set(social).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess :+ Fields Added For " + userID);

                        Fragment someFragment = new WorkHistory();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, someFragment ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure :" + e.toString());
                    }
                });


            }
        });

        return view;
    }
}