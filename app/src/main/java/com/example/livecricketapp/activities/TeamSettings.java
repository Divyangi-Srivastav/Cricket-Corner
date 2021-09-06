package com.example.livecricketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityTeamSettingsBinding;
import com.example.livecricketapp.model.AllTeamInfo;
import com.example.livecricketapp.model.SingleTeamInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeamSettings extends AppCompatActivity {

    private ActivityTeamSettingsBinding binding;
    private FirebaseFirestore db;
    private String tournamentId = "1630656851257";
    private List<SingleTeamInfo> infoList = new ArrayList<>();
    private List<String> teamNames = new ArrayList<>();
    private AllTeamInfo allTeamInfo = new AllTeamInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //tournamentId = getIntent().getStringExtra("id");

        db  =  FirebaseFirestore.getInstance();
        get_data();

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, teamNames);
//        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < infoList.size(); i++) {
                    if (parent.getSelectedItem().toString().equalsIgnoreCase(infoList.get(i).getTeamName())) {
                        set_player_names(infoList.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void edit(View view) {

        switch (view.getId()) {
            case R.id.name_player1_edit:
                Toast.makeText(TeamSettings.this, "hhhhhhh", Toast.LENGTH_SHORT).show();
                binding.namePlayer1.setEnabled(true);
                binding.namePlayer1.setFocusable(true);
                binding.namePlayer1.setClickable(true);
                binding.namePlayer1.setFocusableInTouchMode(true);
                break;
            case R.id.name_player2_edit:
                binding.namePlayer2.setFocusable(true);
                binding.namePlayer2.setFocusable(true);
                binding.namePlayer2.setClickable(true);
                binding.namePlayer2.setFocusableInTouchMode(true);
                break;
            case R.id.name_player3_edit:
                binding.namePlayer3.setFocusable(true);
                binding.namePlayer3.setFocusable(true);
                binding.namePlayer3.setClickable(true);
                binding.namePlayer3.setFocusableInTouchMode(true);
                break;
            case R.id.name_player4_edit:
                binding.namePlayer4.setFocusable(true);
                binding.namePlayer4.setFocusable(true);
                binding.namePlayer4.setClickable(true);
                binding.namePlayer4.setFocusableInTouchMode(true);
                break;
            case R.id.name_player5_edit:
                binding.namePlayer5.setFocusable(true);
                binding.namePlayer5.setFocusable(true);
                binding.namePlayer5.setClickable(true);
                binding.namePlayer5.setFocusableInTouchMode(true);
                break;
            case R.id.name_player6_edit:
                binding.namePlayer6.setFocusable(true);
                binding.namePlayer6.setFocusable(true);
                binding.namePlayer6.setClickable(true);
                binding.namePlayer6.setFocusableInTouchMode(true);
                break;
            case R.id.name_player7_edit:
                binding.namePlayer7.setFocusable(true);
                binding.namePlayer7.setFocusable(true);
                binding.namePlayer7.setClickable(true);
                binding.namePlayer7.setFocusableInTouchMode(true);
                break;
            case R.id.name_player8_edit:
                binding.namePlayer8.setFocusable(true);
                binding.namePlayer8.setFocusable(true);
                binding.namePlayer8.setClickable(true);
                binding.namePlayer8.setFocusableInTouchMode(true);
                break;
            case R.id.name_player9_edit:
                binding.namePlayer9.setFocusable(true);
                binding.namePlayer9.setFocusable(true);
                binding.namePlayer9.setClickable(true);
                binding.namePlayer9.setFocusableInTouchMode(true);
                break;
            case R.id.name_player10_edit:
                binding.namePlayer10.setFocusable(true);
                binding.namePlayer10.setFocusable(true);
                binding.namePlayer10.setClickable(true);
                binding.namePlayer10.setFocusableInTouchMode(true);
                break;
            case R.id.name_player11_edit:
                binding.namePlayer11.setFocusable(true);
                binding.namePlayer11.setFocusable(true);
                binding.namePlayer11.setClickable(true);
                binding.namePlayer11.setFocusableInTouchMode(true);
                break;
        }

    }

    public void set_paid(View view) {

        for (int i = 0; i < infoList.size(); i++) {
            if (binding.spinner.getSelectedItem().toString().equalsIgnoreCase(infoList.get(i).getTeamName())) {
                infoList.get(i).setPaid(true);
            }
        }
        binding.paid.setBackgroundColor(Color.parseColor("#B9CCE2"));
        binding.unpaid.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    public void set_unpaid(View view) {

        for (int i = 0; i < infoList.size(); i++) {
            if (binding.spinner.getSelectedItem().toString().equalsIgnoreCase(infoList.get(i).getTeamName())) {
                infoList.get(i).setPaid(false);
            }
        }
        binding.unpaid.setBackgroundColor(Color.parseColor("#B9CCE2"));
        binding.paid.setBackgroundColor(Color.parseColor("#FFFFFF"));

    }

    public void back(View view) {
        finish();
    }

    public void get_data() {
        db.collection("Tournament Team Info").document(tournamentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allTeamInfo = documentSnapshot.toObject(AllTeamInfo.class);
                        infoList = allTeamInfo.getTeamInfos();
                        get_team_names();
                    }
                });
    }

    public void get_team_names() {
        for (int i = 0; i < infoList.size() ; i++) {
            teamNames.add(infoList.get(i).getTeamName());
        }
        set_data();
        set_player_names(infoList.get(0));
    }

    public void set_data() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, teamNames);
        binding.spinner.setAdapter(adapter);
    }

    public void set_player_names(SingleTeamInfo singleTeamInfo) {

        if (singleTeamInfo.getPlayerNames().get(0).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer1.setText(singleTeamInfo.getPlayerNames().get(0) + "( C )");
        else
            binding.namePlayer1.setText(singleTeamInfo.getPlayerNames().get(0));

        if (singleTeamInfo.getPlayerNames().get(1).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer2.setText(singleTeamInfo.getPlayerNames().get(1) + "( C )");
        else
            binding.namePlayer2.setText(singleTeamInfo.getPlayerNames().get(1));

        if (singleTeamInfo.getPlayerNames().get(2).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer3.setText(singleTeamInfo.getPlayerNames().get(2) + "( C )");
        else
            binding.namePlayer3.setText(singleTeamInfo.getPlayerNames().get(2));

        if (singleTeamInfo.getPlayerNames().get(3).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer4.setText(singleTeamInfo.getPlayerNames().get(3) + "( C )");
        else
            binding.namePlayer4.setText(singleTeamInfo.getPlayerNames().get(3));

        if (singleTeamInfo.getPlayerNames().get(4).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer5.setText(singleTeamInfo.getPlayerNames().get(4) + "( C )");
        else
            binding.namePlayer5.setText(singleTeamInfo.getPlayerNames().get(4));

        if (singleTeamInfo.getPlayerNames().get(5).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer6.setText(singleTeamInfo.getPlayerNames().get(5) + "( C )");
        else
            binding.namePlayer6.setText(singleTeamInfo.getPlayerNames().get(5));

        if (singleTeamInfo.getPlayerNames().get(6).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer7.setText(singleTeamInfo.getPlayerNames().get(6) + "( C )");
        else
            binding.namePlayer7.setText(singleTeamInfo.getPlayerNames().get(6));

        if (singleTeamInfo.getPlayerNames().get(7).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer8.setText(singleTeamInfo.getPlayerNames().get(7) + "( C )");
        else
            binding.namePlayer8.setText(singleTeamInfo.getPlayerNames().get(7));

        if (singleTeamInfo.getPlayerNames().get(8).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer9.setText(singleTeamInfo.getPlayerNames().get(8) + "( C )");
        else
            binding.namePlayer9.setText(singleTeamInfo.getPlayerNames().get(8));

        if (singleTeamInfo.getPlayerNames().get(9).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer10.setText(singleTeamInfo.getPlayerNames().get(9) + "( C )");
        else
            binding.namePlayer10.setText(singleTeamInfo.getPlayerNames().get(0));

        if (singleTeamInfo.getPlayerNames().get(10).equalsIgnoreCase(singleTeamInfo.getCaptainName()))
            binding.namePlayer11.setText(singleTeamInfo.getPlayerNames().get(10) + "( C )");
        else
            binding.namePlayer11.setText(singleTeamInfo.getPlayerNames().get(10));
    }

    public void save_changes ( View view )
    {
        allTeamInfo.setTeamInfos(infoList);
        db.collection("Tournament Team Info")
                .document(tournamentId)
                .set(allTeamInfo);
    }
}