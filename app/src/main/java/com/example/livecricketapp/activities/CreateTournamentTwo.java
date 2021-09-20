package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityCreateTournamentTwoBinding;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleTeamInfo;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CreateTournamentTwo extends AppCompatActivity implements View.OnClickListener {

    private ActivityCreateTournamentTwoBinding binding;
    private List<String> stringList = new ArrayList<>();
    private TournamentInfo tournamentInfo;
    private List<SingleTeamInfo> singleTeamInfos = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(CreateTournamentTwo.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(CreateTournamentTwo.this , Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        tournamentInfo = (TournamentInfo) getIntent().getSerializableExtra("info");

        for (int i = 0; i < tournamentInfo.getNumber_of_teams(); i++) {
            stringList.add("Team " + (i + 1));
        }
        tournamentInfo.getTeamNames().remove(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tournamentInfo.getTeamNames());
        binding.spinner.setAdapter(adapter);
        binding.info.setOnClickListener(this::onClick);
        binding.btnSaveTeam.setOnClickListener(this::onClick);
        binding.btnSubmit.setOnClickListener(this::onClick);

    }

    public void clear(View view) {
        switch (view.getId()) {
            case R.id.name_player1_cross:
                binding.namePlayer1.getText().clear();
                break;
            case R.id.name_player2_cross:
                binding.namePlayer2.getText().clear();
                break;
            case R.id.name_player3_cross:
                binding.namePlayer3.getText().clear();
                break;
            case R.id.name_player4_cross:
                binding.namePlayer4.getText().clear();
                break;
            case R.id.name_player5_cross:
                binding.namePlayer5.getText().clear();
                break;
            case R.id.name_player6_cross:
                binding.namePlayer6.getText().clear();
                break;
            case R.id.name_player7_cross:
                binding.namePlayer7.getText().clear();
                break;
            case R.id.name_player8_cross:
                binding.namePlayer8.getText().clear();
                break;
            case R.id.name_player9_cross:
                binding.namePlayer9.getText().clear();
                break;
            case R.id.name_player10_cross:
                binding.namePlayer10.getText().clear();
                break;
            case R.id.captain_name_cross:
                binding.captainName.getText().clear();
                break;
            case R.id.upi_id_cross:
                binding.upiId.getText().clear();
                break;
        }

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.info:

                if (binding.infoCard.getVisibility() == View.INVISIBLE) {
                    binding.infoCard.setVisibility(View.VISIBLE);
                } else if (binding.infoCard.getVisibility() == View.VISIBLE) {
                    binding.infoCard.setVisibility(View.INVISIBLE);
                }
                break;

            case R.id.btn_submit:

                if ( tournamentInfo.getNumber_of_teams() == singleTeamInfos.size() ) {
                    AllTeamInfo allTeamInfo = new AllTeamInfo();
                    allTeamInfo.setTournamentId(tournamentInfo.getTournamentId());
                    allTeamInfo.setTeamInfos(singleTeamInfos);
                    upload_to_firebase(allTeamInfo);
                    move_to_page_three();
                }
                else{
                    Toast.makeText(this, "Save info of all Teams", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_save_team:

                if ( checkEmpty() )
                {
                    SingleTeamInfo singleTeamInfo = new SingleTeamInfo();
                    singleTeamInfo.setCaptainName(binding.captainName.getText().toString());
                    singleTeamInfo.setUpiId(binding.upiId.getText().toString());
                    singleTeamInfo.setTeamName(binding.spinner.getSelectedItem().toString());
                    singleTeamInfo.setPlayerNames(get_Player_List());
                    singleTeamInfos.add(singleTeamInfo);

                    Toast.makeText(this, "Team Info Saved Successfully", Toast.LENGTH_SHORT).show();
                    empty_all();
                }

                break;

        }

    }

    private void empty_all() {
        binding.namePlayer1.getText().clear();
        binding.namePlayer2.getText().clear();
        binding.namePlayer3.getText().clear();
        binding.namePlayer4.getText().clear();
        binding.namePlayer5.getText().clear();
        binding.namePlayer6.getText().clear();
        binding.namePlayer7.getText().clear();
        binding.namePlayer8.getText().clear();
        binding.namePlayer9.getText().clear();
        binding.namePlayer10.getText().clear();
        binding.captainName.getText().clear();
        binding.upiId.getText().clear();
    }

    private List<String> get_Player_List()
    {
        List<String> players = new ArrayList<>();
        players.add(binding.captainName.getText().toString() + "( C )");
        players.add(binding.namePlayer1.getText().toString());
        players.add(binding.namePlayer2.getText().toString());
        players.add(binding.namePlayer3.getText().toString());
        players.add(binding.namePlayer4.getText().toString());
        players.add(binding.namePlayer5.getText().toString());
        players.add(binding.namePlayer6.getText().toString());
        players.add(binding.namePlayer7.getText().toString());
        players.add(binding.namePlayer8.getText().toString());
        players.add(binding.namePlayer9.getText().toString());
        players.add(binding.namePlayer10.getText().toString());
        return players;
    }

    private Boolean checkEmpty ()
    {
        if ( binding.namePlayer1.getText().toString().isEmpty() )
        {
            binding.namePlayer1.setError("Enter Name of Player 1");
            return false;
        } else  if ( binding.namePlayer2.getText().toString().isEmpty() )
        {
            binding.namePlayer2.setError("Enter Name of Player 2");
            return false;
        }else  if ( binding.namePlayer3.getText().toString().isEmpty() )
        {
            binding.namePlayer3.setError("Enter Name of Player 3");
            return false;
        }else  if ( binding.namePlayer4.getText().toString().isEmpty() )
        {
            binding.namePlayer4.setError("Enter Name of Player 4");
            return false;
        }else  if ( binding.namePlayer5.getText().toString().isEmpty() )
        {
            binding.namePlayer5.setError("Enter Name of Player 5");
            return false;
        }else  if ( binding.namePlayer6.getText().toString().isEmpty() )
        {
            binding.namePlayer6.setError("Enter Name of Player 6");
            return false;
        }else  if ( binding.namePlayer7.getText().toString().isEmpty() )
        {
            binding.namePlayer7.setError("Enter Name of Player 7");
            return false;
        }else  if ( binding.namePlayer8.getText().toString().isEmpty() )
        {
            binding.namePlayer8.setError("Enter Name of Player 8");
            return false;
        }else  if ( binding.namePlayer9.getText().toString().isEmpty() )
        {
            binding.namePlayer9.setError("Enter Name of Player 9");
            return false;
        }else  if ( binding.namePlayer10.getText().toString().isEmpty() )
        {
            binding.namePlayer10.setError("Enter Name of Player 10");
            return false;
        }else  if ( binding.captainName.getText().toString().isEmpty() )
        {
            binding.captainName.setError("Enter Name of Captain");
            return false;
        }else  if ( binding.upiId.getText().toString().isEmpty() )
        {
            binding.upiId.setError("Enter UPI ID of team");
            return false;
        }
        else {
            return true;
        }
    }

    private void upload_to_firebase( AllTeamInfo allTeamInfo )
    {
        db.collection("Tournament Team Info").document(tournamentInfo.getTournamentId()).set(allTeamInfo);
    }

    public void move_to_page_three ()
    {
        Intent intent = new Intent(this, CreateTournamentThree.class);
        intent.putExtra("id",tournamentInfo.getTournamentId());
        startActivity(intent);
    }

}