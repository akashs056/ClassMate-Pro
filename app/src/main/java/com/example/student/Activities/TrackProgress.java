package com.example.student.Activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.student.Models.UserModel;
import com.example.student.Models.sem1Model;
import com.example.student.Models.sem2Model;
import com.example.student.R;
import com.example.student.databinding.ActivityTrackProgressBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackProgress extends AppCompatActivity {
    ActivityTrackProgressBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String sem1Mark,sem2Mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityTrackProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=getIntent().getStringExtra("uid");
        setStatusBarColor();
        database.getReference().child("Student").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user=snapshot.getValue(UserModel.class);
                if (!user.getProfileImage().equals("No Image")){
                    Glide.with(TrackProgress.this).load(user.getProfileImage())
                            .placeholder(R.drawable.user).into(binding.profileImage);
                }
                binding.studentname.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fetchMarks(uid);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void displayMarks(String sem1Mark, String sem2Mark) {
        // Display sem1 and sem2 marks in your UI
        binding.editTextSem1.setText(sem1Mark);
        binding.editTextSem2.setText(sem2Mark);
    }
    private void fetchMarks(String uid) {
        database.getReference().child("Student").child(uid).child("Progress")
                .child("Sem1Marks").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            sem1Model sem1mark = snapshot.getValue(sem1Model.class);
                            binding.edphy.setText(sem1mark.getPhysics());
                            binding.edmath.setText(sem1mark.getMath());
                            binding.edJava.setText(sem1mark.getJava());

                            // Fetch Sem1 marks
                            double physicsMark = Double.parseDouble(sem1mark.getPhysics());
                            double mathMark = Double.parseDouble(sem1mark.getMath());
                            double javaMark = Double.parseDouble(sem1mark.getJava());

                            int totalMarksSem1 = (int) (physicsMark + mathMark + javaMark)/3;
                            // Set the total marks for Semester 1 in the TextView
                            binding.editTextSem1.setText(String.valueOf(totalMarksSem1));
                            // Determine lowest and highest scored subjects
                            String weakestSubject = "Physics"; // Initialize weakest subject
                            String strongestSubject = "Physics"; // Initialize strongest subject
                            double lowestMark = physicsMark;
                            double highestMark = physicsMark;
                            if (mathMark < lowestMark) {
                                lowestMark = mathMark;
                                weakestSubject = "Mathematics";
                            } else if (mathMark > highestMark) {
                                highestMark = mathMark;
                                strongestSubject = "Mathematics";
                            }
                            if (javaMark < lowestMark) {
                                lowestMark = javaMark;
                                weakestSubject = "Java";
                            } else if (javaMark > highestMark) {
                                highestMark = javaMark;
                                strongestSubject = "Java";
                            }
                            // Set text for Weak button
                            binding.weak.setText(weakestSubject);
                            // Set text for Strong button
                            binding.strong.setText(strongestSubject);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
        database.getReference().child("Student").child(uid).child("Progress")
                        .child("Sem2Marks").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            sem2Model sem1mark=snapshot.getValue(sem2Model.class);

                            binding.edpython.setText(sem1mark.getPython());
                            binding.eddbms.setText(sem1mark.getDbms());
                            binding.edmpp.setText(sem1mark.getMicroprocessor());

                            double pythonMark = Double.parseDouble(sem1mark.getPython());
                            double dbmsMark = Double.parseDouble(sem1mark.getDbms());
                            double microprocessorMark = Double.parseDouble(sem1mark.getMicroprocessor());
                            double totalMarksSem2 = (pythonMark + dbmsMark + microprocessorMark)/3;
                            binding.editTextSem2.setText(String.valueOf((int) totalMarksSem2));
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