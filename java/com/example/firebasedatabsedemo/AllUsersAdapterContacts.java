package com.example.firebasedatabsedemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.CallClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

class AllUsersAdapterContact extends RecyclerView.Adapter<AllUsersAdapterContact.AllUsersViewHolder>{
    Activity context;
    ArrayList<Users> usersArrayList;
    public AllUsersAdapterContact(Activity context, ArrayList<Users>usersArrayList){
        this.context=context;
        this.usersArrayList=usersArrayList;
    }
    public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allusercontact,parent,false);
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


        public AllUsersViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName=(TextView)itemView.findViewById(R.id. userName);
            textViewContact=(TextView)itemView.findViewById(R.id.contact);


        }
    }
}

