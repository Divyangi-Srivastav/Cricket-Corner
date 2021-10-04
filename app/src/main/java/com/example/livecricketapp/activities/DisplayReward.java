package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.adapters.DisplayRewardsAdapter;
import com.example.livecricketapp.databinding.ActivityDisplayRewardBinding;
import com.example.livecricketapp.model.Reward;
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
    private DisplayRewardsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayRewardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        get_rewards();
        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(DisplayReward.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:

                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(DisplayReward.this , Dashboard.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        adapter = new DisplayRewardsAdapter(this, rewards);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void get_rewards()
    {
        db.collection("reward")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful() )
                        {
                            for (QueryDocumentSnapshot snapshot : task.getResult())
                            {
                                Reward reward = snapshot.toObject(Reward.class);
                                rewards.add(reward);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public void back(View view) {
        finish();
    }

}