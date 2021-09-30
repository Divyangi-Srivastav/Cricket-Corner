package com.example.livecricketapp.user.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.Dashboard;
import com.example.livecricketapp.activities.HomeActivity;
import com.example.livecricketapp.activities.UpdatePlayerScore;
import com.example.livecricketapp.databinding.ActivityMatchBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.user.adapters.ScorecardAdapter;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MatchActivity extends AppCompatActivity implements View.OnClickListener , ScorecardAdapter.Update_scorecard {

    private ActivityMatchBinding binding;
    private FirebaseFirestore db;
    private String tournamentId;
    private String matchNo;
    private SingleMatchInfo singleMatchInfo;
    private ProgressDialog dialog;
    private ScorecardAdapter adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        singleMatchInfo = new SingleMatchInfo();

        binding.team1.setOnClickListener(this::onClick);
        binding.team2.setOnClickListener(this::onClick);

        db = FirebaseFirestore.getInstance();

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");

        get_realtime_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(MatchActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(MatchActivity.this, Settings.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(MatchActivity.this, Dashboard.class);
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

    public void refresh ( View view )
    {

    }

    private void get_realtime_data ()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Preparing the Score Board");
        dialog.show();

        db.collection("Match Info")
                .document(tournamentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        AllMatchInfo allMatchInfo = value.toObject(AllMatchInfo.class);
                        for (int i = 0; i < allMatchInfo.getMatchInfos().size(); i++) {
                            if (allMatchInfo.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(matchNo)) {
                                singleMatchInfo = allMatchInfo.getMatchInfos().get(i);
                                binding.team1.setText(singleMatchInfo.getTeam1Score().getTeamName());
                                binding.team2.setText(singleMatchInfo.getTeam2Score().getTeamName());
                                if (singleMatchInfo.getTeam2Score().getCards() == null && singleMatchInfo.getTeam1Score().getCards() == null) {
                                    Toast.makeText(MatchActivity.this, "Match Data Still Not Specified", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    adapter1 = new ScorecardAdapter(MatchActivity.this, singleMatchInfo.getTeam1Score(), MatchActivity.this);
                                    adapter2 = new ScorecardAdapter(MatchActivity.this, singleMatchInfo.getTeam2Score(), MatchActivity.this);
                                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MatchActivity.this));
                                    binding.recyclerView.setAdapter(adapter1);

                                    binding.totalRuns.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamRuns()) + " Scored");
                                    binding.totalWickets.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamWickets()) + " Wickets");

                                    dialog.dismiss();
                                }
                            }
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.team1:
                binding.team1.setBackgroundColor(Color.parseColor("#B9CCE2"));
                binding.team2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.totalRuns.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamRuns()) + " Scored");
                binding.totalWickets.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamWickets()) + " Wickets");
                binding.recyclerView.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                break;
            case R.id.team2:
                binding.team2.setBackgroundColor(Color.parseColor("#B9CCE2"));
                binding.team1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.totalRuns.setText(String.valueOf(singleMatchInfo.getTeam2Score().getTeamRuns()) + " Scored");
                binding.totalWickets.setText(String.valueOf(singleMatchInfo.getTeam2Score().getTeamWickets()) + " Wickets");
                binding.recyclerView.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void update_runs(int a, String teamName) {

    }

    @Override
    public void update_wickets(int a, String teamName) {

    }
}