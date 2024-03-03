package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.student.Adapters.NoticeAdapter;
import com.example.student.Models.NoticeModel;
import com.example.student.R;
import com.example.student.databinding.ActivityNoticeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NoticeActivity extends AppCompatActivity {
    ActivityNoticeBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String key;
    ArrayList<NoticeModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        key=getIntent().getStringExtra("key");
        list=new ArrayList<>();
        NoticeAdapter adapter=new NoticeAdapter(list,this);
        binding.testrv.setAdapter(adapter);
        binding.testrv.setLayoutManager(new LinearLayoutManager(this));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("classRooms")
                .child(key).child("courseDetails").child("notices")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            list.clear();
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                NoticeModel model=snapshot1.getValue(NoticeModel.class);
                                list.add(model);
                            }
                            Collections.reverse(list);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}