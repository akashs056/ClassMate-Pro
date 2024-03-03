package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.student.Adapters.SubjectApater;
import com.example.student.Models.ClassRoomModel;
import com.example.student.Models.SubjectModel;
import com.example.student.R;
import com.example.student.databinding.ActivityMyClassRoomBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyClassRoom extends AppCompatActivity {
    ActivityMyClassRoomBinding binding;
    ArrayList<SubjectModel> list;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyClassRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        list=new ArrayList<>();
        String uid=auth.getUid();
        key=getIntent().getStringExtra("key");
        SubjectApater adapter=new SubjectApater(list,this,key);
        binding.subjectRV.setAdapter(adapter);
        binding.subjectRV.setLayoutManager(new LinearLayoutManager(this));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=binding.key.getText().toString();
                ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("ClassRoom Key",key);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(MyClassRoom.this, "Key copied to clipboard", Toast.LENGTH_SHORT).show();

                binding.copy.setText("COPIED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.copy.setText("COPY");
                    }
                },10000);
            }
        });


        database.getReference().child("classRooms")
                .child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ClassRoomModel model=snapshot.getValue(ClassRoomModel.class);
                        binding.key.setText(model.getKey());
                        binding.clasname.setText(model.getClassName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        database.getReference().child("classRooms")
                .child(key)
                .child("subjects").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if (snapshot.exists()) {
                            for(DataSnapshot snapshot1:snapshot.getChildren()) {
                                SubjectModel model = snapshot1.getValue(SubjectModel.class);
                                list.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}