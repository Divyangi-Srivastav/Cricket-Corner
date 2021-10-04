package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHostATour.setOnClickListener(this::onClick);
        binding.btnHostAMatch.setOnClickListener(this::onClick);
        binding.btnViewAdRequest.setOnClickListener(this::onClick);

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                            Intent intent = new Intent(HomeActivity.this , HomeActivity.class);
                            startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                            Intent intent1 = new Intent(HomeActivity.this , Dashboard.class);
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
            case R.id.btn_host_a_match:
                Intent intent3 = new Intent(this , HostMatchActivityOne.class);
                startActivity(intent3);
                break;
            case R.id.btn_host_a_tour:
                Intent intent = new Intent( HomeActivity.this , Host_a_tournament.class );
                startActivity(intent);
                break;
            case R.id.btn_view_ad_request:
                Intent intent2 = new Intent(this, AdsRequest.class);
                startActivity(intent2);
                break;
        }
    }
}