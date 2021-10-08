package com.example.livecricketapp.admin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityMatchEndResultBinding;
import com.example.livecricketapp.model.AllMatchInfo;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MatchEndResult extends AppCompatActivity {

    private ActivityMatchEndResultBinding binding;
    private FirebaseFirestore db;
    private String tournamentId;
    private String matchNo;
    private AllMatchInfo allMatchInfo;
    private SingleMatchInfo singleMatchInfo;
    private ProgressDialog dialog;
    private int a =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchEndResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        singleMatchInfo = new SingleMatchInfo();
        allMatchInfo = new AllMatchInfo();

        db = FirebaseFirestore.getInstance();

        tournamentId = getIntent().getStringExtra("tour");
        matchNo = getIntent().getStringExtra("match");

        get_single_match_info();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(MatchEndResult.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(MatchEndResult.this, SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(MatchEndResult.this, DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

    }

    private void get_single_match_info() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Preparing the Score Board");
        dialog.show();
        db.collection("Match Info")
                .document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allMatchInfo = documentSnapshot.toObject(AllMatchInfo.class);
                        for (int i = 0; i < allMatchInfo.getMatchInfos().size(); i++) {
                            if (allMatchInfo.getMatchInfos().get(i).getMatchNo().equalsIgnoreCase(matchNo)) {
                                singleMatchInfo = allMatchInfo.getMatchInfos().get(i);
                                allMatchInfo.getMatchInfos().remove(i);
                                a = i;
                                dialog.dismiss();
                            }
                            dialog.dismiss();
                        }
                    }
                });
    }

    public void declare_result ( View view )
    {
        if ( ! binding.matchResult.getText().toString().isEmpty() )
        {
            singleMatchInfo.setMatchResult(binding.matchResult.getText().toString());
            singleMatchInfo.setMatchStatus(2);
            List<SingleMatchInfo> matchInfos = new ArrayList<>();
            matchInfos = allMatchInfo.getMatchInfos();
            matchInfos.add(a,singleMatchInfo);
            allMatchInfo.setMatchInfos(matchInfos);
            db.collection("Match Info").document(tournamentId).set(allMatchInfo);
            Toast.makeText(this, "Match Result Declared Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void back(View view) {
        finish();
    }

}