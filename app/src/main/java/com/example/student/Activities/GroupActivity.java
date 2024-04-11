package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.student.Adapters.GroupChatAdapter;
import com.example.student.Models.ClassRoomModel;
import com.example.student.Models.GroupChatModel;
import com.example.student.R;
import com.example.student.databinding.ActivityGroupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    ActivityGroupBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String key;
    ArrayList<GroupChatModel> list;
    String className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=auth.getUid();
        key=getIntent().getStringExtra("key");

        database.getReference().child("classRooms").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ClassRoomModel model=snapshot.getValue(ClassRoomModel.class);
                    binding.textView.setText(model.getClassName());
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


        list=new ArrayList<>();
        GroupChatAdapter adapter=new GroupChatAdapter(list,this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database.getReference().child("classRooms").child(key).child("GroupChats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        GroupChatModel model=snapshot1.getValue(GroupChatModel.class);
                        list.add(model);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}