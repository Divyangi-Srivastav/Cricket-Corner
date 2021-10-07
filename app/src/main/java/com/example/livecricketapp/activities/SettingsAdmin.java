package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivitySettingsAdminBinding;
import com.example.livecricketapp.user.activities.HomeActivityUser;
import com.example.livecricketapp.user.activities.SettingsUser;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsAdmin extends AppCompatActivity {

    private ActivitySettingsAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(SettingsAdmin.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(SettingsAdmin.this , DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    public void log_out (View view)
    {

    }

    public void change_Password ( View view )
    {

    }

}