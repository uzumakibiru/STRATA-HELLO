package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText fname,lname, buildingNo, email,flatNo,dob,contactNo,suburb,password,cnfPassword;
    private Button signup;
    private TextView login;
    FirebaseAuth fAuth;
    FirebaseUser currentuser;
    View progressbtn1;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference= FirebaseDatabase.getInstance().getReference("Strata");
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        buildingNo=(EditText)findViewById(R.id.BuildinNo);
        email=(EditText)findViewById(R.id.email);
        flatNo=(EditText)findViewById(R.id.flatNo);
        dob=(EditText)findViewById(R.id.dob);
        contactNo=(EditText)findViewById(R.id.contactNo);
        suburb=(EditText)findViewById(R.id.suburb);
        password=(EditText)findViewById(R.id.password);
        cnfPassword=(EditText)findViewById(R.id.cnfPassword);
        //signup=(Button)findViewById(R.id.signup);
        progressbtn1=(View)findViewById(R.id. progressbtn1);
        login=(TextView)findViewById(R.id.login);
        fAuth=FirebaseAuth.getInstance();
        currentuser=fAuth.getCurrentUser();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent=new Intent(MainActivity.this,Login.class);
                startActivity(loginintent);
            }
        });
        progressbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbtn1 progressBtn=new progressbtn1(MainActivity.this,progressbtn1);
                progressBtn.buttonActivated();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBtn.bottonFinished();

                    }
                },3000);
                addUsers();

            }
        });
    }
    public void addUsers(){
        final String userFName=fname.getText().toString();
        final String userLName=lname.getText().toString();
        final String userBuildingNo=buildingNo.getText().toString();
        final String useremail = email.getText().toString();
        final String userFlatNo=flatNo.getText().toString();
        final String userDob=dob.getText().toString();
        final String userContactNo=contactNo.getText().toString();
        final String userSuburb=suburb.getText().toString();
        final String userPassword=password.getText().toString();
        final String userCnfPassword=cnfPassword.getText().toString();
        if(!TextUtils.isEmpty(userFName) && !TextUtils.isEmpty(userLName) && !TextUtils.isEmpty(userBuildingNo) && !TextUtils.isEmpty(useremail)
                && !TextUtils.isEmpty(userFlatNo) && !TextUtils.isEmpty(userDob)&& !TextUtils.isEmpty(userContactNo) && !TextUtils.isEmpty(userSuburb)
                && !TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(userCnfPassword) && userPassword.equals(userCnfPassword)){
            fAuth.createUserWithEmailAndPassword(useremail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String id= fAuth.getCurrentUser().getUid();

                        Users users=new Users(id,userFName,userLName,userBuildingNo,useremail,userFlatNo,userDob,userContactNo,userSuburb,userPassword,userCnfPassword);
                        databaseReference.child(id).setValue(users);
                        fname.setText("");
                        lname.setText("");
                        buildingNo.setText("");
                        flatNo.setText("");
                        dob.setText("");
                        contactNo.setText("");
                        suburb.setText("");
                        password.setText("");
                        cnfPassword.setText("");
                        //Toast.makeText(MainActivity.this,"Registration Success.", Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(MainActivity.this,"User Created.", Toast.LENGTH_SHORT).show();
                   // }
                    //else{
                      //  Toast.makeText(MainActivity.this,"Error: Check details!!", Toast.LENGTH_SHORT).show();
                    //}
                       Toast.makeText(MainActivity.this,"Registration Success.", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,"User Created.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Login.class));

                    }
                    else{
                        //Toast.makeText(MainActivity.this,"Registration Failed.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this,"User Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        else{
            Toast.makeText(MainActivity.this,"Please Enter All details", Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this,"User Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
           /* String id= fAuth.getCurrentUser().getUid();
            //String id= databaseReference.push().getKey();
            Users users=new Users(id,userFName,userLName,userBuildingNo,useremail,userFlatNo,userDob,userContactNo,userSuburb,userPassword,userCnfPassword);
            databaseReference.child(id).setValue(users);
            fname.setText("");
            lname.setText("");
            buildingNo.setText("");
            flatNo.setText("");
            dob.setText("");
            contactNo.setText("");
            suburb.setText("");
            password.setText("");
            cnfPassword.setText("");
            //Toast.makeText(MainActivity.this,"Registration Success.", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this,"User Created.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this,"Error: Check details!!", Toast.LENGTH_SHORT).show();
        }*/


}
}
