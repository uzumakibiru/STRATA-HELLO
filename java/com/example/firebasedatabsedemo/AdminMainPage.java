package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminMainPage extends AppCompatActivity {
    ListView adminListView;
    private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        RootRef= FirebaseDatabase.getInstance().getReference();
        adminListView=findViewById(R.id.adminListview);
        String[] value=new  String[]{
                "View User","Update User","Update Admin","Lease Agreement","Create Group","Update Group","Go Back"
        };

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,value);
        adminListView.setAdapter(adapter);
        adminListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                   gotoUrl("https://console.firebase.google.com/u/0/project/fir-demo-5c0d5/database/fir-demo-5c0d5/data");

                }
                if(position==1) {
                    gotoUrl("https://console.firebase.google.com/u/0/project/fir-demo-5c0d5/authentication/users");
                }
                if(position==2) {
                    gotoUrl("https://console.firebase.google.com/u/0/project/fir-demo-5c0d5/authentication/providers");
                }
                if(position==3) {
                    Intent intent = new Intent(view.getContext(), leaseagreementprofile.class);
                    startActivity(intent);

                }
                if(position==4) {
                    RequestNewGroup();
                }
                if(position==5) {
                    gotoUrl("https://console.firebase.google.com/u/0/project/fir-demo-5c0d5/database/fir-demo-5c0d5/data");
                }
                if(position==6) {
                    Intent intent = new Intent(view.getContext(), Homescree.class);
                    startActivity(intent);
                }

            }



        });

    }

    private void gotoUrl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    private void RequestNewGroup() {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminMainPage.this,R.style.AlertDialog);
        builder.setTitle("Enter Group Name");
        final EditText groupNameField=new EditText(AdminMainPage.this);
        groupNameField.setHint("E.g. Strathfield Superbowl");
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName=groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(AdminMainPage.this,"Group Name Can't be Empty.",Toast.LENGTH_SHORT).show();
                }
                else{
                    CreateNewGroup(groupName);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void CreateNewGroup(final String groupName) {
        RootRef.child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminMainPage.this,groupName + " group Created",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}