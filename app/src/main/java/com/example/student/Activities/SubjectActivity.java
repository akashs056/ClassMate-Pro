package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.student.Adapters.FIleAdapter;
import com.example.student.Models.FileModel;
import com.example.student.R;
import com.example.student.databinding.ActivitySubjectBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    ActivitySubjectBinding binding;
    String key;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<FileModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        binding=ActivitySubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        key=getIntent().getStringExtra("key");
        String subjectName=getIntent().getStringExtra("subjectName");

        binding.subjectName.setText(subjectName);
        list=new ArrayList<>();
        FIleAdapter adapter=new FIleAdapter(list,this);
        binding.pdfsRv.setAdapter(adapter);
        binding.pdfsRv.setLayoutManager(new LinearLayoutManager(this));

        database.getReference().child("classRooms").child(key).child("subjects").child(subjectName)
                        .child("files")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        list.clear();
                                        if (snapshot.exists()){
                                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                                FileModel model=snapshot1.getValue(FileModel.class);
                                                list.add(model);
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

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