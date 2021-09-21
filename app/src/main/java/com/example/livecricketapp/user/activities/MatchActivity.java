package com.example.livecricketapp.user.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.Dashboard;
import com.example.livecricketapp.activities.HomeActivity;
import com.example.livecricketapp.databinding.ActivityMatchBinding;
import com.example.livecricketapp.user.adapters.ScorecardAdapter;
import com.google.android.material.navigation.NavigationBarView;

public class MatchActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMatchBinding binding;
    private ScorecardAdapter scorecardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(MatchActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(MatchActivity.this, Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        scorecardAdapter = new ScorecardAdapter(this);
        binding.recyclerView.setAdapter(scorecardAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.team1.setOnClickListener(this::onClick);
        binding.team2.setOnClickListener(this::onClick);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.team1:
                binding.team1.setBackgroundColor(Color.parseColor("#B9CCE2"));
                binding.team2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.team2:
                binding.team2.setBackgroundColor(Color.parseColor("#B9CCE2"));
                binding.team1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;


        }

    }
}