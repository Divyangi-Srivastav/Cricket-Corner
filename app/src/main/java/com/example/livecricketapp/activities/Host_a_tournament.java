package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityHostAtournamentBinding;
import com.example.livecricketapp.model.TournamentHostInfo;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Host_a_tournament extends AppCompatActivity {

    private ActivityHostAtournamentBinding binding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostAtournamentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(Host_a_tournament.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(Host_a_tournament.this , Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Tournament Host Info");
    }


    public void back(View view) {
        finish();
    }

    public void clear(View view) {

        switch (view.getId()) {
            case R.id.name_cross:
                binding.name.getText().clear();
                break;
            case R.id.phone_cross:
                binding.phone.getText().clear();
                break;
            case R.id.address_cross:
                binding.address.getText().clear();
                break;
        }
    }

    public void create_tournament ( View view  )
    {
        if (check_empty())
        {
            TournamentHostInfo tournamentHostInfo = new TournamentHostInfo();
            tournamentHostInfo.setHostName(binding.name.getText().toString());
            tournamentHostInfo.setPhoneNumber(binding.phone.getText().toString());
            tournamentHostInfo.setAddress(binding.address.getText().toString());
            tournamentHostInfo.setTournamentId(generate_tournament_Id());

            push_data_to_firebase(tournamentHostInfo);
            move_to_create_tournament(tournamentHostInfo.getTournamentId());
        }
    }

    public String generate_tournament_Id ()
    {
        long time = System.currentTimeMillis();
        return String.valueOf(time);
    }

    public void push_data_to_firebase( TournamentHostInfo tournamentHostInfo )
    {
        databaseReference.push().setValue(tournamentHostInfo);
    }

    public void move_to_create_tournament( String id )
    {
        Intent intent = new Intent(this , CreateTournamentOne.class);
        intent.putExtra("id", id );
        startActivity( intent );
    }

    public Boolean check_empty()
    {
        if ( binding.name.getText().toString().isEmpty() )
        {
            binding.name.setError("Enter the name");
            return false;
        }
        else if ( binding.phone.getText().toString().isEmpty() )
        {
            binding.phone.setError("Enter the phone Number");
            return false;
        }
        else if ( binding.address.getText().toString().isEmpty() )
        {
            binding.address.setError("Enter the address");
            return false;
        }
        else {
            return true;
        }
    }

}