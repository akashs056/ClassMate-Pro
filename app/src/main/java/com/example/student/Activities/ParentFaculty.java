package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.student.Adapters.FacultyAdapter;
import com.example.student.Models.FacultyModel;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityParentFacultyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentFaculty extends AppCompatActivity {
    ActivityParentFacultyBinding binding;
    ArrayList<FacultyModel> list;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String uid;
    FacultyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityParentFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        uid=getIntent().getStringExtra("uid");

        adapter=new FacultyAdapter(list,this);
        binding.facultyRv.setAdapter(adapter);
        binding.facultyRv.setLayoutManager(new LinearLayoutManager(this));

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
                        UserModel user=snapshot.getValue(UserModel.class);

                        database.getReference().child("classRooms")
                                .child(user.getKey())
                                .child("faculties")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                                String key=snapshot1.getKey();
                                                database.getReference().child("faculty")
                                                        .child(key).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if (snapshot.exists()) {
                                                                    FacultyModel faculty = snapshot.getValue(FacultyModel.class);
                                                                    list.add(faculty);
                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}