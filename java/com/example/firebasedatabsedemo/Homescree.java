package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Homescree extends AppCompatActivity {
    private Toolbar mToolbBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextAccessorAdapter mTextAccessorAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescree);
        mToolbBar=(Toolbar)findViewById(R.id.mainToolBar);
        setSupportActionBar(mToolbBar);
        getSupportActionBar().setTitle("Hello Chat");
        mViewPager=(ViewPager)findViewById(R.id.mainTabsPager);
        mTextAccessorAdapter=new TextAccessorAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTextAccessorAdapter);

        mTabLayout=(TabLayout) findViewById(R.id.mainLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mAuth=FirebaseAuth.getInstance();
        RootRef= FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return  true;
    }
private void SendUserToLoginActivity(){
    Intent loginIntent=new Intent(Homescree.this,Login.class);
    startActivity(loginIntent);
}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.mainSignOut){
             mAuth.signOut();
             SendUserToLoginActivity();
         }
         if (item.getItemId()==R.id.mainQuery){
             Intent callIntent=new Intent(Homescree.this, query.class);
             startActivity(callIntent);
         }
        if (item.getItemId()==R.id.mainAdminPanel) {
            Intent callIntent = new Intent(Homescree.this, PhoneAuthentication.class);
            startActivity(callIntent);
        }
        if (item.getItemId()==R.id.mainCreateGroup){
           // RequestNewGroup();
            Toast.makeText(Homescree.this,"Contact Admin",Toast.LENGTH_SHORT).show();
        }
         if (item.getItemId()==R.id.mainCallPeople){
             Intent callIntent=new Intent(Homescree.this, Call1.class);
             startActivity(callIntent);
         }
         if (item.getItemId()==R.id.settings){
            Intent callIntent=new Intent(Homescree.this, Preferance.class);
            startActivity(callIntent);
        }
         return true;
    }

  /*  private void RequestNewGroup() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Homescree.this,R.style.AlertDialog);
        builder.setTitle("Enter Group Name");
        final EditText groupNameField=new EditText(Homescree.this);
        groupNameField.setHint("E.g. Strathfield Superbowl");
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName=groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(Homescree.this,"Group Name Can't be Empty.",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Homescree.this,groupName + " group Created",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}

