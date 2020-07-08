package com.example.hireme.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hireme.Activity.MenuActivity;
import com.example.hireme.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SocialMedia extends Fragment {
    Button pr;
    EditText fb,github,coding;
    FirebaseFirestore fstore;
    public static final String TAG = "TAG";
    String userID;
    private FirebaseAuth fAuth;
    public SocialMedia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_social_media, container, false);
        pr=view.findViewById(R.id.menuprocedd);
        fAuth= FirebaseAuth.getInstance();
        fb=view.findViewById(R.id.fblink);
        fstore=FirebaseFirestore.getInstance();
        github=view.findViewById(R.id.githublink);
        coding=view.findViewById(R.id.codinglink);
        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID=fAuth.getCurrentUser().getUid();
                final String mfblink = fb.getText().toString();
                final String mgithublink = github.getText().toString();
                final String mcoding = coding.getText().toString();
                DocumentReference documentReference=fstore.collection("System").document(userID);
                Map<String,Object> social=new HashMap<>();
                social.put("Facebook",mfblink);
                social.put("Github",mgithublink);
                social.put("CodingPlatform",mcoding);
                documentReference.set(social).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess :+ Fields Added For " + userID);
                        startActivity(new Intent(getContext(), MenuActivity.class));

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