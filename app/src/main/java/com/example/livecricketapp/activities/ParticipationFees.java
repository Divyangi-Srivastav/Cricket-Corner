package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityParticipationFeesBinding;

public class ParticipationFees extends AppCompatActivity {

    private ActivityParticipationFeesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticipationFeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}