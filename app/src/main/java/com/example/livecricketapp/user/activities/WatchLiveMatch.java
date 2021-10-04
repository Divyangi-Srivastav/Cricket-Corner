package com.example.livecricketapp.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityWatchLiveMatchBinding;

public class WatchLiveMatch extends AppCompatActivity {

    private ActivityWatchLiveMatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchLiveMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}