package com.example.student.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.student.Models.ComplaintModel;
import com.example.student.R;
import com.example.student.databinding.ActivityVoiceYourConcernBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VoiceYourConcern extends AppCompatActivity {
    ActivityVoiceYourConcernBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityVoiceYourConcernBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=auth.getUid();
        String key=getIntent().getStringExtra("key");

        setStatusBarColor();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=binding.title.getText().toString();
                String description=binding.description.getText().toString();
                ComplaintModel model=new ComplaintModel(uid,title,description);
                database.getReference().child("classRooms")
                        .child(key).child("ConcerHub").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(VoiceYourConcern.this, "Thanks for Your Concern", Toast.LENGTH_SHORT).show();
                                binding.title.setText("");
                                binding.description.setText("");
                            }
                        });
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