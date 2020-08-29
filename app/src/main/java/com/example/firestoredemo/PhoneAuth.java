package com.example.firestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {

    EditText phonenumber , otpcode;
    Button sendCode,check,image;
    String number , code,getCode;
    private PhoneAuthProvider pAuth;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        pAuth = PhoneAuthProvider.getInstance();
        mAuth = FirebaseAuth.getInstance();

        phonenumber = findViewById(R.id.phone);
        otpcode = findViewById(R.id.otpcode);
        sendCode = findViewById(R.id.send);
        check = findViewById(R.id.check);
        image = findViewById(R.id.Image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhoneAuth.this,ImageFirebaseUploadDemo.class));
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcode();
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = phonenumber.getText().toString().trim();
                if (number.isEmpty())
                {
                    Toast.makeText(PhoneAuth.this, "Enter number Plz", Toast.LENGTH_SHORT).show();
                }
                else if(number.length() < 10)
                {
                    Toast.makeText(PhoneAuth.this, "Enter Proper number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pAuth.verifyPhoneNumber("+1" + number,60,TimeUnit.SECONDS,PhoneAuth.this,mCallbacks);

                }
            }
        });
    }

    private void checkcode() {
        code=otpcode.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code,getCode);

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    startActivity(new Intent(PhoneAuth.this,ImageFirebaseUploadDemo.class));
                }
                else
                {
                    Toast.makeText(PhoneAuth.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            getCode = s;
        }
    };

}