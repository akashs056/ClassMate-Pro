package com.example.student.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.student.Models.ParentsModel;
import com.example.student.Models.UserModel;
import com.example.student.R;
import com.example.student.databinding.ActivityParentRegisterBinding;
import com.example.student.databinding.ActivityParentsLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentRegisterActivity extends AppCompatActivity {
    ActivityParentRegisterBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityParentRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentRegisterActivity.this,parentsLogin.class));
            }
        });

        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StudentRollNo=binding.nameET.getText().toString();
                String StudentUid=binding.professionET.getText().toString();
                if (StudentRollNo.isEmpty()){
                    binding.nameET.setError("This Field is Mandatory");
                    return;
                }
                if (StudentUid.isEmpty()){
                    binding.professionET.setError("This field is Mandatory");
                    return;
                }
                database.getReference().child("Student").child(StudentUid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            UserModel user=snapshot.getValue(UserModel.class);
                            if (snapshot.child("rollNo").getValue(String.class).equals(StudentRollNo)){
                                String email=binding.emailET.getText().toString();
                                String password=binding.passwordET.getText().toString();
                                if (email.isEmpty()){
                                    binding.emailET.setError("This Field is Mandatory");
                                    return;
                                }
                                if (password.isEmpty()){
                                    binding.passwordET.setError("This field is Mandatory");
                                    return;
                                }
                                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            String id=task.getResult().getUser().getUid();
                                            ParentsModel model=new ParentsModel(StudentUid,StudentRollNo,email,password,id);
                                            database.getReference().child("Parents").child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    database.getReference().child("Student").child(StudentUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()){
                                                                UserModel user=snapshot.getValue(UserModel.class);
                                                                database.getReference().child("classRooms").child(user.getKey())
                                                                        .child("Parent").child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                startActivity(new Intent(ParentRegisterActivity.this, ParentsMainActivity.class));
                                                                                finishAffinity();
                                                                                Toast.makeText(ParentRegisterActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                    startActivity(new Intent(ParentRegisterActivity.this, ParentsMainActivity.class));
                                                    finishAffinity();
                                                    Toast.makeText(ParentRegisterActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });

                            }else{
                                Toast.makeText(ParentRegisterActivity.this, "Incorrect Login Details", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            binding.professionET.setError("Incorrect Uid");
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