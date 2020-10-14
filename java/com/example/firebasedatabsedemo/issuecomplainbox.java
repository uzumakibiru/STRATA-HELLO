package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class issuecomplainbox extends AppCompatActivity {
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuecomplainbox);
        editText=findViewById(R.id.issueComplainMessage);
        button=findViewById(R.id. issueComplainButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(issuecomplainbox.this, "No issue explained",Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("IssueReport").push().setValue(message);
                    Toast.makeText(issuecomplainbox.this, "Issue Posted",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(issuecomplainbox.this,issuecomplainreport.class);
                    startActivity(intent);
                }
            }
        });
    }
}
