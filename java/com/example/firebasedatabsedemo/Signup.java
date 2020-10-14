package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Signup extends AppCompatActivity {
    Button mButtonSignup;
    ImageView mImageViewGoogle;
    ImageView mImageViewFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mButtonSignup=(Button)findViewById(R.id.button_signup);
        mImageViewGoogle=(ImageView)findViewById(R.id.imageview_google);
        mImageViewFacebook=(ImageView)findViewById(R.id.imageview_facebook);
        mImageViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleintent= new Intent(Signup.this,google.class);
                startActivity(googleintent);
            }
        });
        mImageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookintent=new Intent(Signup.this,facebook.class);
                startActivity(facebookintent);
            }
        });
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Signupintent=new Intent(Signup.this,MainActivity.class);
                startActivity(Signupintent);
            }
        });
    }
}
