package com.example.firestoredemo.FireStore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firestoredemo.R;

import java.util.ArrayList;

public class userAdapter extends BaseAdapter {

    Context context;
    ArrayList<user> users;

    public userAdapter(Context applicationContext, ArrayList<user> arrayList) {
        this.context=applicationContext;
        this.users=arrayList;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        user user = users.get(position);

        final LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.user_custom,null);
        TextView email = convertView.findViewById(R.id.cemail);
        TextView name = convertView.findViewById(R.id.cname);
        TextView number = convertView.findViewById(R.id.cnumber);
        TextView password = convertView.findViewById(R.id.cpassword);


        email.setText(user.getEmail());
        name.setText(user.getName());
        password.setText(user.getPassword());
        number.setText(user.getNumber());

        return convertView;
    }
}
