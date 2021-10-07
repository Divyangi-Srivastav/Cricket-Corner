package com.example.livecricketapp.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.livecricketapp.DataOperations.OperationOnDate;
import com.example.livecricketapp.R;
import com.example.livecricketapp.activities.DashboardAdmin;
import com.example.livecricketapp.databinding.ActivityBuySubscriptionBinding;
import com.example.livecricketapp.model.SingleMatchInfo;
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

public class BuySubscription extends AppCompatActivity implements View.OnClickListener{

    private ActivityBuySubscriptionBinding binding;
    private FirebaseFirestore db;
    private String date;
    private List<String> tournamentList = new ArrayList<>();
    private List<TournamentInfo> tournamentInfoList = new ArrayList<>();
    private SingleMatchInfo singleMatchInfo = new SingleMatchInfo();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuySubscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        singleMatchInfo = (SingleMatchInfo) getIntent().getSerializableExtra("match");

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        db = FirebaseFirestore.getInstance();

        get_tour_data();

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(BuySubscription.this , HomeActivityUser.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(BuySubscription.this , SettingsUser.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(BuySubscription.this , DashboardUser.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        binding.matchOnlyPay.setOnClickListener(this::onClick);
        binding.tournamentOnlyPay.setOnClickListener(this::onClick);

    }

    private void get_tour_data ()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        db.collection("Tournament Info")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful() )
                        {
                            for (QueryDocumentSnapshot snapshot : task.getResult())
                            {
                                TournamentInfo tournamentInfo = snapshot.toObject(TournamentInfo.class);
                                tournamentInfoList.add(tournamentInfo);
                                if (check_ongoing(tournamentInfo))
                                {
                                    tournamentList.add(tournamentInfo.getTournamentName());
                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BuySubscription.this, R.layout.support_simple_spinner_dropdown_item, tournamentList);
                            binding.spinner.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    }
                });
    }

    private Boolean check_ongoing ( TournamentInfo info )
    {
        int days1 = OperationOnDate.number_of_days(info.getStart_date(),date);
        int days2 = OperationOnDate.number_of_days(info.getStart_date(),info.getEnd_date());

        if ( days1 < 1 )
        {
            return false ;
        }
        else if ( days1 <= days2 )
        {
            return  true;
        }
        else
        {
            return false ;
        }

    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.match_only_pay:
                TournamentInfo info1 = get_tournament(binding.spinner.getSelectedItem().toString());
                Intent intent1 = new Intent(this,PaymentActivity.class);
                intent1.putExtra("activity","match_subscription");
                intent1.putExtra("tour",info1);
                intent1.putExtra("amount",10);
                intent1.putExtra("match",singleMatchInfo);
                startActivity(intent1);
                break;
            case R.id.tournament_only_pay:
                TournamentInfo info = get_tournament(binding.spinner.getSelectedItem().toString());
                Intent intent = new Intent(this,PaymentActivity.class);
                intent.putExtra("activity","tour_subscription");
                intent.putExtra("tour",info);
                intent.putExtra("amount",70);
                startActivity(intent);
                break;
        }

    }

    private TournamentInfo get_tournament( String name )
    {
        for ( int i=0 ; i < tournamentInfoList.size() ; i++ )
        {
            if ( tournamentInfoList.get(i).getTournamentName().equalsIgnoreCase(name) )
                return tournamentInfoList.get(i);
        }
        return null;
    }
}