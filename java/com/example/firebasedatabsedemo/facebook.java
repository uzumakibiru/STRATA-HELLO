package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class facebook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
    }
    public void fsignup(View view){
        openfacebook("https://www.facebook.com/reg/?rs=2");

    }
    public void openfacebook(String facebook){
        Uri uri= Uri.parse(facebook);
        Intent facebookintent= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(facebookintent);
    }
}
