package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class google extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
    }
    public void gsignup(View view){
        opengoogle("https://accounts.google.com/signup/v2/webcreateaccount?hl=en&flowName=GlifWebSignIn&flowEntry=SignUp");

    }
    public void opengoogle(String google){
        Uri uri= Uri.parse(google);
        Intent googleintent= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(googleintent);
    }
}

