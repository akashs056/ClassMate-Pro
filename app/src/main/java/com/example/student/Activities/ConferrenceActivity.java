package com.example.student.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.student.R;
import com.example.student.databinding.ActivityConferrenceBinding;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;

public class ConferrenceActivity extends AppCompatActivity {

    ActivityConferrenceBinding binding;
    String meetingId,userId,name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConferrenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        meetingId=getIntent().getStringExtra("meetingId");
        userId=getIntent().getStringExtra("userId");
        name=getIntent().getStringExtra("name");

        binding.meetingId.setText("Meeting Id : "+ meetingId);

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=binding.meetingId.getText().toString();
                ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("ClassRoom Key",key);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(ConferrenceActivity.this, "MeetingId copied to clipboard", Toast.LENGTH_SHORT).show();

                binding.copy.setText("COPIED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.copy.setText("COPY");
                    }
                },10000);
            }
        });
        addFragment();
    }
    public void addFragment() {
        long appID = AppConstants.appId;
        String appSign = AppConstants.appSign;

        ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userId, name,meetingId,config);
        config.turnOnCameraWhenJoining=false;
        config.turnOnMicrophoneWhenJoining=false;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow();
    }
}