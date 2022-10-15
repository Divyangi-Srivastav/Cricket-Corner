package com.example.livecricketapp.admin.activities;

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
    private String tournamentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostMatchTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");
        tournamentName = getIntent().getStringExtra("name");

        binding.tournamnetName.setText(tournamentName);
        binding.matchNo.setText(matchNo);
        binding.liveStream.setOnClickListener(this::onClick);
        binding.playerScore.setOnClickListener(this::onClick);
        binding.matchResult.setOnClickListener(this::onClick);
        binding.updateBatsman.setOnClickListener(this::onClick);

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(HostMatchActivityTwo.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(HostMatchActivityTwo.this , SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(HostMatchActivityTwo.this , DashboardAdmin.class);
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
                intent.putExtra("tour",tournamentId);
                intent.putExtra("match",matchNo);
                startActivity(intent);
                break;
            case R.id.match_result:
                Intent intent1 = new Intent(this, MatchEndResult.class);
                intent1.putExtra("tour",tournamentId);
                intent1.putExtra("match",matchNo);
                startActivity(intent1);
                break;
            case R.id.player_score:
                Intent intent2 = new Intent(this, UpdatePlayerScore.class);
                intent2.putExtra("tour",tournamentId);
                intent2.putExtra("match",matchNo);
                startActivity(intent2);
                break;

            case R.id.update_batsman:
                Intent intent4 = new Intent(this, UpdateBatsmanBowler.class);
                intent4.putExtra("tour",tournamentId);
                intent4.putExtra("match",matchNo);
                startActivity(intent4);
                break;
        }

    }
}