package com.example.livecricketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.livecricketapp.adapters.CreateTournamentThreeAdapterOne;
import com.example.livecricketapp.adapters.CreateTournamentThreeAdapterTwo;
import com.example.livecricketapp.databinding.ActivityCreateTournamentThreeBinding;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class CreateTournamentThree extends AppCompatActivity  implements CreateTournamentThreeAdapterOne.Save_data {

   private ActivityCreateTournamentThreeBinding binding;
   private CreateTournamentThreeAdapterOne adapter;
   private FirebaseFirestore db;
   private String TournamentId = "1630576702102";
   private TournamentInfo tournamentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentThreeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        get_data();
        tournamentInfo = new TournamentInfo();
    }

    public void back(View view) {
        finish();
    }

    public void get_data ()
    {
        db.collection("Tournament Info").document(TournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        tournamentInfo = documentSnapshot.toObject(TournamentInfo.class);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentThree.this));
                        adapter = new CreateTournamentThreeAdapterOne(CreateTournamentThree.this,tournamentInfo ,CreateTournamentThree.this::save_all_data);
                        binding.recyclerView.setAdapter(adapter);
                        Toast.makeText(CreateTournamentThree.this, "hhhhhh", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void save_all_data() {

    }
}