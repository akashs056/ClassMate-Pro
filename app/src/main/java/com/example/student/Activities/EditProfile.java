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

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {
    com.example.student.databinding.ActivityEditProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Uri selectedImage;
    FirebaseStorage storage;
    ProgressDialog dialog;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.example.student.databinding.ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        key=getIntent().getStringExtra("key");
        String uid=auth.getUid();
        dialog=new ProgressDialog(EditProfile.this);
        dialog.setMessage("Updating Profile....");
        dialog.setCancelable(false);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("Student")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isDestroyed()){
                            return;
                        }
                        UserModel user=snapshot.getValue(UserModel.class);
                        binding.nameEd.setText(user.getName());
                        binding.emailED.setText(user.getEmail());
                        binding.collgeED.setText(user.getCollege());
                        binding.rollNoEd.setText(user.getRollNo());
                        binding.phoneED.setText(user.getPhone());
                        binding.programEd.setText(user.getProgram());

                        Glide.with(EditProfile.this).load(user.getProfileImage())
                                .placeholder(R.drawable.user)
                                .into(binding.profileImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (selectedImage!=null) {
                    String college = binding.collgeED.getText().toString();
                    String name = binding.nameEd.getText().toString();
                    String email = binding.emailED.getText().toString();
                    String rollno = binding.rollNoEd.getText().toString();
                    String program = binding.programEd.getText().toString();

                    StorageReference reference=storage.getReference().child("StudentsProfile").child(uid);
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String image=uri.toString();
                                        database.getReference().child("Student").child(uid).child("profileImage").setValue(image);
                                        database.getReference().child("classRooms").child(key).child("Students").child(uid).child("profileImage").setValue(image);
                                        Toast.makeText(EditProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
                    });

                    database.getReference().child("Student").child(uid).child("college").setValue(college);
                    database.getReference().child("Student").child(uid).child("rollNo").setValue(rollno);
                    database.getReference().child("Student").child(uid).child("email").setValue(email);
                    database.getReference().child("Student").child(uid).child("name").setValue(name);
                    database.getReference().child("Student").child(uid).child("program").setValue(program);

                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("college").setValue(college);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("rollNo").setValue(rollno);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("email").setValue(email);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("name").setValue(name);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("program").setValue(program);


                }else {
                    String college = binding.collgeED.getText().toString();
                    String name = binding.nameEd.getText().toString();
                    String email = binding.emailED.getText().toString();
                    String rollno = binding.rollNoEd.getText().toString();
                    String program = binding.programEd.getText().toString();

                    database.getReference().child("Student").child(uid).child("college").setValue(college);
                    database.getReference().child("Student").child(uid).child("rollNo").setValue(rollno);
                    database.getReference().child("Student").child(uid).child("email").setValue(email);
                    database.getReference().child("Student").child(uid).child("name").setValue(name);
                    database.getReference().child("Student").child(uid).child("program").setValue(program);

                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("college").setValue(college);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("rollNo").setValue(rollno);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("email").setValue(email);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("name").setValue(name);
                    database.getReference().child("classRooms").child(key).child("Students").child(uid).child("program").setValue(program);

                    Toast.makeText(EditProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null&& data.getData()!=null){
            binding.profileImage.setImageURI(data.getData());
            selectedImage=data.getData();
        }
    }
}