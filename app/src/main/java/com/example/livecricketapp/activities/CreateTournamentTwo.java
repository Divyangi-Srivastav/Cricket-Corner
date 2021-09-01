package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.databinding.ActivityCreateTournamentTwoBinding;

public class CreateTournamentTwo extends AppCompatActivity {

    private ActivityCreateTournamentTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void clear ( View view )
    {

    }

    public void back(View view) {
        finish();
    }

}