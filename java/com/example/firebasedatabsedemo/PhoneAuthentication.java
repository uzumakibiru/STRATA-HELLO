package com.example.firebasedatabsedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class PhoneAuthentication extends AppCompatActivity {
    EditText editTextPhone, editTextCode;
    FirebaseAuth mAuth;
    String codeSent;
  //  CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        editTextPhone=findViewById(R.id.mainPhoneNumber);
        editTextCode=findViewById(R.id.mainVerificationCode);
       // ccp=(CountryCodePicker)findViewById(R.id.ccp);
     //   ccp.registerCarrierNumberEditText(editTextPhone);
        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.mainRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerifiationCode();
            }
        });
        findViewById(R.id.mainVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignCode();

            }
        });
    }
    private void verifySignCode(){
        String code=editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Veirfied",Toast.LENGTH_SHORT).show();
                            Intent adminIntent = new Intent(PhoneAuthentication.this, AdminMainPage.class);
                            startActivity(adminIntent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),"Incorrect Verification code",Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });
    }
    private void sendVerifiationCode(){
        String phone= editTextPhone.getText().toString();
       /* if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }
        if(phone.length()<10){
            editTextPhone.setError("Please edit valid phone number");
            editTextPhone.requestFocus();
            return;
        }*/
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }
    };
}
