package com.example.hireme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hireme.Activity.Info;
import com.example.hireme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputEditText mEmail,mPassword,mUsername,mConfirmPassword;
    Button mSignupbtn;
    TextView mloginbtn;

    FirebaseFirestore fstore;
    String userID;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mEmail=findViewById(R.id.etEmailLogin);
        mPassword=findViewById(R.id.etPasswordLogin);
        mSignupbtn=(Button)findViewById(R.id.btnsignup);
        mloginbtn=(TextView)findViewById(R.id.txtlogin);
        //pbar=(ProgressBar)findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        mUsername=findViewById(R.id.etName);
        mConfirmPassword=findViewById(R.id.etcnfrmPasswordLogin);
        fstore=FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();

        }
        mSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                final String username=mUsername.getText().toString();
                final String phone=mConfirmPassword.getText().toString();

                if (email.isEmpty()){
                    mEmail.setError("Email is Required");
                    mEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    mEmail.setError("Password is Required");
                    mPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 characters");
                    return;
                }
                //pbar.setVisibility(View.VISIBLE);

                //register the user in firebase
                if (!(email.isEmpty() && password.isEmpty())) {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //
                                FirebaseUser fuser=fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUp.this,"verification email has been sent",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUp.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                                Toast.makeText(SignUp.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                userID=fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fstore.collection("System").document(userID);
                                Map<String,Object> user=new HashMap<>();
                                user.put("Name",username);
                                user.put("Email",email);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"onSuccess :+ user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onFailure :" + e.toString());
                                    }
                                });
                                startActivity(new Intent(SignUp.this,Info.class));

                            } else {
                                Toast.makeText(SignUp.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(SignUp.this, "Error in connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,MainActivity.class));
            }
        });

    }

}