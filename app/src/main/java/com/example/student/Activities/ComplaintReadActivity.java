package com.example.student.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.student.R;
import com.example.student.databinding.ActivityComplaintReadBinding;

public class ComplaintReadActivity extends AppCompatActivity {
    ActivityComplaintReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String title=getIntent().getStringExtra("title");
        String description=getIntent().getStringExtra("description");

        binding.title.setText(title);
        binding.description.setText(description);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}