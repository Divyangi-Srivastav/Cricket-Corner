package com.example.livecricketapp.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.databinding.ActivitySettingsBinding;

public class Settings extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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