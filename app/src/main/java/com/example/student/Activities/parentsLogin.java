package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.student.Models.ParentsModel;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityParentsLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class parentsLogin extends AppCompatActivity {
    ActivityParentsLoginBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityParentsLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(parentsLogin.this,ParentRegisterActivity.class));
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.loginMailET.getText().toString();
                String password=binding.loginPasswordET.getText().toString();
                if (email.isEmpty()){
                    binding.loginMailET.setError("email is mandatory");
                    return;
                }
                if (password.isEmpty()){
                    binding.loginPasswordET.setError("password is mandatory");
                    return;
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(parentsLogin.this, ParentsMainActivity.class));
                            finishAffinity();
                            Toast.makeText(parentsLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}