package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.databinding.ActivityAdsRequestBinding;

public class AdsRequest extends AppCompatActivity {

    private ActivityAdsRequestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdsRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void back(View view) {
        finish();
    }

}