package com.example.firebasedatabsedemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notification extends Application {
    public static  final String CHANNEL1_ID="channel1";

    @Override
    public void onCreate() {
        super.onCreate();
    }
    private  void createnotification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name;
            NotificationChannel channel=new NotificationChannel(CHANNEL1_ID,"Chaneel1", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Message sent");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
