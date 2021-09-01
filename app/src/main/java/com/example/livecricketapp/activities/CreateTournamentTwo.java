package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityCreateTournamentTwoBinding;

import java.util.ArrayList;
import java.util.List;

public class CreateTournamentTwo extends AppCompatActivity implements View.OnClickListener {

    private ActivityCreateTournamentTwoBinding binding;
    private int number_of_teams;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        number_of_teams = getIntent().getIntExtra("number",5);

        for ( int i=0 ; i < number_of_teams ; i++  )
        {
            stringList.add("Team " + (i +1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,stringList );
        binding.spinner.setAdapter(adapter);
        binding.info.setOnClickListener(this::onClick);
    }

    public void clear ( View view )
    {
        switch ( view.getId() )
        {
            case R.id.name_player1_cross:

                

                break;
        }

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.info:

                if ( binding.infoCard.getVisibility() == View.INVISIBLE )
                {
                    binding.infoCard.setVisibility(View.VISIBLE);
                }
                else if ( binding.infoCard.getVisibility() == View.VISIBLE )
                {
                    binding.infoCard.setVisibility(View.INVISIBLE);
                }

                break;
        }


    }
}