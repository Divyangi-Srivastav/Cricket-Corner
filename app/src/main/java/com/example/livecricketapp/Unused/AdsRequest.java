package com.example.livecricketapp.Unused;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.admin.activities.DashboardAdmin;
import com.example.livecricketapp.admin.activities.HomeActivity;
import com.example.livecricketapp.admin.activities.SettingsAdmin;
import com.example.livecricketapp.admin.adapters.AdRequestsAdapter;
import com.example.livecricketapp.databinding.ActivityAdsRequestBinding;
import com.example.livecricketapp.model.AdBanner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdsRequest extends AppCompatActivity implements AdRequestsAdapter.On_Click {

    private ActivityAdsRequestBinding binding;
    private FirebaseFirestore db;
    private List<AdBanner> bannerList = new ArrayList<>();
    private AdRequestsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdsRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        get_data();

        adapter = new AdRequestsAdapter(this , bannerList , "admin" , this::change_status);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(AdsRequest.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(AdsRequest.this , SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(AdsRequest.this , DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    private void get_data(){

        db.collection("Ads")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful() )
                        {
                            for (QueryDocumentSnapshot snapshot : task.getResult())
                            {
                                AdBanner banner = snapshot.toObject(AdBanner.class);
                                bannerList.add(banner);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void change_status(int a) {

    }
}