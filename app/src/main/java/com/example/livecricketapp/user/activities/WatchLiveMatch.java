package com.example.livecricketapp.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityWatchLiveMatchBinding;
import com.example.livecricketapp.model.SingleMatchInfo;

public class WatchLiveMatch extends AppCompatActivity implements View.OnClickListener{

    private ActivityWatchLiveMatchBinding binding;
    private SingleMatchInfo singleMatchInfo;
    private String tournamentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchLiveMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        singleMatchInfo = new SingleMatchInfo();
        singleMatchInfo = (SingleMatchInfo) getIntent().getSerializableExtra("match");
        tournamentId = getIntent().getStringExtra("tour");

        binding.rewardAPlayer.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {

        switch( v.getId() )
        {
            case R.id.reward_a_player:
                Intent intent = new Intent(this,RewardTeamPlayer.class);
                intent.putExtra("match",singleMatchInfo);
                intent.putExtra("tour",tournamentId);
                startActivity(intent);
                break;
        }

    }
}