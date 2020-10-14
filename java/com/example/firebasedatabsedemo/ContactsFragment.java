package com.example.firebasedatabsedemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private View ContactsView;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    SinchClient sinchClient;
    Call call;
    ArrayList<Users> usersArrayList;
    DatabaseReference reference;
    Button democallButton;
    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       ContactsView =inflater.inflate(R.layout.fragment_contacts, container, false);
       recyclerView=(RecyclerView) ContactsView.findViewById(R.id.contacts_list);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       reference= FirebaseDatabase.getInstance().getReference().child("Strata");
        usersArrayList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Strata");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        fetchAllUsers();

       return ContactsView;
    }

    public void fetchAllUsers() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersArrayList.clear();
                for(DataSnapshot dss:dataSnapshot.getChildren()){
                    Users user=dss.getValue(Users.class);
                    usersArrayList.add(user);
                }
                AllUsersAdapterContact adapter= new AllUsersAdapterContact(getActivity(),usersArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

