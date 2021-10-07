package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.DataOperations.OperationOnDate;
import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.Dashboard;
import com.example.livecricketapp.activities.HomeActivity;
import com.example.livecricketapp.databinding.ActivityTournamentsBinding;
import com.example.livecricketapp.model.TournamentInfo;
import com.example.livecricketapp.user.adapters.TournamentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tournaments extends AppCompatActivity implements TournamentAdapter.On_Click {

    private ActivityTournamentsBinding binding;
    private String date;
    private FirebaseFirestore db;
    private List<TournamentInfo> previousTournament = new ArrayList<>();
    private List<TournamentInfo> ongoingTournament = new ArrayList<>();
    private List<TournamentInfo> upcomimgTournament = new ArrayList<>();
    private TournamentAdapter previousAdapter , ongoingAdapter , upcomimgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        previousAdapter = new TournamentAdapter(this,previousTournament , "previous",this::move_to_teams_and_matches );
        ongoingAdapter = new TournamentAdapter(this,ongoingTournament , "ongoing" , this::move_to_teams_and_matches );
        upcomimgAdapter = new TournamentAdapter(this,upcomimgTournament , "upcoming" , this::move_to_teams_and_matches);

        binding.ongoingTournament.setAdapter(ongoingAdapter);
        binding.ongoingTournament.setLayoutManager(new LinearLayoutManager(this));
        binding.previousTournament.setAdapter(previousAdapter);
        binding.previousTournament.setLayoutManager(new LinearLayoutManager(this));
        binding.upcomingTournament.setAdapter(upcomimgAdapter);
        binding.upcomingTournament.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        get_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(Tournaments.this , HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(Tournaments.this , SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(Tournaments.this , Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    private void get_data() {

        db.collection("Tournament Info")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful() )
                        {
                            for (QueryDocumentSnapshot snapshot : task.getResult())
                            {
                                TournamentInfo info = snapshot.toObject(TournamentInfo.class);
                                sort_tournament(info);
                            }
                        }
                        else {
                            Log.d("Tournament" , "Error getting docs" );
                        }
                    }
                });

    }

    private void sort_tournament( TournamentInfo info )
    {
        int days1 = OperationOnDate.number_of_days(info.getStart_date(),date);
        int days2 = OperationOnDate.number_of_days(info.getStart_date(),info.getEnd_date());

        if ( days1 < 1 )
        {
            upcomimgTournament.add(info);
        }
        else if ( days1 <= days2 )
        {
            ongoingTournament.add(info);
        }
        else
        {
            previousTournament.add(info);
        }
        previousAdapter.notifyDataSetChanged();
        ongoingAdapter.notifyDataSetChanged();
        upcomimgAdapter.notifyDataSetChanged();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void move_to_teams_and_matches(TournamentInfo tournamentInfo) {

        Intent intent = new Intent(this , TeamsAndMatches.class);
        intent.putExtra("info",tournamentInfo);
        startActivity(intent);
    }
}