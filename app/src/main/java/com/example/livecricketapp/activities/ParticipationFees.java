package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityParticipationFeesBinding;
import com.google.android.material.navigation.NavigationBarView;

public class ParticipationFees extends AppCompatActivity {

    private ActivityParticipationFeesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticipationFeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String text = "<b>NOTE:</b> Team removed from here for not paying participation fees will be removed from the fixture and their matches will not be scheduled, i.e. <b>their participation from the tournament will be cancelled!</b>";
        binding.text.setText(Html.fromHtml(text));

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(ParticipationFees.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(ParticipationFees.this , Dashboard.class);
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

}