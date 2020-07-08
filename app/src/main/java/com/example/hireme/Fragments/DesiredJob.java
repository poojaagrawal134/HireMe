package com.example.hireme.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hireme.Activity.MenuActivity;
import com.example.hireme.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DesiredJob extends Fragment {
    Button Saveinfo;
    EditText desirefLocation,expectedsalary,indrustry;
    FirebaseFirestore fstore;
    public static final String TAG = "TAG";
    String userID;
    private FirebaseAuth fAuth;

    public DesiredJob() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_desired_job, container, false);


        Spinner spinner =view.findViewById(R.id.spinner);
        Saveinfo=view.findViewById(R.id.saveinfo);
        fAuth= FirebaseAuth.getInstance();
        desirefLocation=view.findViewById(R.id.edtdesiredLocation);
        fstore=FirebaseFirestore.getInstance();
        expectedsalary=view.findViewById(R.id.edtexpectedSalary);
        indrustry=view.findViewById(R.id.edtindrustry);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select Role");
        arrayList.add("Software Developer");
        arrayList.add("Android Developer");
        arrayList.add("Data Analyst");
        arrayList.add("Python Developer");
        arrayList.add("HR Manager");
        arrayList.add("Content Writing");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        Saveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID=fAuth.getCurrentUser().getUid();
                final String indr = indrustry.getText().toString();
                final String exp = expectedsalary.getText().toString();
                final String des = desirefLocation.getText().toString();
                DocumentReference documentReference=fstore.collection("System").document(userID);
                Map<String,Object> adduser=new HashMap<>();
                adduser.put("Industry",indr);
                adduser.put("ExpectedSalary",exp);
                adduser.put("DesiredLocation",des);
                documentReference.set(adduser).addOnSuccessListener(new OnSuccessListener<Void>() {
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