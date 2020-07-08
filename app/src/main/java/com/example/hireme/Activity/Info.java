package com.example.hireme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.hireme.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Info extends AppCompatActivity {
      private EditText mdateofbirth,mPhone,mWhatsapp,mLanguage,mLocation;
      Button proceed;
    String userID;
    FirebaseFirestore fstore;
    public static final String TAG = "TAG";
    private FirebaseAuth fAuth;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        proceed = (Button) findViewById(R.id.proceedbtn);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        mdateofbirth = (EditText)findViewById(R.id.edtDOB);
        mPhone = (EditText)findViewById(R.id.edtphone);
        mWhatsapp = (EditText)findViewById(R.id.edtwhatsapp);
        mLanguage = (EditText)findViewById(R.id.edtlangugae);
        mLocation = (EditText)findViewById(R.id.edtNativelocation);
        mdateofbirth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                //get current date and to set popup date picker

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Info.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month=month+1;
                String date = month + "/" + day + "/" + year;

                mdateofbirth.setText(date);

            }
        };
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userID=fAuth.getCurrentUser().getUid();

                // final String sgender = rgender.getText().toString();
                final String nLocation = mLocation.getText().toString();
                final String nPhone = mPhone.getText().toString();
                final String nWhatsapp = mWhatsapp.getText().toString();
                final String nDOB = mdateofbirth.getText().toString();
                final String nLanguage = mLanguage.getText().toString();

                DocumentReference documentReference=fstore.collection("System").document(userID);
                Map<String,Object> user=new HashMap<>();
               // user.put("Gender",sgender);
                //user.put("Email",semail);
                user.put("Phone_Number",nPhone);
                user.put("WhatsApp_Number",nWhatsapp);
                user.put("Languages_Known",nLanguage);
                user.put("Native_Location ",nLocation);
                user.put("DateOfBirth",nDOB);


                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess :+ user Profile is created for " + userID);
                        startActivity(new Intent(Info.this, MenuActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure :" + e.toString());
                    }
                });
                //startActivity(new Intent(Info.this, MenuActivity.class));
            }
        });

    }
}