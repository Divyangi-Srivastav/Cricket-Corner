package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.AdsRequest;
import com.example.livecricketapp.activities.Dashboard;
import com.example.livecricketapp.activities.HomeActivity;
import com.example.livecricketapp.databinding.ActivityTeamsAndMatchesBinding;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.TournamentInfo;
import com.example.livecricketapp.user.adapters.TeamsNamesAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeamsAndMatches extends AppCompatActivity implements TeamsNamesAdapter.On_Click {

    private ActivityTeamsAndMatchesBinding binding;
    private TournamentInfo info;
    private FirebaseFirestore db;
    private TeamsNamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamsAndMatchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        info = (TournamentInfo) getIntent().getSerializableExtra("info");

        db = FirebaseFirestore.getInstance();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(TeamsAndMatches.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(TeamsAndMatches.this , Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    public void back(View view) {
        finish();
    }

    private void set_data()
    {
        binding.tournamentName.setText(info.getTournamentName());
        binding.noOfTeams.setText("Number of Teams : " + String.valueOf(info.getNumber_of_teams()));
        binding.startDate.setText("Start Date : " + info.getStart_date());
        binding.endDate.setText("End Date : " + info.getEnd_date());
        get_team_data();
    }

    private void get_team_data( )
    {
        db.collection("Tournament Team Info")
                .document(info.getTournamentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        AllTeamInfo allTeamInfo = documentSnapshot.toObject(AllTeamInfo.class);
                        adapter = new TeamsNamesAdapter(TeamsAndMatches.this,allTeamInfo.getTeamInfos(),TeamsAndMatches.this::open_team);
                        binding.recyclerTeams.setAdapter(adapter);
                        binding.recyclerTeams.setLayoutManager(new LinearLayoutManager(TeamsAndMatches.this));
                    }
                });
    }

    @Override
    public void open_team(int a) {

    }
}