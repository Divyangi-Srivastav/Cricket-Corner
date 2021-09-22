package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Binder;
import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityStartLiveStreamingBinding;

public class StartLiveStreaming extends AppCompatActivity {

    private ActivityStartLiveStreamingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartLiveStreamingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}