package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

               if (check_empty()) {
                   TournamentInfo tournamentInfo = new TournamentInfo();
                   tournamentInfo.setTournamentId(getIntent().getStringExtra("id"));
                   tournamentInfo.setTournamentName(binding.tournamentName.getText().toString());
                   tournamentInfo.setNumber_of_teams(Integer.parseInt(binding.numberOfTeams.getText().toString()));
                   tournamentInfo.setFees(Float.parseFloat(binding.fees.getText().toString()));
                   tournamentInfo.setNo_of_matches_day(Integer.parseInt(binding.numberOfMatches.getText().toString()));
                   tournamentInfo.setStart_date(binding.startDateAndTime.getText().toString());
                   tournamentInfo.setEnd_date(binding.endDateAndTime.getText().toString());
                   tournamentInfo.setTeamNames(createTournamentAdapter.get_list());
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       tournamentInfo.setMatch_start_time(String.valueOf(binding.timePicker1.getHour()) + " : " + String.valueOf(binding.timePicker1.getMinute()));
                       tournamentInfo.setMatch_end_time(String.valueOf(binding.timePicker2.getHour()) + " : " + String.valueOf(binding.timePicker2.getMinute()));
                   }
                   upload_to_firestore(tournamentInfo);
               }
                break;
        }

    }

    public Boolean check_empty ()
    {
        if ( binding.tournamentName.getText().toString().isEmpty() )
        {
            binding.tournamentName.setError("Enter the tournament Name");
            return false;
        }
        else if ( binding.numberOfTeams.getText().toString().isEmpty() )
        {
            binding.numberOfTeams.setError("Enter the Number of teams");
            return false;
        }
        else if ( binding.fees.getText().toString().isEmpty() )
        {
            binding.fees.setError("Enter the tournament Fees");
            return false;
        }
        else if ( binding.numberOfMatches.getText().toString().isEmpty() )
        {
            binding.numberOfMatches.setError("Enter the Number of Matches");
            return false;
        }
        else if ( binding.startDateAndTime.getText().toString().isEmpty() )
        {
            binding.startDateAndTime.setError("Enter the Start Date and Time");
            return false;
        }
        else if ( binding.endDateAndTime.getText().toString().isEmpty() )
        {
            binding.endDateAndTime.setError("Enter the Start Date and Time");
            return false;
        }
        else {
            return true;
        }
    }

    public void upload_to_firestore ( TournamentInfo tournamentInfo )
    {
        db.collection("Tournament").document(tournamentInfo.getTournamentId()).set(tournamentInfo);
    }

}