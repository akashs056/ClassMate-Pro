package com.example.student.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.student.R;
import com.example.student.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();

        binding.editTextPhone.requestFocus();
        binding.editTextPhone.setText("+91");



        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=binding.editTextPhone.getText().toString();
                if (phone.length()<9){
                    binding.editTextPhone.setError("Invalid Phone");
                }
                Intent intent=new Intent(LoginActivity.this, OTPActivity.class);
                intent.putExtra("phone",binding.editTextPhone.getText().toString());
                startActivity(intent);

            }
        });

    }
}