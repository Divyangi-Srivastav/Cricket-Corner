package com.example.livecricketapp.user.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.livecricketapp.databinding.ActivityDashboardUserBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardUser extends AppCompatActivity {

    private ActivityDashboardUserBinding binding;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference("users");

    }

    public void edit_content ( View view )
    {

    }

    private void get_user ()
    {

    }


}