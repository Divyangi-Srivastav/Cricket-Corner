package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.DashboardAdmin;
import com.example.livecricketapp.databinding.ActivityRewardTeamPlayerBinding;
import com.example.livecricketapp.model.PlayerScoreCard;
import com.example.livecricketapp.model.Reward;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RewardTeamPlayer extends AppCompatActivity implements View.OnClickListener{

    private ActivityRewardTeamPlayerBinding binding;
    private SingleMatchInfo singleMatchInfo = new SingleMatchInfo();
    private String tournamentId;
    private String tournamentName;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRewardTeamPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        singleMatchInfo = (SingleMatchInfo) getIntent().getSerializableExtra("match");
        tournamentId = getIntent().getStringExtra("tour");
        set_team();
        get_tournament_names();

        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                set_player(binding.spinner1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(RewardTeamPlayer.this, HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(RewardTeamPlayer.this, SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(RewardTeamPlayer.this, DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        binding.btnSubmit.setOnClickListener(this::onClick);
    }

    private void get_tournament_names ()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Tournament Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        TournamentInfo tournamentInfo = documentSnapshot.toObject(TournamentInfo.class);
                        tournamentName = tournamentInfo.getTournamentName();
                    }
                });
    }

    private void set_team ()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,get_team_names());
        binding.spinner1.setAdapter(adapter);
    }

    private void set_player ( String teamName )
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,get_player_list(teamName));
        binding.spinner2.setAdapter(adapter);
    }

    private List<String> get_team_names ()
    {
        List<String> teamList = new ArrayList<>();
        teamList.add(singleMatchInfo.getTeam1Score().getTeamName());
        teamList.add(singleMatchInfo.getTeam2Score().getTeamName());
        return teamList;
    }

    private List<String> get_player_list ( String teamName )
    {
        List<String> playerList = new ArrayList<>();
        playerList.add("Entire Team");
        List<PlayerScoreCard> cards = get_player_card(teamName);
        for ( int i=0 ; i < cards.size() ; i++ )
        {
            playerList.add(cards.get(i).getPlayerName());
        }
        return playerList;
    }

    private List<PlayerScoreCard> get_player_card (String teamName  )
    {
        if ( singleMatchInfo.getTeam1Score().getTeamName().equalsIgnoreCase(teamName) )
        {
            return singleMatchInfo.getTeam1Score().getCards();
        }else {
            return singleMatchInfo.getTeam2Score().getCards();
        }
    }

    public void back(View view) {
        finish();
    }

    public void clear ( View view )
    {
        if (view.getId() == R.id.name_cross)
        {
            binding.name.getText().clear();
        }else if ( view.getId() == R.id.amount_cross ){
            binding.amount.getText().clear();
        }
    }

    private Boolean check_empty ()
    {
        if ( binding.name.getText().toString().isEmpty() )
        {
            binding.name.setError("Required");
            return false;
        }else if ( binding.amount.getText().toString().isEmpty() )
        {
            binding.amount.setError("Required");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() )
        {
            case R.id.btn_submit:

                if ( check_empty() )
                {
                   create_reward();
                }

                break;
        }

    }

    private void create_reward ()
    {
        Reward reward = new Reward();
        reward.setMatchId(singleMatchInfo.getMatchNo());
        reward.setTeamName(binding.spinner1.getSelectedItem().toString());
        if ( binding.spinner2.getSelectedItem().toString().equalsIgnoreCase("Entire Team") )
            reward.setPlayer(false);
        else
            reward.setPlayer(true);
        reward.setPlayerName(binding.spinner2.getSelectedItem().toString());
        reward.setAmount(Integer.parseInt(binding.amount.getText().toString()));
        reward.setUserName(binding.name.getText().toString());
        reward.setTournamentId(tournamentId);
        reward.setTournamentName(tournamentName);
        move_to_payment_Activity(reward);
    }

    private void move_to_payment_Activity( Reward reward )
    {
        Intent intent = new Intent(this , PaymentActivity.class);
        intent.putExtra("activity","reward");
        intent.putExtra("reward",reward);
        startActivity(intent);
        finish();
    }

}