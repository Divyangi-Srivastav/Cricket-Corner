package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityHostMatchTwoBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HostMatchActivityTwo extends AppCompatActivity implements View.OnClickListener {

    private ActivityHostMatchTwoBinding binding;
    private String tournamentId;
    private String matchNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostMatchTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(HostMatchActivityTwo.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(HostMatchActivityTwo.this , Dashboard.class);
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

    @Override
    public void onClick(View v) {

        switch ( v.getId() )
        {
            case R.id.live_stream:
                Intent intent = new Intent(this,StartLiveStreaming.class);
                startActivity(intent);
                break;
            case R.id.team_score:
                Intent intent1 = new Intent(this, UpdateTeamScore.class);
                startActivity(intent1);
                break;
            case R.id.player_score:
                Intent intent2 = new Intent(this, UpdatePlayerScore.class);
                startActivity(intent2);
                break;
        }

    }
}