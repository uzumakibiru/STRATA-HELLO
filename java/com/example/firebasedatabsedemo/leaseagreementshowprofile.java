package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class leaseagreementshowprofile extends AppCompatActivity {
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageView;
    TextView name,email,building,unit,startdate,enddate;
    FloatingActionButton floatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaseagreementshowprofile);
        name=findViewById(R.id.mainNameSp);
        email=findViewById(R.id.mainEmailSp);
        building=findViewById(R.id.mainBuildingSp);
        unit=findViewById(R.id.mainUnitSp);
        startdate=findViewById(R.id.mainStartDateSp);
        enddate=findViewById(R.id.mainEndDateSp);
        floatingActionButton=findViewById(R.id.mainFloatButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(leaseagreementshowprofile.this,PhoneAuthentication.class);
                startActivity(intent);
            }
        });
        imageView=findViewById(R.id.mainLeaseImageViewSp);
        documentReference=db.collection("users").document("profile");
        storageReference=firebaseStorage.getInstance().getReference("profile image");
    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){
                            String name_result=task.getResult().getString("name");
                            String email_result=task.getResult().getString("email");
                            String building_result=task.getResult().getString("building");
                            String unit_result=task.getResult().getString("unit");
                            String startdate_result=task.getResult().getString("startdate");
                            String enddate_result=task.getResult().getString("enddate");
                            String Url=task.getResult().getString("url");

                            Picasso.get().load(Url).into(imageView);
                            name.setText(name_result);
                            email.setText(email_result);
                            building.setText(building_result);
                            unit.setText(unit_result);
                            startdate.setText(startdate_result);
                            enddate.setText(enddate_result);
                        }
                        else{
                            Toast.makeText(leaseagreementshowprofile.this,"No Profile exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
