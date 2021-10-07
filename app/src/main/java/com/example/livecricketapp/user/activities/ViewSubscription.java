package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.DashboardAdmin;
import com.example.livecricketapp.databinding.ActivityViewSubscriptionBinding;
import com.example.livecricketapp.model.AllSubscriptions;
import com.example.livecricketapp.user.adapters.ViewSubscriptionAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewSubscription extends AppCompatActivity {

    private ActivityViewSubscriptionBinding binding;
    private ViewSubscriptionAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewSubscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        get_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(ViewSubscription.this , HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(ViewSubscription.this , SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(ViewSubscription.this , DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });
    }

    private void get_data ()
    {
        db.collection("Subscription")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        AllSubscriptions allSubscriptions = documentSnapshot.toObject(AllSubscriptions.class);
                        if ( allSubscriptions.getList().size() > 0 )
                        {
                            adapter = new ViewSubscriptionAdapter(ViewSubscription.this , allSubscriptions.getList());
                            binding.recyclerView.setAdapter(adapter);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(ViewSubscription.this));
                        }
                    }
                });
    }

    public void back(View view) {
        finish();
    }

}