package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityUpdateTeamScoreBinding;

public class UpdateTeamScore extends AppCompatActivity {

    private ActivityUpdateTeamScoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTeamScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}