package com.example.livecricketapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.DataOperations.OperationOnDate;
import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.DisplayRewardsAdapter;
import com.example.livecricketapp.databinding.ActivityDisplayRewardBinding;
import com.example.livecricketapp.model.Reward;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DisplayReward extends AppCompatActivity {

    private ActivityDisplayRewardBinding binding;
    private FirebaseFirestore db;
    private List<Reward> rewards = new ArrayList<>();
    private List<String> tournamentList = new ArrayList<>();
    private List<TournamentInfo> tournamentInfoList = new ArrayList<>();
    private List<String> matchList = new ArrayList<>();
    private DisplayRewardsAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayRewardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait ...");

        db = FirebaseFirestore.getInstance();

        get_tournaments();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(DisplayReward.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(DisplayReward.this, Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });


        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                get_match_list(get_match_number(binding.spinner1.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dialog.show();
                get_rewards(binding.spinner1.getSelectedItem().toString() , binding.spinner2.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new DisplayRewardsAdapter(this, rewards);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void get_rewards(String tour_name, String match_no) {

        rewards.clear();

        db.collection("reward")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Reward reward = snapshot.toObject(Reward.class);
                                if (reward.getTournamentName().equalsIgnoreCase(tour_name))
                                    if (reward.getMatchId().equalsIgnoreCase(match_no))
                                        rewards.add(reward);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public void back(View view) {
        finish();
    }

    private void get_tournaments() {
        db.collection("Tournament Info")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                TournamentInfo info = snapshot.toObject(TournamentInfo.class);
                                tournamentInfoList.add(info);
                                tournamentList.add(info.getTournamentName());
                            }
                        }
                        set_tournament_spinner();
                    }
                });
    }

    private void set_tournament_spinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tournamentList);
        binding.spinner1.setAdapter(adapter);
    }

    private int get_match_number(String name) {
        for (int i = 0; i < tournamentInfoList.size(); i++) {
            if (name.equalsIgnoreCase(tournamentInfoList.get(i).getTournamentName())) {
                TournamentInfo info = tournamentInfoList.get(i);
                int a = OperationOnDate.number_of_days(info.getStart_date(), info.getEnd_date()) * info.getNo_of_matches_day();
                return a;
            }
        }
        return 0;
    }

    private void get_match_list(int a) {
        matchList.clear();
        for (int i = 0; i < a; i++) {
            matchList.add("Match " + String.valueOf(i + 1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, matchList);
        binding.spinner2.setAdapter(adapter);
    }

}