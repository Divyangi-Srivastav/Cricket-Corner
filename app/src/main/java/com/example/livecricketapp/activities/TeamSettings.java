package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.databinding.ActivityTeamSettingsBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeamSettings extends AppCompatActivity {

    private ActivityTeamSettingsBinding binding;
    private FirebaseFirestore db;
    private String tournamentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tournamentId = getIntent().getStringExtra("id");
    }

    public void edit ( View view )
    {

    }

    public void set_paid ( View view )
    {

    }

    public void set_unpaid ( View view )
    {

    }

    public void back(View view) {
        finish();
    }

    public void get_data ()
    {
        db.collection("Tournament Team Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener()
    }

}