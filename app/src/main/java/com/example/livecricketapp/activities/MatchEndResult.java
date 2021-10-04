package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.databinding.ActivityMatchEndResultBinding;

public class MatchEndResult extends AppCompatActivity {

    private ActivityMatchEndResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchEndResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void back(View view) {
        finish();
    }

}