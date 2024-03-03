package com.example.student.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.student.R;
import com.example.student.databinding.ActivityOpenNoticeBinding;

public class OpenNoticeActivity extends AppCompatActivity {
    ActivityOpenNoticeBinding binding;
    String title;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOpenNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("description");

        binding.heading.setText(title);
        binding.body.setText(description);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}