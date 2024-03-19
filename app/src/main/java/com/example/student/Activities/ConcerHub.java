package com.example.student.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.student.Adapters.ConcernHubAdapter;
import com.example.student.Adapters.NoticeAdapter;
import com.example.student.Models.ComplaintModel;
import com.example.student.Models.FileModel;
import com.example.student.Models.NoticeModel;
import com.example.student.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class ConcerHub extends AppCompatActivity {
    com.example.student.databinding.ActivityConcerHubBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<ComplaintModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= com.example.student.databinding.ActivityConcerHubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String key=getIntent().getStringExtra("key");
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        list=new ArrayList<>();
        ConcernHubAdapter adapter=new ConcernHubAdapter(list,this);
        binding.escalationRv.setAdapter(adapter);
        binding.escalationRv.setLayoutManager(new LinearLayoutManager(this));
        setStatusBarColor();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConcerHub.this, VoiceYourConcern.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
        database.getReference().child("classRooms")
                .child(key).child("ConcerHub")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            list.clear();
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                ComplaintModel model=snapshot1.getValue(ComplaintModel.class);
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
    private void setStatusBarColor() {
        Window window = getWindow();
        if (window != null) {
            int statusBarColor = ContextCompat.getColor(this, R.color.orange);
            window.setStatusBarColor(statusBarColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = window.getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}