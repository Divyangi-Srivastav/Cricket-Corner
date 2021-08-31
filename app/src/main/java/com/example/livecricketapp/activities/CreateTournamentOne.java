package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.livecricketapp.adapters.CreateTournamentAdapter;
import com.example.livecricketapp.databinding.ActivityCreateTournamentOneBinding;

public class CreateTournamentOne extends AppCompatActivity {

    private ActivityCreateTournamentOneBinding binding;
    private CreateTournamentAdapter createTournamentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentOneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.numberOfTeams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createTournamentAdapter = new CreateTournamentAdapter(CreateTournamentOne.this , Integer.parseInt(s.toString()));
                binding.recyclerView.setAdapter(createTournamentAdapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void clear ( View view )
    {

    }

}