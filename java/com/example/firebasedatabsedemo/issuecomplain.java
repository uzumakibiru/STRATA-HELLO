package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class issuecomplain extends AppCompatActivity {
    EditText editText;
     Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuecomplain);
        editText=findViewById(R.id.issueMessage);
        button=findViewById(R.id. issueButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(issuecomplain.this, "No issue explained",Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("CleanerReport").push().setValue(message);
                    Toast.makeText(issuecomplain.this, "Issue Posted",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(issuecomplain.this,cleanerstatus.class);
                    startActivity(intent);
                }
            }
        });
    }
}
