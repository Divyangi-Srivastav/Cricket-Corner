package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityTeamSettingsBinding;

public class TeamSettings extends AppCompatActivity {

    private ActivityTeamSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}