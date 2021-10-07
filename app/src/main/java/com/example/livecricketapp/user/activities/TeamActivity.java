package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.DashboardAdmin;
import com.example.livecricketapp.databinding.ActivityTeamBinding;
import com.example.livecricketapp.model.SingleTeamInfo;
import com.google.android.material.navigation.NavigationBarView;

public class TeamActivity extends AppCompatActivity {

    private ActivityTeamBinding binding;
    private SingleTeamInfo teamInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        teamInfo = new SingleTeamInfo();
        teamInfo = (SingleTeamInfo) getIntent().getSerializableExtra("team");
        set_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(TeamActivity.this , HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(TeamActivity.this , SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(TeamActivity.this , DashboardUser.class);
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
        binding.teamName.setText(teamInfo.getTeamName());
        binding.namePlayer1.setText(teamInfo.getPlayerNames().get(0));
        binding.namePlayer2.setText(teamInfo.getPlayerNames().get(1));
        binding.namePlayer3.setText(teamInfo.getPlayerNames().get(2));
        binding.namePlayer4.setText(teamInfo.getPlayerNames().get(3));
        binding.namePlayer5.setText(teamInfo.getPlayerNames().get(4));
        binding.namePlayer6.setText(teamInfo.getPlayerNames().get(5));
        binding.namePlayer7.setText(teamInfo.getPlayerNames().get(6));
        binding.namePlayer8.setText(teamInfo.getPlayerNames().get(7));
        binding.namePlayer9.setText(teamInfo.getPlayerNames().get(8));
        binding.namePlayer10.setText(teamInfo.getPlayerNames().get(9));
        binding.namePlayer11.setText(teamInfo.getPlayerNames().get(10));
    }

}