package com.example.firebasedatabsedemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private View ContactsView;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    SinchClient sinchClient;
    Call call;
    ArrayList<Users> usersArrayList;
    DatabaseReference reference;
            public ChatsFragment() {
                // Required empty public constructor
            }


            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                //Inflate the layout for this fragment
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
                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    Users user = dss.getValue(Users.class);
                    usersArrayList.add(user);
                }
                AllUsersAdapterChat adapter = new AllUsersAdapterChat(getActivity(), usersArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
         /*   @Override
            public void onStart() {
                super.onStart();

                FirebaseRecyclerOptions<Users>options= new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(ChatsReference, Users.class)
                        .build();

                FirebaseRecyclerAdapter<Users,ChatsViewHolder>adapter=
                        new FirebaseRecyclerAdapter<Users, ChatsViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ChatsViewHolder holder, int position, @NonNull Users model) {
                                final   String usersIDs=getRef(position).getKey();
                                UsersRef.child(usersIDs).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        final    String retname=dataSnapshot.child("userFName").getValue().toString();

                                        holder.userName.setText(retname);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplaylayout, parent,false);
                                return  new ChatsViewHolder(view);
                            }
                        };
                chatlist.setAdapter(adapter);
                adapter.startListening();
            }
            public static class ChatsViewHolder extends  RecyclerView.ViewHolder{
                TextView userStatus, userName;

                public ChatsViewHolder(@NonNull View itemView) {
                    super(itemView);




                    userName= itemView.findViewById(R.id.userName );


                }
            }*/
    }}
