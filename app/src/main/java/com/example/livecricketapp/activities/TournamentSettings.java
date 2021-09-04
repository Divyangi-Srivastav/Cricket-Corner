package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityTournamentSettingsBinding;

public class TournamentSettings extends AppCompatActivity {

    private ActivityTournamentSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void back(View view) {
        finish();
    }
}