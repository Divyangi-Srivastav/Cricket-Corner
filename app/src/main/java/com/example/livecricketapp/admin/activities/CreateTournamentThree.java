package com.example.livecricketapp.admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.DataOperations.OperationOnDate;
import com.example.livecricketapp.R;
import com.example.livecricketapp.admin.adapters.CreateTournamentThreeAdapterOne;
import com.example.livecricketapp.databinding.ActivityCreateTournamentThreeBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CreateTournamentThree extends AppCompatActivity  {

   private ActivityCreateTournamentThreeBinding binding;
   private CreateTournamentThreeAdapterOne adapter;
   private FirebaseFirestore db;
   private String TournamentId = "1630527652705";
   private TournamentInfo tournamentInfo;
   private List<SingleMatchInfo> singleMatchInfos = new ArrayList<>();
   private AllMatchInfo matchInfo;
   private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentThreeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(CreateTournamentThree.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(CreateTournamentThree.this , SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(CreateTournamentThree.this , DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        TournamentId = getIntent().getStringExtra("id");
        matchInfo = new AllMatchInfo();
        sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();
        get_data();
        tournamentInfo = new TournamentInfo();
    }

    public void back(View view) {
        finish();
    }

    public void get_data ()
    {
        db.collection("Tournament Info").document(TournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        tournamentInfo = documentSnapshot.toObject(TournamentInfo.class);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentThree.this));
                        adapter = new CreateTournamentThreeAdapterOne(CreateTournamentThree.this,tournamentInfo ,create_list() );
                        binding.recyclerView.setAdapter(adapter);
                    }
                });
    }

    public List<String> create_list ()
    {
        List<String> dates = new ArrayList<>();
        String date = tournamentInfo.getStart_date();
        dates.add(date);
        int a = OperationOnDate.number_of_days(tournamentInfo.getStart_date(),tournamentInfo.getEnd_date());
        for ( int i=1 ; i < a ; i++ )
        {
            date = OperationOnDate.date_increase(date);
            dates.add(date);
        }
        return dates;
    }

    public void save_all_data( View view ) {

        int no_of_days = OperationOnDate.number_of_days(tournamentInfo.getStart_date(),tournamentInfo.getEnd_date());
        int a = tournamentInfo.getNo_of_matches_day() * no_of_days;

        for ( int i =0 ; i < a ; i++ )
        {
            String s = "Match " + String.valueOf(i+1);
            Gson gson = new Gson();
            Log.d("hhhhhhhhhhhhhhhhhhh",sharedPreferences.getString(s,""));
            SingleMatchInfo singleMatchInfo = gson.fromJson( sharedPreferences.getString(s,"") , SingleMatchInfo.class);
            sharedPreferences.edit().remove(s).commit();
            if ( singleMatchInfo == null )
                binding.btnSubmit.setError("Set All Matches");
            singleMatchInfos.add(singleMatchInfo);
        }
        matchInfo.setTournamentId(tournamentInfo.getTournamentId());
        matchInfo.setMatchInfos(singleMatchInfos);
        db.collection("Match Info").document(tournamentInfo.getTournamentId()).set(matchInfo);
        singleMatchInfos.clear();
        Intent intent = new Intent( this , HomeActivity.class);
        startActivity(intent);
    }
}