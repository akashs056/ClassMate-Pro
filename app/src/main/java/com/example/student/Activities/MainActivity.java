package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.student.Adapters.FacultyAdapter;
import com.example.student.Models.FacultyModel;
import com.example.student.Models.NoticeModel;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<FacultyModel> list;
    String key;
    String title;
    String description;
    String className;
    FacultyAdapter adapter;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=auth.getUid();
        list=new ArrayList<>();
        adapter=new FacultyAdapter(list,this);
        binding.facultyRv.setAdapter(adapter);
        binding.facultyRv.setLayoutManager(new LinearLayoutManager(this));

        binding.releaseNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, NoticeActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        binding.UpdateTextMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, TestMarks.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        binding.MyClasRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MyClassRoom.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        binding.updateCourceDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CourseDetails.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        binding.joinCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,JoinCall.class);
                startActivity(intent);
            }
        });

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });


        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        database.getReference().child("Student")
                .child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isDestroyed()){
                            return;
                        }
                        UserModel user=snapshot.getValue(UserModel.class);
                        key=user.getKey();
                        if (user.getProfileImage()!=null && !user.getProfileImage().equals("No Image")) {
                            Glide.with(MainActivity.this).load(user.getProfileImage())
                                    .placeholder(R.drawable.user)
                                    .into(binding.profileImage);
                        }
                        binding.name.setText(user.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        database.getReference().child("Student").child(uid)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModel model=snapshot.getValue(UserModel.class);
                                assert model != null;
                                database.getReference().child("classRooms").child(model.getKey()).child("courseDetails").child("notices")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    DataSnapshot lastNoticeSnapshot = null;
                                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                        lastNoticeSnapshot = snapshot1;
                                                    }

                                                    if (lastNoticeSnapshot != null) {
                                                        NoticeModel model = lastNoticeSnapshot.getValue(NoticeModel.class);
                                                        title=model.getNoticeTitle();
                                                        description=model.getNoticeDescription();
                                                        binding.newNotice.setText(title);
                                                        binding.description.setText(description);
                                                    }
                                                }
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
        binding.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        binding.noticeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, OpenNoticeActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("description",description);
                startActivity(intent);
            }
        });

        database.getReference().child("Student")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user=snapshot.getValue(UserModel.class);

                        database.getReference().child("classRooms")
                                .child(user.getKey())
                                .child("faculties")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                                String key=snapshot1.getKey();
                                                database.getReference().child("faculty")
                                                        .child(key).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if (snapshot.exists()) {
                                                                    FacultyModel faculty = snapshot.getValue(FacultyModel.class);
                                                                    list.add(faculty);
                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                            }

                                        }
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

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    String token=task.getResult();
                    Log.d("MyToken",token);
                    updateFCMTokenInDatabase(token);

                }
            }
        });

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
    private void updateFCMTokenInDatabase(String token) {
        String uid = auth.getUid();
        if (uid != null) {
            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            database.getReference().child("Student").child(uid).child("fcmToken").setValue(token)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference().child("Student").child(uid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserModel  userModel=snapshot.getValue(UserModel.class);
                                    database.getReference().child("classRooms").child(userModel.getKey()).child("Students").child(uid).child("fcmToken").setValue(token);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
        }
    }
}