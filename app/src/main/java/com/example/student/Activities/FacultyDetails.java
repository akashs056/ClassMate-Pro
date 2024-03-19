package com.example.student.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.student.R;
import com.example.student.databinding.ActivityFacultyDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FacultyDetails extends AppCompatActivity {
    ActivityFacultyDetailsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String collegeName,name,email,phone,age,gender,image,facultyUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFacultyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        collegeName=getIntent().getStringExtra("college");
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        phone=getIntent().getStringExtra("phone");
        age=getIntent().getStringExtra("age");
        gender=getIntent().getStringExtra("gender");
        image=getIntent().getStringExtra("image");
        facultyUid=getIntent().getStringExtra("facultyUid");

        Glide.with(this).load(image).placeholder(R.drawable.user).into(binding.profileImage);

        binding.collgeED.setText(collegeName);
        binding.nameEd.setText(name);
        binding.emailED.setText(email);
        binding.phoneED.setText(phone);
        binding.rollNoEd.setText(age);
        binding.programEd.setText(gender);

        binding.complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FacultyDetails.this, CompalintActivity.class);
                startActivity(intent);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}