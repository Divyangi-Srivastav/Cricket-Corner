package com.example.livecricketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.livecricketapp.DataOperations.OperationOnDate;
import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.HostAMatchTourAdapter;
import com.example.livecricketapp.databinding.ActivityHostMatchMainBinding;
import com.example.livecricketapp.model.TournamentInfo;
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

public class HostMatchMainActivity extends AppCompatActivity implements HostAMatchTourAdapter.On_Click {

    private ActivityHostMatchMainBinding binding;
    private FirebaseFirestore db;
    private String date;
    private List<TournamentInfo> infoList = new ArrayList<>();
    private HostAMatchTourAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostMatchMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        get_tournament_info();
        adapter = new HostAMatchTourAdapter(this, infoList,this::select_tour);
        binding.recyclerViewTour.setAdapter(adapter);
        binding.recyclerViewTour.setLayoutManager(new LinearLayoutManager(this));

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(HostMatchMainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(HostMatchMainActivity.this, Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    private void get_tournament_info() {
        db.collection("Tournament Info").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful())
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                TournamentInfo info = snapshot.toObject(TournamentInfo.class);
                                if (info != null)
                                    if (check_ongoing(info)) {
                                        infoList.add(info);
                                        adapter.notifyDataSetChanged();
                                    }
                            }
                    }
                });
    }

    private Boolean check_ongoing(TournamentInfo info) {
        if (info.getStart_date() != null && info.getEnd_date() != null) {
            int days1 = OperationOnDate.number_of_days(info.getStart_date(), date);
            int days2 = OperationOnDate.number_of_days(info.getStart_date(), info.getEnd_date());

            if (days1 <= days2 && days1 >= 1)
                return true;
        }
        return false;
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void select_tour(int a) {

    }
}