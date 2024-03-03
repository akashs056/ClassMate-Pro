package com.example.student.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.student.R;
import com.example.student.databinding.ActivityJoinCallBinding;

import java.util.UUID;

public class JoinCall extends AppCompatActivity {
    ActivityJoinCallBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityJoinCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meetingId=binding.code.getText().toString();
                String name=binding.yourname.getText().toString();

                if (meetingId.length()!=10){
                    binding.code.setError("Invalid Meeting ID");
                    binding.code.requestFocus();
                    return;
                }
                if (name.isEmpty()){
                    binding.yourname.setError("Name is Mandatory");
                }
                startMeeting(meetingId,name);
            }
        });
    }
    void startMeeting(String meetingId,String name){
        String userId= UUID.randomUUID().toString();

        Intent intent=new Intent(JoinCall.this, ConferrenceActivity.class);
        intent.putExtra("meetingId",meetingId);
        intent.putExtra("name",name);
        intent.putExtra("userId",userId);
        startActivity(intent);
        finish();

    }
}