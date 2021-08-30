package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityHostAtournamentBinding;

public class Host_a_tournament extends AppCompatActivity {

    private ActivityHostAtournamentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostAtournamentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    

    public void back ( View view )
    {
        finish();
    }
}