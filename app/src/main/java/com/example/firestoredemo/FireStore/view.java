package com.example.firestoredemo.FireStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;

public class view extends AppCompatActivity {

    RecyclerView userlist;
    private FirebaseFirestore dbview;

    private FirestoreRecyclerAdapter adapter;

    ArrayList<user> arraylistuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        userlist = findViewById(R.id.userlist);
        dbview = FirebaseFirestore.getInstance();


        Query query = dbview.collection("user");

        FirestoreRecyclerOptions<user> options = new FirestoreRecyclerOptions.Builder<user>()
                .setQuery(query,user.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<user, userviewHolder>(options) {
            @NonNull
            @Override
            public userviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_custom,parent,false);
                return new userviewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull userviewHolder holder, int position, @NonNull user model) {
                holder.email.setText(model.getEmail());
                holder.name.setText(model.getName());
                holder.number.setText(model.getNumber());
                holder.password.setText(model.getPassword());
            }
        };

         userlist.setHasFixedSize(true);
         userlist.setLayoutManager(new LinearLayoutManager(this));
         userlist.setAdapter(adapter);
    }

    private class userviewHolder extends RecyclerView.ViewHolder {

        private TextView email,name,number,password;

        public userviewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.cemail);
            name = itemView.findViewById(R.id.cname);
            number = itemView.findViewById(R.id.cnumber);
            password = itemView.findViewById(R.id.cpassword);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
