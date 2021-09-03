package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.DashboardAdapter;
import com.example.livecricketapp.databinding.ActivityDashboardBinding;
import com.example.livecricketapp.model.DashboardTournamentInfo;
import com.example.livecricketapp.model.TournamentHostInfo;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.connection.HostInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements DashboardAdapter.Move_Next {

    private ActivityDashboardBinding binding;
    private DashboardAdapter dashboardAdapter;
    private ChildEventListener childEventListener;
    private DatabaseReference databaseReference;
    private List<DashboardTournamentInfo> info = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dashboardAdapter = new DashboardAdapter(this,this::move_to_other_activity,info);
        binding.recyclerView.setAdapter(dashboardAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("Tournament Host Info");
        db = FirebaseFirestore.getInstance();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                TournamentHostInfo hostInfo = snapshot.getValue(TournamentHostInfo.class);
                DashboardTournamentInfo info1 = new DashboardTournamentInfo();
                info1.setTournamentId(hostInfo.getTournamentId());
                info1.setHostName(hostInfo.getHostName());
                update_further(info1);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);
    }

    private void update_further( DashboardTournamentInfo info1 ) {

        db.collection("Tournament Info").document(info1.getTournamentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        TournamentInfo tournamentInfo = documentSnapshot.toObject(TournamentInfo.class);
                        info1.setTournamentName(tournamentInfo.getTournamentName());
                        info.add(info1);
                        dashboardAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void back(View view) {
        finish();
    }

    public void edit_content ( View view )
    {

    }

    @Override
    public void move_to_other_activity(int a) {
        Intent intent = new Intent(this,TournamentSettings.class);
        startActivity(intent);
    }
}