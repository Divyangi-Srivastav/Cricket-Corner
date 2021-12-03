package com.example.livecricketapp.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivitySetStreamingCredentialBinding;
import com.example.livecricketapp.model.StreamingCred;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetStreamingCredential extends AppCompatActivity implements View.OnClickListener {

    private ActivitySetStreamingCredentialBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetStreamingCredentialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        binding.update.setOnClickListener(this);

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(SetStreamingCredential.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(SetStreamingCredential.this, SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(SetStreamingCredential.this, DashboardAdmin.class);
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

    public void clear(View view) {
        switch (view.getId()) {
            case R.id.token_cross:
                binding.token.getText().clear();
                break;
            case R.id.channel_name_cross:
                binding.channelName.getText().clear();
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.update) {
            StreamingCred cred = new StreamingCred();

            if ( check_empty() )
            {
                cred.setName(binding.channelName.getText().toString());
                cred.setId(binding.token.getText().toString());
                update_to_firestore(cred);
            }
        }
    }

    private Boolean check_empty() {
        if (binding.channelName.getText().toString().isEmpty()) {
            binding.channelName.setError("Required");
            return false;
        } else if (binding.token.getText().toString().isEmpty()) {
            binding.token.setError("Required");
            return false;
        } else {
            return true;
        }
    }

    private void update_to_firestore(StreamingCred cred) {
        db.collection("Cred").document("Streaming").set(cred);
        Toast.makeText(this, "Credential Updated Successfully", Toast.LENGTH_SHORT).show();
        binding.token.getText().clear();
        binding.channelName.getText().clear();
    }
}