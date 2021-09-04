package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityTournamentSettingsBinding;
import com.example.livecricketapp.model.DashboardTournamentInfo;

public class TournamentSettings extends AppCompatActivity implements View.OnClickListener {

    private ActivityTournamentSettingsBinding binding;
    private DashboardTournamentInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        info = new DashboardTournamentInfo();
        info = (DashboardTournamentInfo) getIntent().getSerializableExtra("id");

        String text = "Tournament Name : " + "<b>" + info.getTournamentName() + "</b> ";
        binding.tournamentName.setText(Html.fromHtml(text));

        binding.btnViewTeams.setOnClickListener(this::onClick);
        binding.btnViewFixtures.setOnClickListener(this::onClick);
        binding.btnViewAdRequest.setOnClickListener(this::onClick);
        binding.btnViewFees.setOnClickListener(this::onClick);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_view_teams:
                Intent intent = new Intent(this, TeamSettings.class);
                intent.putExtra("id",info.getTournamentId());
                startActivity(intent);
                break;
            case R.id.btn_view_fixtures:
                Intent intent2 = new Intent(this, Fixtures.class);
                intent2.putExtra("id",info.getTournamentId());
                startActivity(intent2);
                break;
            case R.id.btn_view_ad_request:
                Intent intent3 = new Intent(this, AdsRequest.class);
                intent3.putExtra("id",info.getTournamentId());
                startActivity(intent3);
                break;
            case R.id.btn_view_fees:
                Intent intent4 = new Intent(this, ParticipationFees.class);
                intent4.putExtra("id",info.getTournamentId());
                startActivity(intent4);
                break;
        }

    }
}