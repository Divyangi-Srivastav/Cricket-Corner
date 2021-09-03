package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.DashboardAdapter;
import com.example.livecricketapp.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity implements DashboardAdapter.Move_Next {

    private ActivityDashboardBinding binding;
    private DashboardAdapter dashboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dashboardAdapter = new DashboardAdapter(this,this::move_to_other_activity);
        binding.recyclerView.setAdapter(dashboardAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void back(View view) {
        finish();
    }

    public void edit_content ( View view )
    {

    }

    @Override
    public void move_to_other_activity(int a) {
        Intent intent = new Intent(this,TournamentSettings.class);
        startActivity(intent);
    }
}