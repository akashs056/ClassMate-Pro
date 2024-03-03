package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.student.R;
import com.example.student.databinding.ActivityOtpactivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukeshsolanki.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding binding;
    FirebaseAuth auth;
    String verificationID;
    ProgressDialog dialog;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=auth.getUid();
        binding.otpView.requestFocus();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Sending OTP");
        dialog.setCancelable(false);
        dialog.show();

        String phone=getIntent().getStringExtra("phone");

        PhoneAuthOptions options=new PhoneAuthOptions.Builder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTPActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        dialog.dismiss();
                        verificationID=s;
                    }
                }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);

        binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,otp);

                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (task.isSuccessful()) {
                                checkProfileSetupStatus(); // Check if profile is set up
                            } else {
                                Toast.makeText(OTPActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(OTPActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
    private void checkProfileSetupStatus() {
        String uid = auth.getUid();
        DatabaseReference userRef = database.getReference("Student").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User node exists, check profile setup status
                    Boolean profileSetup = snapshot.child("profileSetup").getValue(Boolean.class);

                    if (profileSetup != null && profileSetup) {
                        // User has set up the profile, redirect to MainActivity
                        startActivity(new Intent(OTPActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        // User hasn't set up the profile, redirect to SetUpProfileActivity
                        startActivity(new Intent(OTPActivity.this, SetUpProfileActivity.class));
                        finishAffinity();
                    }
                } else {
                    // User node doesn't exist, handle accordingly (e.g., redirect to SetUpProfileActivity)
                    startActivity(new Intent(OTPActivity.this, SetUpProfileActivity.class));
                    finishAffinity();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                Toast.makeText(OTPActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}