package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.CreateTournamentAdapter;
import com.example.livecricketapp.databinding.ActivityCreateTournamentOneBinding;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateTournamentOne extends AppCompatActivity implements View.OnClickListener{

    private ActivityCreateTournamentOneBinding binding;
    private CreateTournamentAdapter createTournamentAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentOneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        binding.btnSubmit.setOnClickListener(this::onClick);

        binding.numberOfTeams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("") ) {
                    createTournamentAdapter = new CreateTournamentAdapter(CreateTournamentOne.this, Integer.parseInt(s.toString()));
                    binding.recyclerView.setAdapter(createTournamentAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
                } else {
                    createTournamentAdapter = new CreateTournamentAdapter(CreateTournamentOne.this, 0);
                    binding.recyclerView.setAdapter(createTournamentAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void clear(View view) {
        switch (view.getId()) {

            case R.id.tournament_name_cross:
                binding.tournamentName.getText().clear();
                break;
            case R.id.number_of_teams_cross:
                binding.numberOfTeams.getText().clear();
                break;
            case R.id.fees_cross:
                binding.fees.getText().clear();
                break;
            case R.id.number_of_matches_cross:
                binding.numberOfMatches.getText().clear();
                break;
            case R.id.start_date_and_time_cross:
                binding.startDateAndTime.getText().clear();
                break;
            case R.id.end_date_and_time_cross:
                binding.endDateAndTime.getText().clear();
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_submit:



                TournamentInfo tournamentInfo = new TournamentInfo();


                break;
        }

    }

    public void check_empty ()
    {
        
    }

}