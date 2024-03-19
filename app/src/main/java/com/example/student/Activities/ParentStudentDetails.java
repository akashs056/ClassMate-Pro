package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityParentStudentDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentStudentDetails extends AppCompatActivity {
    ActivityParentStudentDetailsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityParentStudentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        String uid=getIntent().getStringExtra("uid");
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

                        Glide.with(ParentStudentDetails.this).load(user.getProfileImage())
                                .placeholder(R.drawable.user)
                                .into(binding.profileImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.TrackProgres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParentStudentDetails.this,TrackProgress.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

    }
}