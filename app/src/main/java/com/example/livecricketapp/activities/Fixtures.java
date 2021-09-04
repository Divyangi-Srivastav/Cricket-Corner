package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityFixturesBinding;

public class Fixtures extends AppCompatActivity {

    private ActivityFixturesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFixturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}