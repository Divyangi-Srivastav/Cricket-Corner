package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityTeamsAndMatchesBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.SingleTeamInfo;
import com.example.livecricketapp.model.TournamentInfo;
import com.example.livecricketapp.user.adapters.MatchesAdapter;
import com.example.livecricketapp.user.adapters.TeamsNamesAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeamsAndMatches extends AppCompatActivity implements TeamsNamesAdapter.On_Click , MatchesAdapter.On_click {

    private ActivityTeamsAndMatchesBinding binding;
    private TournamentInfo info;
    private FirebaseFirestore db;
    private TeamsNamesAdapter adapter;
    private MatchesAdapter matchesAdapter;
    private List<SingleTeamInfo> teamInfoList = new ArrayList<>();
    private List<SingleMatchInfo> matchInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamsAndMatchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        info = (TournamentInfo) getIntent().getSerializableExtra("info");

        db = FirebaseFirestore.getInstance();

        set_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(TeamsAndMatches.this , HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(TeamsAndMatches.this , SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(TeamsAndMatches.this , DashboardUser.class);
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
        get_match_data();
        String text = "<b>NOTE : </b>You can donate participation fees on behalf of your favourite team and support them win tournament.";
        binding.note.setText(Html.fromHtml(text));
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
                        if ( allTeamInfo.getTeamInfos() != null )
                        {
                            teamInfoList = allTeamInfo.getTeamInfos();
                            adapter = new TeamsNamesAdapter(TeamsAndMatches.this,teamInfoList,TeamsAndMatches.this::open_team);
                            binding.recyclerTeams.setAdapter(adapter);
                            binding.recyclerTeams.setLayoutManager(new LinearLayoutManager(TeamsAndMatches.this));
                        }
                    }
                });
    }

    private void get_match_data()
    {
        db.collection("Match Info")
                .document(info.getTournamentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        AllMatchInfo allMatchInfo = documentSnapshot.toObject(AllMatchInfo.class);
                        if ( allMatchInfo.getMatchInfos() != null ) {
                            matchInfoList = allMatchInfo.getMatchInfos();
                            matchesAdapter = new MatchesAdapter(TeamsAndMatches.this, matchInfoList, TeamsAndMatches.this::move_to_match_info);
                            binding.recyclerMatches.setAdapter(matchesAdapter);
                            binding.recyclerMatches.setLayoutManager(new LinearLayoutManager(TeamsAndMatches.this));
                        }
                    }
                });
    }

    @Override
    public void open_team(int a) {
        Intent intent = new Intent(this, TeamActivity.class);
        intent.putExtra("team",teamInfoList.get(a));
        startActivity(intent);
    }

    @Override
    public void move_to_match_info(int a) {
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("tour",info.getTournamentId());
        intent.putExtra("match",matchInfoList.get(a).getMatchNo());
        intent.putExtra("over" , info.getTotal_overs());
        startActivity(intent);
    }
}