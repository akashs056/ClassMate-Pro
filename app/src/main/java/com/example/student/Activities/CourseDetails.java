package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.student.Adapters.CoursePdfAdapter;
import com.example.student.Models.FileModel;
import com.example.student.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseDetails extends AppCompatActivity {
    com.example.student.databinding.ActivityCourseDetailsBinding binding;
    String key;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<FileModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.example.student.databinding.ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        key=getIntent().getStringExtra("key");
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        list=new ArrayList<>();
        CoursePdfAdapter adapter=new CoursePdfAdapter(list,this);
        binding.courceRv.setAdapter(adapter);
        binding.courceRv.setLayoutManager(new LinearLayoutManager(this));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("classRooms")
                .child(key)
                .child("courseDetails")
                .child("files")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1:snapshot.getChildren()) {
                            FileModel model = snapshot1.getValue(FileModel.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        database.getReference().child("classRooms")
                .child(key)
                .child("courseDetails").child("mainImage").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isDestroyed()){
                            return;
                        }
                        Glide.with(CourseDetails.this).load(snapshot.getValue()).placeholder(R.drawable.placeholder).into(binding.mainImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}