package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    com.example.student.databinding.ActivityProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding= com.example.student.databinding.ActivityProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=auth.getUid();
        key=getIntent().getStringExtra("key");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("Student")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isDestroyed()) {
                            return;
                        }
                        UserModel user=snapshot.getValue(UserModel.class);
                        binding.nameEd.setText(user.getName());
                        binding.emailED.setText(user.getEmail());
                        binding.collgeED.setText(user.getCollege());
                        binding.phoneED.setText(user.getPhone());
                        binding.rollNoEd.setText(user.getRollNo());
                        binding.programEd.setText(user.getProgram());
                        binding.uid.setText(user.getUid());

                        Glide.with(ProfileActivity.this).load(user.getProfileImage())
                                .placeholder(R.drawable.user)
                                .into(binding.profileImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid=binding.uid.getText().toString();
                ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Uid",uid);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(ProfileActivity.this, "Uid copied to clipboard", Toast.LENGTH_SHORT).show();

                binding.copy.setText("COPIED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.copy.setText("COPY");
                    }
                },10000);
            }
        });

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, EditProfile.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

    }
}