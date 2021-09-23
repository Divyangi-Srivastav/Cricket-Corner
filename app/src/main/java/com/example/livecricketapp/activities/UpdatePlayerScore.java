package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityUpdatePlayerScoreBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.PlayerScoreCard;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.TeamScoreCard;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.base.Strings;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UpdatePlayerScore extends AppCompatActivity {

    private ActivityUpdatePlayerScoreBinding binding;
    private FirebaseFirestore db;
    private String tournamentId;
    private String matchNo;
    private AllTeamInfo info;
    private SingleMatchInfo singleMatchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePlayerScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        info = new AllTeamInfo();
        singleMatchInfo = new SingleMatchInfo();

        db = FirebaseFirestore.getInstance();

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");

        get_player_names();



        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(UpdatePlayerScore.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(UpdatePlayerScore.this , Dashboard.class);
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

    private void get_player_names()
    {
        db.collection("Tournament Team Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        info = documentSnapshot.toObject(AllTeamInfo.class);
                        get_single_match_info();
                    }
                });
    }

    private void get_single_match_info ()
    {
        db.collection("Match Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        AllMatchInfo info = documentSnapshot.toObject(AllMatchInfo.class);
                        for ( int i =0 ; i < info.getMatchInfos().size() ; i ++ )
                        {
                            if ( info.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(matchNo) )
                            {
                                singleMatchInfo = info.getMatchInfos().get(i);
                                set_player_names();
                            }
                        }
                    }
                });
    }

    private void set_player_names()
    {
        for ( int i=0 ; i< info.getTeamInfos().size() ; i++ )
        {
            if ( info.getTeamInfos().get(i).getTeamName().equalsIgnoreCase(singleMatchInfo.getTeam1Score().getTeamName()) )
            {
                List<String> playerNames = info.getTeamInfos().get(i).getPlayerNames();
                List<PlayerScoreCard> scoreCards= new ArrayList<>();
                TeamScoreCard teamScoreCard = new TeamScoreCard();
                teamScoreCard.setTeamName(info.getTeamInfos().get(i).getTeamName());
                for ( int j=0 ; j < 11 ; j++ )
                {
                    PlayerScoreCard card = new PlayerScoreCard();
                    card.setPlayerName(playerNames.get(j));
                    scoreCards.add(card);
                }
                teamScoreCard.setCards(scoreCards);
                singleMatchInfo.setTeam1Score(teamScoreCard);
            }
            else if ( info.getTeamInfos().get(i).getTeamName().equalsIgnoreCase(singleMatchInfo.getTeam2Score().getTeamName()) )
            {
                List<String> playerNames = info.getTeamInfos().get(i).getPlayerNames();
                List<PlayerScoreCard> scoreCards= new ArrayList<>();
                TeamScoreCard teamScoreCard = new TeamScoreCard();
                teamScoreCard.setTeamName(info.getTeamInfos().get(i).getTeamName());
                for ( int j=0 ; j < 11 ; j++ )
                {
                    PlayerScoreCard card = new PlayerScoreCard();
                    card.setPlayerName(playerNames.get(j));
                    scoreCards.add(card);
                }
                teamScoreCard.setCards(scoreCards);
                singleMatchInfo.setTeam1Score(teamScoreCard);
            }
        }
    }

}