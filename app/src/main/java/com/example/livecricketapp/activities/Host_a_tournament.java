package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityHostAtournamentBinding;
import com.example.livecricketapp.model.TournamentHostInfo;
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
            case R.id.upi_id_cross:
                binding.upiId.getText().clear();
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
        TournamentHostInfo tournamentHostInfo = new TournamentHostInfo();
        tournamentHostInfo.setHostName(binding.name.getText().toString());
        tournamentHostInfo.setUpiId(binding.upiId.getText().toString());
        tournamentHostInfo.setPhoneNumber(binding.phone.getText().toString());
        tournamentHostInfo.setAddress(binding.address.getText().toString());

        tournamentHostInfo.setTournamentId(generate_tournament_Id());

        push_data_to_firebase(tournamentHostInfo);
        move_to_create_tournament();
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

    public void move_to_create_tournament()
    {
        Intent intent = new Intent(this , CreateTournamentOne.class);
        startActivity( intent );
    }

}