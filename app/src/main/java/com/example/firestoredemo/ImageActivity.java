package com.example.firestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    RecyclerView imagelist;
    private FirebaseFirestore dbimage;

    private FirestoreRecyclerAdapter adapterimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2);

        imagelist = findViewById(R.id.imagelist);
        dbimage = FirebaseFirestore.getInstance();

        Query query = dbimage.collection("images");

        FirestoreRecyclerOptions<Upload> options = new FirestoreRecyclerOptions.Builder<Upload>()
                .setQuery(query,Upload.class)
                .build();

        adapterimage = new FirestoreRecyclerAdapter<Upload, imageholder>(options){
            @NonNull
            @Override
            public imageholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageitem,parent,false);
                return new imageholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull imageholder holder, int position, @NonNull Upload model) {
                holder.item_name.setText(model.getName());
                Picasso.with(ImageActivity.this)
                        .load(model.getUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.item_image);
            }
        };
        imagelist.setHasFixedSize(true);
        imagelist.setLayoutManager(new LinearLayoutManager(this));
        imagelist.setAdapter(adapterimage);

    }


    private class imageholder extends RecyclerView.ViewHolder
    {
        TextView item_name;
        ImageView item_image;

        public imageholder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_image = itemView.findViewById(R.id.item_image);

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterimage.startListening();
    }
}
