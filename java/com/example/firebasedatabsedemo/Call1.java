package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.ArrayList;
import java.util.List;

public class Call1 extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    SinchClient sinchClient;
    Call call;
    ArrayList<Users> usersArrayList;
    DatabaseReference reference;
    Button democallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersArrayList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Strata");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
       // democallButton = (Button) findViewById(R.id.demoCallButton);

        android.content.Context context = this.getApplicationContext();
        SinchClient sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey("c4a19c41-498d-482d-878c-6c78aa66a004")
                .applicationSecret("jNgfZFjkX0uu6V09tNyEog==")
                .environmentHost("clientapi.sinch.com")
                .userId(auth.getUid())
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();


        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener(){

        });
        sinchClient.start();

        fetchAllUsers();
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
                AllUsersAdapter adapter= new AllUsersAdapter(Call1.this,usersArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }



    public   class sinchCallListener implements CallListener {


        @Override
        public void onCallProgressing(com.sinch.android.rtc.calling.Call call) {
            Toast.makeText(Call1.this,"Ringing...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEstablished(com.sinch.android.rtc.calling.Call call) {
            Toast.makeText(Call1.this,"Call established",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEnded(com.sinch.android.rtc.calling.Call endedcall) {
            Toast.makeText(Call1.this,"Call Ended",Toast.LENGTH_SHORT).show();
            call=null;
            endedcall.hangup();
        }

        @Override
        public void onShouldSendPushNotification(com.sinch.android.rtc.calling.Call call, List<PushPair> list) {

        }
    }
    public class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, final com.sinch.android.rtc.calling.Call call) {
            AlertDialog alertDialog =new AlertDialog.Builder(Call1.this).create();
            alertDialog.setTitle("Calling");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    call.hangup();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                call.answer();
                call.addCallListener(new sinchCallListener());
                Toast.makeText(getApplicationContext(),"Call is started",Toast.LENGTH_SHORT).show();





                }
            });
            alertDialog.show();

        }
    }

public  void callUser(String user){
    Toast.makeText(getApplicationContext(),"Call is started",Toast.LENGTH_SHORT).show();
String phone="tel:"+user;
    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phone));

    try {
        if (ActivityCompat.checkSelfPermission(Call1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }
    catch (ActivityNotFoundException e){
        Toast.makeText(Call1.this,"Activity not Found!",Toast.LENGTH_SHORT).show();
    }
/*
            call=sinchClient.getCallClient().callUser(user.getUserId());
            call.addCallListener( new sinchCallListener());
            openCallerDialog(call);
*/

   /* public void openCallerDialog(final Call call) {
        AlertDialog alertDialogCall=new AlertDialog.Builder(Call1.this).create();
        alertDialogCall.setTitle("AlERT");
        alertDialogCall.setMessage("CALLING");
        alertDialogCall.setButton(AlertDialog.BUTTON_NEUTRAL, "Hang up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                call.hangup();
            }
        });
        alertDialogCall.show();*/
    }

    private void openCallerDialog(Call call) {
        AlertDialog alertDialogCall=new AlertDialog.Builder(Call1.this).create();
        alertDialogCall.setTitle("AlERT");
        alertDialogCall.setMessage("CALLING");
        alertDialogCall.setButton(AlertDialog.BUTTON_NEUTRAL, "Hang up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent hangCall=new Intent(Call1.this, Call1.class);
                startActivity(hangCall);
                /*dialog.dismiss();
                call.hangup();*/
            }
        });
        alertDialogCall.show();

    }
}

