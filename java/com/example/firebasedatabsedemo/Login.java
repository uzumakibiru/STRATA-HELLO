package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private static final String CHANNEL_ID = "Notification";
    private static final int NOTIFICATION_ID = 101;
    private EditText username;
    private EditText password;
    private TextView info;
    private Button Login;
    private Button mTextViewRegister;
    private TextView mTextViewForgotPassword;
    ProgressBar progressBar;
    View view, view1;


    FirebaseAuth fAuth;
    private int counter=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.edittext_username);
        password=(EditText) findViewById(R.id.edittext_password);
        info=(TextView) findViewById(R.id.textview_info);
       //Login=(Button)findViewById(R.id.button_Login);
        mTextViewForgotPassword=(TextView) findViewById(R.id.textview_forgot_password);
        info.setText("Number of attempts: 5");
        //mTextViewRegister = (Button) findViewById(R.id.button_signup);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
       view=(View)findViewById(R.id.include);
       view1=(View)findViewById(R.id. signup);
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
           NotificationChannel channel=new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
           NotificationManager manager=getSystemService(NotificationManager.class);
           manager.createNotificationChannel(channel);
       }
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent=new Intent(Login.this,Signup.class);
                startActivity(registerIntent);
            }
        });
        mTextViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpasswordintent=new Intent(Login.this,forgotpass.class);
                startActivity(forgotpasswordintent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder= new NotificationCompat.Builder(Login.this,"My Notification");
                        builder.setSmallIcon(R.drawable.logo);
                        builder.setContentTitle("Strata");
                        builder.setContentText("New Login attempt");
                        builder.setAutoCancel(true);
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(Login.this);
                notificationManagerCompat.notify(1,builder.build());



                progressbtn progressBtn=new progressbtn(Login.this,view);
                progressBtn.buttonActivated();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBtn.bottonFinished();

                    }
                },3000);
                String userEmail=username.getText().toString();
                String userPassword=password.getText().toString();
                if(TextUtils.isEmpty(userEmail)){
                    username.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(userPassword)){
                    password.setError("Password is required");
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Success!!", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(getApplicationContext(),Homescree.class));
                            }
                            else {
                                info.setText("Remaining Attempts:" + String.valueOf(counter));
                                counter--;
                                if (counter == -1) {

                                    Login.setEnabled(false);
                                    Toast.makeText(Login.this, "Login Failed!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    });


                }
               /* loginUser();*/
               // validate(username.getText().toString(),password.getText().toString());

        });

      /*  Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Animation animation= AnimationUtils.loadAnimation(Login.this,R.anim.blink_anim);
               Login.startAnimation(animation);
                validate(username.getText().toString(),password.getText().toString());
            }
        });*/
    }

   /* private void loginUser( ) {
        if(!validateUsername() | !validatePasword()){
            return;
        }
        else{
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername=username.getText().toString().trim();
        final String userEnteredPassword=password.getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Strata");
        Query checkUser=reference.orderByChild("userContactNo").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String passwordFromDB=dataSnapshot.child(userEnteredUsername).child("userPassword").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword)){
                        String dobFromDB=dataSnapshot.child(userEnteredUsername).child("userDob").getValue(String.class);
                        String fNameFromDB=dataSnapshot.child(userEnteredUsername).child("userFName").getValue(String.class);
                        String lNameFromDB=dataSnapshot.child(userEnteredUsername).child("userLName").getValue(String.class);
                        String contactNoFromDB=dataSnapshot.child(userEnteredUsername).child("userContactNo").getValue(String.class);
                        String buildingNoFromDB=dataSnapshot.child(userEnteredUsername).child("userBuildingNo").getValue(String.class);
                        String flatNoFromDB=dataSnapshot.child(userEnteredUsername).child("userFlatNo").getValue(String.class);
                        String suburbFromDB=dataSnapshot.child(userEnteredUsername).child("userSuburb").getValue(String.class);
                        String emailFromDB=dataSnapshot.child(userEnteredUsername).child("userEmail").getValue(String.class);
                        Intent intent=new Intent(getApplicationContext(),Homescree.class);
                        intent.putExtra("userDob",dobFromDB);
                        intent.putExtra("userFname",fNameFromDB);
                        intent.putExtra("userLName",lNameFromDB);
                        intent.putExtra("userContactNo",contactNoFromDB);
                        intent.putExtra("userBuildingNo",buildingNoFromDB);
                        intent.putExtra("userFlatNo",flatNoFromDB);
                        intent.putExtra("userSuburb",suburbFromDB);
                        intent.putExtra("userEmail",emailFromDB);
                        startActivity(intent);
                    }
                    else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }

                }
                else{
                    username.setError("No such user exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean validateUsername(){
        String val=username.getText().toString();
        if(val.isEmpty()){
            username.setError("Field is Empty");
            return false;
        }else{
            username.setError(null);
           return true;
        }

    }

    private  Boolean validatePasword(){
        String val=password.getText().toString();
        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }
*/

    /*private void validate(String user, String pwd) {
        if ((user.equals("Admin")) && (pwd.equals("Admin") )) {
            Intent intent = new Intent(Login.this, Homescree.class);
            startActivity(intent);
        } else {
            info.setText("Remaining Attempts:" +String.valueOf(counter));
            counter--;
            if(counter==-1){

                Login.setEnabled(false);
                Toast.makeText(Login.this, "Login Blocked!!", Toast.LENGTH_SHORT).show();
            }

        }
    }*/

}
