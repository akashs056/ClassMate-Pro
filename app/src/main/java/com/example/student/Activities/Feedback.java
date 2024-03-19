package com.example.student.Activities;



import static android.app.PendingIntent.getActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.student.Models.ComplaintModel;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityFeedbackBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {
    ActivityFeedbackBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String name,image,facultyUid,StudentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("image");
        facultyUid = getIntent().getStringExtra("facultyUid");
        Glide.with(this).load(image).placeholder(R.drawable.user).into(binding.profileImage);
        binding.studentname.setText(name);
        setStatusBarColor();
        database.getReference().child("Student")
                .child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isDestroyed()) {
                            return;
                        }
                        UserModel user=snapshot.getValue(UserModel.class);
//                        StudentName=user.getName();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = binding.message.getText().toString();
                ComplaintModel model = new ComplaintModel(StudentName, "", feedback);
                database.getReference().child("faculty")
                        .child(facultyUid).child("MyFeedbacks")
                        .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                binding.message.setText("");
                                binding.professionET.setText("");
                                binding.nameET.setText("");
                                Toast.makeText(Feedback.this, "Feedback sent Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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