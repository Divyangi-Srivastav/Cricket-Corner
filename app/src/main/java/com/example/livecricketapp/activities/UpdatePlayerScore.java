package com.example.livecricketapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityUpdatePlayerScoreBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.PlayerScoreCard;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.TeamScoreCard;
import com.example.livecricketapp.user.adapters.ScorecardAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UpdatePlayerScore extends AppCompatActivity implements ScorecardAdapter.Update_scorecard, View.OnClickListener {

    private ActivityUpdatePlayerScoreBinding binding;
    private FirebaseFirestore db;
    private String tournamentId;
    private String matchNo;
    private AllTeamInfo info;
    private AllMatchInfo allMatchInfo;
    private SingleMatchInfo singleMatchInfo;
    private ProgressDialog dialog;
    private ScorecardAdapter adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePlayerScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        info = new AllTeamInfo();
        singleMatchInfo = new SingleMatchInfo();
        allMatchInfo = new AllMatchInfo();

        db = FirebaseFirestore.getInstance();

        binding.team1.setOnClickListener(this::onClick);
        binding.team2.setOnClickListener(this::onClick);
        binding.btnSubmit.setOnClickListener(this::onClick);

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");

        get_single_match_info();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(UpdatePlayerScore.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(UpdatePlayerScore.this, Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    public void back(View view) {
        update_data_on_firebase();
        finish();
    }

    private void get_player_names() {
        db.collection("Tournament Team Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        info = documentSnapshot.toObject(AllTeamInfo.class);
                        set_player_names();
                    }
                });
    }

    private void get_single_match_info() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Preparing the Score Board");
        dialog.show();
        db.collection("Match Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allMatchInfo = documentSnapshot.toObject(AllMatchInfo.class);
                        for (int i = 0; i < allMatchInfo.getMatchInfos().size(); i++) {
                            if (allMatchInfo.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(matchNo)) {
                                singleMatchInfo = allMatchInfo.getMatchInfos().get(i);
                                binding.team1.setText(singleMatchInfo.getTeam1Score().getTeamName());
                                binding.team2.setText(singleMatchInfo.getTeam2Score().getTeamName());
                                if (singleMatchInfo.getTeam2Score().getCards() == null && singleMatchInfo.getTeam1Score().getCards() == null) {
                                    get_player_names();
                                } else {
                                    adapter1 = new ScorecardAdapter(UpdatePlayerScore.this, singleMatchInfo.getTeam1Score(), UpdatePlayerScore.this);
                                    adapter2 = new ScorecardAdapter(UpdatePlayerScore.this, singleMatchInfo.getTeam2Score(), UpdatePlayerScore.this);
                                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(UpdatePlayerScore.this));
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

    private void set_player_names() {
        for (int i = 0; i < info.getTeamInfos().size(); i++) {
            if (info.getTeamInfos().get(i).getTeamName().equalsIgnoreCase(singleMatchInfo.getTeam1Score().getTeamName())) {
                List<String> playerNames = info.getTeamInfos().get(i).getPlayerNames();
                List<PlayerScoreCard> scoreCards = new ArrayList<>();
                TeamScoreCard teamScoreCard = new TeamScoreCard();
                teamScoreCard.setTeamName(info.getTeamInfos().get(i).getTeamName());
                for (int j = 0; j < 11; j++) {
                    PlayerScoreCard card = new PlayerScoreCard();
                    card.setPlayerName(playerNames.get(j));
                    scoreCards.add(card);
                }
                teamScoreCard.setCards(scoreCards);
                singleMatchInfo.setTeam1Score(teamScoreCard);
            }
            if (info.getTeamInfos().get(i).getTeamName().equalsIgnoreCase(singleMatchInfo.getTeam2Score().getTeamName())) {
                List<String> playerNames = info.getTeamInfos().get(i).getPlayerNames();
                List<PlayerScoreCard> scoreCards = new ArrayList<>();
                TeamScoreCard teamScoreCard = new TeamScoreCard();
                teamScoreCard.setTeamName(info.getTeamInfos().get(i).getTeamName());
                for (int j = 0; j < 11; j++) {
                    PlayerScoreCard card = new PlayerScoreCard();
                    card.setPlayerName(playerNames.get(j));
                    scoreCards.add(card);
                }
                teamScoreCard.setCards(scoreCards);
                singleMatchInfo.setTeam2Score(teamScoreCard);
            }

            adapter1 = new ScorecardAdapter(UpdatePlayerScore.this, singleMatchInfo.getTeam1Score(), UpdatePlayerScore.this);
            adapter2 = new ScorecardAdapter(UpdatePlayerScore.this, singleMatchInfo.getTeam2Score(), UpdatePlayerScore.this);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(UpdatePlayerScore.this));
            binding.recyclerView.setAdapter(adapter1);

            binding.totalRuns.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamRuns()) + " Scored");
            binding.totalWickets.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamWickets()) + " Wickets");

            dialog.dismiss();
        }
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

            case R.id.btn_submit:

                update_data_on_firebase();

                break;
        }

    }

    @Override
    public void update_runs(int a, String teamName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Runs" );
        builder.setItems(new CharSequence[]{"0 Run", "1 Run", "2 Run", "3 Run", "4 Run", "5 Run", "6 Run"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 1);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 1);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                            case 2:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 2);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 2);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                            case 3:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 3);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 3);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                            case 4:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 4);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 4);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                            case 5:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 5);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 5);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                            case 6:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 6);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setRuns(list.get(a).getRuns() + 6);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                        }
                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();

                        update_total_runs(teamName);
                        update_total_wickets(teamName);
                    }
                });
        builder.create().show();

    }

    @Override
    public void update_wickets(int a, String teamName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Wickets");
        builder.setItems(new CharSequence[]{"0 Wicket", "1 Wicket"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam2Score().getCards();
                                    list.get(a).setWickets(list.get(a).getWickets() + 1);
                                    singleMatchInfo.getTeam2Score().setCards(list);
                                } else if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
                                    List<PlayerScoreCard> list = singleMatchInfo.getTeam1Score().getCards();
                                    list.get(a).setWickets(list.get(a).getWickets() + 1);
                                    singleMatchInfo.getTeam1Score().setCards(list);
                                }
                                break;
                        }
                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();

                        update_total_runs(teamName);
                        update_total_wickets(teamName);
                    }
                });
        builder.create().show();

    }

    public void update_data_on_firebase() {
        for (int i = 0; i < allMatchInfo.getMatchInfos().size(); i++) {
            if (allMatchInfo.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(singleMatchInfo.getMatchNo())) {
                List<SingleMatchInfo> infoList = allMatchInfo.getMatchInfos();
                infoList.remove(i);
                infoList.add(i, singleMatchInfo);
            }
        }

        db.collection("Match Info")
                .document(tournamentId)
                .set(allMatchInfo);
    }

    public void update_total_runs(String teamName) {
        if (singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
            int score = 0;
            for (int i = 0; i < 11; i++) {
                score = score + singleMatchInfo.getTeam1Score().getCards().get(i).getRuns();
            }
            singleMatchInfo.getTeam1Score().setTeamRuns(score);
            binding.totalRuns.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamRuns()) + " Scored");
        }

        if (singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
            int score = 0;
            for (int i = 0; i < 11; i++) {
                score = score + singleMatchInfo.getTeam2Score().getCards().get(i).getRuns();
            }
            singleMatchInfo.getTeam2Score().setTeamRuns(score);
            binding.totalRuns.setText(String.valueOf(singleMatchInfo.getTeam2Score().getTeamRuns()) + " Scored");
        }

    }

    public void update_total_wickets(String teamName) {
        if (!singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName)) {
            int wickets = 0;
            for (int i = 0; i < 11; i++) {
                wickets = wickets + singleMatchInfo.getTeam2Score().getCards().get(i).getWickets();
            }
            singleMatchInfo.getTeam1Score().setTeamWickets(wickets);
            binding.totalWickets.setText(String.valueOf(singleMatchInfo.getTeam2Score().getTeamWickets()) + " Wickets");
        }

        if (!singleMatchInfo.getTeam2Score().getTeamName().equalsIgnoreCase(teamName)) {
            int wickets = 0;
            for (int i = 0; i < 11; i++) {
                wickets = wickets + singleMatchInfo.getTeam1Score().getCards().get(i).getWickets();
            }
            singleMatchInfo.getTeam2Score().setTeamWickets(wickets);
            binding.totalWickets.setText(String.valueOf(singleMatchInfo.getTeam1Score().getTeamWickets()) + " Wickets");
        }
    }

    @Override
    public void onBackPressed() {

        update_data_on_firebase();

        super.onBackPressed();
    }
}