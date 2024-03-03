package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.student.Adapters.ComplaintAdapter;
import com.example.student.Models.ComplaintModel;
import com.example.student.R;
import com.example.student.databinding.ActivityCompalintBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompalintActivity extends AppCompatActivity {
    ActivityCompalintBinding binding;
    String key;
    FirebaseAuth auth;
    FirebaseDatabase database;

    ArrayList<ComplaintModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCompalintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        String uid=auth.getUid();
        database=FirebaseDatabase.getInstance();
        key=getIntent().getStringExtra("key");

        list=new ArrayList<>();
        ComplaintAdapter adapter=new ComplaintAdapter(list,this,key);
        binding.complaintRv.setAdapter(adapter);
        binding.complaintRv.setLayoutManager(new LinearLayoutManager(this));

        database.getReference().child("classRooms").child(key)
                .child("Students").child(uid).child("complaints").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            list.clear();
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                ComplaintModel model=snapshot1.getValue(ComplaintModel.class);
                                list.add(model);
                                adapter.notifyDataSetChanged();
                            }
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