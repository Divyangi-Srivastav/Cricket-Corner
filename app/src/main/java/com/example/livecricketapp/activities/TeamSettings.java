package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityTeamSettingsBinding;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleTeamInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeamSettings extends AppCompatActivity {

    private ActivityTeamSettingsBinding binding;
    private FirebaseFirestore db;
    private String tournamentId = "1630656851257";
    private List<SingleTeamInfo> infoList = new ArrayList<>();
    private List<String> teamNames = new ArrayList<>();
    private AllTeamInfo allTeamInfo = new AllTeamInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tournamentId = getIntent().getStringExtra("id");

        db  =  FirebaseFirestore.getInstance();
        get_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(TeamSettings.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(TeamSettings.this , SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(TeamSettings.this , DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < infoList.size(); i++) {
                    if (parent.getSelectedItem().toString().equalsIgnoreCase(infoList.get(i).getTeamName())) {
                        set_player_names(infoList.get(i));
                        if (infoList.get(i).getPaid())
                        {
                            binding.paid.setBackgroundColor(Color.parseColor("#B9CCE2"));
                            binding.unpaid.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                        else
                        {
                            binding.unpaid.setBackgroundColor(Color.parseColor("#B9CCE2"));
                            binding.paid.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void edit(View view) {

        switch (view.getId()) {
            case R.id.name_player1_edit:
                binding.namePlayer1.setEnabled(true);
                binding.namePlayer1.setFocusable(true);
                binding.namePlayer1.setClickable(true);
                binding.namePlayer1.setFocusableInTouchMode(true);
                break;
            case R.id.name_player2_edit:
                binding.namePlayer2.setFocusable(true);
                binding.namePlayer2.setFocusable(true);
                binding.namePlayer2.setClickable(true);
                binding.namePlayer2.setFocusableInTouchMode(true);
                break;
            case R.id.name_player3_edit:
                binding.namePlayer3.setFocusable(true);
                binding.namePlayer3.setFocusable(true);
                binding.namePlayer3.setClickable(true);
                binding.namePlayer3.setFocusableInTouchMode(true);
                break;
            case R.id.name_player4_edit:
                binding.namePlayer4.setFocusable(true);
                binding.namePlayer4.setFocusable(true);
                binding.namePlayer4.setClickable(true);
                binding.namePlayer4.setFocusableInTouchMode(true);
                break;
            case R.id.name_player5_edit:
                binding.namePlayer5.setFocusable(true);
                binding.namePlayer5.setFocusable(true);
                binding.namePlayer5.setClickable(true);
                binding.namePlayer5.setFocusableInTouchMode(true);
                break;
            case R.id.name_player6_edit:
                binding.namePlayer6.setFocusable(true);
                binding.namePlayer6.setFocusable(true);
                binding.namePlayer6.setClickable(true);
                binding.namePlayer6.setFocusableInTouchMode(true);
                break;
            case R.id.name_player7_edit:
                binding.namePlayer7.setFocusable(true);
                binding.namePlayer7.setFocusable(true);
                binding.namePlayer7.setClickable(true);
                binding.namePlayer7.setFocusableInTouchMode(true);
                break;
            case R.id.name_player8_edit:
                binding.namePlayer8.setFocusable(true);
                binding.namePlayer8.setFocusable(true);
                binding.namePlayer8.setClickable(true);
                binding.namePlayer8.setFocusableInTouchMode(true);
                break;
            case R.id.name_player9_edit:
                binding.namePlayer9.setFocusable(true);
                binding.namePlayer9.setFocusable(true);
                binding.namePlayer9.setClickable(true);
                binding.namePlayer9.setFocusableInTouchMode(true);
                break;
            case R.id.name_player10_edit:
                binding.namePlayer10.setFocusable(true);
                binding.namePlayer10.setFocusable(true);
                binding.namePlayer10.setClickable(true);
                binding.namePlayer10.setFocusableInTouchMode(true);
                break;
            case R.id.name_player11_edit:
                binding.namePlayer11.setFocusable(true);
                binding.namePlayer11.setFocusable(true);
                binding.namePlayer11.setClickable(true);
                binding.namePlayer11.setFocusableInTouchMode(true);
                break;
        }

    }

    public void set_paid(View view) {

        for (int i = 0; i < infoList.size(); i++) {
            if (binding.spinner.getSelectedItem().toString().equalsIgnoreCase(infoList.get(i).getTeamName())) {
                infoList.get(i).setPaid(true);
            }
        }
        binding.paid.setBackgroundColor(Color.parseColor("#B9CCE2"));
        binding.unpaid.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    public void set_unpaid(View view) {

        for (int i = 0; i < infoList.size(); i++) {
            if (binding.spinner.getSelectedItem().toString().equalsIgnoreCase(infoList.get(i).getTeamName())) {
                infoList.get(i).setPaid(false);
            }
        }
        binding.unpaid.setBackgroundColor(Color.parseColor("#B9CCE2"));
        binding.paid.setBackgroundColor(Color.parseColor("#FFFFFF"));

    }

    public void back(View view) {
        finish();
    }

    public void get_data() {
        db.collection("Tournament Team Info").document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allTeamInfo = documentSnapshot.toObject(AllTeamInfo.class);
                        infoList = allTeamInfo.getTeamInfos();
                        get_team_names();
                    }
                });
    }

    public void get_team_names() {
        for (int i = 0; i < infoList.size() ; i++) {
            teamNames.add(infoList.get(i).getTeamName());
        }
        set_data();
        set_player_names(infoList.get(0));
    }

    public void set_data() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, teamNames);
        binding.spinner.setAdapter(adapter);
    }

    public void set_player_names(SingleTeamInfo singleTeamInfo) {

        binding.namePlayer1.setText(singleTeamInfo.getPlayerNames().get(0));
        binding.namePlayer2.setText(singleTeamInfo.getPlayerNames().get(1));
        binding.namePlayer3.setText(singleTeamInfo.getPlayerNames().get(2));
        binding.namePlayer4.setText(singleTeamInfo.getPlayerNames().get(3));
        binding.namePlayer5.setText(singleTeamInfo.getPlayerNames().get(4));
        binding.namePlayer6.setText(singleTeamInfo.getPlayerNames().get(5));
        binding.namePlayer7.setText(singleTeamInfo.getPlayerNames().get(6));
        binding.namePlayer8.setText(singleTeamInfo.getPlayerNames().get(7));
        binding.namePlayer9.setText(singleTeamInfo.getPlayerNames().get(8));
        binding.namePlayer10.setText(singleTeamInfo.getPlayerNames().get(9));
        binding.namePlayer11.setText(singleTeamInfo.getPlayerNames().get(10));
    }

    private List<String> get_Player_List()
    {
        List<String> players = new ArrayList<>();
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
        players.add(binding.namePlayer11.getText().toString());
        return players;
    }

    private void save_player_names()
    {
        String teamName = binding.spinner.getSelectedItem().toString();
        for ( int i=0 ; i <infoList.size() ; i++ ){
            if ( infoList.get(i).getTeamName().equalsIgnoreCase(teamName) )
            {
                infoList.get(i).setPlayerNames(get_Player_List());
            }
        }
    }

    public void save_changes ( View view )
    {
        save_player_names();
        allTeamInfo.setTeamInfos(infoList);
        db.collection("Tournament Team Info")
                .document(tournamentId)
                .set(allTeamInfo);
        Toast.makeText(TeamSettings.this, "Team Data Updated Successfully", Toast.LENGTH_SHORT).show();
        set_focusable_false();
    }

    private void set_focusable_false()
    {
        binding.namePlayer1.setFocusable(false);
        binding.namePlayer2.setFocusable(false);
        binding.namePlayer3.setFocusable(false);
        binding.namePlayer4.setFocusable(false);
        binding.namePlayer5.setFocusable(false);
        binding.namePlayer6.setFocusable(false);
        binding.namePlayer7.setFocusable(false);
        binding.namePlayer8.setFocusable(false);
        binding.namePlayer9.setFocusable(false);
        binding.namePlayer10.setFocusable(false);
        binding.namePlayer11.setFocusable(false);
    }
}