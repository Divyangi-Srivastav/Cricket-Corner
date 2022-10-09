package com.example.livecricketapp.admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityUpdateBatsmanBowlerBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UpdateBatsmanBowler extends AppCompatActivity implements View.OnClickListener {

    private ActivityUpdateBatsmanBowlerBinding binding ;
    private String tournamentId;
    private String matchNo;
    private FirebaseFirestore db ;
    private AllMatchInfo allMatchInfo ;
    private SingleMatchInfo singleMatchInfo ;
    private AllTeamInfo allTeamInfo ;

    List<String> teams = new ArrayList<>();
    List<String> team1 = new ArrayList<>();
    List<String> team2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBatsmanBowlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");

        get_data();
        get_tournament_team_info();

        binding.btnSubmit.setOnClickListener(this::onClick);
    }

    private void get_data() {

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
                            }
                        }
                    }
                });
    }

    private void get_tournament_team_info() {

        db.collection("Tournament Team Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allTeamInfo = documentSnapshot.toObject(AllTeamInfo.class);
                        teams.add(allTeamInfo.getTeamInfos().get(0).getTeamName());
                        teams.add(allTeamInfo.getTeamInfos().get(1).getTeamName());
                        team1 = allTeamInfo.getTeamInfos().get(0).getPlayerNames();
                        team2 = allTeamInfo.getTeamInfos().get(1).getPlayerNames();
                        update_data();
                    }
                });

    }

    private void update_data()
    {
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, teams);
        ArrayAdapter<String> team1Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, team1);
        ArrayAdapter<String> team2Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, team2);

        binding.team.setAdapter(teamAdapter);
        binding.team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String batting = parent.getItemAtPosition(position).toString();

                if ( allTeamInfo.getTeamInfos().get(0).getTeamName().equalsIgnoreCase(batting) )
                {
                    binding.batsman1.setAdapter(team1Adapter);
                    binding.batsman2.setAdapter(team1Adapter);
                    binding.bowler.setAdapter(team2Adapter);
                }
                else
                {
                    binding.batsman1.setAdapter(team2Adapter);
                    binding.batsman2.setAdapter(team2Adapter);
                    binding.bowler.setAdapter(team1Adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btn_submit:

                for (int i = 0; i < allMatchInfo.getMatchInfos().size(); i++) {
                    if (allMatchInfo.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(matchNo)) {
                        allMatchInfo.getMatchInfos().get(i).setBattingTeam(binding.team.getSelectedItem().toString());
                        allMatchInfo.getMatchInfos().get(i).setBatsman1(binding.batsman1.getSelectedItem().toString());
                        allMatchInfo.getMatchInfos().get(i).setBatsman2(binding.batsman2.getSelectedItem().toString());
                        allMatchInfo.getMatchInfos().get(i).setBowler(binding.bowler.getSelectedItem().toString());

                        db.collection("Match Info").document(tournamentId).set(allMatchInfo);
                        Toast.makeText(UpdateBatsmanBowler.this , "Data Updated Successfully" , Toast.LENGTH_SHORT ).show();
                    }
                }

                break;
        }
    }
}