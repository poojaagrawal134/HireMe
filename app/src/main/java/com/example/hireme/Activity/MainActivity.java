package com.example.hireme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hireme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mloginbtn;
    ImageView iemail;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmail=(EditText)findViewById(R.id.etEmailLogin1);
        mPassword=(EditText)findViewById(R.id.etPasswordLogin1);
        mloginbtn=(Button)findViewById(R.id.btnlogin);
       iemail = findViewById(R.id.btnmail);

        //pbar=(ProgressBar)findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;

                }
                if (TextUtils.isEmpty(password)){
                    mEmail.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 characters");
                    return;
                }
               // pbar.setVisibility(View.VISIBLE);
                //Authenticate

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Error" +task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        iemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }
}