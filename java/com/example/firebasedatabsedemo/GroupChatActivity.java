package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.firebasedatabsedemo.notification.CHANNEL1_ID;

public class GroupChatActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageButton sendMessageButton;
    private EditText userMessageInput;
    private ScrollView mScrollView;
    private TextView displayTextMessage;
    NotificationManagerCompat notificationManager;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, groupNameRef, groupMessageKeyRef;
    private  String currentGroupName, currentUserID,currentUserName,currentDate, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        currentGroupName=getIntent().getExtras().get("groupName").toString();
        Toast.makeText(GroupChatActivity.this,currentGroupName,Toast.LENGTH_SHORT).show();
        notificationManager= NotificationManagerCompat.from(this);
        mAuth=FirebaseAuth.getInstance();
       currentUserID= mAuth.getCurrentUser().getUid();
        UserRef= FirebaseDatabase.getInstance().getReference().child("Strata");
        groupNameRef=FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);




        Initializefields();

        GetUserInfo();


        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageInfoDatabase();
                userMessageInput.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                String title="Hello";
                NotificationCompat.Builder builder= new NotificationCompat.Builder(GroupChatActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("New Notification")
                        .setContentText(title)
                        .setAutoCancel(true);
                Intent intent=new Intent(GroupChatActivity.this,Homescree.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",title);
                PendingIntent pendingIntent=PendingIntent.getActivity(GroupChatActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager=(NotificationManager)getSystemService(
                        Context.NOTIFICATION_SERVICE
                );

            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.quickchat,menu);
        return  true;
    }
    private void SendUserToLoginActivity(){

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.mainBusy){
            SaveMessageInfoDatabase();
            userMessageInput.setText("I am Busy");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
        if (item.getItemId()==R.id.mainWill){
            SaveMessageInfoDatabase();
            userMessageInput.setText("I will call back");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
        if (item.getItemId()==R.id.mainHow){
            SaveMessageInfoDatabase();
            userMessageInput.setText("How are you?");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
        if (item.getItemId()==R.id.mainThankYou){
            SaveMessageInfoDatabase();
            userMessageInput.setText("Thank you");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        groupNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void Initializefields() {
        mToolbar=(Toolbar) findViewById(R.id.groupChatBarLayout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(currentGroupName);
        sendMessageButton=(ImageButton) findViewById(R.id. sendMessageButton);
        userMessageInput=(EditText)findViewById(R.id. inputGroupMessage);
        displayTextMessage=(TextView) findViewById(R.id.groupChatTextDisplay);
        mScrollView=(ScrollView)findViewById(R.id. myScrollView);
    }


    private void GetUserInfo() {
       UserRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   //currentUserID=mAuth.getCurrentUser().getUid();
                   currentUserName=dataSnapshot.child("userFName").getValue().toString();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
    private void SaveMessageInfoDatabase() {
        String message= userMessageInput.getText().toString();
        String messageKey=groupNameRef.push().getKey();
        if(TextUtils.isEmpty(message)){
            Toast.makeText(this,"No Message", Toast.LENGTH_SHORT).show();
        }
        else{
            Calendar calFordate =Calendar.getInstance();
            SimpleDateFormat currentDateFormat=new SimpleDateFormat("MMM dd, yyyy");
            currentDate=currentDateFormat.format(calFordate.getTime());

            Calendar calForTime =Calendar.getInstance();
            SimpleDateFormat currentTimeFormat=new SimpleDateFormat("hh:mm a");
            currentTime=currentTimeFormat.format(calForTime.getTime());

            HashMap<String,Object>groupMessageKey=new HashMap<>();
            groupNameRef.updateChildren(groupMessageKey);
            groupMessageKeyRef=groupNameRef.child(messageKey);

            HashMap<String, Object> messageInfoMap=new HashMap<>();
            messageInfoMap.put("name" ,currentUserName);
            messageInfoMap.put("message" ,message);
            messageInfoMap.put("date" ,currentDate);
            messageInfoMap.put("time" ,currentTime);
            groupMessageKeyRef.updateChildren(messageInfoMap);
        }
    }
    private void DisplayMessages(DataSnapshot dataSnapshot) {
        Iterator iterator=dataSnapshot.getChildren().iterator();
        while (iterator.hasNext()){
            String chatDate=(String)((DataSnapshot)iterator.next()).getValue();
            String chatMessage=(String)((DataSnapshot)iterator.next()).getValue();
            String chatName=(String)((DataSnapshot)iterator.next()).getValue();
            String chatTime=(String)((DataSnapshot)iterator.next()).getValue();
            displayTextMessage.append(chatName + " :\n" + chatMessage + "\n"+ chatTime +"     "+chatDate +"\n\n\n");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }
}
