package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityHostMatchTwoBinding;

public class HostMatchActivityTwo extends AppCompatActivity {

    private ActivityHostMatchTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostMatchTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}