package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.FixturesAdapters;
import com.example.livecricketapp.databinding.ActivityFixtuesMatchSettingsBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.TeamScoreCard;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FixtuesMatchSettings extends AppCompatActivity {

    private ActivityFixtuesMatchSettingsBinding binding;
    private FirebaseFirestore db;
    private AllMatchInfo allMatchInfo = new AllMatchInfo();
    private SingleMatchInfo singleMatchInfo = new SingleMatchInfo();
    private String tournamentId;
    private List<String> teams = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFixtuesMatchSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        allMatchInfo = (AllMatchInfo) getIntent().getSerializableExtra("all");
        tournamentId = allMatchInfo.getTournamentId();
        singleMatchInfo = (SingleMatchInfo) getIntent().getSerializableExtra("match");

        db = FirebaseFirestore.getInstance();

        get_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(FixtuesMatchSettings.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(FixtuesMatchSettings.this , Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    public void save_changes ( View view )
    {
        List<SingleMatchInfo> list = new ArrayList<>();
        list = allMatchInfo.getMatchInfos();

        for ( int i=0 ; i < list.size() ; i++ )
        {
            if ( list.get(i).getMatchNo().equalsIgnoreCase(singleMatchInfo.getMatchNo()) )
            {
                list.remove(i);
            }
        }

        TeamScoreCard teamScoreCard1 = singleMatchInfo.getTeam1Score();
        teamScoreCard1.setTeamName(binding.spinner1.getSelectedItem().toString());
        singleMatchInfo.setTeam1Score(teamScoreCard1);
        TeamScoreCard teamScoreCard = singleMatchInfo.getTeam2Score();
        teamScoreCard.setTeamName(binding.spinner2.getSelectedItem().toString());
        singleMatchInfo.setTeam2Score(teamScoreCard);
        singleMatchInfo.setMatchResult(binding.matchResult.getText().toString());
        list.add(singleMatchInfo);
        allMatchInfo.setMatchInfos(list);
        db.collection("Match Info").document(tournamentId).set(allMatchInfo);
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    private void get_data ()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait");
        dialog.show();
        db.collection("Tournament Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        TournamentInfo info = documentSnapshot.toObject(TournamentInfo.class);
                        teams = info.getTeamNames();
                        update_view();
                    }
                });
    }

    private void update_view()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, teams);
        binding.spinner1.setAdapter(adapter);
        binding.spinner2.setAdapter(adapter);
        binding.spinner1.setSelection(get_team_index(singleMatchInfo.getTeam1Score().getTeamName()));
        binding.spinner2.setSelection(get_team_index(singleMatchInfo.getTeam2Score().getTeamName()));
        binding.matchResult.setText(singleMatchInfo.getMatchResult());
        binding.match.setText(singleMatchInfo.getMatchNo() + " | " + singleMatchInfo.getDate() + " | " +singleMatchInfo.getTime());
        dialog.dismiss();
    }

    private int get_team_index ( String teamName )
    {
        for ( int i=0 ; i < teams.size() ; i++ )
        {
            if ( teamName.equalsIgnoreCase(teams.get(i)) )
            {
                return i;
            }
        }
        return 0;
    }

    public void back(View view) {
        finish();
    }
}