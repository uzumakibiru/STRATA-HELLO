package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class leaseagreementprofile extends AppCompatActivity {
    EditText name,email,building,unit,startDate,endDate;
    Button button;
    ProgressBar progressBar;
    private Uri imageUri;
    private  static final int PICK_IMAGE=1;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaseagreementprofile);
        imageView=findViewById(R.id.mainImageView);
        name=findViewById(R.id.mainName);
        email=findViewById(R.id.mainEmail);
        building=findViewById(R.id.mainBuilding);
        unit=findViewById(R.id.mainUnit);
        startDate=findViewById(R.id.mainLeaseStartDate);
        endDate=findViewById(R.id.mainLeaseEndDate);
        button=findViewById(R.id.mainSave);
        progressBar=findViewById(R.id.mainSaveProgressBar);
        documentReference=db.collection("users").document("profile");
        storageReference=firebaseStorage.getInstance().getReference("profile image");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadData();
            }
        });


    }

    public void ChooseImage(View view) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE||resultCode==RESULT_OK||
        data!=null||data.getData()!=null){
            imageUri=data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }
    }
    private  String getFileExt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void UploadData(){
        String Name=name.getText().toString();
        String Email=email.getText().toString();
        String Building=building.getText().toString();
        String Unit=unit.getText().toString();
        String StartDate=startDate.getText().toString();
        String EndDate=endDate.getText().toString();
        if(!TextUtils.isEmpty(Name)||!TextUtils.isEmpty(Email)||!TextUtils.isEmpty(Building)||!TextUtils.isEmpty(Unit)||
                !TextUtils.isEmpty(StartDate)||!TextUtils.isEmpty(EndDate)||imageUri!=null){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference=storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
            uploadTask=reference.putFile(imageUri);
            Task<Uri>uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri download_uri=task.getResult();
                                Map<String,String>profile=new HashMap<>();
                                profile.put("name",Name);
                                profile.put("email",Email);
                                profile.put("building",Building);
                                profile.put("unit",Unit);
                                profile.put("startdate",StartDate);
                                profile.put("enddate",EndDate);
                                profile.put("url",download_uri.toString());
                                documentReference.set(profile)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(leaseagreementprofile.this,"Profile Created",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(leaseagreementprofile.this,leaseagreementshowprofile.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(leaseagreementprofile.this,"Failed",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        else {
            Toast.makeText(leaseagreementprofile.this,"All Fileds Required",Toast.LENGTH_SHORT).show();
        }
    }
}
