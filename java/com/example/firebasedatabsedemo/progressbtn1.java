package com.example.firebasedatabsedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

class progressbtn1 {
    private CardView cardView1;
    private ConstraintLayout layout1;
    private ProgressBar progressBar1;
    private TextView textView1;
    Animation fade_in;

    progressbtn1(Context ct,View view){
        cardView1=view.findViewById(R.id.progresscardview1);
        layout1=view.findViewById(R.id.constraintLayout1);
        progressBar1=view.findViewById(R.id. progressBar4);
        textView1=view.findViewById(R.id. textView4);

    }
    void buttonActivated(){
        progressBar1.setVisibility(View.VISIBLE);
        textView1.setText("Saving");
    }

    void bottonFinished(){
        //  layout.setBackgroundColor(R.color.green);
        progressBar1.setVisibility(View.GONE);
        textView1.setText("Sign up");
    }
}
