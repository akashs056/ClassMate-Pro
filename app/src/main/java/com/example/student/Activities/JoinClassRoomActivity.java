package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityJoinClassRoomBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinClassRoomActivity extends AppCompatActivity {

    ActivityJoinClassRoomBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String MainKey;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityJoinClassRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=auth.getUid();
        MainKey=getIntent().getStringExtra("key");
        dialog=new ProgressDialog(JoinClassRoomActivity.this);
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);


        binding.imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=binding.editTextText.getText().toString();
                dialog.show();
                if (!key.isEmpty()){
                    database.getReference().child("classRooms").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                database.getReference().child("Student").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            UserModel model=snapshot.getValue(UserModel.class);
                                            database.getReference().child("classRooms").child(key).child("Students").child(uid).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    database.getReference().child("Student").child(uid).child("key").setValue(key).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            database.getReference().child("classRooms").child(MainKey).child("Students").child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    dialog.dismiss();
                                                                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("key").setValue(key);
                                                                    Toast.makeText(JoinClassRoomActivity.this, "ClassRoom Changed Successfully", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(JoinClassRoomActivity.this,MainActivity.class));
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }else{
                                dialog.dismiss();
                                Toast.makeText(JoinClassRoomActivity.this, "Classroom with specified key doesnt exists", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    dialog.dismiss();
                    binding.editTextText.setError("Key is required");
                    return;
                }
            }
        });





    }
}