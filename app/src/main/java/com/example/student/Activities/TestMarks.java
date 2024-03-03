package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.student.Adapters.TestAdapter;
import com.example.student.Models.TestModel;
import com.example.student.R;
import com.example.student.databinding.ActivityTestMarksBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestMarks extends AppCompatActivity {

    ActivityTestMarksBinding binding;
    String key;
    FirebaseDatabase database;
    ArrayList<TestModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTestMarksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        key=getIntent().getStringExtra("key");
        list=new ArrayList<>();
        TestAdapter adapter=new TestAdapter(list,this);
        binding.testrv.setAdapter(adapter);
        binding.testrv.setLayoutManager(new LinearLayoutManager(this));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("classRooms")
                .child(key)
                .child("courseDetails")
                .child("TestMarks").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            list.clear();
                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                TestModel model=snapshot1.getValue(TestModel.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}