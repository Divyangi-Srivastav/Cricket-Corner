package com.example.livecricketapp.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.livecricketapp.databinding.ActivityHomeUserBinding;

public class HomeActivityUser extends AppCompatActivity {

    private ActivityHomeUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}