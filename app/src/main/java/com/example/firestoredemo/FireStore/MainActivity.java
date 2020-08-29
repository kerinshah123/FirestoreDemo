package com.example.firestoredemo.FireStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText name, email, password, number;
    Button register, loginuser;
    int id;
    private String txtname, txtemail, txtpassword, txtnumber;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        number = findViewById(R.id.number);

        register = findViewById(R.id.register);
        loginuser = findViewById(R.id.loginuser);

        loginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtname = name.getText().toString().trim();
                txtemail = email.getText().toString().trim();
                txtpassword = password.getText().toString().trim();
                txtnumber = number.getText().toString().trim();

                if (txtname.isEmpty()) {
                    name.setError("PLzz.. Enter Username");
                    name.setFocusable(true);
                } else if (txtemail.isEmpty()) {
                    email.setError("Plzz. Enter Email");
                    email.setFocusable(true);
                } else if (txtpassword.isEmpty()) {
                    password.setError("PLzz.. Enter Password");
                    password.setFocusable(true);
                } else if (txtnumber.isEmpty()) {
                    number.setError("Plzz.. Enter Number");
                    number.setFocusable(true);
                } else {

                    CollectionReference dbuser = db.collection("user");

                    user user = new user(id, txtname, txtemail, txtpassword, txtnumber);
                    dbuser.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Login.class));
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });

    }


}
