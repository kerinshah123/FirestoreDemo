package com.example.firestoredemo.FireStore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    EditText loginemail,loginpassword;
    Button login;

    private String txtloginemail,txtloginpassword;

    private FirebaseFirestore dblogin;
    private Object Tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dblogin = FirebaseFirestore.getInstance();

        loginemail=findViewById(R.id.loginemail);
        loginpassword=findViewById(R.id.loginpassword);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtloginemail= loginemail.getText().toString().trim();
                txtloginpassword = loginpassword.getText().toString().trim();

                if (txtloginemail.isEmpty())
                {
                    loginemail.setError("Enter plz");
                    loginemail.setFocusable(true);
                }
                else if (txtloginpassword.isEmpty())
                {
                    loginpassword.setError("enter plz");
                    loginpassword.setFocusable(true);
                }
                else
                {


                    dblogin.collection("user")
                            .whereEqualTo("email",txtloginemail)
                            .whereEqualTo("password",txtloginpassword)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(!queryDocumentSnapshots.isEmpty()) {
                                        startActivity(new Intent(Login.this,view.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, "Email or Password Is not correct", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

    }
}
