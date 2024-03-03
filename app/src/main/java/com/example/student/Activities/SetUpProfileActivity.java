package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivitySetUpProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetUpProfileActivity extends AppCompatActivity {

    ActivitySetUpProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySetUpProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        String uid=auth.getUid();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Updating Your Profile");
        dialog.setCancelable(false);

        binding.setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String college=binding.collgeED.getText().toString();
                String name=binding.nameEd.getText().toString();
                String email=binding.emailED.getText().toString();
                String key=binding.keyEd.getText().toString();
                String rollNo=binding.rollEd.getText().toString();
                String program=binding.programEd.getText().toString();

                if (college.isEmpty()){
                    binding.collgeED.setError("This field is Required");
                    return;
                } if (name.isEmpty()){
                    binding.nameEd.setError("This field is Required");
                    return;
                }  if (email.isEmpty()) {
                    binding.emailED.setError("This field is Required");
                    return;
                } if (key.isEmpty()){
                    binding.keyEd.setError("This field is Required");
                    return;
                }  if (rollNo.isEmpty()) {
                    binding.rollEd.setError("This field is Required");
                    return;
                } if (program.isEmpty()){
                    binding.programEd.setError("This field is Required");
                    return;
                }

        dialog.show();
        String phone=auth.getCurrentUser().getPhoneNumber();
        UserModel user=new UserModel(college,name,email,rollNo,program,uid,key,phone,"No Image");
        user.setProfileSetup(true);
        database.getReference().child("classRooms")
        .child(key)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    database.getReference().child("classRooms").child(key)
                            .child("Students")
                            .child(uid)
                            .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("Student")
                                            .child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dialog.dismiss();
                                                    startActivity(new Intent(SetUpProfileActivity.this, MainActivity.class));
                                                    finishAffinity();
                                                    Toast.makeText(SetUpProfileActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                }else {
                    binding.keyEd.setError("ClassRoom with this key doesnt Exixts");
                    dialog.dismiss();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
        });

    }
}