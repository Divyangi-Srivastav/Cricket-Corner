package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.ParticipationFeesAdapter;
import com.example.livecricketapp.databinding.ActivityParticipationFeesBinding;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleTeamInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ParticipationFees extends AppCompatActivity {

    private ActivityParticipationFeesBinding binding;
    private ParticipationFeesAdapter adapter;
    private FirebaseFirestore db;
    private String tournamentId;
    private AllTeamInfo allTeamInfo;
    private List<SingleTeamInfo> infoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticipationFeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tournamentId = getIntent().getStringExtra("id");
        db = FirebaseFirestore.getInstance();
        get_data();

        String text = "<b>NOTE:</b> Team removed from here for not paying participation fees will be removed from the fixture and their matches will not be scheduled, i.e. <b>their participation from the tournament will be cancelled!</b>";
        binding.text.setText(Html.fromHtml(text));

        adapter = new ParticipationFeesAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(ParticipationFees.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(ParticipationFees.this , Dashboard.class);
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

    private void get_data()
    {
        db.collection("Tournament Team Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allTeamInfo = documentSnapshot.toObject(AllTeamInfo.class);
                        infoList = allTeamInfo.getTeamInfos();
                    }
                });
    }

}