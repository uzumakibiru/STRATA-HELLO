package com.example.firebasedatabsedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

class progressbtn {
    private CardView cardView;
    private ConstraintLayout layout;
    private ProgressBar progressBar;
    private TextView textView;
    Animation fade_in;

    progressbtn(Context ct,View view){
        cardView=view.findViewById(R.id.progresscardview);
        layout=view.findViewById(R.id.constraintLayout);
        progressBar=view.findViewById(R.id. progressBar3);
        textView=view.findViewById(R.id. textView3);

    }
    void buttonActivated(){
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Wait");
    }

    void bottonFinished(){
     //  layout.setBackgroundColor(R.color.green);
        progressBar.setVisibility(View.GONE);
        textView.setText("Log in");
    }
}
