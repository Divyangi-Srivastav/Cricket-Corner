package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityHomeUserBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivityUser extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.btnViewATour.setOnClickListener(this::onClick);
        binding.btnViewAMatch.setOnClickListener(this::onClick);
        binding.btnMakeAdRequest.setOnClickListener(this::onClick);

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(HomeActivityUser.this, SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(HomeActivityUser.this , DashboardUser.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_view_a_match:

                break;
            case R.id.btn_view_a_tour:
                Intent intent = new Intent( this ,Tournaments.class );
                startActivity(intent);
                break;
            case R.id.btn_make_ad_request:
                Intent intent2 = new Intent(this, AdvertiseYourself.class);
                startActivity(intent2);
                break;
        }
    }
}