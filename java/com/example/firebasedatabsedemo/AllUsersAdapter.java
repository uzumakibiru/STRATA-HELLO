package com.example.firebasedatabsedemo;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.CallClient;

import java.util.ArrayList;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.AllUsersViewHolder>{
   Activity context;
   ArrayList<Users> usersArrayList;
   public  AllUsersAdapter(Activity context,ArrayList<Users>usersArrayList){
       this.context=context;
       this.usersArrayList=usersArrayList;
   }
    public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allusers,parent,false);
       AllUsersViewHolder allUsersAdapter=new AllUsersViewHolder(view);
        return allUsersAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull AllUsersViewHolder holder, int position) {
Users user=usersArrayList.get(position);
holder.textViewName.setText(user.getUserFName());
holder.textViewContact.setText(user.getUserContactNo());
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class AllUsersViewHolder extends  RecyclerView.ViewHolder {

       TextView textViewName;
       TextView textViewContact;
       Button button;

        public AllUsersViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName=(TextView)itemView.findViewById(R.id. userName);
            textViewContact=(TextView)itemView.findViewById(R.id.userContact);
            button=(Button)itemView.findViewById(R.id.callButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Users user=usersArrayList.get(getAdapterPosition());

                 String hello=user.getUserContactNo();
                      Log.d(hello,"Biru");



                   ((Call1)context).callUser(hello);
                }
            });

        }
    }
}
