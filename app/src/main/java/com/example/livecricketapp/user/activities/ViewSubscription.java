package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.Dashboard;
import com.example.livecricketapp.activities.HomeActivity;
import com.example.livecricketapp.databinding.ActivityViewSubscriptionBinding;
import com.google.android.material.navigation.NavigationBarView;

public class ViewSubscription extends AppCompatActivity {

    private ActivityViewSubscriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewSubscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(ViewSubscription.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(ViewSubscription.this , Settings.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(ViewSubscription.this , Dashboard.class);
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