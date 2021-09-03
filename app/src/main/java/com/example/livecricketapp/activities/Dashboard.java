package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void back(View view) {
        finish();
    }

    public void edit_content ( View view )
    {

    }
}