package com.example.livecricketapp.admin.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.livecricketapp.R;
import com.example.livecricketapp.admin.adapters.CreateTournamentOneAdapter;
import com.example.livecricketapp.admin.adapters.CreateTournamentTimeAdapter;
import com.example.livecricketapp.databinding.ActivityCreateTournamentOneBinding;
import com.example.livecricketapp.model.TournamentInfo;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTournamentOne extends AppCompatActivity implements View.OnClickListener{

    private ActivityCreateTournamentOneBinding binding;
    private CreateTournamentOneAdapter createTournamentOneAdapter;
    private CreateTournamentTimeAdapter createTournamentTimeAdapter;
    private FirebaseFirestore db;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTournamentOneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();


        binding.btnSubmit.setOnClickListener(this::onClick);
        binding.startDateAndTime.setOnClickListener(this::onClick);
        binding.endDateAndTime.setOnClickListener(this::onClick);


        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {
                    case R.id.home:
                        Intent intent = new Intent(CreateTournamentOne.this , HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settings:
                        Intent intent2 = new Intent(CreateTournamentOne.this , SettingsAdmin.class);
                        startActivity(intent2);
                        break;

                    case R.id.account:
                        Intent intent1 = new Intent(CreateTournamentOne.this , DashboardAdmin.class);
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });

        binding.numberOfTeams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("") ) {
                    createTournamentOneAdapter = new CreateTournamentOneAdapter(CreateTournamentOne.this, Integer.parseInt(s.toString()));
                    binding.recyclerView.setAdapter(createTournamentOneAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
                } else {
                    createTournamentOneAdapter = new CreateTournamentOneAdapter(CreateTournamentOne.this, 0);
                    binding.recyclerView.setAdapter(createTournamentOneAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.numberOfMatches.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("") ) {
                    createTournamentTimeAdapter = new CreateTournamentTimeAdapter(CreateTournamentOne.this, Integer.parseInt(s.toString()));
                    binding.recyclerViewTime.setAdapter(createTournamentTimeAdapter);
                    binding.recyclerViewTime.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
                } else {
                    createTournamentTimeAdapter = new CreateTournamentTimeAdapter(CreateTournamentOne.this, 0);
                    binding.recyclerViewTime.setAdapter(createTournamentTimeAdapter);
                    binding.recyclerViewTime.setLayoutManager(new LinearLayoutManager(CreateTournamentOne.this));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void clear(View view) {
        switch (view.getId()) {

            case R.id.tournament_name_cross:
                binding.tournamentName.getText().clear();
                break;
            case R.id.number_of_teams_cross:
                binding.numberOfTeams.getText().clear();
                break;
            case R.id.number_of_overs_cross:
                binding.numberOfOvers.getText().clear();
                break;
            case R.id.fees_cross:
                binding.fees.getText().clear();
                break;
            case R.id.number_of_matches_cross:
                binding.numberOfMatches.getText().clear();
                break;
            case R.id.start_date_and_time_cross:
                binding.startDateAndTime.getText().clear();
                break;
            case R.id.end_date_and_time_cross:
                binding.endDateAndTime.getText().clear();
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_submit:{
                if (check_empty()) {
                    TournamentInfo tournamentInfo = new TournamentInfo();
                    tournamentInfo.setTournamentId(getIntent().getStringExtra("id"));
                    tournamentInfo.setTournamentName(binding.tournamentName.getText().toString());
                    tournamentInfo.setTotal_overs(Integer.parseInt(binding.numberOfOvers.getText().toString()));
                    tournamentInfo.setNumber_of_teams(Integer.parseInt(binding.numberOfTeams.getText().toString()));
                    tournamentInfo.setFees(Float.parseFloat(binding.fees.getText().toString()));
                    tournamentInfo.setNo_of_matches_day(Integer.parseInt(binding.numberOfMatches.getText().toString()));
                    tournamentInfo.setStart_date(binding.startDateAndTime.getText().toString());
                    tournamentInfo.setEnd_date(binding.endDateAndTime.getText().toString());
                    tournamentInfo.setTeamNames(createTournamentOneAdapter.get_list());
                    tournamentInfo.setMatchTimings(createTournamentTimeAdapter.getTimeList());
                    upload_to_firestore(tournamentInfo);

                    Intent intent = new Intent( CreateTournamentOne.this , CreateTournamentTwo.class );
                    intent.putExtra("info",tournamentInfo);
                    startActivity(intent);

                }
                break;
            }
            case R.id.start_date_and_time:

                DatePickerDialog.OnDateSetListener date2 = (view, year, month, day) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,day);
                    updateLabel(v);
                };
                new DatePickerDialog(this,date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.end_date_and_time:
                
                DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,day);
                    updateLabel(v);
                };
                new DatePickerDialog(this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }

    }
    private void updateLabel(View v){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        EditText et = (EditText) v;
        et.setText(dateFormat.format(myCalendar.getTime()));
    }


    public Boolean check_empty ()
    {
        if ( binding.tournamentName.getText().toString().isEmpty() )
        {
            binding.tournamentName.setError("Enter the tournament Name");
            return false;
        }
        else if ( binding.numberOfTeams.getText().toString().isEmpty() )
        {
            binding.numberOfTeams.setError("Enter the Number of teams");
            return false;
        }
        else if ( binding.numberOfOvers.getText().toString().isEmpty() )
        {
            binding.numberOfOvers.setError("Enter the Number of overs");
            return false;
        }
        else if ( binding.fees.getText().toString().isEmpty() )
        {
            binding.fees.setError("Enter the tournament Fees");
            return false;
        }
        else if ( binding.numberOfMatches.getText().toString().isEmpty() )
        {
            binding.numberOfMatches.setError("Enter the Number of Matches");
            return false;
        }
        else if ( binding.startDateAndTime.getText().toString().isEmpty() )
        {
            binding.startDateAndTime.setError("Enter the Start Date and Time");
            return false;
        }
        else if ( binding.endDateAndTime.getText().toString().isEmpty() )
        {
            binding.endDateAndTime.setError("Enter the Start Date and Time");
            return false;
        }
        else {
            return true;
        }
    }

    public void upload_to_firestore ( TournamentInfo tournamentInfo )
    {
        db.collection("Tournament Info").document(tournamentInfo.getTournamentId()).set(tournamentInfo);
    }

}