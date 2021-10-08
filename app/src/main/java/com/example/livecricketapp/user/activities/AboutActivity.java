package com.example.livecricketapp.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}