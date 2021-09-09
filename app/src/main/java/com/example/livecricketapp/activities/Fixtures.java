package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.FixturesAdapters;
import com.example.livecricketapp.databinding.ActivityFixturesBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Fixtures extends AppCompatActivity implements FixturesAdapters.On_click {

    private ActivityFixturesBinding binding;
    private List<SingleMatchInfo> singleMatchInfos = new ArrayList<>();
    private String tournamentId = "";
    private FixturesAdapters adapters;
    private FirebaseFirestore db;
    private AllMatchInfo allMatchInfo = new AllMatchInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFixturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        get_data();

        adapters = new FixturesAdapters(this , singleMatchInfos , this::move_to_match_settings);

        binding.recyclerView.setAdapter(adapters);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tournamentId = getIntent().getStringExtra("id");

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(Fixtures.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(Fixtures.this , Dashboard.class);
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

    public void get_data()
    {
        db.collection("Tournament Info").document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allMatchInfo = documentSnapshot.toObject(AllMatchInfo.class);
                        singleMatchInfos = allMatchInfo.getMatchInfos();
                        adapters.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void move_to_match_settings(int a) {
        Intent intent = new Intent(this, FixtuesMatchSettings.class);
        intent.putExtra("match",singleMatchInfos.get(a));
        startActivity(intent);
    }
}