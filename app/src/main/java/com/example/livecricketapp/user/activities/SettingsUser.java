package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.DashboardAdmin;
import com.example.livecricketapp.databinding.ActivitySettingsUserBinding;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsUser extends AppCompatActivity {

    private ActivitySettingsUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(SettingsUser.this , HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(SettingsUser.this , DashboardUser.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

    }

    public void move_to_change_Password ( View view )
    {
        Intent intent = new Intent(this , ChangePassword.class);
        startActivity(intent);
    }

    public void move_to_view_subscription ( View view )
    {
        Intent intent = new Intent(this , ViewSubscription.class);
        startActivity(intent);
    }

}