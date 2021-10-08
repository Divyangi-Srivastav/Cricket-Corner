package com.example.livecricketapp.admin.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.SingleMatchInfo;
import com.example.livecricketapp.model.TeamScoreCard;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CreateTournamentThreeAdapterTwo extends RecyclerView.Adapter<CreateTournamentThreeAdapterTwo.CreateViewHolder> {

    private LayoutInflater layoutInflater;
    private int no_of_matches;
    private List<String> matchTimings;
    private List<String> teamNames;
    private Context context;
    private String date;
    private int a;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public CreateTournamentThreeAdapterTwo(Context context, int no_of_matches, List<String> matchTimings, List<String> teamNames, String date, int a) {
        this.layoutInflater = LayoutInflater.from(context);
        this.no_of_matches = no_of_matches;
        this.matchTimings = matchTimings;
        this.context = context;
        this.teamNames = teamNames;
        this.date = date;
        this.a = a;
        sharedPreferences = context.getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @NotNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view_matches_two, parent, false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CreateTournamentThreeAdapterTwo.CreateViewHolder holder, int position) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, teamNames);
        holder.spinner1.setAdapter(adapter);
        holder.spinner2.setAdapter(adapter);
        holder.textView.setText("Match " + String.valueOf((a * no_of_matches) + position + 1) + " | " + date + " | " + matchTimings.get(position));
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button1.setVisibility(View.GONE);
                SingleMatchInfo singleMatchInfo = new SingleMatchInfo();
                singleMatchInfo.setDate(date);
                singleMatchInfo.setMatchNo("Match " + String.valueOf((a * no_of_matches) + holder.getAdapterPosition() + 1));

                TeamScoreCard teamScoreCard = new TeamScoreCard();
                teamScoreCard.setTeamName(holder.spinner1.getSelectedItem().toString());
                singleMatchInfo.setTeam1Score(teamScoreCard);
                teamScoreCard = new TeamScoreCard();
                teamScoreCard.setTeamName(holder.spinner2.getSelectedItem().toString());
                singleMatchInfo.setTeam2Score(teamScoreCard);
                singleMatchInfo.setTime(matchTimings.get(holder.getAdapterPosition()));
                Gson gson = new Gson();
                String jsonString = gson.toJson(singleMatchInfo);
                editor.putString(singleMatchInfo.getMatchNo(), jsonString);
                editor.commit();
            }
        });

//        holder.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                SingleMatchInfo singleMatchInfo = new SingleMatchInfo();
//                singleMatchInfo.setDate(date);
//                singleMatchInfo.setMatchNo("Match " + String.valueOf((a * no_of_matches) + position +1 ));
//                singleMatchInfo.setTeam1(holder.spinner1.getSelectedItem().toString());
//                singleMatchInfo.setTeam2(holder.spinner2.getSelectedItem().toString());
//                singleMatchInfo.setTime(matchTimings.get(position));
//                Gson gson = new Gson();
//                String jsonString = gson.toJson(singleMatchInfo);
//                editor.putString(singleMatchInfo.getMatchNo(),jsonString);
//                editor.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        holder.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                SingleMatchInfo singleMatchInfo = new SingleMatchInfo();
//                singleMatchInfo.setDate(date);
//                singleMatchInfo.setMatchNo("Match " + String.valueOf((a * no_of_matches) + position +1 ));
//                singleMatchInfo.setTeam1(holder.spinner1.getSelectedItem().toString());
//                singleMatchInfo.setTeam2(holder.spinner2.getSelectedItem().toString());
//                singleMatchInfo.setTime(matchTimings.get(position));
//                Gson gson = new Gson();
//                String jsonString = gson.toJson(singleMatchInfo);
//                editor.putString(singleMatchInfo.getMatchNo(),jsonString);
//                editor.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return no_of_matches;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder {

        private Spinner spinner1, spinner2;
        private TextView textView;
        private Button button1;

        public CreateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.match);
            spinner1 = itemView.findViewById(R.id.spinner1);
            spinner2 = itemView.findViewById(R.id.spinner2);
            button1 = itemView.findViewById(R.id.save_time);
        }
    }


}
