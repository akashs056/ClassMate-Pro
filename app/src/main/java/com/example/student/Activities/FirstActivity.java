package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.student.R;
import com.example.student.databinding.ActivityFirstBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstActivity extends AppCompatActivity {
    ActivityFirstBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();
        if (currentUser!=null){
            binding.student.setVisibility(View.GONE);
            binding.parent.setVisibility(View.GONE);
            binding.textView13.setVisibility(View.GONE);
            DatabaseReference studentsRef = FirebaseDatabase.getInstance().getReference().child("Student").child(currentUser.getUid());
            DatabaseReference parentsRef = FirebaseDatabase.getInstance().getReference().child("Parents").child(currentUser.getUid());
            showProgressBar();
            studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ssnapshot) {
                    hideProgressBar();
                    if (ssnapshot.exists()){
                        startActivity(new Intent(FirstActivity.this, MainActivity.class));
                        finishAffinity();
                    }else{
                        showProgressBar();
                        parentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot psnapshot) {
                                hideProgressBar();
                                if (psnapshot.exists()){
                                    startActivity(new Intent(FirstActivity.this, ParentsMainActivity.class));
                                    finishAffinity();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                hideProgressBar();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    hideProgressBar();
                }
            });

        }else{

        }

        binding.student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            }
        });
        binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,parentsLogin.class));
            }
        });
    }
    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.logo.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
        binding.logo.setVisibility(View.GONE);
    }
}