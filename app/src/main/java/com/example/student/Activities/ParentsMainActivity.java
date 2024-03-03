package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.student.Models.ParentsModel;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class ParentsMainActivity extends AppCompatActivity {
    com.example.student.databinding.ActivityParentsMainBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String key;

    String StudentUid;
    String uid;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.example.student.databinding.ActivityParentsMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        database.getReference().child("Parents").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ParentsModel parentsModel=snapshot.getValue(ParentsModel.class);
                StudentUid=parentsModel.getStudentUID();
                database.getReference().child("Student").child(StudentUid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user=snapshot.getValue(UserModel.class);
                        key=user.getKey();
                        uid=user.getUid();
                        Glide.with(ParentsMainActivity.this).load(user.getProfileImage()).placeholder(R.drawable.user).into(binding.profile);
                        binding.name.setText(user.getName());
                        binding.rollNo.setText(user.getRollNo());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.testmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(ParentsMainActivity.this, TestMarks.class);
            intent.putExtra("key",key);
            startActivity(intent);
            }
        });

        binding.complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(ParentsMainActivity.this, ParentComplaintActivity.class);
            intent.putExtra("key",key);
            intent.putExtra("uid",uid);
            startActivity(intent);
            }
        });

        binding.studentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Parents").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ParentsModel parentsModel=snapshot.getValue(ParentsModel.class);
                        String uid=parentsModel.getStudentUID();
                        Intent intent=new Intent(ParentsMainActivity.this,ParentStudentDetails.class);
                        intent.putExtra("uid",uid);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        binding.courseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(ParentsMainActivity.this, CourseDetails.class);
            intent.putExtra("key",key);
            startActivity(intent);
            }
        });

        binding.faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParentsMainActivity.this, ParentFaculty.class);
                intent.putExtra("uid",StudentUid);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            auth.signOut();
                            startActivity(new Intent(ParentsMainActivity.this,FirstActivity.class));
                            finishAffinity();
                        }
                    }
                });
            }
        });

        binding.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParentsMainActivity.this, NoticeActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    String token=task.getResult();
                    updateFCMTokenInDatabase(token);
                }
            }
        });
    }
    private void updateFCMTokenInDatabase(String token) {
        String uid = auth.getUid();
        if (uid != null) {
            database.getReference().child("Parents").child(uid).child("studentUID").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String StudentUid=snapshot.getValue(String.class);
                        database.getReference().child("Student").child(StudentUid).child("key").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String key=snapshot.getValue(String.class);
                                    database.getReference().child("classRooms").child(key).child("Parent").child(uid).child("fcmToken").setValue(token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            database.getReference().child("classRooms").child(key).child("Students").child(StudentUid).child("parentFcmToken").setValue(token);
                                            Toast.makeText(ParentsMainActivity.this, "done", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        areNotificationsEnabled();
        requestNotificationPermission();
    }
    private void requestNotificationPermission() {
        if (!areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android Oreo and above
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        }
    }
    private boolean areNotificationsEnabled() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        return notificationManagerCompat.areNotificationsEnabled();
    }

}